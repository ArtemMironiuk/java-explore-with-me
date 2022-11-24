package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.model.dto.user.NewUserRequest;
import ru.practicum.model.dto.user.UserDto;
import ru.practicum.handler.exception.ObjectNotFoundException;
import ru.practicum.utils.mapper.UserMapper;
import ru.practicum.model.User;
import ru.practicum.repository.UserRepository;
import ru.practicum.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public UserDto createUser(NewUserRequest newUser) {
        @Valid User user = userRepository.save(UserMapper.toUser(newUser));
        return UserMapper.toUserDto(user);
    }

    @Override
    public List<UserDto> findUsers(long[] ids, Integer from, Integer size) {
        List<User> users = new ArrayList<>();
        for (long id: ids) {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ObjectNotFoundException("Нет такого пользователя!"));
            //TODO exception
            users.add(user);
        }
        return users
                .stream()
                .map(UserMapper::toUserDto)
                .collect(toList());
    }

    @Transactional
    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("Нет такого пользователя!"));
        //TODO exception
        userRepository.deleteById(userId);
    }
}
