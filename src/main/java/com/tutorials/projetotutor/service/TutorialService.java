package com.tutorials.projetotutor.service;

import com.tutorials.projetotutor.mapper.TutorialMapper;
import com.tutorials.projetotutor.dto.TutorialDto;
import com.tutorials.projetotutor.model.TutorialModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TutorialService {

    List<TutorialModel> getAllTutorials(String title);

    TutorialModel createTutorial(TutorialModel tutorial);

    Optional<TutorialModel> updateTutorial(Long id, TutorialModel tutorial);

    List<TutorialModel> findBytitle(String tile);

    void deleteTutorial(Long id);

    Page<TutorialModel> findByPublished(Pageable peageable);

    List<TutorialModel> getAllTuorials(String title);

    boolean adicionarCategoria(Long tutorialId, Long categoriaId);

    TutorialDto createTutorial(TutorialDto dto);

    TutorialDto getTutorialById(Long id);

    Optional<TutorialDto> updateTutorial(Long id, TutorialDto dto);
}
