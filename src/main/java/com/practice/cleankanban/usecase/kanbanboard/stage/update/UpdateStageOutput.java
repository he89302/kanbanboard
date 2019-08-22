package com.practice.cleankanban.usecase.kanbanboard.stage.update;

import com.practice.cleankanban.domain.usecase.Output;

public interface UpdateStageOutput extends Output {
    void setStageId(String id);

    String getStageId();
}
