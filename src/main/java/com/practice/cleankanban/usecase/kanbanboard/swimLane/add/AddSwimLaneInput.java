package com.practice.cleankanban.usecase.kanbanboard.swimLane.add;

import com.practice.cleankanban.domain.usecase.Input;

public interface AddSwimLaneInput extends Input {
    void setSwimLaneName(String name);
    String getSwimLaneName();
    void setMiniStageId(String id);
    String getMiniStageId();
    void setStageId(String id);
    String getStageId();
}
