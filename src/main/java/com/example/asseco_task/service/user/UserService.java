package com.example.asseco_task.service.user;

import com.example.asseco_task.entity.User;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Set;

public interface UserService {

    Integer upsert(User user);

    User findByPesel(String pesel);

    List<User> getAllUsers();

    Resource getAllUsersToFile();

    User findByFilter(String pesel, String firstName, String lastName);

    List<User> saveUsers(Set<User> users);
}
