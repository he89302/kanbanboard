package com.practice.cleankanban.usecase.kanbanboard.stage.update;

public class SingleUpdateStagePresenter implements UpdateStageOutput {

    private String id;

    @Override
    public void setStageId(String id) {
        this.id = id;
    }

    @Override
    public String getStageId() {
        return id;
    }
}
