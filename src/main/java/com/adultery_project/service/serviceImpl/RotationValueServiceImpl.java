package com.adultery_project.service.serviceImpl;

import com.adultery_project.models.RotationValue;
import com.adultery_project.repository.RotationValueRepository;
import com.adultery_project.service.service.RotationValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class RotationValueServiceImpl implements RotationValueService {
    @Autowired
    RotationValueRepository rotationValueRepository;

    @Override
    public RotationValue save(RotationValue rotationValue) {
        return rotationValueRepository.save(rotationValue);
    }

    @Override
    public RotationValue edit(RotationValue rotationValue) {
        return rotationValueRepository.save(rotationValue);
    }

    @Override
    public void delete(int id) {
          rotationValueRepository.deleteById(id);
    }

    @Override
    public RotationValue findById(int id) {
        return rotationValueRepository.findById(id).get();
    }

    @Override
    public List<RotationValue> getAll() {
        return rotationValueRepository.findAll();
    }
}