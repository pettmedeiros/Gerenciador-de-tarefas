package com.peterson.gerenciador_tarefas.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.peterson.gerenciador_tarefas.repository.UsuarioRepository;

@Configuration
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final AuthenticationConfiguration authenticationConfiguration;

    public SecurityConfig(JwtUtil jwtUtil, AuthenticationConfiguration authenticationConfiguration) {
        this.jwtUtil = jwtUtil;
        this.authenticationConfiguration = authenticationConfiguration;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManager authManager = authenticationConfiguration.getAuthenticationManager();

        JwtAuthenticationFilter authFilter = new JwtAuthenticationFilter(authManager, jwtUtil);
        authFilter.setFilterProcessesUrl("/api/usuarios/login");

        JwtAuthorizationFilter authorizationFilter = new JwtAuthorizationFilter(authManager, jwtUtil);

        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/", 
                    "/index.html", 
                    "/script.js", 
                    "/style.css",
                    "/api/usuarios/cadastrar", 
                    "/api/usuarios/login"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ESSA É A FORMA CORRETA EM 2025 (SPRING BOOT 3.5+)
    @Bean
    public UserDetailsService userDetailsService(UsuarioRepository usuarioRepository) {
        return email -> usuarioRepository.findByEmail(email)
            .map(usuario -> org.springframework.security.core.userdetails.User
                .withUsername(usuario.getEmail())
                .password(usuario.getSenha())
                .roles("USER")
                .build()
            )
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));
    }

    // AQUI ESTÁ O SEGREDO: O AuthenticationManager É CONFIGURADO AUTOMATICAMENTE
    // SÓ POR TER O UserDetailsService COMO @Bean
    // NÃO PRECISA DE NENHUM authenticationManager() EXTRA!
    // O Spring faz tudo sozinho
}