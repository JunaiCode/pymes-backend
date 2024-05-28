package com.pdg.pymesbackend.mapper;

import com.pdg.pymesbackend.dto.CompanyDTO;
import com.pdg.pymesbackend.dto.RegisterDTO;
import com.pdg.pymesbackend.model.Company;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    Company fromCompanyDTO(CompanyDTO company);

    @Mapping(target = "cityId", source ="city")
    @Mapping(target = "economicSectorId", source = "economicSector")
    @Mapping(target="employees",source = "numberEmployees")
    Company fromRegisterDTO(RegisterDTO company);
}
