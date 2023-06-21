package com.example.asseco_task.controller;

import com.example.asseco_task.entity.CommunicationMethod;
import com.example.asseco_task.service.communication_method.CommunicationMethodService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/communication-method")
public class CommunicationMethodController {

    private final CommunicationMethodService communicationMethodService;

    public CommunicationMethodController(CommunicationMethodService communicationMethodService) {
        this.communicationMethodService = communicationMethodService;
    }

    @PostMapping("/upsert")
    public Integer addUserCommunicationMethod(@RequestBody CommunicationMethod communicationMethod) {
        return communicationMethodService.upsert(communicationMethod);
    }
}
