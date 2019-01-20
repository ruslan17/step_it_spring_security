package api.security.service;

import api.security.model.SecurityUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CustomAuthenticationManager
        implements AuthenticationManager {

    private final SecurityUserService securityUserService;

    public CustomAuthenticationManager(SecurityUserService securityUserService) {
        this.securityUserService = securityUserService;
    }

    @Override
    public Authentication authenticate(
            Authentication authentication) throws AuthenticationException {

        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        SecurityUser securityUser = (SecurityUser) securityUserService.loadUserByUsername(username);

        if (Objects.isNull(securityUser)) {
            throw new BadCredentialsException("Security user not found");
        }
        if (!Objects.equals(password, securityUser.getPassword())) {
            throw new BadCredentialsException("Wrong password!");
        }
        if (!securityUser.isEnabled()) {
            throw new DisabledException("User is disabled!");
        }

        return new UsernamePasswordAuthenticationToken(username, password, securityUser.getAuthorities());
    }
}