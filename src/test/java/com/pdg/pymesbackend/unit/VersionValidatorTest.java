package com.pdg.pymesbackend.unit;

import com.pdg.pymesbackend.model.Version;
import com.pdg.pymesbackend.repository.VersionRepository;
import com.pdg.pymesbackend.service.validator.implementations.VersionValidatorImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VersionValidatorTest {
    @Mock
    VersionRepository versionRepository;
    @InjectMocks
    VersionValidatorImpl versionValidator;

    @Test
    void testValidateVersion(){
        when(versionRepository.findById("1")).thenReturn(Optional.of(Version.builder().versionId("1").name("V 2.0").build()));
        Version version = versionValidator.validateVersion("1");

        assertEquals("1", version.getVersionId());
        assertEquals("V 2.0", version.getName());

    }

    @Test
    void testNull(){

    }
}
