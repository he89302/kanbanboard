package com.practice.cleankanban.adapter.presenter.kanbanboard;

import java.util.List;

public class StageInfo {

    private String name;
    private String id;
    private String boardId;
    private List<MiniStageInfo> miniStages;
    private int ordering;

    public StageInfo(String name, String id, int ordering, String boardId, List<MiniStageInfo> miniStages) {
        this.name = name;
        this.id = id;
        this.ordering = ordering;
        this.boardId = boardId;
        this.miniStages = miniStages;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getBoardId() {
        return boardId;
    }

    public List<MiniStageInfo> getMiniStageInfos() {
        return miniStages;
    }

    public int getOrdering() {
        return ordering;
    }

    public MiniStageInfo getDefaultMiniStage() {
        return  miniStages.get(0);
    }

    public SwimLaneInfo getDefaultSwimLaneOfMiniStage() {
        return  miniStages.get(0).getDefaultOfSwimLane();
    }
}
