package com.example.asseco_task.repository.specs;

import com.example.asseco_task.entity.User;
import com.example.asseco_task.entity.User_;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecs {

    public static Specification<User> peselEquals(String pesel) {
        return (root, query, criteriaBuilder) ->
            pesel != null ? criteriaBuilder.equal(root.get(User_.PESEL), pesel) : criteriaBuilder.conjunction();
    }

    public static Specification<User> firstNameEquals(String firstName) {
        return (root, query, criteriaBuilder) ->
                firstName != null ? criteriaBuilder.equal(root.get(User_.FIRST_NAME), firstName) : criteriaBuilder.conjunction();
    }

    public static Specification<User> lastNameEquals(String lastName) {
        return (root, query, criteriaBuilder) ->
                lastName != null ? criteriaBuilder.equal(root.get(User_.LAST_NAME), lastName) : criteriaBuilder.conjunction();
    }
}
