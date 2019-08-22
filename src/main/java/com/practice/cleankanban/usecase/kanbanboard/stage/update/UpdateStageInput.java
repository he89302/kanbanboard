package com.practice.cleankanban.usecase.kanbanboard.stage.update;

import com.practice.cleankanban.domain.usecase.Input;

public interface UpdateStageInput extends Input {
    String getStageId();

    String getName();

    void setStageId(String id);

    void setName(String editName);
}
