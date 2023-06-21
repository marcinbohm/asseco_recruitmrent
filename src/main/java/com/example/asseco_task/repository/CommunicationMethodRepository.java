package com.example.asseco_task.repository;

import com.example.asseco_task.entity.CommunicationMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CommunicationMethodRepository extends JpaRepository<CommunicationMethod, Integer>, JpaSpecificationExecutor<CommunicationMethod> {
}
