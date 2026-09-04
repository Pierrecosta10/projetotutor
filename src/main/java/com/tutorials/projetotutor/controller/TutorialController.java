package com.tutorials.projetotutor.controller;

import com.tutorials.projetotutor.model.TutorialModel;
import com.tutorials.projetotutor.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TutorialController {

    @Autowired
    TutorialRepository tutorialRepository;

    // Get - Buscar Todos

    @GetMapping("/tutorials")
    public ResponseEntity<List<TutorialModel>> getAllTutorials(@RequestParam(required = false) String title) {
        try {
            List<TutorialModel> tutorials;

            if (title == null)
                tutorials = tutorialRepository.findAll();
            else {
                tutorials = tutorialRepository.findPorTitle(title);
            }

            return ResponseEntity.ok(tutorials);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Get - Buscar por ID

    @GetMapping("/tutorials/{id}")
    public ResponseEntity<TutorialModel> getTutorialById(@PathVariable("id") Long id) {
        Optional<TutorialModel> tutorialData = tutorialRepository.findById(id);

        if (tutorialData.isPresent()) {
            return ResponseEntity.ok(tutorialData.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Criar

    @PostMapping("/tutorials")
    public ResponseEntity<TutorialModel> createTutorial(@RequestBody TutorialModel tutorial) {
        try {
            TutorialModel tutorialSalvo = tutorialRepository
                    .save(tutorial);

            URI location = URI.create(
                    "/api/tutorials" + tutorialSalvo.getId());

            return ResponseEntity.created(location).body(tutorialSalvo);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    //Put - Atualizar

    @PutMapping("/tutorials/{id}")
    public ResponseEntity<TutorialModel> updateTutorial(@PathVariable("id") Long id, @RequestBody TutorialModel tutorial) {

        Optional<TutorialModel> tutorialData = tutorialRepository.findById(id);

        if (tutorialData.isPresent()) {
            TutorialModel tutorialAtual = tutorialData.get();

            tutorialAtual.setTitle(tutorial.getTitle());
            tutorialAtual.setDescription(tutorial.getDescription());
            tutorialAtual.setPublished(tutorial.getPublished());

            TutorialModel tutorialAtualizado = tutorialRepository.save(tutorialAtual);

            return ResponseEntity.ok(tutorialAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get - Busca por Titulo

    @GetMapping("/tutorials/search")
    public ResponseEntity<List<TutorialModel>> findByTitle(@RequestParam String title){
        try {
            List<TutorialModel> tutorials = tutorialRepository.findPorTitle(title);

            return ResponseEntity.ok(tutorials);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Delete - Por ID
    @DeleteMapping("/tutorials/{id}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable ("id") Long id) {
        try {
            tutorialRepository.deleteById(id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Delete - Todos

    @DeleteMapping("/tutorials")
    public ResponseEntity<HttpStatus> deleteAllTutorials() {
        try {
            tutorialRepository.deleteAll();
            return ResponseEntity.notFound().build();

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Get - Buscar Publicados

    @GetMapping("/tutorials/published")
    public ResponseEntity<List<TutorialModel>> findByPublished() {
        try {
            List<TutorialModel> tutorials = tutorialRepository.findByPublished(true);

            if (tutorials.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(tutorials);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


}