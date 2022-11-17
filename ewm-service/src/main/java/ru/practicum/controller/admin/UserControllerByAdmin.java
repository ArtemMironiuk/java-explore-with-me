package ru.practicum.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/admin/users")
@Slf4j
@RequiredArgsConstructor
@Validated
public class UserControllerByAdmin {

    private final UserService userService;

    @GetMapping
    public List<UserDto> findUsers(@RequestParam(name = "ids", required = false) Long ids,
                                   @RequestParam(name = "from", defaultValue = "0") Integer from,
                                   @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Получен запрос к эндпоинту GET, /admin/users");
        return userService.findUsers(ids);
    }

    @PostMapping
    public UserDto createUser(@RequestBody NewUserRequest newUser) {
        log.info("Получен запрос к эндпоинту POST, /admin/users");
        return userService.createUser(newUser);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        log.info("Получен запрос к эндпоинту DELETE, /admin/users/{userId}");
        userService.deleteUser(userId);
    }
}
