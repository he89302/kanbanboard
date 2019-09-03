package com.practice.cleankanban.usecase.kanbanboard.stage.remove;

import com.practice.cleankanban.domain.usecase.Input;

public interface RemoveStageInput extends Input {
    void setBoardId(String boardId);
    String getBoardId();
    void setStageId(String stageId);
    String getStageId();
}