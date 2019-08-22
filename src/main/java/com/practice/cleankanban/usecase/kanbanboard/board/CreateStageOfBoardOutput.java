package com.practice.cleankanban.usecase.kanbanboard.board;

import com.practice.cleankanban.domain.usecase.Output;

public interface CreateStageOfBoardOutput extends Output {
    void setStageId(String stageId);

    String getStageId();
}
