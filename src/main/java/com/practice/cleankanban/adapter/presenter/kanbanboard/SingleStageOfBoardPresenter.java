package com.practice.cleankanban.adapter.presenter.kanbanboard;

import com.practice.cleankanban.usecase.kanbanboard.board.CreateStageOfBoardOutput;

public class SingleStageOfBoardPresenter implements CreateStageOfBoardOutput {
    private String stageId = "";

    @Override
    public void setStageId(String stageId) {
        this.stageId = stageId;
    }

    @Override
    public String getStageId() {
        return stageId;
    }
}
