package com.practice.cleankanban.usecase.workItem.create;

import com.practice.cleankanban.domain.usecase.Input;

public interface CreateWorkItemInput extends Input {
    void setWorkItemName(String s);
    String getWorkItemName();
    void setSwimLaneId(String id);
    String getSwimLaneId();
    void setMiniStageId(String id);
    String getMiniStageId();
    void setStageId(String id);
    String getStageId();
}
