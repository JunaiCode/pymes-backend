package com.pdg.pymesbackend.mapper;

import com.pdg.pymesbackend.dto.CompanyDTO;
import com.pdg.pymesbackend.model.Company;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    Company fromCompanyDTO(CompanyDTO company);
}
