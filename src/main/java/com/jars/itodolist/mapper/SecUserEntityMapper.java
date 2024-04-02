package com.jars.itodolist.mapper;

import com.example.model.SecUser;
import com.jars.itodolist.model.SecUserEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SecUserEntityMapper {
    SecUserEntity toEntity(SecUser secUser);

    SecUser toDto(SecUserEntity secUserEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SecUserEntity partialUpdate(SecUser secUser, @MappingTarget SecUserEntity secUserEntity);
}