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
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TutorialController {

    @Autowired
    TutorialRepository tutorialRepository;
    @Autowired
    private TutorialService tutorialService;

    // Get - Buscar Todos

    @GetMapping("/tutorials")
    public ResponseEntity<List<TutorialModel>> getAllTutorials(@RequestParam(required = false) String title) {
        try {
            List<TutorialModel> tutorials = tutorialService.getAllTutorials(title);

            return ResponseEntity.ok(tutorials);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
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
    public ResponseEntity<TutorialDto> updateTutorial(@PathVariable("id") Long id, @RequestBody TutorialDto dto
    ) {
        return tutorialService.updateTutorial(id, dto).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/tutorials/{tutorialId}/categorias/{categoriaId}")
    public ResponseEntity<TutorialDto> adicionarCategoria(@PathVariable("tutorialId") Long tutorialId, @PathVariable("categoriaId") Long categoriaId) {
        boolean associado = tutorialService.adicionarCategoria(tutorialId, categoriaId);

        if (!associado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    // Get - Busca por Titulo

    @GetMapping("/tutorials/search")
    public ResponseEntity<List<TutorialModel>> findByTitle(@RequestParam String title){
        try {
            List<TutorialModel> tutorials = tutorialService.findBytitle(title);

            return ResponseEntity.ok(tutorials);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Delete - Por ID
    @DeleteMapping("/tutorials/{id}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable ("id") Long id) {
        try {
            tutorialService.deleteTutorial(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    // Get - Buscar Publicados

    @GetMapping("/tutorials/published")
    public ResponseEntity<Page<TutorialModel>> findByPublished(@PageableDefault(size = 10)Pageable peageable
    ) {
        try {
            Page<TutorialModel> tutorials = tutorialService.findByPublished(peageable);

            if (tutorials.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(tutorials);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}