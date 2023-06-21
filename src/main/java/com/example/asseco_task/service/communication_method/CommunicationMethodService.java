package com.example.asseco_task.service.communication_method;

import com.example.asseco_task.entity.CommunicationMethod;

public interface CommunicationMethodService {
    Integer upsert(CommunicationMethod communicationMethod);
}
