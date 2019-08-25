package com.practice.cleankanban.usecase.kanbanboard.stage.get;

import com.practice.cleankanban.domain.usecase.Input;

public interface GetStagesInput extends Input {
    void setBoardId(String boardId);
    String getBoardId();
    void setGetAllStage(boolean arg);
    boolean isGetAllStage();
}
