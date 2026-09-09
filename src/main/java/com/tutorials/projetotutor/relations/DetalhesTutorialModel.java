package com.tutorials.projetotutor.relations;

import jakarta.persistence.*;

@Entity
@Table(name = "detalhes_tutorial")
public class DetalhesTutorialModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String objetivo;

    @Column(name = "duracao_minutos")
    private Integer duracaoMinutos;

    public DetalhesTutorialModel(){
    }

    public DetalhesTutorialModel(Long id, String objetivo, Integer duracaoMinutos) {
        this.id = id;
        this.objetivo = objetivo;
        this.duracaoMinutos = duracaoMinutos;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public Integer getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public void setDuracaoMinutos(Integer duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }
}
