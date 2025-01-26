package app.e_leaning.services;

import app.e_leaning.models.User;
import app.e_leaning.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        return userRepository.save(user);
    }
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public User updateUser(Long id, User updatedUser) {
        // Fetch user, update fields, and save
        return userRepository.findById(id).map(user -> {
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setRole(updatedUser.getRole());
            user.setActive(updatedUser.isActive());
            // you can add additional fields to update here
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    public User activateDeactivateUser(Long id, boolean isActive) {
        return userRepository.findById(id).map(user -> {
            user.setActive(isActive);
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }
    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
    public boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
    public boolean authenticateUser(String username, String password) {

        // Implement authentication logic (e.g., compare password, check JWT, etc.)
        Optional<User> user = userRepository.findByUsername(username);

        // Example, use bcrypt or similar for secure password handling
        return user.map(value -> value.getPassword().equals(password)).orElse(false);
    }
}
