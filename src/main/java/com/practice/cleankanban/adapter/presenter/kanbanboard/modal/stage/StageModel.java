package com.practice.cleankanban.adapter.presenter.kanbanboard.modal.stage;

public class StageModel {

    private String boardId;
    private String stageId;
    private String miniStageId;
    private String swimLaneId;
    private String name;

    public StageModel(){}

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public void setStageId(String stageId) {
        this.stageId = stageId;
    }

    public String getStageId() {
        return stageId;
    }

    public String getMiniStageId() {
        return miniStageId;
    }

    public void setMiniStageId(String miniStageId) {
        this.miniStageId = miniStageId;
    }

    public String getSwimLaneId() {
        return swimLaneId;
    }

    public void setSwimLaneId(String swimLaneId) {
        this.swimLaneId = swimLaneId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBoardId() {
        return boardId;
    }
}
