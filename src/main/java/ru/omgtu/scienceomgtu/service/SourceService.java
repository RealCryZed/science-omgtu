package ru.omgtu.scienceomgtu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.omgtu.scienceomgtu.model.Source;
import ru.omgtu.scienceomgtu.model.SourceType;
import ru.omgtu.scienceomgtu.repository.SourceRepository;
import ru.omgtu.scienceomgtu.repository.SourceTypeRepository;

import java.util.List;

@Service
public class SourceService {
    @Autowired
    private SourceRepository sourceRepository;

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


    public void deleteSource(Integer id) {
        sourceRepository.deleteById(id);
    }
}
