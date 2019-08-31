package com.practice.cleankanban.adapter.presenter.kanbanboard;

import com.practice.cleankanban.usecase.kanbanboard.board.move_stage.MoveStageOfBoardOutput;

public class MoveStageOfBoardMessagePresenter implements MoveStageOfBoardOutput {

    private boolean isMoveStage = false;
    private String message;

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public boolean isMoveStage() {
        return isMoveStage;
    }

    @Override
    public void setMoveStage(boolean moveStage) {
        this.isMoveStage = moveStage;
    }

}