package com.tutorials.projetotutor.mapper;

import org.mapstruct.MappingTarget;

public abstract class TutorialBaseMapper <D, E> {

  public abstract D toDto(E entity);

  public abstract E toEntity(D dto);

  public abstract void update(D dto, @MappingTarget E entity);
}
