package com.practice.cleankanban.domain.model.kanbanboard.board;

import com.practice.cleankanban.domain.Entity;


public class BoardStage extends Entity {

    public static final String DEFAULT_NAME = "";
    private String boardId;
    private String stageId;

    private int ordering;

    public BoardStage(String boardId, String stageId) {
        super(DEFAULT_NAME);
        this.boardId = boardId;
        this.stageId = stageId;

        ordering = -1;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getStageId() {
        return stageId;
    }

    public void setStageId(String stageId) {
        this.stageId = stageId;
    }

    public int getOrdering() {
        return ordering;
    }

    public void setOrdering(int ordering) {
        this.ordering = ordering;
    }

    public void moveForwardOrdering() {
        ordering--;
    }

    public void moveBackward() {
        ordering++;
    }
}
