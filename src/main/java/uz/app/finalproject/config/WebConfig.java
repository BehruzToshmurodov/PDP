package uz.app.finalproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(c -> c.disable())
                .cors(c -> c.configurationSource(corsConfigurationSource()))
                .userDetailsService(userDetailsService())
                .authorizeRequests(auth -> auth.anyRequest().permitAll())
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return (username) -> {
            return null;
        };
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedHeaders(List.of("*")); // Allow all headers
        config.setAllowedMethods(List.of("GET", "PUT", "POST", "DELETE", "OPTIONS" , "PATCH")); // Allow all standard HTTP methods
        config.setAllowedOriginPatterns(List.of(
                "http://localhost:8080", // For local development
                "https://etadoor.koyeb.app", // Your app's Koyeb domain
                "https://anonymous-octopus-pdpteam-487d0d53.koyeb.app" // Swagger UI domain
        ));
        config.setAllowCredentials(true); // Allow cookies or credentials
        config.setExposedHeaders(List.of("Authorization", "Content-Type")); // Optional: if you need these exposed
        source.registerCorsConfiguration("/**", config);
        return source;
    }


}
