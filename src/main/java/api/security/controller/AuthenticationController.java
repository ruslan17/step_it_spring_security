package api.security.controller;

import api.security.model.dto.JwtAuthenticationRequest;
import api.security.model.dto.JwtAuthenticationResponse;
import api.security.service.AuthenticationService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
@CrossOrigin(origins = "http://localhost:63342", allowCredentials = "false")
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