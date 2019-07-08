package com.practice.cleankanban.usecase.kanbanboard.miniStage.add;

import com.practice.cleankanban.domain.usecase.Input;

public interface AddMiniStageInput extends Input {
    void setMiniStageName(String name);
    String getMiniStageName();
    void setStageId(String id);
    String getStageId();
}
