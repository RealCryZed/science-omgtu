package ru.omgtu.scienceomgtu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.omgtu.scienceomgtu.model.SourceType;

@Repository
public interface SourceTypeRepository extends JpaRepository<SourceType, Integer> {
    SourceType findSourceByName(String name);
}
