package com.emanas.middleware.utility.security;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.emanas.middleware.models.HiuUser;

@Service
public class SecurityService {

	public HttpHeaders checkSecurity() {

		HiuUser hiu = new HiuUser();
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		hiu = (HiuUser) authentication.getPrincipal();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + hiu.getMheAccessToken());
		headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
		return headers;

	}

	public HiuUser getHIU() {

		HiuUser hiu = new HiuUser();
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		hiu = (HiuUser) authentication.getPrincipal();

		return hiu;

	}

}
