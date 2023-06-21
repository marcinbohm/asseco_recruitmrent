package com.example.asseco_task.service.communication_method;

import com.example.asseco_task.entity.CommunicationMethod;
import com.example.asseco_task.exceptions.FailureException;
import com.example.asseco_task.mapper.SmartMapper;
import com.example.asseco_task.repository.CommunicationMethodRepository;
import javax.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommunicationMethodServiceImpl implements CommunicationMethodService {

    private final CommunicationMethodRepository communicationMethodRepository;
    private final CommunicationMethodVerificationService communicationMethodVerificationService;

    public CommunicationMethodServiceImpl(CommunicationMethodRepository communicationMethodRepository,
                                          CommunicationMethodVerificationService communicationMethodVerificationService) {
        this.communicationMethodRepository = communicationMethodRepository;
        this.communicationMethodVerificationService = communicationMethodVerificationService;
    }

    @Override
    public Integer upsert(CommunicationMethod communicationMethod) {
        boolean adding = (communicationMethod.getId() == null);

        CommunicationMethod communicationMethodExisting =
                (adding ? new CommunicationMethod() : communicationMethodRepository.findById(communicationMethod.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Communication Method with Id: " + communicationMethod.getId() + " not found!")));

        SmartMapper.transferData(communicationMethod, communicationMethodExisting);

        Optional<String> verificationStatus =
                communicationMethodVerificationService.verifyCommunicationMethod(communicationMethodExisting);

        if (verificationStatus.isPresent()) {
            throw new FailureException(verificationStatus.get());
        }

        CommunicationMethod userSaved = communicationMethodRepository.save(communicationMethodExisting);
        return userSaved.getId();
    }
}
