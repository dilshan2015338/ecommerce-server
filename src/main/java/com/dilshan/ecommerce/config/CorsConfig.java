package com.dilshan.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        
        // Allow specific origins (for development)
        config.setAllowedOrigins(Arrays.asList(
            "http://localhost:3000",   // React default
            "http://localhost:5173",   // Vite default
            "http://127.0.0.1:3000",
            "http://127.0.0.1:5173"
        ));
        
        // Allow all HTTP methods
        config.setAllowedMethods(Arrays.asList(
            "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"
        ));
        
        // Allow all headers
        config.setAllowedHeaders(Arrays.asList(
            "Authorization",
            "Content-Type",
            "Accept",
            "X-Requested-With",
            "Origin",
            "Access-Control-Request-Method",
            "Access-Control-Request-Headers"
        ));
        
        // Allow credentials (cookies, authorization headers)
        config.setAllowCredentials(true);
        
        // Cache CORS response for 1 hour
        config.setMaxAge(3600L);
        
        // Expose custom headers to the frontend
        config.setExposedHeaders(Arrays.asList(
            "Authorization",
            "X-Total-Count",
            "X-Page-Total"
        ));
        
        // Apply configuration to all endpoints
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        
        return new CorsFilter(source);
    }
}