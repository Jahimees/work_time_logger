package com.example.worktime.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

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
