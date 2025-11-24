package com.project1.project1.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



//l'iplementation de WebMvcConfigurer pour configurer CORS

@Configuration
public class CorsConfig  {
     @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // s’applique à TOUTES les routes
                .allowedOrigins("http://localhost:8080", "http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
};
}
}