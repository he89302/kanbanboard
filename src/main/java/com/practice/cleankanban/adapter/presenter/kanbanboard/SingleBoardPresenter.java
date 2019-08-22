package com.practice.cleankanban.adapter.presenter.kanbanboard;

import com.practice.cleankanban.usecase.kanbanboard.board.add.AddBoardOutput;

import javax.ws.rs.Path;

@Path("/stage")
public class SingleBoardPresenter implements AddBoardOutput {

    private String boardId;
    private String boardName;

    @Override
    public void setBoardName(String name) {
        this.boardName = name;
    }

    @Override
    public String getBoardName() {
        return boardName;
    }

    @Override
    public void setBoardId(String id) {
        this.boardId = id;
    }

    @Override
    public String getBoardId() {
        return boardId;
    }
}
