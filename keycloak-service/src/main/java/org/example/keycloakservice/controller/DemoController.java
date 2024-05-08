package org.example.keycloakservice.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
public class DemoController {
    @GetMapping
    @PreAuthorize("hasRole('user')")
    public String hello(){
        return "Hello from Spring boot & Keycloak- USER";
    }
    @GetMapping("/hello-2")
    @PreAuthorize("hasRole('admin')")
    public String hello2(){
        return "Hello from Spring boot & Keycloak- ADMIN";
    }

}
