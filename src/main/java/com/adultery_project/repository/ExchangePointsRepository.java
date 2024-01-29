package com.adultery_project.repository;

import com.adultery_project.models.ExchangePoints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangePointsRepository extends JpaRepository<ExchangePoints,Integer> {
}
