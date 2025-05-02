package com.marketplace.backend.order.controller;

import com.marketplace.backend.order.dto.LoginDTO;
import com.marketplace.backend.order.entity.UserEntity;
import com.marketplace.backend.order.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserEntity> create(@RequestBody UserEntity user) {
        System.out.println("Received JSON: " + user);
        UserEntity createdUser = userService.create(user);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        List<UserEntity> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<UserEntity> getById(@PathVariable String uuid) {
        UUID userUuid = UUID.fromString(uuid);
        UserEntity user = userService.getUserById(userUuid);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<UserEntity> update(@PathVariable String uuid, @RequestBody UserEntity userDetails) {
        UUID userUuid = UUID.fromString(uuid);
        UserEntity updatedUser = userService.update(userUuid, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable String uuid) {
        UUID userUuid = UUID.fromString(uuid);
        userService.delete(userUuid);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{uuid}/validate-password")
    public ResponseEntity<Boolean> validatePassword(@PathVariable String uuid, @RequestBody String password) {
        UUID userUuid = UUID.fromString(uuid);
        boolean isValid = userService.validatePassword(userUuid, password);
        return ResponseEntity.ok(isValid);
    }

    @PostMapping("/login")
    public ResponseEntity<UserEntity> login(@RequestBody LoginDTO loginRequest) {
        UserEntity user = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok(user);
    }
}