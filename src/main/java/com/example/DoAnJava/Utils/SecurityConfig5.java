package com.example.DoAnJava.Utils;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig5 {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request->request
                        .anyRequest()
                        .authenticated())
                .oauth2Login(oauth2Login ->
                        oauth2Login.loginPage("https://shoptopping-89b153dfa8dc.herokuapp.com/login/oauth2/code/google") // Specify your custom login page here
                                .defaultSuccessUrl("/") // Change to your desired landing page after successful login
                );
        return http.build();
    }
}
