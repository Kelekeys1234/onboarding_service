package com.calvary.onboarding.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

	  @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**")
	        .allowedOriginPatterns("*") // Use allowedOriginPatterns instead of allowedOrigins
	                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
	                .allowedHeaders("*");
//	                .allowCredentials(true);
	    }
//	@Bean
//	public CorsFilter corsFilter() {
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		CorsConfiguration config = new CorsConfiguration();
//		config.addAllowedOrigin("*"); // Allow requests from any origin
//		config.addAllowedMethod("*"); // Allow all HTTP methods
//		config.addAllowedHeader("*"); // Allow all headers
//		source.registerCorsConfiguration("/**", config);
//		return new CorsFilter(source);
//	}

}