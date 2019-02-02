package api.controller;

import api.annotations.CheckAuthorities;
import api.model.User;
import api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{userId}")
    @CheckAuthorities
    public User findUser(@PathVariable Integer userId) {
        return repository.findById(userId).get();
    }

    @PostMapping("/save")
    public void save(@RequestBody @Valid User user) {
        System.out.println(user);
    }

}