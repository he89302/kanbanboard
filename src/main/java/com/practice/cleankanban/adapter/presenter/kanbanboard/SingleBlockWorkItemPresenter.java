package com.practice.cleankanban.adapter.presenter.kanbanboard;

import com.practice.cleankanban.usecase.workItem.block.BlockWorkItemOutput;

public class SingleBlockWorkItemPresenter implements BlockWorkItemOutput {

    private String note;

    @Override
    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String getNote() {
        return note;
    }
}
