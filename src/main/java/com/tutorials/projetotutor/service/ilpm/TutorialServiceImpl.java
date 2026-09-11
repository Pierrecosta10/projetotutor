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

    public TutorialServiceImpl(
            TutorialRepository tutorialRepository, CategoriaRepository categoriaRepository, TutorialMapper tutorialMapper
    ) {
        this.tutorialRepository = tutorialRepository;
        this.categoriaRepository = categoriaRepository;
        this.tutorialMapper = tutorialMapper;
    }

    // Busca todos os tutoriais ou filtra pelo título.
    @Override
    public List<TutorialModel> getAllTuorials(String title) {

        if (title == null) {
            return tutorialRepository.findAll();
        }

        return tutorialRepository.findByTitle(title);
    }

    // Retorna todos os tutoriais.
    @Override
    public List<TutorialModel> getAllTutorials(String title) {
        return tutorialRepository.findAll();
    }

    // Salva um novo tutorial.
    @Override
    public TutorialModel createTutorial(TutorialModel tutorial) {
        return tutorialRepository.save(tutorial);
    }

    // Atualiza um tutorial pelo ID.
    @Override
    public Optional<TutorialModel> updateTutorial(Long id, TutorialModel tutorial) {

        Optional<TutorialModel> tutorialData = tutorialRepository.findById(id);

        if (tutorialData.isPresent()) {

            TutorialModel tutorialAtual = tutorialData.get();

            // Atualiza os dados do tutorial.
            tutorialAtual.setTitle(tutorial.getTitle());
            tutorialAtual.setDescription(tutorial.getDescription());
            tutorialAtual.setPublished(tutorial.getPublished());

            // Verifica se foram enviados detalhes.
            if (tutorial.getDetalhes() != null) {

                // Cria os detalhes caso ainda não existam.
                if (tutorialAtual.getDetalhes() == null) {
                    tutorialAtual.setDetalhes(new DetalhesTutorialModel());
                }

                tutorialAtual.getDetalhes().setObjetivo(tutorial.getDetalhes().getObjetivo()
                );

                tutorialAtual.getDetalhes().setDuracaoMinutos(tutorial.getDetalhes().getDuracaoMinutos()
                );
            }

            TutorialModel tutorialAtualizado = tutorialRepository.save(tutorialAtual);

            return Optional.of(tutorialAtualizado);
        }

        // Retorna vazio se não encontrar o tutorial.
        return Optional.empty();
    }

    // Busca tutoriais pelo título.
    @Override
    public List<TutorialModel> findBytitle(String tile) {
        return tutorialRepository.findByTitle(tile);
    }

    // Exclui um tutorial pelo ID.
    @Override
    public void deleteTutorial(Long id) {
        tutorialRepository.deleteById(id);
    }

    // Retorna os tutoriais publicados com paginação.
    @Override
    public Page<TutorialModel> findByPublished(Pageable peageable) {
        return tutorialRepository.findByPublished(true, peageable);
    }

    // Adiciona uma categoria a um tutorial.
    @Override
    @Transactional
    public boolean adicionarCategoria(
            Long tutorialId, Long categoriaId
    ) {

        Optional<TutorialModel> tutorialData =
                tutorialRepository.findById(tutorialId);

        Optional<CategoriaModel> categoriaData = categoriaRepository.findById(categoriaId);

        if (tutorialData.isEmpty() || categoriaData.isEmpty()) {
            return false;
        }

        TutorialModel tutorial = tutorialData.get();
        CategoriaModel categoria = categoriaData.get();

        tutorial.getCategorias().add(categoria);

        return true;
    }

    private TutorialModel atualizarDetalhes(TutorialDto dto, TutorialModel tutorial
    ) {
        DetalhesTutorialModel detalhesRecebidos = dto.getDetalhes();

        // Mantém o tutorial quando não há detalhes para atualizar.
        if (detalhesRecebidos == null) {
            return tutorial;
        }

        if (tutorial.getDetalhes() == null) {
            tutorial.setDetalhes(new DetalhesTutorialModel());
        }

        DetalhesTutorialModel detalhesAtuais = tutorial.getDetalhes();

        if (detalhesRecebidos.getObjetivo() != null) {
            detalhesAtuais.setObjetivo(
                    detalhesRecebidos.getObjetivo()
            );
        }

        if (detalhesRecebidos.getDuracaoMinutos() != null) {
            detalhesAtuais.setDuracaoMinutos(
                    detalhesRecebidos.getDuracaoMinutos()
            );
        }

        return tutorial;
    }

    // Cria um tutorial usando DTO.
    @Override
    @Transactional
    public TutorialDto createTutorial(TutorialDto dto) {

        TutorialModel tutorial = tutorialMapper.toEntity(dto);

        TutorialModel tutorialSalvo = tutorialRepository.save(tutorial);

        // Converte o Model para DTO.
        return tutorialMapper.toDto(tutorialSalvo);
    }

    // Busca um tutorial pelo ID e retorna como DTO.
    @Override
    @Transactional(readOnly = true)
    public TutorialDto getTutorialById(Long id) {

        Optional<TutorialModel> tutorialModel = tutorialRepository.findById(id);

        if(tutorialModel.isPresent()){
            TutorialModel tutorialModeResp = tutorialModel.get();
            return tutorialMapper.toDto(tutorialModeResp);
        }

        return ;
    }

    // Atualiza um tutorial usando DTO.
    @Override
    @Transactional
    public Optional<TutorialDto> updateTutorial(Long id, TutorialDto dto) {

        return tutorialRepository.findById(id).map(tutorial -> {

                    // Atualiza o Model com os dados do DTO.
                    tutorialMapper.update(dto, tutorial);

                    // Salva as alterações.
                    TutorialModel tutorialAtualizado = tutorialRepository.save(tutorial);

                    // Retorna o tutorial atualizado como DTO.
                    return tutorialMapper.toDto(tutorialAtualizado);
                });
    }
}