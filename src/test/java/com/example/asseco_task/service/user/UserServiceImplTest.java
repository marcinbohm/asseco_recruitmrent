package com.example.asseco_task.service.user;

import com.example.asseco_task.entity.User;
import com.example.asseco_task.exceptions.FailureException;
import com.example.asseco_task.repository.UserRepository;
import com.example.asseco_task.repository.specs.UserSpecs;
import junit.framework.TestCase;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class UserServiceImplTest extends TestCase {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserVerificationService userVerificationService;

    private UserServiceImpl userService;

    protected void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(userRepository, userVerificationService);
    }

    public void testUpsert_UpdatingExistingUser_UserSaved() {
        // Given
        User user = new User();
        user.setId(1);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userVerificationService.verifyUser(any(User.class))).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer((Answer<User>) invocation -> invocation.getArgument(0));

        // When
        Integer savedUserId = userService.upsert(user);

        // Then
        assertNotNull(savedUserId);
        assertEquals(user.getId(), savedUserId);
        verify(userRepository).save(user);
    }

    public void testGetAllUsers_ReturnsAllUsers() {
        // Given
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());

        when(userRepository.findAll()).thenReturn(users);

        // When
        List<User> retrievedUsers = userService.getAllUsers();

        // Then
        assertEquals(users, retrievedUsers);
        verify(userRepository).findAll();
    }

    public void testGetAllUsersToFile_ReturnsResourceWithContent() {
        // Given
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());

        when(userRepository.findAll()).thenReturn(users);

        // When
        Resource resource = userService.getAllUsersToFile();

        // Then
        assertNotNull(resource);
    }
}
