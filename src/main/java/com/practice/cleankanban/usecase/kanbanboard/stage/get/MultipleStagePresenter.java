package com.practice.cleankanban.usecase.kanbanboard.stage.get;

import com.practice.cleankanban.domain.model.kanbanboard.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class MultipleStagePresenter implements GetStagesOutput {
    private List<Stage> stages;


    public MultipleStagePresenter() {
        super();
        stages = new ArrayList<>();
    }
    @Override
    public List<Stage> getStages() {
        return stages;
    }

    @Override
    public void setStages(List<Stage> stages) {
        this.stages = stages;
    }
}
