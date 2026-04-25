package com.example.store.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class GenericModelMapper
{

    private final ModelMapper modelMapper;

    public GenericModelMapper(ModelMapper modelMapper)
    {
        this.modelMapper = modelMapper;
    }

    public <D, T> D convertToDTO(T entity, Class<D> dtoClass) {
        if (entity == null) {
            return null;
        }
        return modelMapper.map(entity, dtoClass);
    }

    public <T, D> T convertToEntity(D dto, Class<T> entityClass) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, entityClass);
    }
}
