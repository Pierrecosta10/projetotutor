package com.tutorials.projetotutor.service.ilpm;

import com.tutorials.projetotutor.dto.TutorialDto;
import com.tutorials.projetotutor.mapper.TutorialMapper;
import com.tutorials.projetotutor.model.TutorialModel;
import com.tutorials.projetotutor.relations.CategoriaModel;
import com.tutorials.projetotutor.relations.DetalhesTutorialModel;
import com.tutorials.projetotutor.repository.CategoriaRepository;
import com.tutorials.projetotutor.repository.TutorialRepository;
import com.tutorials.projetotutor.service.TutorialService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TutorialServiceImpl implements TutorialService {
    private final TutorialRepository tutorialRepository;
    private final CategoriaRepository categoriaRepository;
    private final TutorialMapper tutorialMapper;

    public TutorialServiceImpl(TutorialRepository tutorialRepository, CategoriaRepository categoriaRepository, TutorialMapper tutorialMapper) {
        this.tutorialRepository = tutorialRepository;
        this.categoriaRepository = categoriaRepository;
        this.tutorialMapper = tutorialMapper;
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

            if (tutorial.getDetalhes() != null) {

                // Cria os detalhes se o tutorial ainda não possuir um registro.
                if (tutorialAtual.getDetalhes() == null) {
                    tutorialAtual.setDetalhes(new DetalhesTutorialModel());
                }

                tutorialAtual.getDetalhes().setObjetivo(tutorial.getDetalhes().getObjetivo());

                tutorialAtual.getDetalhes().setDuracaoMinutos(
                        tutorial.getDetalhes().getDuracaoMinutos()
                );
            }

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
    public Page<TutorialModel> findByPublished(Pageable peageable){
        return tutorialRepository.findByPublished(true, peageable);
    }

    @Override
    @Transactional
    public boolean adicionarCategoria(Long tutorialId, Long categoriaId) {

        Optional<TutorialModel> tutorialData =
                tutorialRepository.findById(tutorialId);

        Optional<CategoriaModel> categoriaData =
                categoriaRepository.findById(categoriaId);

        if (tutorialData.isEmpty() || categoriaData.isEmpty()) {
            return false;
        }

        TutorialModel tutorial = tutorialData.get();
        CategoriaModel categoria = categoriaData.get();

        tutorial.getCategorias().add(categoria);

        return true;
    }

    @Override
    @Transactional
    public TutorialDto createTutorial(TutorialDto dto) {
        TutorialModel tutorial = tutorialMapper.toEntity(dto);

        TutorialModel tutorialSalvo =
                tutorialRepository.save(tutorial);

        return tutorialMapper.toDto(tutorialSalvo);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TutorialDto> getTutorialById(Long id) {
        return tutorialRepository.findById(id)
                .map(tutorialMapper::toDto);
    }

    @Override
    @Transactional
    public Optional<TutorialDto> updateTutorial(
            Long id,
            TutorialDto dto
    ) {
        return tutorialRepository.findById(id)
                .map(tutorial -> {
                    tutorialMapper.update(dto, tutorial);

                    TutorialModel tutorialAtualizado =
                            tutorialRepository.save(tutorial);

                    return tutorialMapper.toDto(tutorialAtualizado);
                });
    }
}
