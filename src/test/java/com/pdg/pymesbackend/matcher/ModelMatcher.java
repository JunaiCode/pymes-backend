package com.pdg.pymesbackend.matcher;

import com.pdg.pymesbackend.model.Model;
import org.mockito.ArgumentMatcher;

public class ModelMatcher implements ArgumentMatcher<Model> {

    private final Model leftModel;

    public ModelMatcher(Model model) {
        leftModel = model;
    }
    @Override
    public boolean matches(Model model) {
        return  model.getModelId() != null &&
                model.getName().equals(leftModel.getName()) &&
                model.getDescription().equals(leftModel.getDescription());
    }
}
