package com.pdg.pymesbackend.unit;

import com.pdg.pymesbackend.dto.ModelDTO;
import com.pdg.pymesbackend.dto.VersionDTO;
import com.pdg.pymesbackend.mapper.ModelMapper;
import com.pdg.pymesbackend.matcher.ModelMatcher;
import com.pdg.pymesbackend.model.Model;
import com.pdg.pymesbackend.model.Version;
import com.pdg.pymesbackend.repository.ModelRepository;
import com.pdg.pymesbackend.repository.VersionRepository;
import com.pdg.pymesbackend.service.modules.implementations.ModelServiceImpl;
import com.pdg.pymesbackend.service.modules.implementations.VersionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ModelServiceTest {

    @Mock
    private ModelRepository modelRepository;

    @Mock
    private VersionRepository versionRepository;

    @Mock
    private VersionServiceImpl versionService;
    @Spy
    private ModelMapper modelMapper;
    @InjectMocks
    private ModelServiceImpl modelService;


    @Test
    void testCreateModel() {
        when(modelMapper.fromCreateDTO(defaultModelCreateDTO())).thenReturn(defaultModelCreate());
        modelService.save(defaultModelCreateDTO());
        Model model1 = defaultModelCreate();
        verify(modelRepository, times(1)).save(argThat(new ModelMatcher(model1)));
    }

    @Test
    void testFindAll() {
        when(modelRepository.findAll()).thenReturn(List.of(defaultModelCreate()));
        List<Model> models = modelService.findAll();
        assertEquals(1, models.size());
    }

    @Test
    void testFindAllEmpty() {
        when(modelRepository.findAll()).thenReturn(List.of());
        List<Model> models = modelService.findAll();
        assertEquals(0, models.size());
    }

    @Test
    void testFindById() {
        when(modelRepository.findById("1")).thenReturn(java.util.Optional.of(defaultModelCreate()));
        Model model = modelService.findById("1");
        assertEquals(defaultModelCreate(), model);
    }

    @Test
    void testFindByIdNotFound() {
        when(modelRepository.findById("1")).thenReturn(java.util.Optional.empty());
        try {
            modelService.findById("1");
        } catch (Exception e) {
            assertEquals("Model not found", e.getMessage());
        }
    }

    @Test
    void testFindVersionsByModelIdEmpty() {
        when(modelRepository.findById("1")).thenReturn(java.util.Optional.of(defaultModelCreate()));
        List<Version> versions = modelService.findVersionsByModelId("1");
        assertEquals(0, versions.size());
    }

    @Test
    void testFindVersionsByModelId() {
        when(modelRepository.findById("1")).thenReturn(java.util.Optional.of(modelCreateTwo()));
        when(versionRepository.findVersionByVersionIdIn(List.of("1", "2"))).thenReturn(List.of(Version.builder().versionId("1").build(), Version.builder().versionId("2").build()));
        List<Version> versions = modelService.findVersionsByModelId("1");
        assertEquals(2, versions.size());
    }

    @Test
    void testAddVersion() {
        when(modelRepository.findById("1")).thenReturn(java.util.Optional.of(defaultModelCreate()));
        when(versionService.save(createVersionDTO())).thenReturn(createVersion());
        modelService.addVersion("1", createVersionDTO());
        Model model1 = defaultModelCreate2();
        verify(modelRepository, times(1)).save(argThat(new ModelMatcher(model1)));
    }

    @Test
    void testAddVersionNotFound() {
        when(modelRepository.findById("1")).thenReturn(java.util.Optional.empty());
        try {
            modelService.addVersion("1", createVersionDTO());
        } catch (Exception e) {
            assertEquals("Model not found", e.getMessage());
        }
    }

    @Test
    void testGetActualVersion() {
        when(modelRepository.findActiveModel()).thenReturn(defaultModelCreate());
        when(versionRepository.findById("1")).thenReturn(java.util.Optional.of(createVersion()));
        String version = modelService.getActualVersion();
        assertEquals("1", version);
    }

    @Test
    void testGetActualVersionNotFound() {
        when(modelRepository.findActiveModel()).thenReturn(defaultModelCreate());
        when(versionRepository.findById("1")).thenReturn(java.util.Optional.empty());
        try {
            String versionId = modelService.getActualVersion();
            assertNull(versionId);
        } catch (Exception e) {
            assertEquals("Version not found", e.getMessage());

        }
    }


    private VersionDTO createVersionDTO(){
        return VersionDTO.builder()
                .dimensions(List.of())
                .build();
    }

    private Version createVersion(){
        return Version.builder()
                .versionId("1")
                .active(true)
                .dimensions(List.of())
                .build();
    }

    private ModelDTO defaultModelCreateDTO() {
        return ModelDTO.builder()
                .name("Model")
                .description("Description")
                .versions(List.of())
                .build();
    }

    private Model modelCreateTwo(){
        return Model.builder()
                .name("Model")
                .description("Description")
                .versions(List.of("1", "2"))
                .build();
    }

    private Model defaultModelCreate() {
        return Model.builder()
                .modelId("1")
                .name("Model")
                .description("Description")
                .versions(List.of("1"))
                .build();
    }

    private Model defaultModelCreate2() {
        return Model.builder()
                .modelId("2")
                .name("Model")
                .description("Description")
                .versions(List.of("1"))
                .build();
    }
}
