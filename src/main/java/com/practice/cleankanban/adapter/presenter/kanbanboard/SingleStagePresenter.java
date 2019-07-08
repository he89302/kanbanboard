package com.practice.cleankanban.adapter.presenter.kanbanboard;

import com.practice.cleankanban.usecase.kanbanboard.stage.add.AddStageOutput;

public class SingleStagePresenter implements AddStageOutput {
    private String stageId;
    private String stageName;
    private String miniStageId;
    @Override
    public String getStageId() {
        return stageId;
    }

    @Override
    public void setStageId(String stageId) {
        this.stageId = stageId;
    }

    @Override
    public String getStageName() {
        return stageName;
    }

    @Override
    public void setStageName(String name) {
        this.stageName = name;
    }

    @Override
    public String getMiniStageId() {
        return miniStageId;
    }

    @Override
    public void setMiniStageId(String miniStageId) {
        this.miniStageId = miniStageId;
    }
}
