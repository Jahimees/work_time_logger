package com.example.worktime.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import static com.example.worktime.util.DataBaseConstant.*;

@Entity(name = ROLE)
@Data
public class Role implements GrantedAuthority, CustomEntity {

    @Id
    @Column(name = ID_ROLE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRole;

    @Column(name = NAME)
    private String name;

    @Override
    public String getAuthority() {
        return getName();
    }
}
