package ru.omgtu.scienceomgtu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.omgtu.scienceomgtu.model.Source;

@Repository
public interface SourceRepository extends JpaRepository<Source, Integer> {
    Source findSourceById(Integer id);

    Source findSourceByName(String name);
}
