package com.marketplace.backend.order.service;

import com.marketplace.backend.order.entity.UserEntity;
import com.marketplace.backend.order.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private static final int SALT_LENGTH = 16;

    private String generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    private String hashPassword(String password, String salt) {
        return BCrypt.hashpw(password + salt, BCrypt.gensalt());
    }

    public UserEntity create(UserEntity user) {
        if (userRepository.findByEmailAndActiveTrue(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        String salt = generateSalt();
        String hashedPassword = hashPassword(user.getPassword(), salt);

        user.setSalt(salt);
        user.setPassword(hashedPassword);

        return userRepository.save(user);
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAllByActiveTrue();
    }

    public UserEntity getUserById(UUID uuid) {
        return userRepository.findByUuidAndActiveTrue(uuid).orElseThrow( () -> new RuntimeException("User not found"));
    }

    public UserEntity update(UUID uuid, UserEntity userDetails) {
        UserEntity user = userRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setPhone(userDetails.getPhone());
        user.setAddresses(userDetails.getAddresses());

        if (!userDetails.getPassword().equals(user.getPassword())) {
            String salt = generateSalt();
            String hashedPassword = hashPassword(userDetails.getPassword(), salt);
            user.setSalt(salt);
            user.setPassword(hashedPassword);
        }

        return userRepository.save(user);
    }

    public void delete(UUID uuid) {
        if (!userRepository.existsById(uuid)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteLogicallyByUuid(uuid);
    }

    public boolean validatePassword(UUID uuid, String password) {
        UserEntity user = userRepository.findByUuidAndActiveTrue(uuid).orElseThrow(
                () -> new RuntimeException("User not found"));
        String hashedInputPassword = hashPassword(password, user.getSalt());
        return hashedInputPassword.equals(user.getPassword());
    }

    public UserEntity login(String email, String password) {
        UserEntity user = userRepository.findByEmailAndActiveTrue(email)
                .orElseThrow(() -> new RuntimeException("Credenciais inválidas"));

        if (!BCrypt.checkpw(password + user.getSalt(), user.getPassword())) {
            throw new RuntimeException("Credenciais inválidas");
        }

        return user;
    }

}
