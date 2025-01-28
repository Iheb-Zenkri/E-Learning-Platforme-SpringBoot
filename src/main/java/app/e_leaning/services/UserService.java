package app.e_leaning.services;

import app.e_leaning.models.User;
import app.e_leaning.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        if (user.getRole() == null) {
            user.setRole(User.Role.Student);
        }
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
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
        return userRepository.findById(id).map(user -> {
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setRole(updatedUser.getRole());
            user.setActive(updatedUser.isActive());
            // you can add additional fields to update here
            return userRepository.save(user);
        }).orElse(updatedUser);
    }
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    public User activateDeactivateUser(Long id, boolean isActive) {
        return userRepository.findById(id).map(user -> {
            user.setActive(isActive);
            return userRepository.save(user);
        }).orElse(new User());
    }
    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
    public boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
    public boolean authenticateUser(String username, String password) {

        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return passwordEncoder.matches(password, user.getPassword());
        }

        return false;
    }
}
