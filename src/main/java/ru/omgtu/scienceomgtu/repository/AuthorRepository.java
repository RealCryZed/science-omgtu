package ru.omgtu.scienceomgtu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.omgtu.scienceomgtu.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Author findAuthorById(Integer id);
}
