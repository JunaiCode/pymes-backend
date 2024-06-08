package com.pdg.pymesbackend;

import com.pdg.pymesbackend.model.*;
import com.pdg.pymesbackend.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
                                        VersionRepository versions,
                                        EvaluationRepository evaluations,
                                        EvaluationResultRepository evaluationResults,
                                        ActionPlanRepository actionplans) {

        Admin admin = Admin.builder().name("Admin").email("admin@admin.com").password("password").id("adminId").build();
        CompanyType companyType = CompanyType.builder().companyTypeId("MICRO").name("Micro Empresa").description("Menos de 10 empleados").build();
        Company company = Company.builder().companyId("companyId").companyType(companyType).employees(20).address("Carrera24").name("Emcali").economicSectorId("4").cityId("1").nit("2032030345").legalRep("Juan")
                .legalRepEmail("juan@hotmail.com").legalRepTel("1234567892").tel("9876543210").password("123").creationDate(LocalDateTime.now()).evaluations(new ArrayList<>()).build();
        ArrayList<String> evaluationsCompany2 = new ArrayList<>();
        evaluationsCompany2.add("Evaluation1Id");
        Company company2 = Company.builder().companyId("companyId2").companyType(companyType).employees(20).address("Carrera25").name("Alkosto").economicSectorId("4").cityId("1").nit("2032030345").legalRep("Pedro")
                .legalRepEmail("pedro@hotmail.com").legalRepTel("1234567892").tel("9876543210").password("123").creationDate(LocalDateTime.now()).evaluations(evaluationsCompany2).build();
        Company company3 = Company.builder().companyId("companyId3").companyType(companyType).employees(20).address("Carrera48").name("Unico").economicSectorId("4").cityId("1").nit("2032030345").legalRep("Pablo")
                .legalRepEmail("pablo@hotmail.com").legalRepTel("1234567892").tel("9876543210").password("123").creationDate(LocalDateTime.now()).evaluations(new ArrayList<>()).build();
        Level level1Tecnologia = Level.builder().levelId("level1TechId").description("Level 1 Tecnologia").name("Nivel 1").value(1).questions(new ArrayList<>()).build();
        Level level2Tecnologia = Level.builder().levelId("level2TechId").description("Level 2 Tecnologia").name("Nivel 2").value(2).questions(new ArrayList<>()).build();
        Level level1Procesos = Level.builder().levelId("level1ProcessId").description("Level 1 Procesos").name("Nivel 1").value(1).questions(new ArrayList<>()).build();
        Level level2Procesos = Level.builder().levelId("level2ProcessId").description("Level 2 Procesos").name("Nivel 2").value(2).questions(new ArrayList<>()).build();
        ArrayList<Level> levelsTecnologia = new ArrayList<>();
        ArrayList<Level> levelsProcesos = new ArrayList<>();



        Dimension dimension1 = Dimension.builder().dimensionId("dimensionTechId").description("Tecnologia").name("Tech").levels(levelsTecnologia).build();
        Tag tagTecnologico = Tag.builder().tagId("tagTechId").description("Software").name("Software").dimensionId(dimension1.getDimensionId()).build();
        Dimension dimension2 = Dimension.builder().dimensionId("dimensionProcessId").description("Procesos").name("Process").levels(levelsTecnologia).build();
        Tag tagProcesos = Tag.builder().tagId("tagProcessId").description("Instrumentos").name("Instrumentos").dimensionId(dimension2.getDimensionId()).build();

        /*TECNOLOGIA Nivel 1*/
        Step stepQuestionTecnologia1 = Step.builder().stepId("stepQuestionTecnologia1").order(1).description("Primer Paso").build();
        Step stepQuestionTecnologia2 = Step.builder().stepId("stepQuestionTecnologia2").order(2).description("Segundo Paso").build();
        Step stepQuestionTecnologia3 = Step.builder().stepId("stepQuestionTecnologia3").order(3).description("Tercer Paso").build();
        Step stepQuestionTecnologia4 = Step.builder().stepId("stepQuestionTecnologia4").order(4).description("Cuarto Paso").build();
        ArrayList<Step> stepsTecnologia1 = new ArrayList<>();
        stepsTecnologia1.add(stepQuestionTecnologia1);
        stepsTecnologia1.add(stepQuestionTecnologia2);
        stepsTecnologia1.add(stepQuestionTecnologia3);
        stepsTecnologia1.add(stepQuestionTecnologia4);
        Recommendation recommendationQuestionTecnologia1 = Recommendation.builder().recommendationId("recommendationQuestionTecnologia1").title("Titulo pregunta 1 tecnologia").questionId("questionTecnologia1Id").description("Tecnologia Q1").steps(stepsTecnologia1).build();
        Option optionTecnologia1 = Option.builder().optionId("optionTecnologia1").value(10).description("Opcion 1").build();
        Option optionTecnologia2 = Option.builder().optionId("optionTecnologia2").value(-1).description("Opcion 2").build();
        Option optionTecnologia3 = Option.builder().optionId("optionTecnologia3").value(-2).description("Opcion 3").build();
        Option optionTecnologia4 = Option.builder().optionId("optionTecnologia4").value(-3).description("Opcion 4").build();
        List<Option> optionsQuestionTecnologia1 = new ArrayList<>();
        optionsQuestionTecnologia1.add(optionTecnologia1);
        optionsQuestionTecnologia1.add(optionTecnologia2);
        optionsQuestionTecnologia1.add(optionTecnologia3);
        optionsQuestionTecnologia1.add(optionTecnologia4);
        Question questionTecnologia1 = Question.builder().questionId("questionTecnologia1Id").question("Pregunta 1 tecnologia").tagId(tagTecnologico.getTagId()).companyTypeId(companyType.getCompanyTypeId()).scorePositive(10).dimensionId(dimension1.getDimensionId()).weight(1.0).options(optionsQuestionTecnologia1).recommendation(recommendationQuestionTecnologia1).build();
        ArrayList<String>questionsTechLevel1 = new ArrayList<>();
        questionsTechLevel1.add(questionTecnologia1.getQuestionId());
        level1Tecnologia.setQuestions(questionsTechLevel1);
        levelsTecnologia.add(level1Tecnologia);

        /*TECNOLOGIA Nivel 2*/
        Step stepQuestionTecnologiaQ2_1 = Step.builder().stepId("stepQuestionTecnologiaQ2_1").order(1).description("Primer Paso").build();
        Step stepQuestionTecnologiaQ2_2 = Step.builder().stepId("stepQuestionTecnologiaQ2_2").order(2).description("Segundo Paso").build();
        Step stepQuestionTecnologiaQ2_3 = Step.builder().stepId("stepQuestionTecnologiaQ2_3").order(3).description("Tercer Paso").build();
        Step stepQuestionTecnologiaQ2_4 = Step.builder().stepId("stepQuestionTecnologiaQ2_4").order(4).description("Cuarto Paso").build();
        ArrayList<Step> stepsTecnologia2 = new ArrayList<>();
        stepsTecnologia2.add(stepQuestionTecnologiaQ2_1);
        stepsTecnologia2.add(stepQuestionTecnologiaQ2_2);
        stepsTecnologia2.add(stepQuestionTecnologiaQ2_3);
        stepsTecnologia2.add(stepQuestionTecnologiaQ2_4);
        Recommendation recommendationQuestionTecnologia2 = Recommendation.builder().recommendationId("recommendationQuestionTecnologia2").title("Titulo pregunta 2 tecnologia").questionId("questionTecnologia2Id").description("Tecnologia Q2").steps(stepsTecnologia2).build();
        Option optionTecnologiaQ2_1 = Option.builder().optionId("optionTecnologiaQ2_1").value(10).description("Opcion 1").build();
        Option optionTecnologiaQ2_2 = Option.builder().optionId("optionTecnologiaQ2_2").value(-1).description("Opcion 2").build();
        Option optionTecnologiaQ2_3 = Option.builder().optionId("optionTecnologiaQ2_3").value(-2).description("Opcion 3").build();
        Option optionTecnologiaQ2_4 = Option.builder().optionId("optionTecnologiaQ2_4").value(-3).description("Opcion 4").build();
        List<Option> optionsQuestionTecnologia2 = new ArrayList<>();
        optionsQuestionTecnologia2.add(optionTecnologiaQ2_1);
        optionsQuestionTecnologia2.add(optionTecnologiaQ2_2);
        optionsQuestionTecnologia2.add(optionTecnologiaQ2_3);
        optionsQuestionTecnologia2.add(optionTecnologiaQ2_4);
        Question questionTecnologia2 = Question.builder().questionId("questionTecnologia2Id").question("Pregunta 2 tecnologia").tagId(tagTecnologico.getTagId()).companyTypeId(companyType.getCompanyTypeId()).scorePositive(10).dimensionId(dimension1.getDimensionId()).weight(1.0).options(optionsQuestionTecnologia2).recommendation(recommendationQuestionTecnologia2).build();
        ArrayList<String>questionsTechLevel2 = new ArrayList<>();
        questionsTechLevel2.add(questionTecnologia2.getQuestionId());
        level2Tecnologia.setQuestions(questionsTechLevel2);
        levelsTecnologia.add(level2Tecnologia);

        /*PROCESOS*/
        Step stepQuestionProcesos1 = Step.builder().stepId("stepQuestionProcesos1").order(1).description("Primer Paso").build();
        Step stepQuestionProcesos2 = Step.builder().stepId("stepQuestionProcesos2").order(2).description("Segundo Paso").build();
        Step stepQuestionProcesos3 = Step.builder().stepId("stepQuestionProcesos3").order(3).description("Tercer Paso").build();
        Step stepQuestionProcesos4 = Step.builder().stepId("stepQuestionProcesos4").order(4).description("Cuarto Paso").build();
        ArrayList<Step> stepsProcesos1 = new ArrayList<>();
        stepsProcesos1.add(stepQuestionProcesos1);
        stepsProcesos1.add(stepQuestionProcesos2);
        stepsProcesos1.add(stepQuestionProcesos3);
        stepsProcesos1.add(stepQuestionProcesos4);
        Recommendation recommendationQuestionProcesos1 = Recommendation.builder().recommendationId("recommendationQuestionProcesos1").title("Titulo pregunta 1 Procesos").questionId("questionProcess1Id").description("Procesos Q1").steps(stepsProcesos1).build();
        Option optionProcesos1 = Option.builder().optionId("optionProcesos1").value(10).description("Opcion 1").build();
        Option optionProcesos2 = Option.builder().optionId("optionProcesos2").value(-1).description("Opcion 2").build();
        Option optionProcesos3 = Option.builder().optionId("optionProcesos3").value(-2).description("Opcion 3").build();
        Option optionProcesos4 = Option.builder().optionId("optionProcesos4").value(-3).description("Opcion 4").build();
        List<Option> optionsQuestionProcesos1 = new ArrayList<>();
        optionsQuestionProcesos1.add(optionProcesos1);
        optionsQuestionProcesos1.add(optionProcesos2);
        optionsQuestionProcesos1.add(optionProcesos3);
        optionsQuestionProcesos1.add(optionProcesos4);
        Question questionProcesos1 = Question.builder().questionId("questionProcess1Id").question("Pregunta 1 Procesos").tagId(tagProcesos.getTagId()).companyTypeId(companyType.getCompanyTypeId()).scorePositive(10).dimensionId(dimension2.getDimensionId()).weight(1.0).options(optionsQuestionProcesos1).recommendation(recommendationQuestionProcesos1).build();
        ArrayList<String>questionsProcessLevel1 = new ArrayList<>();
        questionsProcessLevel1.add(questionProcesos1.getQuestionId());
        level1Procesos.setQuestions(questionsProcessLevel1);
        levelsProcesos.add(level1Procesos);

        /*PROCESOS Nivel 2*/
        Step stepQuestionProcesosQ2_1 = Step.builder().stepId("stepQuestionProcesosQ2_1").order(1).description("Primer Paso").build();
        Step stepQuestionProcesosQ2_2 = Step.builder().stepId("stepQuestionProcesosQ2_2").order(2).description("Segundo Paso").build();
        Step stepQuestionProcesosQ2_3 = Step.builder().stepId("stepQuestionProcesosQ2_3").order(3).description("Tercer Paso").build();
        Step stepQuestionProcesosQ2_4 = Step.builder().stepId("stepQuestionProcesosQ2_4").order(4).description("Cuarto Paso").build();
        ArrayList<Step> stepsProcesos2 = new ArrayList<>();
        stepsProcesos2.add(stepQuestionProcesosQ2_1);
        stepsProcesos2.add(stepQuestionProcesosQ2_2);
        stepsProcesos2.add(stepQuestionProcesosQ2_3);
        stepsProcesos2.add(stepQuestionProcesosQ2_4);
        Recommendation recommendationQuestionProcesos2 = Recommendation.builder().recommendationId("recommendationQuestionProcesos2").title("Titulo pregunta 2 Procesos").questionId("questionProcess2").description("Procesos Q2").steps(stepsProcesos2).build();
        Option optionProcesosQ2_1 = Option.builder().optionId("optionProcesosQ2_1").value(10).description("Opcion 1").build();
        Option optionProcesosQ2_2 = Option.builder().optionId("optionProcesosQ2_2").value(-1).description("Opcion 2").build();
        Option optionProcesosQ2_3 = Option.builder().optionId("optionProcesosQ2_3").value(-2).description("Opcion 3").build();
        Option optionProcesosQ2_4 = Option.builder().optionId("optionProcesosQ2_4").value(-3).description("Opcion 4").build();
        List<Option> optionsQuestionProcesos2 = new ArrayList<>();
        optionsQuestionProcesos2.add(optionProcesosQ2_1);
        optionsQuestionProcesos2.add(optionProcesosQ2_2);
        optionsQuestionProcesos2.add(optionProcesosQ2_3);
        optionsQuestionProcesos2.add(optionProcesosQ2_4);
        Question questionProcesos2 = Question.builder().questionId("questionProcess2").question("Pregunta 2 Procesos").tagId(tagProcesos.getTagId()).companyTypeId(companyType.getCompanyTypeId()).scorePositive(10).dimensionId(dimension2.getDimensionId()).weight(1.0).options(optionsQuestionProcesos2).recommendation(recommendationQuestionProcesos2).build();
        ArrayList<String>questionsProcessLevel2 = new ArrayList<>();
        questionsProcessLevel2.add(questionProcesos2.getQuestionId());
        level2Procesos.setQuestions(questionsProcessLevel2);
        levelsProcesos.add(level2Procesos);

        dimension1.setLevels(levelsTecnologia);
        dimension2.setLevels(levelsProcesos);
        ArrayList<Dimension> dimensionsVersion = new ArrayList<>();
        dimensionsVersion.add(dimension1);
        dimensionsVersion.add(dimension2);
        Version version1 = Version.builder().versionId("Version1Id").name("V1").active(true).dimensions(dimensionsVersion).build();
        ArrayList<String> versionsModel = new ArrayList<>();
        versionsModel.add(version1.getVersionId());
        Model model = Model.builder().modelId("modelId").active(true).description("Model").name("Modelo 1").versions(versionsModel).build();

        /*EVALUATION*/
        ArrayList<Recommendation> recommendationsPlan = new ArrayList<>();
        recommendationsPlan.add(recommendationQuestionTecnologia1);
        recommendationsPlan.add(recommendationQuestionProcesos1);
        ArrayList<RecommendationActionPlan> recommendationActionPlans = new ArrayList<>();
        RecommendationActionPlan recoPlan = RecommendationActionPlan.builder().recommendationActionPlanId("RecommendationPlanId1").completed(false).date(LocalDateTime.now()).step(recommendationQuestionTecnologia1.getSteps().get(0)).recommendationId(recommendationQuestionTecnologia1.getRecommendationId()).build();
        RecommendationActionPlan recoPlan2 = RecommendationActionPlan.builder().recommendationActionPlanId("RecommendationPlanId2").completed(false).date(LocalDateTime.now()).step(recommendationQuestionProcesos1.getSteps().get(0)).recommendationId(recommendationQuestionProcesos1.getRecommendationId()).build();
        recommendationActionPlans.add(recoPlan);
        ActionPlan actionPlan = ActionPlan.builder().actionPlanId("ActionPlan1Id").recommendationActionPlans(recommendationActionPlans).recommendations(recommendationsPlan).start(LocalDateTime.now()).end(LocalDateTime.now()).build();
        EvaluationResult evaluationResult1 = new EvaluationResult("evaluationResult1Id","dimensionTechId","questionTecnologia1Id","optionTecnologia1",false);
        EvaluationResult evaluationResult2 = new EvaluationResult("evaluationResult2Id","dimensionProcessId","questionProcess1Id","optionProcesos3",false);
        ArrayList<String> questionResult = new ArrayList<>();
        questionResult.add(evaluationResult1.getEvaluationResultId());
        questionResult.add(evaluationResult2.getEvaluationResultId());
        ArrayList<DimensionResult> dimensionResults = new ArrayList<>();
        DimensionResult dimensionTechResult = DimensionResult.builder().dimensionName("Tech").dimensionId("dimensionTechId").levelId("level1TechId").levelName("Nivel 1").levelValue(1).build();
        DimensionResult dimensionProcessResult = DimensionResult.builder().dimensionName("Process").dimensionId("dimensionProcessId").levelId("level1ProcessId").levelName("Nivel 1").levelValue(1).build();
        dimensionResults.add(dimensionTechResult);
        dimensionResults.add(dimensionProcessResult);
        Evaluation evaluation = Evaluation.builder().evaluationId("Evaluation1Id").date(LocalDateTime.now()).dimensionResults(dimensionResults).questionResults(questionResult).completed(false).actionPlanId(actionPlan.getActionPlanId()).build();
        Evaluation evaluation2 = Evaluation.builder().evaluationId("EvaluationCompletedId").date(LocalDateTime.now()).dimensionResults(new ArrayList<>()).questionResults(new ArrayList<>()).completed(true).actionPlanId(actionPlan.getActionPlanId()).build();


        return args -> {
            admins.deleteAll();
            companies.deleteAll();
            dimensions.deleteAll();
            tags.deleteAll();
            levels.deleteAll();
            recommendations.deleteAll();
            versions.deleteAll();
            models.deleteAll();
            questions.deleteAll();
            evaluations.deleteAll();
            actionplans.deleteAll();
            evaluationResults.deleteAll();
            evaluationResults.save(evaluationResult1);
            evaluationResults.save(evaluationResult2);
            admins.save(admin);
            companies.save(company);
            companies.save(company2);
            companies.save(company3);
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
            actionplans.save(actionPlan);
            evaluations.save(evaluation);
            evaluations.save(evaluation2);

        };
    }
}