package com.example.app_sus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService; // Importe seu UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1. INJETAR O UserDetailsService (que o Spring entenderá como seu UsuarioService)
    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. DEFINIR O DaoAuthenticationProvider Bean
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService); // Usa seu UsuarioService
        authProvider.setPasswordEncoder(passwordEncoder());     // Usa seu PasswordEncoder
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(
                    "/css/**", 
                    "/js/**", 
                    "/images/**", 
                    "/login",
                    "/h2-console/**"
                ).permitAll()
                // Regras mais genéricas por enquanto:
                .requestMatchers("/api/admin/**").hasRole("ADMIN") // Exemplo se você tiver rotas de admin
                .requestMatchers("/api/**").authenticated() // Protege todas as outras APIs
                .anyRequest().authenticated() // Todas as outras requisições de view exigem autenticação
            )
            .formLogin(form -> form
                .loginPage("/login")         
                .loginProcessingUrl("/login") 
                .usernameParameter("username") 
                .passwordParameter("password") 
                .defaultSuccessUrl("/", true) 
                .failureUrl("/login?error=true") 
                .permitAll()                 
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) 
                .logoutSuccessUrl("/login?logout") 
                .permitAll()
            )
            .csrf(csrf -> csrf
                .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**"))
            )
            .headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions.sameOrigin())
            );
        
        // O Spring Security usará automaticamente o DaoAuthenticationProvider que definimos como bean
        // para o processo de autenticação.
        return http.build();
    }
}