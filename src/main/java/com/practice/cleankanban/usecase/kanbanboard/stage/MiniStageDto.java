package com.practice.cleankanban.usecase.kanbanboard.stage;


import java.util.ArrayList;
import java.util.List;

public class MiniStageDto {

    private String stageId;
    private String miniStageId;
    private String miniStageName;
    private List<SwimLaneDto> swimLanes = new ArrayList<>();

    public MiniStageDto(String stageId, String miniStageId, String miniStageName, List<SwimLaneDto> swimLaneDtoList) {
        this.stageId = stageId;
        this.miniStageId = miniStageId;
        this.miniStageName = miniStageName;
        this.swimLanes = swimLaneDtoList;
    }

    public String getId() {
        return miniStageId;
    }

    public String getStageId() {
        return stageId;
    }

    public List<SwimLaneDto> getSwimLanes() {
        return swimLanes;
    }

    public String getMiniStageName() {
        return miniStageName;
    }

    public SwimLaneDto getDefaultOfSwimLane() {
        return swimLanes.get(0);
    }
}
