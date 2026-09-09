package com.tutorials.projetotutor.repository;

import com.tutorials.projetotutor.relations.CategoriaModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<CategoriaModel, Long> {
}
