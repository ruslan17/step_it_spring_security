package api.security.service;

import api.security.exceptions.AuthenticationException;
import api.security.model.dto.JwtAuthenticationRequest;
import api.security.model.dto.JwtAuthenticationResponse;
import api.security.util.TokenUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationService {

    private final TokenUtils tokenUtils;

    private final UserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            TokenUtils tokenUtils,
            @Qualifier("securityUserService")
                    UserDetailsService userDetailsService,
            @Qualifier("customAuthenticationManager")
            AuthenticationManager authenticationManager) {
        this.tokenUtils = tokenUtils;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    public JwtAuthenticationResponse createAuthenticationToken(
            JwtAuthenticationRequest request) {

        authenticate(request.getUsername(), request.getPassword());

        String token = tokenUtils.generateToken(request.getUsername());

        return new JwtAuthenticationResponse(token);
    }

    public void authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new AuthenticationException("User is disabled", e);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Bad credentials", e);
        }


    }
}
