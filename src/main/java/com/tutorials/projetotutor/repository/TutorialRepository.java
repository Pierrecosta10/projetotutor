package com.tutorials.projetotutor.repository;

import com.tutorials.projetotutor.model.TutorialModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TutorialRepository extends JpaRepository<TutorialModel, Long> {
    List<TutorialModel> findByPublished(boolean published);

    List<TutorialModel> findByTitleContaining(String title);
}
