package com.pdg.pymesbackend;

import com.pdg.pymesbackend.model.*;
import com.pdg.pymesbackend.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@TestConfiguration
public class TestConfigurationData {
    @Bean
    CommandLineRunner commandLineRunner(
                                        AdminRepository admins,
                                        CompanyRepository companies,
                                        DimensionRepository dimensions,
                                        LevelRepository levels,
                                        ModelRepository models,
                                        QuestionRepository questions,
                                        RecommendationRepository recommendations,
                                        TagRepository tags,
                                        VersionRepository versions) {

        Admin admin = Admin.builder().name("Admin").email("admin@admin.com").password("password").id(UUID.randomUUID().toString()).build();
        CompanyType companyType = CompanyType.builder().companyTypeId("MICRO").description("MicroEmpresa").description("Menos de 10 empleados").build();
        Company company = Company.builder().companyId(UUID.randomUUID().toString()).companyType(companyType).employees(20).address("Carrera24").name("Emcali").economicSectorId("4").cityId("1").nit("2032030345").legalRep("Juan")
                .legalRepEmail("juan@hotmail.com").legalRepTel("1234567892").tel("9876543210").password("123").creationDate(LocalDateTime.now()).evaluations(new ArrayList<String>()).build();
        Level level1Tecnologia = Level.builder().levelId(UUID.randomUUID().toString()).description("Level 1 Tecnologia").name("Nivel 1").value(1).questions(new ArrayList<String>()).build();
        Level level2Tecnologia = Level.builder().levelId(UUID.randomUUID().toString()).description("Level 2 Tecnologia").name("Nivel 2").value(2).questions(new ArrayList<String>()).build();
        Level level1Procesos = Level.builder().levelId(UUID.randomUUID().toString()).description("Level 1 Procesos").name("Nivel 1").value(1).questions(new ArrayList<String>()).build();
        Level level2Procesos = Level.builder().levelId(UUID.randomUUID().toString()).description("Level 2 Procesos").name("Nivel 2").value(2).questions(new ArrayList<String>()).build();
        ArrayList<Level> levelsTecnologia = new ArrayList<>();
        ArrayList<Level> levelsProcesos = new ArrayList<>();



        Dimension dimension1 = Dimension.builder().dimensionId(UUID.randomUUID().toString()).description("Tecnologia").name("Tech").levels(levelsTecnologia).build();
        Tag tagTecnologico = Tag.builder().tagId(UUID.randomUUID().toString()).description("Software").name("Software").dimensionId(dimension1.getDimensionId()).build();
        Dimension dimension2 = Dimension.builder().dimensionId(UUID.randomUUID().toString()).description("Procesos").name("Process").levels(levelsTecnologia).build();
        Tag tagProcesos = Tag.builder().tagId(UUID.randomUUID().toString()).description("Instrumentos").name("Instrumentos").dimensionId(dimension2.getDimensionId()).build();

        /*TECNOLOGIA Nivel 1*/
        Step stepQuestionTecnologia1 = Step.builder().stepId(UUID.randomUUID().toString()).order(1).description("Primer Paso").build();
        Step stepQuestionTecnologia2 = Step.builder().stepId(UUID.randomUUID().toString()).order(2).description("Segundo Paso").build();
        Step stepQuestionTecnologia3 = Step.builder().stepId(UUID.randomUUID().toString()).order(3).description("Tercer Paso").build();
        Step stepQuestionTecnologia4 = Step.builder().stepId(UUID.randomUUID().toString()).order(4).description("Cuarto Paso").build();
        ArrayList<Step> stepsTecnologia1 = new ArrayList<>();
        stepsTecnologia1.add(stepQuestionTecnologia1);
        stepsTecnologia1.add(stepQuestionTecnologia2);
        stepsTecnologia1.add(stepQuestionTecnologia3);
        stepsTecnologia1.add(stepQuestionTecnologia4);
        Recommendation recommendationQuestionTecnologia1 = Recommendation.builder().recommendationId(UUID.randomUUID().toString()).title("Titulo pregunta 1 tecnologia").questionId("1234").description("Tecnologia Q1").steps(stepsTecnologia1).build();
        Option optionTecnologia1 = Option.builder().optionId(UUID.randomUUID().toString()).value(10).description("Opcion 1").build();
        Option optionTecnologia2 = Option.builder().optionId(UUID.randomUUID().toString()).value(-1).description("Opcion 2").build();
        Option optionTecnologia3 = Option.builder().optionId(UUID.randomUUID().toString()).value(-2).description("Opcion 3").build();
        Option optionTecnologia4 = Option.builder().optionId(UUID.randomUUID().toString()).value(-3).description("Opcion 4").build();
        List<Option> optionsQuestionTecnologia1 = new ArrayList<Option>();
        optionsQuestionTecnologia1.add(optionTecnologia1);
        optionsQuestionTecnologia1.add(optionTecnologia2);
        optionsQuestionTecnologia1.add(optionTecnologia3);
        optionsQuestionTecnologia1.add(optionTecnologia4);
        Question questionTecnologia1 = Question.builder().questionId("1234").question("Pregunta 1 tecnologia").tagId(tagTecnologico.getTagId()).companyTypeId(companyType.getCompanyTypeId()).scorePositive(10).dimensionId(dimension1.getDimensionId()).weight(1.0).options(optionsQuestionTecnologia1).recommendation(recommendationQuestionTecnologia1).build();
        ArrayList<String>questionsTechLevel1 = new ArrayList<>();
        questionsTechLevel1.add(questionTecnologia1.getQuestionId());
        level1Tecnologia.setQuestions(questionsTechLevel1);
        levelsTecnologia.add(level1Tecnologia);

        /*TECNOLOGIA Nivel 2*/
        Step stepQuestionTecnologiaQ2_1 = Step.builder().stepId(UUID.randomUUID().toString()).order(1).description("Primer Paso").build();
        Step stepQuestionTecnologiaQ2_2 = Step.builder().stepId(UUID.randomUUID().toString()).order(2).description("Segundo Paso").build();
        Step stepQuestionTecnologiaQ2_3 = Step.builder().stepId(UUID.randomUUID().toString()).order(3).description("Tercer Paso").build();
        Step stepQuestionTecnologiaQ2_4 = Step.builder().stepId(UUID.randomUUID().toString()).order(4).description("Cuarto Paso").build();
        ArrayList<Step> stepsTecnologia2 = new ArrayList<>();
        stepsTecnologia2.add(stepQuestionTecnologiaQ2_1);
        stepsTecnologia2.add(stepQuestionTecnologiaQ2_2);
        stepsTecnologia2.add(stepQuestionTecnologiaQ2_3);
        stepsTecnologia2.add(stepQuestionTecnologiaQ2_4);
        Recommendation recommendationQuestionTecnologia2 = Recommendation.builder().recommendationId(UUID.randomUUID().toString()).title("Titulo pregunta 2 tecnologia").questionId("123456").description("Tecnologia Q2").steps(stepsTecnologia2).build();
        Option optionTecnologiaQ2_1 = Option.builder().optionId(UUID.randomUUID().toString()).value(10).description("Opcion 1").build();
        Option optionTecnologiaQ2_2 = Option.builder().optionId(UUID.randomUUID().toString()).value(-1).description("Opcion 2").build();
        Option optionTecnologiaQ2_3 = Option.builder().optionId(UUID.randomUUID().toString()).value(-2).description("Opcion 3").build();
        Option optionTecnologiaQ2_4 = Option.builder().optionId(UUID.randomUUID().toString()).value(-3).description("Opcion 4").build();
        List<Option> optionsQuestionTecnologia2 = new ArrayList<Option>();
        optionsQuestionTecnologia1.add(optionTecnologiaQ2_1);
        optionsQuestionTecnologia1.add(optionTecnologiaQ2_2);
        optionsQuestionTecnologia1.add(optionTecnologiaQ2_3);
        optionsQuestionTecnologia1.add(optionTecnologiaQ2_4);
        Question questionTecnologia2 = Question.builder().questionId("123456").question("Pregunta 2 tecnologia").tagId(tagTecnologico.getTagId()).companyTypeId(companyType.getCompanyTypeId()).scorePositive(10).dimensionId(dimension1.getDimensionId()).weight(1.0).options(optionsQuestionTecnologia2).recommendation(recommendationQuestionTecnologia2).build();
        ArrayList<String>questionsTechLevel2 = new ArrayList<>();
        questionsTechLevel2.add(questionTecnologia2.getQuestionId());
        level1Tecnologia.setQuestions(questionsTechLevel2);
        levelsTecnologia.add(level2Tecnologia);

        /*PROCESOS*/
        Step stepQuestionProcesos1 = Step.builder().stepId(UUID.randomUUID().toString()).order(1).description("Primer Paso").build();
        Step stepQuestionProcesos2 = Step.builder().stepId(UUID.randomUUID().toString()).order(2).description("Segundo Paso").build();
        Step stepQuestionProcesos3 = Step.builder().stepId(UUID.randomUUID().toString()).order(3).description("Tercer Paso").build();
        Step stepQuestionProcesos4 = Step.builder().stepId(UUID.randomUUID().toString()).order(4).description("Cuarto Paso").build();
        ArrayList<Step> stepsProcesos1 = new ArrayList<>();
        stepsProcesos1.add(stepQuestionProcesos1);
        stepsProcesos1.add(stepQuestionProcesos2);
        stepsProcesos1.add(stepQuestionProcesos3);
        stepsProcesos1.add(stepQuestionProcesos4);
        Recommendation recommendationQuestionProcesos1 = Recommendation.builder().recommendationId(UUID.randomUUID().toString()).title("Titulo pregunta 1 Procesos").questionId("12345").description("Procesos Q1").steps(stepsProcesos1).build();
        Option optionProcesos1 = Option.builder().optionId(UUID.randomUUID().toString()).value(10).description("Opcion 1").build();
        Option optionProcesos2 = Option.builder().optionId(UUID.randomUUID().toString()).value(-1).description("Opcion 2").build();
        Option optionProcesos3 = Option.builder().optionId(UUID.randomUUID().toString()).value(-2).description("Opcion 3").build();
        Option optionProcesos4 = Option.builder().optionId(UUID.randomUUID().toString()).value(-3).description("Opcion 4").build();
        List<Option> optionsQuestionProcesos1 = new ArrayList<Option>();
        optionsQuestionTecnologia1.add(optionProcesos1);
        optionsQuestionTecnologia1.add(optionProcesos2);
        optionsQuestionTecnologia1.add(optionProcesos3);
        optionsQuestionTecnologia1.add(optionProcesos4);
        Question questionProcesos1 = Question.builder().questionId("12345").question("Pregunta 1 Procesos").tagId(tagProcesos.getTagId()).companyTypeId(companyType.getCompanyTypeId()).scorePositive(10).dimensionId(dimension2.getDimensionId()).weight(1.0).options(optionsQuestionProcesos1).recommendation(recommendationQuestionProcesos1).build();
        ArrayList<String>questionsProcessLevel1 = new ArrayList<>();
        questionsProcessLevel1.add(questionProcesos1.getQuestionId());
        level1Procesos.setQuestions(questionsProcessLevel1);
        levelsProcesos.add(level1Procesos);

        /*PROCESOS Nivel 2*/
        Step stepQuestionProcesosQ2_1 = Step.builder().stepId(UUID.randomUUID().toString()).order(1).description("Primer Paso").build();
        Step stepQuestionProcesosQ2_2 = Step.builder().stepId(UUID.randomUUID().toString()).order(2).description("Segundo Paso").build();
        Step stepQuestionProcesosQ2_3 = Step.builder().stepId(UUID.randomUUID().toString()).order(3).description("Tercer Paso").build();
        Step stepQuestionProcesosQ2_4 = Step.builder().stepId(UUID.randomUUID().toString()).order(4).description("Cuarto Paso").build();
        ArrayList<Step> stepsProcesos2 = new ArrayList<>();
        stepsProcesos2.add(stepQuestionProcesosQ2_1);
        stepsProcesos2.add(stepQuestionProcesosQ2_2);
        stepsProcesos2.add(stepQuestionProcesosQ2_3);
        stepsProcesos2.add(stepQuestionProcesosQ2_4);
        Recommendation recommendationQuestionProcesos2 = Recommendation.builder().recommendationId(UUID.randomUUID().toString()).title("Titulo pregunta 2 Procesos").questionId("1234567").description("Procesos Q2").steps(stepsProcesos2).build();
        Option optionProcesosQ2_1 = Option.builder().optionId(UUID.randomUUID().toString()).value(10).description("Opcion 1").build();
        Option optionProcesosQ2_2 = Option.builder().optionId(UUID.randomUUID().toString()).value(-1).description("Opcion 2").build();
        Option optionProcesosQ2_3 = Option.builder().optionId(UUID.randomUUID().toString()).value(-2).description("Opcion 3").build();
        Option optionProcesosQ2_4 = Option.builder().optionId(UUID.randomUUID().toString()).value(-3).description("Opcion 4").build();
        List<Option> optionsQuestionProcesos2 = new ArrayList<Option>();
        optionsQuestionProcesos2.add(optionProcesosQ2_1);
        optionsQuestionProcesos2.add(optionProcesosQ2_2);
        optionsQuestionProcesos2.add(optionProcesosQ2_3);
        optionsQuestionProcesos2.add(optionProcesosQ2_4);
        Question questionProcesos2 = Question.builder().questionId("1234567").question("Pregunta 2 Procesos").tagId(tagProcesos.getTagId()).companyTypeId(companyType.getCompanyTypeId()).scorePositive(10).dimensionId(dimension2.getDimensionId()).weight(1.0).options(optionsQuestionProcesos2).recommendation(recommendationQuestionProcesos2).build();
        ArrayList<String>questionsProcessLevel2 = new ArrayList<>();
        questionsProcessLevel2.add(questionProcesos2.getQuestionId());
        level2Procesos.setQuestions(questionsProcessLevel2);
        levelsProcesos.add(level2Procesos);

        dimension1.setLevels(levelsTecnologia);
        dimension2.setLevels(levelsProcesos);
        ArrayList<Dimension> dimensionsVersion = new ArrayList<>();
        dimensionsVersion.add(dimension1);
        dimensionsVersion.add(dimension2);
        Version version1 = Version.builder().versionId(UUID.randomUUID().toString()).name("V1").active(true).dimensions(dimensionsVersion).build();
        ArrayList<String> versionsModel = new ArrayList<>();
        versionsModel.add(version1.getVersionId());
        Model model = Model.builder().modelId(UUID.randomUUID().toString()).active(true).description("Model").versions(versionsModel).build();

        return args -> {
            admins.save(admin);
            companies.save(company);
            dimensions.save(dimension1);
            dimensions.save(dimension2);
            tags.save(tagProcesos);
            tags.save(tagTecnologico);
            levels.save(level1Tecnologia);
            levels.save(level1Procesos);
            levels.save(level2Procesos);
            levels.save(level2Tecnologia);
            recommendations.save(recommendationQuestionTecnologia1);
            recommendations.save(recommendationQuestionTecnologia2);
            recommendations.save(recommendationQuestionProcesos1);
            recommendations.save(recommendationQuestionProcesos2);
            versions.save(version1);
            models.save(model);
            questions.save(questionTecnologia1);
            questions.save(questionTecnologia2);
            questions.save(questionProcesos1);
            questions.save(questionProcesos2);
        };
    }
}