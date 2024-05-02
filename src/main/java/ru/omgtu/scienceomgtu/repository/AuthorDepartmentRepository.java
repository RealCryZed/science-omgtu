package ru.omgtu.scienceomgtu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.omgtu.scienceomgtu.model.Author;
import ru.omgtu.scienceomgtu.model.AuthorDepartment;

public interface AuthorDepartmentRepository extends JpaRepository<AuthorDepartment, Integer> {
    void deleteByAuthor(Author author);
}
