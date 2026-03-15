package com.gokul.vendor_management.config;

import com.gokul.vendor_management.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // PUBLIC
                        .requestMatchers("/auth/**").permitAll()

                        // VENDOR
                        .requestMatchers(HttpMethod.POST, "/vendors").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/vendors/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/vendors/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/vendors/**").hasAnyRole("ADMIN", "AGENT")

                        // CUSTOMER
                        .requestMatchers(HttpMethod.POST, "/customers").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/customers/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/customers/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/customers/**").hasAnyRole("ADMIN", "AGENT")

                        // AGENT MANAGEMENT
                        .requestMatchers(HttpMethod.POST, "/agents").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/agents/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/agents/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/agents/**").hasRole("ADMIN")

                        // SUBSCRIPTION
                        .requestMatchers(HttpMethod.POST, "/subscriptions").hasAnyRole("ADMIN", "AGENT")
                        .requestMatchers(HttpMethod.PUT, "/subscriptions/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/subscriptions/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/subscriptions/**").hasAnyRole("ADMIN", "AGENT")

                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(
            JwtUtil jwtUtil,
            UserDetailsService userDetailsService) {

        return new JwtAuthenticationFilter(jwtUtil, userDetailsService);
    }

    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails admin = User.builder()
                .username("Gokul")
                .password(passwordEncoder().encode("Gokul@2003"))
                .roles("ADMIN")
                .build();

        UserDetails agent = User.builder()
                .username("Agent1")
                .password(passwordEncoder().encode("Agent@123"))
                .roles("AGENT")
                .build();

        return new InMemoryUserDetailsManager(admin, agent);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}