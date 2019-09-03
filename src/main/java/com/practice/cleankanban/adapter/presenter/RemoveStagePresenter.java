package com.practice.cleankanban.adapter.presenter;

import com.practice.cleankanban.usecase.kanbanboard.stage.remove.RemoveStageOutput;

public class RemoveStagePresenter implements RemoveStageOutput {
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

}