package org.example.keycloakservice.config;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

    static Keycloak keycloak = null;
    @Value("${keycloak.server-url}")
    public  String serverUrl;
    @Value("${keycloak.realm}")
    public  String realm;
    @Value("${keycloak.client-id}")
    private  String clientId;
    @Value("${keycloak.client-secret}")
    private  String clientSecret;
    @Value("${keycloak.username}")
    private  String userName;
    @Value("${keycloak.password}")
    private  String password;

    public KeycloakConfig() {
    }
@Bean
    public  Keycloak getInstance() {
        if (keycloak == null) {
            keycloak = KeycloakBuilder.builder()
                    .serverUrl(serverUrl)
                    .realm(realm)
                    .grantType(OAuth2Constants.PASSWORD)
                    .username(userName)
                    .password(password)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .resteasyClient(new ResteasyClientBuilder()
                            .connectionPoolSize(10)
                            .build()
                    )
                    .build();
        }
        return keycloak;
    }
    @Bean
    public RealmResource getRealmResource(Keycloak keycloak)
    {
        return keycloak.realm(realm);
    }
    @Bean
    public UsersResource getUsersResource(RealmResource realmResource)
    {
        return realmResource.users();
    }



}