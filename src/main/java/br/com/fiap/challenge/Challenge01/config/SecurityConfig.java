package br.com.fiap.challenge.Challenge01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/login", "/", "/login/**").permitAll();
                    auth.requestMatchers("/*/delete/**", "/*/create", "/*/update/**", "/relatorio/aprovar/**", "/relatorio/negar/**").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.POST, "/*/").hasRole("ADMIN");
                    auth.anyRequest().authenticated();
                })
                .formLogin(login -> login
                        .loginPage("/login")
                        .usernameParameter("email")
                        .passwordParameter("senha")
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .logout(logout -> logout.logoutSuccessUrl("/login?logout")
                        .permitAll())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authProvider);
    }
}
