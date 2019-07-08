package com.practice.cleankanban.adapter.presenter;

import com.practice.cleankanban.usecase.workItem.create.CreateWorkItemOutput;

public class SingleWorkItemPresenter implements CreateWorkItemOutput {
    private String name;
    private String id;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getWorkItemId() {
        return id;
    }

    @Override
    public void setWorkItemId(String id) {
        this.id = id;
    }
}
