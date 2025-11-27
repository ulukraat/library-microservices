package com.user.api.controller;

import com.user.api.dto.UserDto;
import com.user.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/profile")
    public ResponseEntity<UserDto> createProfile(@AuthenticationPrincipal Jwt principal,
                                                 @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.createProfile(principal, userDto));
    }
    @GetMapping("/profile/{id}")
    public ResponseEntity<UserDto> getProfile(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.findById(id));
    }
    @PutMapping("/profile/{id}")
    public ResponseEntity<UserDto> updateProfile(@PathVariable UUID id,
                                                 @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.updateProfile(id, userDto));
    }
    @DeleteMapping("/profile/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable UUID id) {
        userService.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }

}
