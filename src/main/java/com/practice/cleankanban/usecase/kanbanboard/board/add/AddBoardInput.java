package com.practice.cleankanban.usecase.kanbanboard.board.add;

import com.practice.cleankanban.domain.usecase.Input;

public interface AddBoardInput extends Input {
    void setBoardName(String standardBoard);
    String getBoardName();
}
