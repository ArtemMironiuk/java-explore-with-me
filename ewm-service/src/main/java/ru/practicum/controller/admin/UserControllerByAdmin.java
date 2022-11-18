package ru.practicum.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.user.NewUserRequest;
import ru.practicum.dto.user.UserDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/users")
@Slf4j
@RequiredArgsConstructor
@Validated
public class UserControllerByAdmin {

    private final UserService userService;

    @GetMapping
    public List<UserDto> findUsers(@RequestParam @PositiveOrZero (name = "ids", required = false) Long ids,
                                   @RequestParam(name = "from", defaultValue = "0") Integer from,
                                   @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Получен запрос к эндпоинту GET, /admin/users");
        return userService.findUsers(ids, from, size);
    }

    @PostMapping
    public UserDto createUser(@RequestBody @Valid NewUserRequest newUser) {
        log.info("Получен запрос к эндпоинту POST, /admin/users");
        return userService.createUser(newUser);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable @Positive Long userId) {
        log.info("Получен запрос к эндпоинту DELETE, /admin/users/{userId}");
        userService.deleteUser(userId);
    }
}
