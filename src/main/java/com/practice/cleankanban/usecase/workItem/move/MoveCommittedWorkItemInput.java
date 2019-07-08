package com.practice.cleankanban.usecase.workItem.move;

import com.practice.cleankanban.domain.usecase.Input;

public interface MoveCommittedWorkItemInput extends Input {
    String getWorkItemId();

    void setWorkItemId(String id);

    String getStageId();

    void setToStageId(String id);

    String getMiniStageId();

    void setToMiniStageId(String id);

    String getSwimLaneId();

    void setToSwimLaneId(String id);
}
