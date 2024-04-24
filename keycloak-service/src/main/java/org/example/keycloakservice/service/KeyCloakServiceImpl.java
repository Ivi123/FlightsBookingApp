package org.example.keycloakservice.service;

import jakarta.servlet.http.HttpServletRequest;
import org.example.keycloakservice.authentication.Credentials;
import org.example.keycloakservice.config.KeyCloakBuilderProvider;
import org.example.keycloakservice.config.KeycloakConfig;
import org.example.keycloakservice.dto.LoginDTO;
import org.example.keycloakservice.dto.RegisterDTO;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;


import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Service
public class KeyCloakServiceImpl implements KeyCloakService
{
    private final UsersResource usersResource;

    private final RealmResource realmResource;

    private final KeyCloakBuilderProvider keycloakBuilderProvider;
    @Autowired
    private HttpServletRequest request;

    public KeyCloakServiceImpl(UsersResource usersResource, RealmResource realmResource, KeyCloakBuilderProvider keycloakBuilderProvider) {
        this.usersResource = usersResource;
        this.realmResource = realmResource;
        this.keycloakBuilderProvider = keycloakBuilderProvider;
    }

    @Override
    public void addUser(RegisterDTO userDTO)
    {
        CredentialRepresentation credential = Credentials
                .createPasswordCredentials(userDTO.getPassword());

        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDTO.getUsername());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setCredentials(Collections.singletonList(credential));
        user.setEnabled(true);

        Response response = usersResource.create(user);

        String userId = CreatedResponseUtil.getCreatedId(response);

        UserResource userResource = usersResource.get(userId);

        ClientRepresentation client =
                realmResource
                        .clients()
                        .findByClientId("flights-booking-app").get(0);

        RoleRepresentation userClientRole =
                realmResource
                        .clients()
                        .get(client.getId())
                        .roles()
                        .get("user")
                        .toRepresentation();

        userResource.roles().clientLevel(client.getId()).add(Collections.singletonList(userClientRole));

        client = realmResource
                .clients()
                .findByClientId("flights-booking-app").get(0);

        userClientRole =
                realmResource
                        .clients()
                        .get(client.getId())
                        .roles()
                        .get("user")
                        .toRepresentation();

        userResource.roles().clientLevel(client.getId()).add(Collections.singletonList(userClientRole));
    }

    public String generateToken(LoginDTO userDTO)
    {
        Keycloak keycloak = keycloakBuilderProvider.newKeycloakBuilderWithPasswordCredentials(userDTO.getUsername(), userDTO.getPassword()).build();

        return keycloak.tokenManager().getAccessToken().getToken();
    }

    public List<UserRepresentation> getUser(String userName){
        UsersResource usersResource = getInstance();
        List<UserRepresentation> user = usersResource.search(userName, true);
        return user;

    }

    public void updateUser(String userId, RegisterDTO userDTO){
        CredentialRepresentation credential = Credentials
                .createPasswordCredentials(userDTO.getPassword());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDTO.getUsername());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setCredentials(Collections.singletonList(credential));

        UsersResource usersResource = getInstance();
        usersResource.get(userId).update(user);
    }
    public void deleteUser(String userId){
        try {
            // Încearcă să obții reprezentarea utilizatorului
            UserRepresentation user = usersResource.get(userId).toRepresentation();

            // Dacă utilizatorul există, îl șterge
            if (user != null) {
                usersResource.get(userId).remove();
                System.out.println("Utilizatorul a fost șters cu succes.");
            } else {
                System.out.println("Utilizatorul nu a fost găsit.");
            }
        } catch (NotFoundException e) {
            System.out.println("Utilizatorul nu a fost găsit.");
            e.printStackTrace();
            // Poți trata această excepție în funcție de cerințele tale
        }

    }


    public void sendVerificationLink(String userId){
        UsersResource usersResource = getInstance();
        usersResource.get(userId)
                .sendVerifyEmail();
    }



    public void sendResetPassword(String userId){
        UsersResource usersResource = getInstance();
        usersResource.get(userId)
                .executeActionsEmail(Arrays.asList("UPDATE_PASSWORD"));
    }

    public UsersResource getInstance(){
        return new KeycloakConfig().getInstance().realm("FlightsBookingApp").users();
    }

    public void logout() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            new SecurityContextLogoutHandler().logout(request, null, auth);
        }
    }
}