package app.e_leaning.controllers;

import app.e_leaning.dtos.UserDTO;
import app.e_leaning.dtos.UserRegistrationDTO;
import app.e_leaning.models.User;
import app.e_leaning.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "Endpoints for managing users")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Create new User")
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserRegistrationDTO userDTO) {
        User user = UserRegistrationDTO.toEntity(userDTO);
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(UserDTO.toDTO(registeredUser));
    }

    @Operation(summary = "Get User by Id")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(value -> ResponseEntity.ok(UserDTO.toDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get User by username")
    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userService.getUserByUsername(username);
        return user.map(value -> ResponseEntity.ok(UserDTO.toDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get User by email")
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.getUserByEmail(email);
        return user.map(value -> ResponseEntity.ok(UserDTO.toDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update User")
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO updatedUserDTO) {
        try {
            User updatedUser = UserDTO.toEntity(updatedUserDTO);
            User savedUser = userService.updateUser(id, updatedUser);
            return ResponseEntity.ok(UserDTO.toDTO(savedUser));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete User")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Long id) {
        boolean isDeleted = userService.deleteUser(id);
        return ResponseEntity.ok(isDeleted);
    }

    @Operation(summary = "Activate User")
    @PatchMapping("/activate-deactivate/{id}")
    public ResponseEntity<UserDTO> activateDeactivateUser(@PathVariable Long id, @RequestParam boolean isActive) {
        try {
            User user = userService.activateDeactivateUser(id, isActive);
            return ResponseEntity.ok(UserDTO.toDTO(user));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Check if Email exist")
    @GetMapping("/exists/email")
    public ResponseEntity<Boolean> emailExists(@RequestParam String email) {
        boolean exists = userService.emailExists(email);
        return ResponseEntity.ok(exists);
    }

    @Operation(summary = "check if Username exist")
    @GetMapping("/exists/username")
    public ResponseEntity<Boolean> usernameExists(@RequestParam String username) {
        boolean exists = userService.usernameExists(username);
        return ResponseEntity.ok(exists);
    }

    @Operation(summary = "Authenticate as a User")
    @PostMapping("/authenticate")
    public ResponseEntity<Boolean> authenticateUser(@RequestParam String username, @RequestParam String password) {
        boolean isAuthenticated = userService.authenticateUser(username, password);
        return ResponseEntity.ok(isAuthenticated);
    }
}

