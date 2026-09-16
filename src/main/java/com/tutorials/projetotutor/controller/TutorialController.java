package com.tutorials.projetotutor.controller;

import com.tutorials.projetotutor.dto.TutorialDto;
import com.tutorials.projetotutor.model.TutorialModel;
import com.tutorials.projetotutor.repository.TutorialRepository;
import com.tutorials.projetotutor.service.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class TutorialController {

    @Autowired
    TutorialRepository tutorialRepository;
    @Autowired
    private TutorialService tutorialService;

    // Get - Buscar Todos

    @GetMapping("/tutorials")
    public ResponseEntity<List<TutorialModel>> getAllTutorials(
            @RequestParam(required = false) String title) {
        return ResponseEntity.ok(tutorialService.getAllTutorials(title)
        );
    }

    // Get - Buscar por ID

    @GetMapping("/tutorials/{id}")
    public ResponseEntity<TutorialDto> getTutorialById(@PathVariable("id") Long id) {

        TutorialDto tutorial = tutorialService.getTutorialById(id);

        return ResponseEntity.ok(tutorial);
    }

    // Criar

    @PostMapping("/tutorials")
    public ResponseEntity<TutorialDto> createTutorial(@RequestBody TutorialDto dto) {
        TutorialDto tutorialSalvo = tutorialService.createTutorial(dto);

        URI location = URI.create("/api/tutorials/" + tutorialSalvo.getId()
        );

        return ResponseEntity.created(location).body(tutorialSalvo);
    }


    //Put - Atualizar

    @PutMapping("/tutorials/{id}")
    public ResponseEntity<TutorialDto> updateTutorial(@PathVariable("id") Long id, @RequestBody TutorialDto dto) {
        TutorialDto tutorialAtualizado = tutorialService.updateTutorial(id, dto);

        return ResponseEntity.ok(tutorialAtualizado);
    }


    @PutMapping("/tutorials/{tutorialId}/categorias/{categoriaId}")
    public ResponseEntity<TutorialDto> adicionarCategoria(@PathVariable("tutorialId") Long tutorialId, @PathVariable("categoriaId") Long categoriaId) {
        TutorialDto tutorialDto = tutorialService.adicionarCategoria(tutorialId, categoriaId);

        return ResponseEntity.ok(tutorialDto);
    }

    // Get - Busca por Titulo
    @GetMapping("/tutorials/search")
    public ResponseEntity<List<TutorialModel>> findByTitle(@RequestParam String title) {
        return ResponseEntity.ok(tutorialService.findByTitle(title)
        );
    }

    // Delete - Por ID
    @DeleteMapping("/tutorials/{id}")
    public ResponseEntity<Void> deleteTutorial(@PathVariable("id") Long id) {
        tutorialService.deleteTutorial(id);

        return ResponseEntity.noContent().build();
    }

    // Get - Buscar Publicados
    @GetMapping("/tutorials/published")
    public ResponseEntity<Page<TutorialModel>> findByPublished(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(tutorialService.findByPublished(pageable));
    }
}