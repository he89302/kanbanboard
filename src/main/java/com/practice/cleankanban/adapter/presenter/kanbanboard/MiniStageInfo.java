package com.practice.cleankanban.adapter.presenter.kanbanboard;

import java.util.ArrayList;
import java.util.List;

public class MiniStageInfo {

    private String stageId;
    private String miniStageId;
    private String miniStageName;
    private List<SwimLaneInfo> swimLanes = new ArrayList<>();

    public MiniStageInfo(String stageId, String miniStageId, String miniStageName, List<SwimLaneInfo> swimLaneInfoList) {
        this.stageId = stageId;
        this.miniStageId = miniStageId;
        this.miniStageName = miniStageName;
        this.swimLanes = swimLaneInfoList;
    }

    public String getId() {
        return miniStageId;
    }

    public SwimLaneInfo getDefaultOfSwimLane() {
        return swimLanes.get(0);
    }
}
