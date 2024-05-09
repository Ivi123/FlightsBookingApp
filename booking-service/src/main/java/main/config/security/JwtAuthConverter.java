package main.config.security;

import main.service.auth.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private static final Logger log = LoggerFactory.getLogger(JwtAuthConverter.class);
    @Autowired
    private JwtTokenService jwtTokenService;
    @Value("${security.jwt.principal-claim}")
    private String principalAttribute;

    @Value("${security.jwt.resource-id}")
    private String resourceId;

    @Value("${security.jwt.role-prefix}")
    private String rolePrefix;

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        Collection<GrantedAuthority> authorities = jwtTokenService.extractRolesFromJwt(jwt);

        String principalName = jwt.getClaimAsString("preferred_username");

        return new JwtAuthenticationToken(jwt, authorities, principalName);
    }

    private String getPrincipalClaimName(Jwt jwt) {
        return jwt.hasClaim(principalAttribute) ? jwt.getClaimAsString(principalAttribute) : jwt.getClaimAsString(JwtClaimNames.SUB);
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        try {
            Map<String, Object> resourceAccess = jwt.getClaimAsMap("resource_access");
            if (resourceAccess == null || !resourceAccess.containsKey(resourceId)) {
                return Set.of();
            }
            Collection<String> resourceRoles = (Collection<String>) ((Map<String, Object>) resourceAccess.get(resourceId)).get("roles");
            return resourceRoles.stream()
                    .map(role -> new SimpleGrantedAuthority(rolePrefix + role))
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            log.error("Error extracting roles from JWT: {}", e.getMessage(), e);
            return Set.of();
        }
    }
}
