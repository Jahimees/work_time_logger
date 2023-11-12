package com.example.worktime.controller.rest;

import com.example.worktime.entity.CustomEntity;
import com.example.worktime.entity.Position;
import com.example.worktime.service.PositionDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PositionRestController {

    private final PositionDataService positionDataService;

    @PreAuthorize("permitAll()")
    @GetMapping("/positions")
    public ResponseEntity<List<? extends CustomEntity>> findAllPositions() {
        return ResponseEntity.ok(positionDataService.findAll());
    }

    @PreAuthorize("hasRole('HR')")
    @PostMapping("/positions")
    public ResponseEntity<CustomEntity> createPosition(@RequestBody Position position) {
        return ResponseEntity.ok(positionDataService.create(position));
    }

    @PreAuthorize("hasRole('HR')")
    @DeleteMapping("/positions/{idPosition}")
    public void deletePosition(@PathVariable int idPosition) {
        positionDataService.delete(idPosition);
    }

    @PreAuthorize("hasRole('HR')")
    @PatchMapping("/positions/{idPosition}")
    public ResponseEntity<CustomEntity> patchPosition(@RequestBody Position position, @PathVariable int idPosition) {
        position.setIdPosition(idPosition);

        return ResponseEntity.ok(positionDataService.patch(position));
    }
}
