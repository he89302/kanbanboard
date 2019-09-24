package com.practice.cleankanban.usecase.kanbanboard.stage.add;

import com.practice.cleankanban.domain.usecase.Output;

public interface AddStageOutput extends Output {
    String getStageId();

    void setStageId(String stageId);

    String getStageName();

    void setStageName(String name);

    String getMiniStageId();

    void setMiniStageId(String miniStageId);

    String getSwimLaneId();

    void setSwimLaneId(String swimLaneId);
}
