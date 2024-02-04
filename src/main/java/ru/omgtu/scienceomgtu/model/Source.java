package ru.omgtu.scienceomgtu.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "source")
@Data
public class Source {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "source_type_id", nullable = false)
    private SourceType sourceType;

    @Transient
    private String sourceTypeString;

    @Column(name = "name", nullable = false)
    @Size(min = 1, message = "Пожалуйста, введите название источника")
    private String name;
}