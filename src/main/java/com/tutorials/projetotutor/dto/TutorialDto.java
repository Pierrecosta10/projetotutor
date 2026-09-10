package com.tutorials.projetotutor.dto;

import com.tutorials.projetotutor.relations.CategoriaModel;
import com.tutorials.projetotutor.relations.DetalhesTutorialModel;

import java.util.HashSet;
import java.util.Set;

public class TutorialDto {
    private Long id;
    private String title;
    private String description;
    private Boolean published;
    private DetalhesTutorialModel detalhes;
    private Set<CategoriaModel> categorias = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public DetalhesTutorialModel getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(DetalhesTutorialModel detalhes) {
        this.detalhes = detalhes;
    }

    public Set<CategoriaModel> getCategorias() {
        return categorias;
    }

    public void setCategorias(Set<CategoriaModel> categorias) {
        this.categorias = categorias;
    }
}
