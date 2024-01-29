package com.adultery_project.repository;

import com.adultery_project.models.RotationValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RotationValueRepository extends JpaRepository<RotationValue,Integer> {

}
