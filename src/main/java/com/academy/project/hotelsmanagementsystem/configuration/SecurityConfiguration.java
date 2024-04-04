package com.academy.project.hotelsmanagementsystem.configuration;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Value("${jwt.key}")
    private String jwtKey;

    @Bean
    PasswordEncoder setEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain setUpFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/api/auth/token").permitAll()
                        .requestMatchers("/api/auth/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/bookings").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/bookings/deleted").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/bookings/{id}").hasAnyRole("ADMIN", "CLIENT")
                        .requestMatchers(HttpMethod.GET, "/bookings/users/{userId}").hasAnyRole("ADMIN", "CLIENT")
                        .requestMatchers(HttpMethod.GET, "/bookings/my").hasAnyRole("ADMIN", "CLIENT")
                        .requestMatchers(HttpMethod.POST, "/bookings").hasAnyRole("ADMIN", "CLIENT")
                        .requestMatchers(HttpMethod.PUT, "/bookings/{id}").hasAnyRole("ADMIN", "CLIENT")
                        .requestMatchers(HttpMethod.DELETE, "/bookings/{id}").hasAnyRole("ADMIN", "CLIENT")
                        .requestMatchers(HttpMethod.GET, "/hotels").hasAnyRole("ADMIN", "CLIENT")
                        .requestMatchers(HttpMethod.GET, "/hotels/{id}").hasAnyRole("ADMIN", "CLIENT")
                        .requestMatchers(HttpMethod.POST, "/hotels").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/hotels/{id}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/hotels/{id}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/roles/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/roles").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/roles/{id}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/roles/{id}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/roomsbooked").hasAnyRole("ADMIN", "CLIENT")
                        .requestMatchers(HttpMethod.GET, "/roomsbooked/deleted").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/roomsbooked").hasAnyRole("ADMIN", "CLIENT")
                        .requestMatchers(HttpMethod.PUT, "/roomsbooked/{id}").hasAnyRole("ADMIN", "CLIENT")
                        .requestMatchers(HttpMethod.DELETE, "/roomsbooked/{id}").hasAnyRole("ADMIN", "CLIENT")
                        .requestMatchers(HttpMethod.GET, "/rooms").hasAnyRole("ADMIN", "CLIENT")
                        .requestMatchers(HttpMethod.GET, "/rooms/{id}").hasAnyRole("ADMIN", "CLIENT")
                        .requestMatchers(HttpMethod.GET, "/rooms/available").hasAnyRole("ADMIN", "CLIENT")
                        .requestMatchers(HttpMethod.POST, "/rooms").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/rooms/{id}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/room/{id}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/roomtypes").hasAnyRole("ADMIN", "CLIENT")
                        .requestMatchers(HttpMethod.GET, "/roomtypes/{id}").hasAnyRole("ADMIN", "CLIENT")
                        .requestMatchers(HttpMethod.POST, "/roomtypes").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/roomtypes/{id}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/roomtypes/{id}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/users").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/users/{id}").hasAnyRole("ADMIN", "CLIENT")
                        .requestMatchers(HttpMethod.PUT, "/users/{id}").hasAnyRole("ADMIN", "CLIENT")
                        .requestMatchers(HttpMethod.DELETE, "/users/{id}").hasAnyRole("ADMIN", "CLIENT")
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))

                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling((exceptions) -> exceptions
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler()))
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authenticationProvider);
    }


    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/swagger-ui/**", "/v3/api-docs/**");
    }

    @Bean
    JwtEncoder initJwtEncoder() {
        return new NimbusJwtEncoder(new ImmutableSecret<>(jwtKey.getBytes()));
    }

    @Bean
    JwtDecoder initDecoder() {
        byte[] bytes = jwtKey.getBytes();
        SecretKeySpec originalKey = new SecretKeySpec(bytes, 0, bytes.length, "RSA");
        return NimbusJwtDecoder.withSecretKey(originalKey).macAlgorithm(MacAlgorithm.HS512).build();
    }


    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
}
