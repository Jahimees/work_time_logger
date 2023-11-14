package com.example.worktime.service;

import com.example.worktime.entity.Position;
import com.example.worktime.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.acls.model.AlreadyExistsException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис отвечающий за бизнес-логику с объектами типа "Position"
 */
@Service
@RequiredArgsConstructor
public class PositionDataService {

    private final PositionRepository positionRepository;

    /**
     * Производит поиск всех должностей в базе
     * @return список всех должностей
     */
    public List<Position> findAll() {
        return positionRepository.findAll();
    }

    /**
     * Создает новую должность в базе
     * @param position новая должность
     * @return сохраненная должность
     */
    public Position create(Position position) {
        if (positionRepository.existsByName(position.getName())) {
            throw new AlreadyExistsException("Department already exists");
        }

        return positionRepository.saveAndFlush(position);
    }

    /**
     * Производит частичное изменение должности в базе
     * @param position изменяемая должность
     * @return измененная должность
     */
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

    /**
     * Удаляет должность по её id
     * @param idPosition id должности
     */
    public void delete(int idPosition) {
        positionRepository.deleteById(idPosition);
    }
}
