package org.example.keycloakservice.authentication;

import org.keycloak.representations.idm.CredentialRepresentation;

public class Credentials {
    // create a CredentialRepresentation object for a password.
    public static CredentialRepresentation createPasswordCredentials(String password) {
        // Create a new instance of CredentialRepresentation to store password credentials.
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();

        // Set the credential to not be temporary. Temporary credentials usually need to be changed after the first login.
        passwordCredentials.setTemporary(false);

        // Set the type of the credential to PASSWORD, which is a constant from the CredentialRepresentation class.
        // This specifies that the credential is a password type.
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);

        // Set the value of the credential to the password provided as a parameter to the method.
        passwordCredentials.setValue(password);

        return passwordCredentials;
    }
}
