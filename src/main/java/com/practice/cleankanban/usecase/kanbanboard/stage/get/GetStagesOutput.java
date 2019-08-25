package com.practice.cleankanban.usecase.kanbanboard.stage.get;

import com.practice.cleankanban.domain.model.kanbanboard.stage.Stage;
import com.practice.cleankanban.domain.usecase.Output;

import java.util.List;

public interface GetStagesOutput extends Output {
    List<Stage> getStages();
    void setStages(List<Stage> stages);
}
