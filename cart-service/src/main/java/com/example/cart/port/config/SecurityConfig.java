package com.example.cart.port.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.stereotype.Component;

@EnableMethodSecurity
@EnableWebSecurity
@Component
class SecurityConfig  {


    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new NullAuthenticatedSessionStrategy();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        grantedAuthoritiesConverter.setAuthoritiesClaimName("role-claims");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.
                csrf().disable().
                authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/carts").hasRole("shop-admin")
                .requestMatchers(HttpMethod.POST, "/cart").permitAll()
                .requestMatchers(HttpMethod.GET, "/cart/**").hasRole("customer")
                .requestMatchers(HttpMethod.PUT, "/cart/**").hasRole("customer")
                .requestMatchers(HttpMethod.DELETE, "/cart/items/**").hasRole("customer")
                .requestMatchers(HttpMethod.PUT, "/cart/items/**").hasRole("customer")
                .requestMatchers(HttpMethod.DELETE, "/cart/**").hasRole("shop-admin")
                .requestMatchers(HttpMethod.POST, "/cart/buy-cart/**").hasRole("customer")
                .anyRequest().permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .oauth2ResourceServer()
                .jwt();
        return http.build();
    }


}