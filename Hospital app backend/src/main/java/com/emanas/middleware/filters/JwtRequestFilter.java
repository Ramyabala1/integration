package com.emanas.middleware.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import com.emanas.middleware.config.ConfigurationUrl;
import com.emanas.middleware.models.HiuUser;
import com.emanas.middleware.utility.JwtUtil;
import com.emanas.middleware.utility.LoadConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	ConfigurationUrl configUrl;

	@Bean
	private JwtUtil jwtUtil() {
		return new JwtUtil();
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		final String authorizationHeader = request.getHeader("Authorization");

		final String ipHeader = request.getHeader("X-Forwarded-Host");

		String username = null;
		String jwt = null;
		HiuUser hiUser = new HiuUser();
		HiuUser hiu = new HiuUser();
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")
				&& SecurityContextHolder.getContext().getAuthentication() == null) {
			jwt = authorizationHeader.substring(7);
			try {
				Claims c = jwtUtil().extractAllClaims(jwt);
			} catch (SignatureException e) {
				throw new SignatureException("Invalid Token");
			}

			username = jwtUtil().extractUsername(jwt);
			if (username != null) {
				hiu.setHiuSessionToken(jwt);
				hiu.setUserName(username);
				try {
					hiUser = restTemplate.postForObject(LoadConfig.getConfigValue("GETUSERSESSION"), hiu,
							HiuUser.class);
				} catch (RestClientException e) {
					// TODO Auto-generated catch block
					throw new BadCredentialsException("Connection timeout");
				}
			} else {
				throw new BadCredentialsException("Access denied");
			}
		}

		if (authorizationHeader != null && hiUser != null
				&& SecurityContextHolder.getContext().getAuthentication() == null) {

			request.getSession().setAttribute("Hiu", hiUser);

			if (jwtUtil().validateToken(jwt, hiUser) && checkWhiteIP(ipHeader)) {
				if (jwtUtil().validateToken(jwt, hiUser)) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							hiUser, null, null);
					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

				} else {
					throw new BadCredentialsException("Invalid Token");
				}
			}
		}

		chain.doFilter(request, response);

	}

	public Boolean checkWhiteIP(String ipValue) {
		String validUserUrl = LoadConfig.getConfigValue("VERIFYIP") + ipValue;
		String response = restTemplate.getForObject(validUserUrl, String.class);
		if (response.equalsIgnoreCase("true"))
			return true;
		else
			return false;

	}

}
