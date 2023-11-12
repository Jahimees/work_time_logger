package com.example.worktime.service;

import com.example.worktime.entity.Position;
import com.example.worktime.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.acls.model.AlreadyExistsException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PositionDataService {

    private final PositionRepository positionRepository;

    public List<Position> findAll() {
        return positionRepository.findAll();
    }

    public Position create(Position position) {
        if (positionRepository.existsByName(position.getName())) {
            throw new AlreadyExistsException("Department already exists");
        }

        return positionRepository.saveAndFlush(position);
    }

    public Position patch(Position position) {
        Optional<Position> positionOptional = positionRepository.findByName(position.getName());

        if (position.getName().isEmpty()) {
            throw new IllegalArgumentException("Field name cannot be empty");
        }

        if (positionOptional.isPresent() && positionOptional.get().getIdPosition() != position.getIdPosition()) {
            throw new AlreadyExistsException("This department already exists");
        }

        return positionRepository.saveAndFlush(position);
    }

    public void delete(int idDepartment) {
        positionRepository.deleteById(idDepartment);
    }
}
