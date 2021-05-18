package com.segieda.credit.service;

import com.segieda.credit.model.Credit;
import com.segieda.credit.model.CreditDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CreditMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "creditName", target = "creditName")
    Credit mapToEntity(CreditDto source);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "creditName", target = "creditName")
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "customer", ignore = true)
    CreditDto mapToDto(Credit source);
}
