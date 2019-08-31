package com.practice.cleankanban.usecase.kanbanboard.board.move_stage;

import com.practice.cleankanban.domain.usecase.Input;

public interface MoveStageOfBoardInput extends Input {

    void setBoardId(String boardId);
    String getBoardId();

	void setStageId(String stageId);
    String getStageId();

	void setOldPosition(int position);
    int getOldPosition();

	void setNewPosition(int position);
    int getNewPosition();
}