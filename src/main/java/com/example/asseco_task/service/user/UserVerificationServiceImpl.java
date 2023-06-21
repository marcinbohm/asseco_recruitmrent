package com.example.asseco_task.service.user;

import com.example.asseco_task.entity.User;
import com.example.asseco_task.exceptions.FailureException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserVerificationServiceImpl implements UserVerificationService {

    @Override
    public Optional<String> verifyUser(User userExisting) {
        try {
//            isUserWithSameFirstNameAndLastName(userExisting.getFirstName(), userExisting.getLastName());
//            isUserWithSamePesel(userExisting.getPesel());
            validatePesel(userExisting.getPesel());
            return Optional.empty();
        } catch (FailureException e) {
            return Optional.of(e.getMessage());
        }
    }

    private void validatePesel(String pesel) {
        int monthWithCentury = Integer.parseInt(pesel.substring(2, 4));

        if (monthWithCentury == 0 || monthWithCentury % 20 > 12) {
            throw new FailureException("Invalid PESEL: Month value is invalid.");
        }

        int day = Integer.parseInt(pesel.substring(4, 6));
        if (day == 0 || day < 1 || day > 31) {
            throw new FailureException("Invalid PESEL: Day value is invalid.");
        }

        if (!pesel.matches("^[0-9]{11}$")) {
            throw new FailureException("Invalid PESEL: PESEL number must consist of 11 digits.");
        }

        int[] times = { 1, 3, 7, 9 };
        int[] digits = new int[11];
        for (int i = 0; i < 11; i++) {
            digits[i] = Character.getNumericValue(pesel.charAt(i));
        }

        int dig11 = digits[10];
        int control = 0;

        for (int i = 0; i < 10; i++) {
            control += digits[i] * times[i % 4];
        }
        control %= 10;

        if ((10 - (control == 0 ? 10 : control)) != dig11) {
            throw new FailureException("Invalid PESEL: Checksum verification failed.");
        }
    }
}
