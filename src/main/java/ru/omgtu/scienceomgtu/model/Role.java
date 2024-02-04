package ru.omgtu.scienceomgtu.model;

import lombok.Data;

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
}