//package com.project.config;
//
//import com.example.Uber.security.JwtAuthFilter;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//@EnableMethodSecurity(securedEnabled = true)
//
//public class WebSecurityConfig {
//
//    private final JwtAuthFilter jwtAuthFilter;
//    private final String[] PUBLIC_ROUTES={"/auth/**"};
//
//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
//
//        httpSecurity
//                .sessionManagement(sessionconfig->sessionconfig.
//                        sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .csrf(csrfConfig->csrfConfig.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(PUBLIC_ROUTES).permitAll()
//                        .anyRequest().authenticated()
//                ).addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return httpSecurity.build();

package com.project.config;

import com.project.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final String[] PUBLIC_ROUTES = {"/auth/**"};

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .sessionManagement(sessionConfig -> sessionConfig
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrfConfig -> csrfConfig.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_ROUTES).permitAll()
                        .requestMatchers("/admin/**").permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}

