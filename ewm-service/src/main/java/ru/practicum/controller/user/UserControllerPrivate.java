package ru.practicum.controller.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users")
@Slf4j
@RequiredArgsConstructor
@Validated
public class UserControllerPrivate {

//    private final UserService userService;
//
//    @GetMapping("/{userId}/events")
//    public UserDto findUserById(@PathVariable Long userId,
//                                @RequestParam(name = "from", defaultValue = "0") Integer from,
//                                @RequestParam(name = "size", defaultValue = "10") Integer size) {
//        log.info("Получен запрос к эндпоинту GET, /users/{userId}");
//        return userService.findUserById(userId);
//    }
//
//    @PostMapping
//    public UserDto createUser(@RequestBody UserDto userDto) {
//        log.info("Получен запрос к эндпоинту POST, /users");
//        return userService.createUser(userDto);
//    }
//
//    @PatchMapping("/{userId}")
//    public UserDto updateUser(@PathVariable Long userId,
//                              @RequestBody UserDto userDto) {
//        log.info("Получен запрос к эндпоинту PATCH, /users/{userId}");
//        return userService.updateUser(userId, userDto);
//    }
//
//    @DeleteMapping("/{userId}")
//    public void deleteUser(@PathVariable Long userId) {
//        log.info("Получен запрос к эндпоинту DELETE, /users/{userId}");
//        userService.deleteUser(userId);
//    }
}
