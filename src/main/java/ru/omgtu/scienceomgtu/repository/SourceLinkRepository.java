package ru.omgtu.scienceomgtu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.omgtu.scienceomgtu.model.Source;
import ru.omgtu.scienceomgtu.model.SourceLink;

public interface SourceLinkRepository extends JpaRepository<SourceLink, Integer> {
    void deleteAllBySourceId(Integer source);
}
