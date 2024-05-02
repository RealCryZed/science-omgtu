package ru.omgtu.scienceomgtu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.omgtu.scienceomgtu.model.Publication;
import ru.omgtu.scienceomgtu.model.Source;
import ru.omgtu.scienceomgtu.model.SourceType;
import ru.omgtu.scienceomgtu.repository.SourceLinkRepository;
import ru.omgtu.scienceomgtu.repository.SourceRatingRepository;
import ru.omgtu.scienceomgtu.repository.SourceRepository;
import ru.omgtu.scienceomgtu.repository.SourceTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SourceService {
    @Autowired
    private PublicationService publicationService;

    @Autowired
    private SourceRepository sourceRepository;

    @Autowired
    private SourceLinkRepository sourceLinkRepository;

    @Autowired
    private SourceRatingRepository sourceRatingRepository;

    @Autowired
    private SourceTypeRepository sourceTypeRepository;

    public void addSource(final Source source) {
        sourceRepository.save(source);
    }

    public void editSource(final Source source) {
        Source sourceToEdit = sourceRepository.findSourceById(source.getId());
        sourceToEdit.setName(source.getName());
        sourceToEdit.setSourceType(sourceToEdit.getSourceType());
        sourceRepository.save(sourceToEdit);
    }

    public Source findSourceById(Integer id) {
        return sourceRepository.findSourceById(id);
    }

    public Source findSourceByName(String name) {
        return sourceRepository.findSourceByName(name);
    }

    public List<Source> getAllSources() {
        return sourceRepository.findAll();
    }

    public SourceType findSourceTypeByName(String name) {
        return sourceTypeRepository.findSourceByName(name);
    }

    public List<SourceType> getAllSourceTypes() {
        return sourceTypeRepository.findAll();
    }

    public Page<Source> findSourcesWithPagination(int offset, Integer pageSize) {
        return sourceRepository.findAll(PageRequest.of(offset - 1, pageSize));
    }

    @Transactional
    public void deleteSource(Integer id) {
        sourceLinkRepository.deleteAllBySourceId(id);
        sourceRatingRepository.deleteAllBySourceId(id);

        List<Publication> publications = publicationService.getAllPublicationsBySource(id);
        for (Publication publication : publications) {
            publication.setSource(findSourceById(1));
        }

        sourceRepository.deleteById(id);
    }
}
