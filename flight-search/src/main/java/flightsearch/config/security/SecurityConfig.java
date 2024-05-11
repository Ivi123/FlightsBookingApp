package flightsearch.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final JwtAuthConverter jwtAuthConverter;

    public SecurityConfig(JwtAuthConverter jwtAuthConverter) {
        this.jwtAuthConverter = jwtAuthConverter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                                .requestMatchers("/search-flights").hasAnyRole("admin", "user")
                                .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)))
                .sessionManagement(sessionManager ->
                        sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}

/*
* CSRF este un atac care profită de autentificarea automată a browserului
* jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)): Configurează JWT ca mecanism de autentificare și
  utilizează JwtAuthConverter pentru conversia JWT-urilor.
* sessionManagement: Configurarea gestionării sesiunilor.
* sessionCreationPolicy(SessionCreationPolicy.STATELESS): Definește politica de gestionare a sesiunii ca fiind "stateless",
  ceea ce înseamnă că fiecare cerere trebuie să aibă un token valabil pentru autentificare.
*/