package com.practice.cleankanban.usecase.kanbanboard.board.add;

import com.practice.cleankanban.domain.usecase.Output;

public interface AddBoardOutput extends Output {
    void setBoardName(String name);
    String getBoardName();
    void setBoardId(String id);
    String getBoardId();
}
