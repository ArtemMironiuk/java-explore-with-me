package ru.practicum.mapper;

import ru.practicum.dto.user.NewUserRequest;
import ru.practicum.dto.user.UserDto;
import ru.practicum.model.User;

public class UserMapper {

    public static User toUser(NewUserRequest newUser) {
        return User.builder()
                .email(newUser.getEmail())
                .name(newUser.getName())
                .build();
    }

    public static UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
