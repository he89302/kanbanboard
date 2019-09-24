package com.practice.cleankanban.adapter.presenter.kanbanboard;

public class BoardModel {

    private String boardId;
    private String name;

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getBoardName() {
        return name;
    }

    public void setBoardName(String boardName) {
        this.name = boardName;
    }
}
