package com.onlinestore.auth2.mappers;

import com.onlinestore.auth2.domain.RoleEntity;
import com.onlinestore.auth2.model.RoleDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {DateMapper.class})
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleDto toRoleDto(RoleEntity role);
    RoleEntity toRoleEntity(RoleDto dto);
}
