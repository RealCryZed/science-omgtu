package ru.omgtu.scienceomgtu.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.omgtu.scienceomgtu.model.Author;
import ru.omgtu.scienceomgtu.model.Organization;
import ru.omgtu.scienceomgtu.repository.AuthorRepository;
import ru.omgtu.scienceomgtu.repository.OrganizationRepository;

import java.util.List;

@RestController
public class MainController {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private OrganizationRepository organizationRepository;

    @GetMapping("/authors")
    public List<Author> authors() {
        return authorRepository.findAll();
    }

    @GetMapping("/organizations")
    public List<Organization> organizations() {
        return organizationRepository.findAll();
    }
}
