package com.adultery_project.repository;

import com.adultery_project.payload.request.MessageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRequestRepository extends JpaRepository<MessageRequest ,Integer> {
}
