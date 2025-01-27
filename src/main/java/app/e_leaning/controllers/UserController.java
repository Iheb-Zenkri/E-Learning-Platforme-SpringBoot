package app.e_leaning.controllers;

import app.e_leaning.dtos.UserDTO;
import app.e_leaning.dtos.UserRegistrationDTO;
import app.e_leaning.models.User;
import app.e_leaning.services.UserService;
import app.e_leaning.utils.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserRegistrationDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(UserMapper.toDTO(registeredUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(value -> ResponseEntity.ok(UserMapper.toDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userService.getUserByUsername(username);
        return user.map(value -> ResponseEntity.ok(UserMapper.toDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.getUserByEmail(email);
        return user.map(value -> ResponseEntity.ok(UserMapper.toDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO updatedUserDTO) {
        try {
            User updatedUser = UserMapper.toEntity(updatedUserDTO);
            User savedUser = userService.updateUser(id, updatedUser);
            return ResponseEntity.ok(UserMapper.toDTO(savedUser));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/activate-deactivate/{id}")
    public ResponseEntity<UserDTO> activateDeactivateUser(@PathVariable Long id, @RequestParam boolean isActive) {
        try {
            User user = userService.activateDeactivateUser(id, isActive);
            return ResponseEntity.ok(UserMapper.toDTO(user));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/exists/email")
    public ResponseEntity<Boolean> emailExists(@RequestParam String email) {
        boolean exists = userService.emailExists(email);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/exists/username")
    public ResponseEntity<Boolean> usernameExists(@RequestParam String username) {
        boolean exists = userService.usernameExists(username);
        return ResponseEntity.ok(exists);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Boolean> authenticateUser(@RequestParam String username, @RequestParam String password) {
        boolean isAuthenticated = userService.authenticateUser(username, password);
        return ResponseEntity.ok(isAuthenticated);
    }
}

