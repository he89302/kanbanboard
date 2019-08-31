package com.practice.cleankanban.usecase.kanbanboard.stage;

import java.util.List;

public class SwimLaneDto {

    private String swimLaneId;
    private String swimLaneName;

    public SwimLaneDto(String swimLaneId,
                       String swimLaneName) {
        this.swimLaneId = swimLaneId;
        this.swimLaneName = swimLaneName;
    }

    public String getSwimLaneId() {
        return swimLaneId;
    }

    public String getSwimLaneName() {
        return swimLaneName;
    }
}
