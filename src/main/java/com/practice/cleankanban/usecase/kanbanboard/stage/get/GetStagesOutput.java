package com.practice.cleankanban.usecase.kanbanboard.stage.get;

import com.practice.cleankanban.domain.model.kanbanboard.stage.Stage;
import com.practice.cleankanban.domain.usecase.Output;
import com.practice.cleankanban.usecase.kanbanboard.stage.StageDto;

import java.util.List;

public interface GetStagesOutput extends Output {
    List<StageDto> getStages();
    void setStages(List<StageDto> stages);
}
