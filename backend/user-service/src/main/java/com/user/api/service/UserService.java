package com.user.api.service;

import com.user.api.dto.UserDto;
import com.user.api.exception.UserNotFoundException;
import com.user.api.factory.UserDtoFactory;
import com.user.api.model.User;
import com.user.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserDtoFactory userDtoFactory;

    public UserDto createProfile(Jwt principal, UserDto userDto) {
        UUID keycloakId = UUID.fromString(principal.getClaim("sub"));

        User user = userDtoFactory.createUser(userDto);
        user.setId(keycloakId);

        User savedUser = userRepository.save(user);

        return userDtoFactory.createUserDto(savedUser);

    }
    public UserDto findById(UUID id) {
        return userRepository.findById(id)
                .map(userDtoFactory::createUserDto)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
    public UserDto updateProfile(UUID id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        user.setDescription(userDto.getDescription());
        User updatedUser = userRepository.save(user);

        return userDtoFactory.createUserDto(updatedUser);
    }
    public void deleteProfile(UUID id) {
        if(!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }
}