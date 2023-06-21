package com.example.asseco_task.service.communication_method;

import com.example.asseco_task.entity.CommunicationMethod;
import com.example.asseco_task.exceptions.FailureException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CommunicationMethodVerificationServiceImpl implements CommunicationMethodVerificationService {

    private static final String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final String allCountryPhoneRegex = "^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$";

    @Override
    public Optional<String> verifyCommunicationMethod(CommunicationMethod communicationMethodExisting) {
        try {
            if (communicationMethodExisting.getUserId() == null)
                throw new FailureException("Communication method user not provided!");
            if (communicationMethodExisting.getType() == null)
                throw new FailureException("Communication method type not provided!");
            if (communicationMethodExisting.getValue() != null) {
                switch (communicationMethodExisting.getType()) {
                    case EMAIL:
                        validateCommunicationMethodEmail(communicationMethodExisting.getValue());
                        break;
                    case ADDRESS:
                        validateCommunicationMethodAddress(communicationMethodExisting.getValue());
                        break;
                    case WORK_PHONE:
                        validateCommunicationMethodPhone(communicationMethodExisting.getValue());
                        break;
                }
            } else
                throw new FailureException("Communication method value not provided!");
            return Optional.empty();
        } catch (FailureException e) {
            return Optional.of(e.getMessage());
        }
    }

    private void validateCommunicationMethodAddress(String value) {
        //TODO Konkatenacja nazwa ulicy + numer domu + kod pocztowy lub nowy obiekt przechowujący adres???
    }

    private void validateCommunicationMethodPhone(String value) {
        Pattern pattern = Pattern.compile(allCountryPhoneRegex);
        Matcher matcher = pattern.matcher(value);
        if (!matcher.matches()) {
            throw new FailureException("Phone number is invalid!");
        }
    }

    private void validateCommunicationMethodEmail(String value) {
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(value);
        if (!matcher.matches()) {
            throw new FailureException("Email is invalid!");
        }
    }
}
