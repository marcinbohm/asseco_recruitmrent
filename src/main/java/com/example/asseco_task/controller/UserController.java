package com.example.asseco_task.controller;

import com.example.asseco_task.entity.User;
import com.example.asseco_task.generator.DataGenerator;
import com.example.asseco_task.service.user.UserService;
import org.jeasy.random.EasyRandom;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/all")
    public ModelAndView showAll() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users");
        modelAndView.addObject("users", userService.getAllUsers());
        return modelAndView;
    }

    @GetMapping("/to-file")
    public ResponseEntity<Resource> getAllUsersFile() throws IOException {
        CacheControl cacheControl = CacheControl.maxAge(5, TimeUnit.MINUTES)
                .noTransform()
                .mustRevalidate();
        Resource resource = userService.getAllUsersToFile();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=users.txt")
                .contentType(MediaType.TEXT_PLAIN)
                .cacheControl(cacheControl)
                .contentLength(resource.contentLength())
                .body(resource);
    }

    @GetMapping("/{pesel}")
    public User getUserByPesel(@PathVariable("pesel") String pesel) {
        return userService.findByPesel(pesel);
    }

    @PostMapping("/generate-users")
    public List<User> generateUsers() {
        Set<User> users = DataGenerator.generateUsers(15000);
        return userService.saveUsers(users);
    }

    @PostMapping("/upsert")
    public Integer addUser(@RequestBody User user) {
        return userService.upsert(user);
    }
}
