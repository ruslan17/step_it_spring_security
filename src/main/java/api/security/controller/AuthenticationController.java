package api.security.controller;

import api.security.model.dto.JwtAuthenticationRequest;
import api.security.model.dto.JwtAuthenticationResponse;
import api.security.service.AuthenticationService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class AuthenticationController {

    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    @PutMapping("/auth")
    public JwtAuthenticationResponse signIn(
            @RequestBody JwtAuthenticationRequest request) {
        return service.createAuthenticationToken(request);
    }

}