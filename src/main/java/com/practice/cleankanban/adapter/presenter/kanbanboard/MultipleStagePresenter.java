package com.practice.cleankanban.adapter.presenter.kanbanboard;

import com.practice.cleankanban.usecase.kanbanboard.stage.StageDto;
import com.practice.cleankanban.usecase.kanbanboard.stage.get.GetStagesOutput;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class MultipleStagePresenter implements GetStagesOutput {
    private List<StageDto> stages;


    public MultipleStagePresenter() {
        super();
        stages = new ArrayList<>();
    }


    @Override
    public List<StageDto> getStages() {
        return stages;
    }

    @Override
    public void setStages(List<StageDto> stages) {
        this.stages = stages;
    }


}
