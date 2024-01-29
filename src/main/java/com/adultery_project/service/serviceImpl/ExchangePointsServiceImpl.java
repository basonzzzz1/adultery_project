package com.adultery_project.service.serviceImpl;

import com.adultery_project.models.ExchangePoints;
import com.adultery_project.repository.ExchangePointsRepository;
import com.adultery_project.service.service.ExchangePointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ExchangePointsServiceImpl implements ExchangePointsService {
    @Autowired
    ExchangePointsRepository exchangePointsRepository;
    @Override
    public ExchangePoints save(ExchangePoints exchangePoints) {
        return exchangePointsRepository.save(exchangePoints);
    }

    @Override
    public ExchangePoints edit(ExchangePoints exchangePoints) {
        return exchangePointsRepository.save(exchangePoints);
    }

    @Override
    public void delete(int id) {
        exchangePointsRepository.deleteById(id);
    }

    @Override
    public ExchangePoints findById(int id) {
        return exchangePointsRepository.findById(id).get();
    }

    @Override
    public List<ExchangePoints> getAll() {
        return exchangePointsRepository.findAll();
    }
}