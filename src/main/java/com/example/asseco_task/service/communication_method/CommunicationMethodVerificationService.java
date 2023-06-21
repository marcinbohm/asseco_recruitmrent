package com.example.asseco_task.service.communication_method;

import com.example.asseco_task.entity.CommunicationMethod;

import java.util.Optional;

public interface CommunicationMethodVerificationService {
    Optional<String> verifyCommunicationMethod(CommunicationMethod communicationMethodExisting);
}
