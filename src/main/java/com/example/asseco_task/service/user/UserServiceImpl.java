package com.example.asseco_task.service.user;

import com.example.asseco_task.entity.User;
import com.example.asseco_task.exceptions.FailureException;
import com.example.asseco_task.mapper.SmartMapper;
import com.example.asseco_task.repository.UserRepository;
import com.example.asseco_task.repository.specs.UserSpecs;
import javax.persistence.EntityNotFoundException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserVerificationService userVerificationService;

    public UserServiceImpl(UserRepository userRepository, UserVerificationService userVerificationService) {
        this.userRepository = userRepository;
        this.userVerificationService = userVerificationService;
    }

    @Override
    public Integer upsert(User user) {
        boolean adding = (user.getId() == null);

        User userExisting =
                (adding ? new User() : userRepository.findById(user.getId())
                        .orElseThrow(() -> new EntityNotFoundException("User with Id: " + user.getId() + " not found!")));

        SmartMapper.transferData(user, userExisting);

        Optional<String> verificationStatus =
                userVerificationService.verifyUser(userExisting);

        if (verificationStatus.isPresent()) {
            throw new FailureException(verificationStatus.get());
        }

        User userSaved = userRepository.save(userExisting);
        return userSaved.getId();
    }

    @Override
    public User findByPesel(String pesel) {
        Specification<User> userSpecification = Specification
                .where(UserSpecs.peselEquals(pesel));
        return findBy(userSpecification);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Resource getAllUsersToFile() {
        List<User> users = userRepository.findAll();

        StringBuilder content = new StringBuilder();
        for (User user : users)
            content.append(user.toString()).append("\n");


        return new ByteArrayResource(content.toString().getBytes(), "Users list");
    }

    @Override
    public User findByFilter(String pesel, String firstName, String lastName) {
        Specification<User> userSpecification = Specification
                .where(UserSpecs.peselEquals(pesel))
                .and(UserSpecs.firstNameEquals(firstName))
                .and(UserSpecs.lastNameEquals(lastName));
        return findBy(userSpecification);
    }

    @Override
    public List<User> saveUsers(Set<User> users) {
        return userRepository.saveAll(users);
    }

    private User findBy(Specification<User> specification){
        return userRepository.findOne(specification)
                .orElseThrow(() -> new EntityNotFoundException("User not found with provided specifications!"));
    }
}
