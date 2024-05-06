package com.pdg.pymesbackend.api;

import com.pdg.pymesbackend.dto.OptionDTO;
import com.pdg.pymesbackend.dto.QuestionDTO;
import com.pdg.pymesbackend.dto.RecommendationDTO;
import com.pdg.pymesbackend.model.Question;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.pdg.pymesbackend.api.QuestionAPI.BASE_URL;

@RequestMapping(BASE_URL)
public interface QuestionAPI {
    String BASE_URL = "/question";

    @PostMapping("/add")
    Question createQuestion(@RequestBody @Valid QuestionDTO question);

    @PostMapping("/add/recommendation/{questionId}")
    Question addRecommendation(@RequestBody @Valid RecommendationDTO recommendation, @PathVariable String questionId);

    @PutMapping("/add/option/{questionId}")
    Question addOption(@RequestBody @Valid OptionDTO option, @PathVariable String questionId);

    @DeleteMapping("/delete/{id}")
    void deleteQuestion(@PathVariable String id);

    @GetMapping("/get/all")
    List<Question> getQuestions();

    @GetMapping("/get/{id}")
    Question getQuestion(@PathVariable String id);

    @GetMapping("/get/tag/{tag}")
    List<Question> getQuestionsByTag(@PathVariable String tag);
}
