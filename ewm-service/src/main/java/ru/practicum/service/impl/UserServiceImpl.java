package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.handler.exception.ExistsElementException;
import ru.practicum.handler.exception.ObjectNotFoundException;
import ru.practicum.model.User;
import ru.practicum.model.dto.user.NewUserRequest;
import ru.practicum.model.dto.user.UserDto;
import ru.practicum.repository.UserRepository;
import ru.practicum.service.UserService;
import ru.practicum.utils.mapper.UserMapper;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserDto createUser(NewUserRequest newUser) {
        if (userRepository.countByName(newUser.getName()) != 0) {
            throw new ExistsElementException("User with this name already exist");
        }
        @Valid User user = userRepository.save(UserMapper.toUser(newUser));
        return UserMapper.toUserDto(user);
    }

    @Override
    public List<UserDto> findUsers(long[] ids, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        return userRepository.findUserByIds(ids, pageable)
                .stream()
                .map(UserMapper::toUserDto)
                .collect(toList());
    }

    @Transactional
    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("Нет такого пользователя!"));
        userRepository.deleteById(userId);
    }
}
