package com.example.worktime.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class CustomPrincipal implements Serializable {

    private static final long serialVersionUID = 1l;

    private int idAccount;
    private Role role;

    public CustomPrincipal(int idAccount, Role role) {
        this.idAccount = idAccount;
        this.role = role;
    }
}
