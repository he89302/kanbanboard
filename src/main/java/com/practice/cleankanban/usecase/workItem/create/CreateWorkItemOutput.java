package com.practice.cleankanban.usecase.workItem.create;

import com.practice.cleankanban.domain.usecase.Output;

public interface CreateWorkItemOutput extends Output {
    String getName();

    void setName(String name);

    String getWorkItemId();

    void setWorkItemId(String id);
}
