package org.example.keycloakservice.config;


import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KeyCloakBuilderProvider
{
    @Value("${keycloak.server-url}")
    private String serverUrl;

    @Value("${keycloak.realm}")
    public String realm;
    @Value("${keycloak.client-id}")
    public String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    public KeycloakBuilder newKeycloakBuilderWithPasswordCredentials(String username, String password)
    {
        return KeycloakBuilder.builder()
                .resteasyClient(new ResteasyClientBuilder()
                        .connectionPoolSize(10)
                        .build()
                )
                .realm(realm)
                .serverUrl(serverUrl)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .username(username)
                .password(password);
    }
}