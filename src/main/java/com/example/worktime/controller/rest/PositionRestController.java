package com.example.worktime.controller.rest;

import com.example.worktime.entity.CustomEntity;
import com.example.worktime.entity.Position;
import com.example.worktime.service.PositionDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest-контроллер по работе с объектами должностей
 */
@RestController
@RequiredArgsConstructor
public class PositionRestController {

    private final PositionDataService positionDataService;

    /**
     * Производит поиск и возврат на клиент всех должностей в системе
     *
     * @return список существующих должностей
     */
    @PreAuthorize("permitAll()")
    @GetMapping("/positions")
    public ResponseEntity<List<? extends CustomEntity>> findAllPositions() {
        return ResponseEntity.ok(positionDataService.findAll());
    }

    /**
     * Производит создание новой должности. Только кадровик
     *
     * @param position создаваемый объект должности
     * @return созданный объект должности
     */
    @PreAuthorize("hasRole('HR')")
    @PostMapping("/positions")
    public ResponseEntity<CustomEntity> createPosition(@RequestBody Position position) {
        return ResponseEntity.ok(positionDataService.create(position));
    }

    /**
     * Удаляет должность из системы. Удаление производится каскадно. То есть,
     * связанные аккаунты будут удалены! Только кадровик
     *
     * @param idPosition id должности
     */
    @PreAuthorize("hasRole('HR')")
    @DeleteMapping("/positions/{idPosition}")
    public void deletePosition(@PathVariable int idPosition) {
        positionDataService.delete(idPosition);
    }

    /**
     * Изменение должности
     *
     * @param position объект с новыми данными
     * @param idPosition id должности
     * @return измененный объект должности
     */
    @PreAuthorize("hasRole('HR')")
    @PatchMapping("/positions/{idPosition}")
    public ResponseEntity<CustomEntity> patchPosition(@RequestBody Position position, @PathVariable int idPosition) {
        position.setIdPosition(idPosition);

        return ResponseEntity.ok(positionDataService.patch(position));
    }
}
