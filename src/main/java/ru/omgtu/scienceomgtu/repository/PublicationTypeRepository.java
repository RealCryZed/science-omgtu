package ru.omgtu.scienceomgtu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.omgtu.scienceomgtu.model.PublicationType;

@Repository
public interface PublicationTypeRepository extends JpaRepository<PublicationType, Integer> {

    PublicationType findPublicationTypeByName(String name);
}
