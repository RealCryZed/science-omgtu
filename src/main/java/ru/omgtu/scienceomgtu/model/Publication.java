package ru.omgtu.scienceomgtu.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "publication")
@Data
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "type_id", nullable = false)
    private PublicationType type;

    @Transient
    @Size(min = 1, message = "Пожалуйста, введите тип публикации!")
    private String publicationType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "source_id", nullable = false)
    private Source source;

    @Transient
    @Size(min = 1, message = "Пожалуйста, введите источник!")
    private String sourceString;

    @Column(name = "title", nullable = false)
    @Size(min = 1, message = "Пожалуйста, введите название статьи!")
    private String title;

    @Column(name = "abstract")
    @Size(min = 1, message = "Пожалуйста, введите аннотацию!")
    private String abstractField;

    @Column(name = "publication_date", nullable = false)
    private LocalDate publicationDate;

    @Transient
    @Pattern(regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$", message = "Пожалуйста, введите корректную дату в формате 'ГГГГ-ММ-ДД'")
    private String date;

    @Column(name = "accepted", nullable = false)
    private Boolean accepted = true;

    @Transient
    private List<Author> authorList;

    @Transient
    private String authors;

    @Transient
    private String keywords;

    @Transient
    private String scopusLink;

    @Transient
    private String doi;

    @Transient
    private String elibrary;
}