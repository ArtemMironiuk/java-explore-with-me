package ru.practicum.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.model.dto.user.NewUserRequest;
import ru.practicum.model.dto.user.UserDto;
import ru.practicum.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/users")
@Slf4j
@RequiredArgsConstructor
public class UserControllerByAdmin {

    private final UserService userService;

    @GetMapping
    public List<UserDto> findUsers(@RequestParam (name = "ids", required = false)  long[] ids,
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
    public void deleteUser(@PathVariable @PositiveOrZero Long userId) {
        log.info("Получен запрос к эндпоинту DELETE, /admin/users/{userId}");
        userService.deleteUser(userId);
    }
}
