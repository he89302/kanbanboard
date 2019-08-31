package com.practice.cleankanban.usecase.kanbanboard.board.move_stage;

import com.practice.cleankanban.domain.usecase.Output;

public interface MoveStageOfBoardOutput extends Output {
    void setMessage(String message);
    String getMessage();
    boolean isMoveStage();
    void setMoveStage(boolean moveStage);
}