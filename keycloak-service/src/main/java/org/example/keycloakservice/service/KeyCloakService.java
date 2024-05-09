package org.example.keycloakservice.service;

import org.example.keycloakservice.dto.RegisterDTO;

public interface KeyCloakService {
    void addUser(RegisterDTO userDTO);
}
