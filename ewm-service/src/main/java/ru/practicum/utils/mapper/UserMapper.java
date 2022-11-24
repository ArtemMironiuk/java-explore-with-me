package ru.practicum.utils.mapper;

import ru.practicum.model.dto.user.NewUserRequest;
import ru.practicum.model.dto.user.UserDto;
import ru.practicum.model.dto.user.UserShortDto;
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

    public static UserShortDto toUserShortDto(User user) {
        return UserShortDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}
