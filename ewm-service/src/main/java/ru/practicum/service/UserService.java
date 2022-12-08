package ru.practicum.service;

import ru.practicum.model.dto.user.NewUserRequest;
import ru.practicum.model.dto.user.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(NewUserRequest newUser);

    List<UserDto> findUsers(long[] ids, Integer from, Integer size);

    void deleteUser(Long userId);
}
