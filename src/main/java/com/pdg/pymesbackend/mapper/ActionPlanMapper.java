package com.pdg.pymesbackend.mapper;


import com.pdg.pymesbackend.dto.ActionPlanDTO;
import com.pdg.pymesbackend.model.ActionPlan;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActionPlanMapper {
    ActionPlan fromActionPlanDTO(ActionPlanDTO actionPlanDTO);
}
