package com.example.asseco_task.enums;

public enum CommunicationMethodEnum {

    EMAIL("email"),
    ADDRESS("address"),
    REGISTERED_ADRESS("registered_address"),
    PRIVATE_PHONE("private_phone"),
    WORK_PHONE("work_phone");

    private final String name;

    CommunicationMethodEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
