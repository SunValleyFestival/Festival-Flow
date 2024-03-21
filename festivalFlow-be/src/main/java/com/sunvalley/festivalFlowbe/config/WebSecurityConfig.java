
package com.sunvalley.festivalFlowbe.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {


    private final ApplicationJwtConfig applicationJwtConfig;

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("festival-flow/user/auth/**", "festival-flow/admin/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(configure -> configure.sessionCreationPolicy(SessionCreationPolicy.NEVER))
                .oauth2ResourceServer((configure) -> configure
                        .jwt(Customizer.withDefaults())
                ).cors(AbstractHttpConfigurer::disable
                ).build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(applicationJwtConfig.getPublicKey()).build();
    }

}
