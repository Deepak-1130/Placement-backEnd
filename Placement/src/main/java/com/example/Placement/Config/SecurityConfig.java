package com.example.Placement.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.Placement.utils.JwtFilter;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() throws Exception{
        return new BCryptPasswordEncoder();
    }
    @Bean
   public SecurityFilterChain filterChain(HttpSecurity http,JwtFilter jwtFilter)throws Exception{
    http
          .csrf(AbstractHttpConfigurer::disable)
          .cors(cors->{})
          .authorizeHttpRequests((auth)->auth
                .requestMatchers("/login", "/addStudent").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class);
    return http.build();
   }
    
}
