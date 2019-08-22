package com.practice.cleankanban.adapter.presenter.kanbanboard.modal.stage;

public class StageModel {

    private String boardId;
    private String stageId;
    private String miniStageId;
    private String name;

    public StageModel(){}

    public StageModel(String boardId, String miniStageId, String stageId, String name) {
        this.setBoardId(boardId);
        this.setMiniStageId(miniStageId);
        this.setStageId(stageId);
        this.setName(name);
    }

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
