package api.security.service;

import api.model.User;
import api.repository.UserRepository;
import api.security.model.Role;
import api.security.model.SecurityUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class SecurityUserService
        implements UserDetailsService {

    private final UserRepository repository;

    public SecurityUserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(
            String username) throws UsernameNotFoundException {

        User user = repository.findByUsername(username);

        return buildSecurityUser(user);
    }

    private SecurityUser buildSecurityUser(User user) {
        return SecurityUser.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(Collections.singletonList(Role.USER))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true).build();
    }

}