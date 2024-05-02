package ru.omgtu.scienceomgtu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.omgtu.scienceomgtu.model.*;
import ru.omgtu.scienceomgtu.repository.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PublicationService {

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private PublicationLinkRepository publicationLinkRepository;

    @Autowired
    private PublicationTypeRepository publicationTypeRepository;

    @Autowired
    private KeywordService keywordService;

    @Autowired
    private AuthorService authorService;

    public void addPublication(Publication publication) {
        publicationRepository.save(publication);
    }

    public List<Publication> getAllPublicationsBySource(Integer sourceId) {
        return publicationRepository.findAllBySourceId(sourceId);
    }

    public List<PublicationType> getPublicationTypes() {
        return publicationTypeRepository.findAll();
    }

    public Page<Publication> findPublicationsWithPagination(int offset, int pageSize) {
        Page<Publication> publicationsPage = publicationRepository.findAll(PageRequest.of(offset - 1, pageSize));

        for (int i = 0; i < publicationsPage.getSize(); i++) {
            List<Author> authors = authorService.getAuthorListByPublication(publicationsPage.getContent().get(i));
            publicationsPage.getContent().get(i).setAuthorList(authors);
        }

        return publicationsPage;
    }

    public Publication getPublicationById(Integer id) {
        Publication publication = publicationRepository.findPublicationById(id);

        publication.setAuthorList(authorService.getAuthorListByPublication(publication));
        publication.setKeywords(keywordService.getKeywordsByPublication(publication));

        String doi = getLink(publication, new PublicationLinkType(1, "DOI"));
        String scopus = getLink(publication, new PublicationLinkType(2, "Scopus"));
        String elibrary = getLink(publication, new PublicationLinkType(3, "Elibrary"));

        if (doi != null) publication.setDoi(doi);
        if (scopus != null) publication.setScopusLink(scopus);
        if (elibrary != null) publication.setElibrary(elibrary);

        return publication;
    }

    public List<Publication> getTop20Publications() {
        List<Publication> publications = publicationRepository.findTop20ByOrderByPublicationDateDesc();

        for (int i = 0; i < publications.size(); i++) {
            List<Author> authors = authorService.getAuthorListByPublication(publications.get(i));
            publications.get(i).setAuthorList(authors);
        }
        return publications;
    }

    private String getLink(Publication publication, PublicationLinkType publicationLinkType) {
        String str = null;
        try {
            str = publicationLinkRepository.findPublicationLinkByPublicationAndLinkType(publication, publicationLinkType).getLink();
        } catch (NullPointerException e) {}

        return str;
    }

    public PublicationType findPublicationTypeByName(String name) {
        return publicationTypeRepository.findPublicationTypeByName(name);
    }

    public void deletePublication(Integer id) {
        publicationRepository.deleteById(id);
    }

    public Publication findPublicationById(Integer id) {
        return publicationRepository.findPublicationById(id);
    }
}
