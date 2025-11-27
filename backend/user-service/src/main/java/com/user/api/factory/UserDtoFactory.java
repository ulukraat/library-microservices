package com.user.api.factory;

import com.user.api.dto.UserDto;
import com.user.api.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoFactory {

    public UserDto createUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .description(user.getDescription())
                .build();
    }
    public User createUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .description(userDto.getDescription())
                .build();
    }
}
