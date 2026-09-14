package com.tutorials.projetotutor.service;

import com.tutorials.projetotutor.dto.TutorialDto;
import com.tutorials.projetotutor.model.TutorialModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TutorialService {

    List<TutorialModel> getAllTutorials(String title);

    TutorialDto getTutorialById(Long id);

    TutorialDto createTutorial(TutorialDto dto);

    TutorialDto updateTutorial(Long id, TutorialDto dto);

    List<TutorialModel> findByTitle(String title);

    Page<TutorialModel> findByPublished(Pageable pageable);

    TutorialDto adicionarCategoria(Long tutorialId, Long categoriaId);

    void deleteTutorial(Long id);

}