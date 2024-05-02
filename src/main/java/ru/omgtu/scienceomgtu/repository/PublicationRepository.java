package ru.omgtu.scienceomgtu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.omgtu.scienceomgtu.model.Publication;

import java.util.List;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Integer> {

    Publication findPublicationById(Integer id);

    List<Publication> findTop20ByOrderByPublicationDateDesc();

    List<Publication> findAllBySourceId(Integer sourceId);
}
