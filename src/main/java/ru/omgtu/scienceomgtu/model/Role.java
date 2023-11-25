package ru.omgtu.scienceomgtu.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "role")
@Data
public class Role {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String role;

    public GrantedAuthority getAuthority() {
        return new SimpleGrantedAuthority(role);
    }
}