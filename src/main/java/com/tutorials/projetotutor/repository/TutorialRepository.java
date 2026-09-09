package com.tutorials.projetotutor.repository;

import com.tutorials.projetotutor.model.TutorialModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.JpqlQueryBuilder;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TutorialRepository extends JpaRepository<TutorialModel, Long> {
//    Optional<TutorialModel> findById (Long id);

    List<TutorialModel> findByPublished(boolean published);

    @Query( value = "SELECT * FROM tutorials WHERE published = :published ORDER BY id",
            countQuery = "SELECT COUNT(*) FROM tutorials WHERE published = :published",
            nativeQuery = true)
    Page<TutorialModel> findByPublished(
            @Param("published") boolean published,
            Pageable pageable);

    List<TutorialModel> id(long id);

    List<TutorialModel> findByTitle(String title);

    List<TutorialModel> id(Long id);

//    Long id(long id);
}
