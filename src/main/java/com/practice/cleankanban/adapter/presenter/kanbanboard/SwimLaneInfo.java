package com.practice.cleankanban.adapter.presenter.kanbanboard;

public class SwimLaneInfo {
    private String swimLaneId;
    private String swimLaneName;

    public SwimLaneInfo(String swimLaneId,
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
