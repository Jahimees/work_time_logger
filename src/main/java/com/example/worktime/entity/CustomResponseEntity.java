package com.example.worktime.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Сущность, которая отвечает за возвращение объекта-ответа, если должен быть
 * возвращен не объект из базы данных. Например возвращает просто информацию о том, что
 * какой-то объект не найден.
 */
@Data
@RequiredArgsConstructor
public class CustomResponseEntity implements CustomEntity {

    public CustomResponseEntity(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;
}
