package api.controller;

import api.model.User;
import api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:63342", allowCredentials = "false")
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<User> findAll() {
        return repository.findAll();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/find/{id}")
    public User findUser(@PathVariable Integer id) {
        return repository.findById(id).get();
    }

}