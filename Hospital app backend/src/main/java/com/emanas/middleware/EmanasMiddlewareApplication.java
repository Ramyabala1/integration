package com.emanas.middleware;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.emanas.middleware.filters.JwtRequestFilter;

import com.google.common.collect.ImmutableList;


//@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
@EnableCaching
@SpringBootApplication
@EnableAutoConfiguration

public class EmanasMiddlewareApplication extends SpringBootServletInitializer  {

	public static void main(String[] args) {
	
	SpringApplication.run(EmanasMiddlewareApplication.class, args);
		
		
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(EmanasMiddlewareApplication.class);
    }
	
	@Bean
	JedisConnectionFactory jedisConnectionFactory(Environment env) {

	    RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
	    redisStandaloneConfiguration.setHostName("127.0.0.1");
	    redisStandaloneConfiguration.setPort(6379);
	    return new JedisConnectionFactory(redisStandaloneConfiguration);

	}

	@Bean
	RedisTemplate<String, Object> redisTemplate(Environment env) {
		JdkSerializationRedisSerializer jackson2JsonRedisSerializer = new JdkSerializationRedisSerializer();
		  
	    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
	    redisTemplate.setConnectionFactory(jedisConnectionFactory(env));
	    redisTemplate.setKeySerializer(new StringRedisSerializer());
	    redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
	    redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);
	    redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
	    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
	   
	    return redisTemplate;

	}
	


	

@Bean
   public RestTemplate getRestTemplate() {
      return new RestTemplate();
   }






}





@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	@Bean
    public JwtRequestFilter JwtRequestFilter() {
        return new JwtRequestFilter();
    }
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	  private static final String[] AUTH_WHITELIST = {
	            // -- Swagger UI v2
	            "/v2/api-docs",
	            "/swagger-resources",
	            "/swagger-resources/**",
	            "/configuration/ui",
	            "/configuration/security",
	            "/swagger-ui.html",
	            "/webjars/**",
	            // -- Swagger UI v3 (OpenAPI)
	            "/v3/api-docs/**",
	            "/swagger-ui/**",
	            "/fhir/hiu/authenticate",
	            "/patientapp/getToken"
	            // other public endpoints of your API may be appended to this array
	    };

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.cors().and().csrf().disable()
				.authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll().
						anyRequest().authenticated().and().
						exceptionHandling().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		httpSecurity.addFilterBefore(JwtRequestFilter(),  UsernamePasswordAuthenticationFilter.class);

	}
	
	  @Bean
	    public CorsConfigurationSource corsConfigurationSource() {
	        final CorsConfiguration configuration = new CorsConfiguration();
	        configuration.setAllowedOrigins(Arrays.asList("*"));
	       
	        configuration.setAllowedMethods(ImmutableList.of("GET", "PUT", "POST", "DELETE",  "OPTION"));
	        
	        configuration.setAllowedHeaders(ImmutableList.of("Content-Type","Access-Control-Allow-Origin","Authorization" 
	        		,"Access-Control-Allow-Headers","Access-Control-Allow-Methods"));
	        configuration.addAllowedOrigin("*");
	        configuration.addAllowedHeader("*");
	        configuration.addAllowedMethod("*");
	        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", configuration);
	        return source;
	    }

}
