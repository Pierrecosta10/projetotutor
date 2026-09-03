package com.tutorials.projetotutor.repository;

import com.tutorials.projetotutor.model.TutorialModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.JpqlQueryBuilder;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TutorialRepository extends JpaRepository<TutorialModel, Long> {
//    Optional<TutorialModel> findById (Long id);

    List<TutorialModel> findByPublished(boolean published);

    @Query("SELECT t FROM TutorialModel t " +
            "WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<TutorialModel> findPorTitle(String title);

    List<TutorialModel> id(long id);

    List<TutorialModel> findByTitle(String title);

//    Long id(long id);
}
