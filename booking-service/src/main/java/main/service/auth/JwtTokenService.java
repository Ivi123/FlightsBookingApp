package main.service.auth;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;
import java.util.HashSet;

@Service
public class JwtTokenService {

    private final JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();

    @Cacheable(cacheNames = "jwtCache", key = "#jwt.tokenValue")
    public Collection<GrantedAuthority> extractRolesFromJwt(Jwt jwt) {
        // The JwtGrantedAuthoritiesConverter extracts the roles from the token
        return new HashSet<>(authoritiesConverter.convert(jwt));
    }
}

