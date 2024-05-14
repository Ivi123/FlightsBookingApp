package org.example.keycloakservice.controller;

import org.example.keycloakservice.dto.LoginDTO;
import org.example.keycloakservice.dto.RegisterDTO;
import org.example.keycloakservice.service.KeyCloakServiceImpl;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/user")
public class KeyCloakController {

    private final KeyCloakServiceImpl service;

    public KeyCloakController(KeyCloakServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/register")
    public String addUser(@RequestBody RegisterDTO userDTO){
        service.addUser(userDTO);
        return "User Added Successfully.";
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO userDTO)
    {
        String token = service.generateToken(userDTO);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @GetMapping(path = "/{userName}")
    public List<UserRepresentation> getUser(@PathVariable("userName") String userName){
        return service.getUser(userName);
    }

    @PutMapping(path = "/update/{userId}")
    public String updateUser(@PathVariable("userId") String userId, @RequestBody RegisterDTO userDTO){
        service.updateUser(userId, userDTO);
        return "User Details Updated Successfully.";
    }

    @DeleteMapping(path = "/{userId}")
    public String deleteUser(@PathVariable("userId") String userId){
        service.deleteUser(userId);
        return "User Deleted Successfully.";
    }

    @GetMapping(path = "/verification-link/{userId}")
    public String sendVerificationLink(@PathVariable("userId") String userId){
        service.sendVerificationLink(userId);
        return "Verification Link Send to Registered E-mail Id.";
    }

    @GetMapping(path = "/reset-password/{userId}")
    public String sendResetPassword(@PathVariable("userId") String userId){
        service.sendResetPassword(userId);
        return "Reset Password Link Send Successfully to Registered E-mail Id.";
    }
    @GetMapping("/logout")
    public String logout() {
        service.logout();
        return "Logout successful";
    }
}