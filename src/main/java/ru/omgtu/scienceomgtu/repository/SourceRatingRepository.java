package ru.omgtu.scienceomgtu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.omgtu.scienceomgtu.model.SourceRating;

public interface SourceRatingRepository extends JpaRepository<SourceRating, Integer> {
    void deleteAllBySourceId(Integer source);
}
