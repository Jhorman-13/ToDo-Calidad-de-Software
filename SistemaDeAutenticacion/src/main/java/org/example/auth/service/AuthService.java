package org.example.auth.service;

import org.example.auth.model.User;
import org.example.auth.model.UserStatus;
import org.example.auth.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Optional;

import java.security.NoSuchAlgorithmException;

@Service
public class AuthService {

    private final UserRepository repository = new UserRepository();

    public String register(String username, String password) {

        if (username == null || username.trim().isEmpty()) {
            return "Username no puede ser vacío";
        }

        if (password == null || password.trim().isEmpty()) {
            return "Contraseña no puede ser vacía";
        }

        if (repository.findByUsername(username).isPresent()) {
            return "Usuario ya existe.";
        }

        String hash = hashPassword(password);
        User user = new User(username, hash);
        repository.save(user);

        return "Usuario registrado correctamente.";
    }

    public String login(String username, String password) {

        if (username == null || username.trim().isEmpty()) {
            return "Username no puede ser vacío";
        }

        if (password == null || password.trim().isEmpty()) {
            return "Contraseña no puede ser vacía";
        }

        Optional<User> optionalUser = repository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            return "Usuario no encontrado";
        }

        User user = optionalUser.get();

        if (user.getStatus() == UserStatus.BLOCKED) {
            return "Usuario bloqueado";
        }

        String hash = hashPassword(password);

        if (user.getPasswordHash().equals(hash)) {
            user.resetAttempts();
            return "Login exitoso";
        } else {
            user.incrementAttempts();
            return "Credenciales incorrectas";
        }
    }

    public String recover(String username) {

        Optional<User> optionalUser = repository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            return "Usuario no encontrado";
        }

        User user = optionalUser.get();
        String newHash = hashPassword("1234");
        user.unblock(newHash);

        return "Contraseña restablecida";
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return new String(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Error hashing password: SHA-256 algorithm not found", e);
        }
    }
}
