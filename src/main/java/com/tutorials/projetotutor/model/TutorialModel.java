package com.tutorials.projetotutor.model;

import com.tutorials.projetotutor.relations.DetalhesTutorialModel;
import jakarta.persistence.*;

@Entity
@Table(name = "tutorials")
public class TutorialModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "published")
    private Boolean published;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "detalhes_id", unique = true)
    private DetalhesTutorialModel detalhes;

    public TutorialModel() {
    }

    public TutorialModel(Long id, String title, String description, Boolean published) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.published = published;
    }

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

    @Override
    public String toString() {
        return "TutorialModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", published=" + published +
                '}';
    }

}
