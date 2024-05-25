package com.pdg.pymesbackend.service.modules.implementations;

import com.pdg.pymesbackend.dto.DimensionQuestionInDTO;
import com.pdg.pymesbackend.dto.out.DimensionQuestionOutDTO;
import com.pdg.pymesbackend.dto.VersionDTO;
import com.pdg.pymesbackend.error.PymeException;
import com.pdg.pymesbackend.error.PymeExceptionType;
import com.pdg.pymesbackend.mapper.VersionMapper;
import com.pdg.pymesbackend.model.Dimension;
import com.pdg.pymesbackend.model.Version;
import com.pdg.pymesbackend.repository.VersionRepository;
import com.pdg.pymesbackend.service.modules.VersionService;
import com.pdg.pymesbackend.service.validator.implementations.VersionValidatorImpl;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class VersionServiceImpl implements VersionService {

    private static final Logger logger = LoggerFactory.getLogger(VersionServiceImpl.class);


    private VersionRepository versionRepository;
    private VersionMapper versionMapper;
    private VersionValidatorImpl versionValidator;
    private QuestionServiceImpl questionService;
    @Override
    public Version save(VersionDTO version) {
        Version newVersion = versionMapper.fromDTO(version);
        newVersion.setVersionId(UUID.randomUUID().toString());
        versionRepository.findByName(newVersion.getName())
                .ifPresent(existingVersion -> {
                    throw new PymeException(PymeExceptionType.VERSION_ALREADY_EXISTS);
                });
        return versionRepository.save(newVersion);
    }

    @Override
    public void addDimension(String versionId, Dimension newDimension) {
        Version version = versionValidator.validateVersion(versionId);
        version.getDimensions().add(newDimension);
        versionRepository.save(version);
    }

    @Override
    public Version get(String id) {
        return versionValidator.validateVersion(id);
    }

    @Override
    public void findDimensionInVersionByName(String versionId, String dimensionName) {
        Version version = versionValidator.validateVersion(versionId);
        //Checks if thhere is already a dimension with the same name in the version
        //if so, throws an exception, else, returns the dimension
        version.getDimensions().stream()
                .filter(dimension -> dimension.getName().equals(dimensionName))
                .findAny()
                .ifPresent(dimension -> {
                    throw new PymeException(PymeExceptionType.DIMENSION_ALREADY_EXISTS);
                });
    }

    @Override
    public Version findVersionByDimensionId(String dimensionId) {
        return versionRepository.findVersionByDimensionId(dimensionId)
                .orElseThrow(() -> new PymeException(PymeExceptionType.VERSION_NOT_FOUND));
    }

    @Override
    public Version updateWithVersion(Version version) {
        return versionRepository.save(version);
    }

    @Override
    public List<DimensionQuestionOutDTO> getFirstQuestions(String versionId, String companyTypeId) {
        Version version = versionValidator.validateVersion(versionId);

        return version.getDimensions().stream()
                .map(dimension -> DimensionQuestionOutDTO.builder()
                        .dimensionId(dimension.getDimensionId())
                        .questions(dimension.getLevels()
                                            .stream().filter(level -> level.getValue() == 1)
                                            .map(level -> questionService.filterQuestionsByCompanyType(level.getQuestions(),companyTypeId))
                                            .flatMap(List::stream)
                                            .map(question -> questionService.mapQuestionToOutDTO(question))
                                            .toList())
                        .build())
                .toList();
    }

    @Override
    public List<DimensionQuestionOutDTO> getDimensionLevelQuestions(DimensionQuestionInDTO dimensionQuestionInDTO) {

        logger.info("Getting questions for version: " + dimensionQuestionInDTO.getVersionId());

        String versionId = dimensionQuestionInDTO.getVersionId();
        String levelId = dimensionQuestionInDTO.getLevelId();
        String companyTypeId = dimensionQuestionInDTO.getCompanyTypeId();

        Version version = versionValidator.validateVersion(versionId);

        return version.getDimensions().stream()
                .map(dimension -> DimensionQuestionOutDTO.builder()
                        .dimensionId(dimension.getDimensionId())
                        .questions(dimension.getLevels()
                                .stream().filter(level -> level.getLevelId().equals(levelId))
                                .map(level -> questionService.filterQuestionsByCompanyType(level.getQuestions(),companyTypeId))
                                .flatMap(List::stream)
                                .map(question -> questionService.mapQuestionToOutDTO(question))
                                .toList())
                        .build())
                .toList();
    }

}
