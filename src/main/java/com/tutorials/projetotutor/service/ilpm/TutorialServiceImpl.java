package com.tutorials.projetotutor.service.ilpm;

import com.tutorials.projetotutor.dto.TutorialDto;
import com.tutorials.projetotutor.exception.RecursoNaoEncontradoException;
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

@Service
public class TutorialServiceImpl implements TutorialService {

    private final TutorialRepository tutorialRepository;
    private final CategoriaRepository categoriaRepository;
    private final TutorialMapper tutorialMapper;

    public TutorialServiceImpl(
            TutorialRepository tutorialRepository,
            CategoriaRepository categoriaRepository,
            TutorialMapper tutorialMapper
    ) {
        this.tutorialRepository = tutorialRepository;
        this.categoriaRepository = categoriaRepository;
        this.tutorialMapper = tutorialMapper;
    }

    // Busca todos ou filtra pelo título.
    @Override
    @Transactional(readOnly = true)
    public List<TutorialModel> getAllTutorials(String title) {
        if (title == null) {
            return tutorialRepository.findAll();
        }

        return tutorialRepository.findByTitle(title);
    }

    // Busca pelo ID e retorna o DTO.
    @Override
    @Transactional(readOnly = true)
    public TutorialDto getTutorialById(Long id) {
        TutorialModel tutorial = buscarTutorialOuFalhar(id);

        return tutorialMapper.toDto(tutorial);
    }

    // Cria um tutorial com os dados do DTO.
    @Override
    @Transactional
    public TutorialDto createTutorial(TutorialDto dto) {
        TutorialModel tutorial = tutorialMapper.toEntity(dto);

        // Define como não publicado quando o valor não é informado.
        if (dto.getPublished() == null) {
            tutorial.setPublished(false);
        }

        TutorialModel tutorialComDetalhes =
                atualizarDetalhes(dto, tutorial);

        TutorialModel tutorialSalvo =
                tutorialRepository.save(tutorialComDetalhes);

        return tutorialMapper.toDto(tutorialSalvo);
    }

    // Atualiza um tutorial existente.
    @Override
    @Transactional
    public TutorialDto updateTutorial(Long id, TutorialDto dto) {
        TutorialModel tutorialAtual = buscarTutorialOuFalhar(id);

        // Atualiza os campos simples.
        tutorialMapper.update(dto, tutorialAtual);

        // Atualiza os detalhes.
        TutorialModel tutorialComDetalhes =
                atualizarDetalhes(dto, tutorialAtual);

        TutorialModel tutorialSalvo =
                tutorialRepository.save(tutorialComDetalhes);

        return tutorialMapper.toDto(tutorialSalvo);
    }

    // Busca tutoriais pelo título.
    @Override
    @Transactional(readOnly = true)
    public List<TutorialModel> findByTitle(String title) {
        return tutorialRepository.findByTitle(title);
    }

    // Busca os publicados com paginação.
    @Override
    @Transactional(readOnly = true)
    public Page<TutorialModel> findByPublished(Pageable pageable) {
        return tutorialRepository.findByPublished(true, pageable);
    }

    // Associa uma categoria e retorna o tutorial atualizado.
    @Override
    @Transactional
    public TutorialDto adicionarCategoria(
            Long tutorialId, Long categoriaId) {
        TutorialModel tutorial = buscarTutorialOuFalhar(tutorialId);

        CategoriaModel categoria =
                categoriaRepository.findById(categoriaId)
                        .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria", categoriaId)
                        );

        tutorial.getCategorias().add(categoria);

        // O vínculo é gravado ao concluir a transação.
        return tutorialMapper.toDto(tutorial);
    }

    // Exclui um tutorial existente.
    @Override
    @Transactional
    public void deleteTutorial(Long id) {
        TutorialModel tutorial = buscarTutorialOuFalhar(id);

        tutorialRepository.delete(tutorial);
    }

    // Busca o tutorial ou lança a exceção.
    private TutorialModel buscarTutorialOuFalhar(Long id) {
        return tutorialRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Tutorial", id)
                );
    }

    // Preenche os detalhes e retorna o tutorial.
    private TutorialModel atualizarDetalhes(
            TutorialDto dto,
            TutorialModel tutorial
    ) {
        DetalhesTutorialModel detalhesRecebidos = dto.getDetalhes();

        // Mantém os detalhes atuais quando não forem enviados.
        if (detalhesRecebidos == null) {
            return tutorial;
        }

        // Cria os detalhes caso ainda não existam.
        if (tutorial.getDetalhes() == null) {
            tutorial.setDetalhes(new DetalhesTutorialModel());
        }

        DetalhesTutorialModel detalhesAtuais =
                tutorial.getDetalhes();

        // Atualiza os campos sem alterar o ID.
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
}