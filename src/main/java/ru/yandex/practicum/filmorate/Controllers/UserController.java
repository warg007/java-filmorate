package ru.yandex.practicum.filmorate.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.Model.User;
import ru.yandex.practicum.filmorate.Service.UserService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/users/{id}")
    public User getOne(@PathVariable int id) {
        return userService.getUserByIdService(id);
    }

    @PutMapping("/users")
    public User update(@RequestBody User user) {
        userService.update(user);
        return user;
    }

    @PostMapping("/users")
    public User addNewUser(@RequestBody User user) {
        userService.addNewUser(user);
        return user;
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public ResponseEntity<String> addNewFriend(@PathVariable("id") int hostId, @PathVariable int friendId) {
        String body = userService.addNewFriend(hostId, friendId);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public ResponseEntity<String> deleteFriend(@PathVariable("id") int hostId, @PathVariable int friendId) {
        String body = userService.deleteFriend(hostId, friendId);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public ResponseEntity<List<User>> commonFriend(@PathVariable("id") int hostId, @PathVariable int otherId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.commonFriends(hostId, otherId));
    }

    @GetMapping("/users/{id}/friends")
    public ResponseEntity<List<User>> friendList(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.friendList(id));
    }
}

