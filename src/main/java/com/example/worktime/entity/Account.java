package com.example.worktime.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import static com.example.worktime.util.DataBaseConstant.*;

@Data
@Entity(name = ACCOUNT)
public class Account implements UserDetails, Serializable, CustomEntity {

    private static final long serialVersionUID = 1l;

    @Id
    @Column(name = ID_ACCOUNT)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAccount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = ID_POSITION)
    private Position position;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = ID_DEPARTMENT)
    private Department department;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = ID_EMPLOYER_DETAILS)
    private EmployerDetails employerDetails;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = ID_ROLE)
    private Role role;

    @Column(name = USERNAME)
    private String username;

    @Column(name = PASSWORD)
    private String password;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(getRole());
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
