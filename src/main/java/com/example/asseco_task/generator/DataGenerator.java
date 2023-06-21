package com.example.asseco_task.generator;

import com.example.asseco_task.entity.CommunicationMethod;
import com.example.asseco_task.entity.User;
import com.example.asseco_task.enums.CommunicationMethodEnum;
import com.github.javafaker.Faker;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class DataGenerator {

    public static Set<User> generateUsers(int count) {
        Set<User> users = new HashSet<>();
        Faker faker = new Faker();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            User user = new User();
            user.setFirstName(faker.name().firstName());
            user.setLastName(faker.name().lastName());
            user.setPesel(generatePesel());

            Set<CommunicationMethod> communicationMethods = new HashSet<>();
            CommunicationMethod method1 = createCommunicationMethod(faker.internet().emailAddress(), CommunicationMethodEnum.EMAIL);
            communicationMethods.add(method1);
            CommunicationMethod method2 = createCommunicationMethod(faker.phoneNumber().phoneNumber(), CommunicationMethodEnum.PRIVATE_PHONE);
            communicationMethods.add(method2);

            user.setCommunicationMethods(communicationMethods);
            users.add(user);
        }

        return users;
    }

    private static CommunicationMethod createCommunicationMethod(String value, CommunicationMethodEnum type) {
        CommunicationMethod method = new CommunicationMethod();
        method.setValue(value);
        method.setType(type);
        return method;
    }

    private static String generatePesel() {
        Random random = new Random();
        StringBuilder peselBuilder = new StringBuilder();

        for (int i = 0; i < 11; i++) {
            int digit = random.nextInt(10);
            peselBuilder.append(digit);
        }

        return peselBuilder.toString();
    }
}
