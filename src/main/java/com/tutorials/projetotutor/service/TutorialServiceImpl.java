package com.tutorials.projetotutor.service;

import com.tutorials.projetotutor.model.TutorialModel;
import com.tutorials.projetotutor.repository.TutorialRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TutorialServiceImpl implements TutorialService {
    private final TutorialRepository tutorialRepository;

    public TutorialServiceImpl(TutorialRepository tutorialRepository) {
        this.tutorialRepository = tutorialRepository;
    }

    @Override
    public List<TutorialModel> getAllTuorials(String title) {
        if (title == null){
            return tutorialRepository.findAll();
        }

        return tutorialRepository.findByTitle(title);

    }

    @Override
    public List<TutorialModel> getAllTutorials(String title) {
        return tutorialRepository.findAll();
    }

    @Override
    public Optional<TutorialModel> getTutorialById(Long id) {
        return tutorialRepository.findById(id);
    }

    @Override
    public TutorialModel createTutorial(TutorialModel tutorial) {
        return tutorialRepository.save(tutorial);
    }

    @Override
    public Optional<TutorialModel> updateTutorial(Long id, TutorialModel tutorial) {
        Optional <TutorialModel> tutorialData = tutorialRepository.findById(id);

        if (tutorialData.isPresent()){
            TutorialModel tutorialAtual = tutorialData.get();

            tutorialAtual.setTitle(tutorial.getTitle());
            tutorialAtual.setDescription(tutorial.getDescription());
            tutorialAtual.setPublished(tutorial.getPublished());

            TutorialModel tutorialAtualizado = tutorialRepository.save(tutorialAtual);

            return Optional.of(tutorialAtualizado);
        }

        return Optional.empty();

    }

    @Override
    public List<TutorialModel> findBytitle(String tile) {
        return tutorialRepository.findByTitle(tile);
    }

    @Override
    public void deleteTutorial(Long id){
        tutorialRepository.deleteById(id);
    }

    @Override
    public void deleteAllTutorials() {
        tutorialRepository.deleteAll();
    }

    @Override
    public Page<TutorialModel> findByPublished(Pageable peageable){
        return tutorialRepository.findByPublished(true, peageable);
    }
}
