package com.tutorials.projetotutor.mapper;

import com.tutorials.projetotutor.dto.TutorialDto;
import com.tutorials.projetotutor.model.TutorialModel;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public abstract class TutorialMapper
        extends TutorialBaseMapper<TutorialDto, TutorialModel> {

    @Override
    public abstract TutorialDto toDto(TutorialModel entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "detalhes", ignore = true)
    @Mapping(target = "categorias", ignore = true)
    public abstract TutorialModel toEntity(TutorialDto dto);

    @Override
    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "detalhes", ignore = true)
    @Mapping(target = "categorias", ignore = true)
    public abstract void update(
            TutorialDto dto,
            @MappingTarget TutorialModel entity
    );
}