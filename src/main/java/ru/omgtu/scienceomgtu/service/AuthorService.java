package ru.omgtu.scienceomgtu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.omgtu.scienceomgtu.model.*;
import ru.omgtu.scienceomgtu.repository.AuthorPublicationOrganizationRepository;
import ru.omgtu.scienceomgtu.repository.AuthorPublicationRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorPublicationRepository authorPublicationRepository;

    @Autowired
    private AuthorPublicationOrganizationRepository authorPublicationOrganizationRepository;

    public List<Author> getAuthorListByPublication(Publication publication) {
        List<Author> authors = new ArrayList<>();
        List<AuthorPublication> authorPublications = new ArrayList<>(authorPublicationRepository.findAuthorPublicationsByPublication(publication));

        for (AuthorPublication authorPublication : authorPublications) {
            Author author = authorPublication.getAuthor();
            List<AuthorPublicationOrganization> authorPublicationOrganizations
                    = authorPublicationOrganizationRepository.findAuthorPublicationOrganizationsByAuthorPublication(authorPublication);

            List<Organization> organizations = new ArrayList<>();
            for (AuthorPublicationOrganization authorPublicationOrganization : authorPublicationOrganizations)
                organizations.add(authorPublicationOrganization.getOrganization());

            author.setOrganizations(organizations);

            StringBuilder orgs = new StringBuilder();
            for (Organization organization : organizations) {
                orgs.append("(").append(organization.getName()).append(") ");
            }
            author.setOrganizations_text(orgs.toString());
            authors.add(author);
        }

        return authors;
    }
}
