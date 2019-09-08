package com.practice.cleankanban.usecase.workItem.block;

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
