package com.practice.cleankanban.usecase.kanbanboard.board;

import com.practice.cleankanban.domain.usecase.Input;

public interface CreateStageOfBoardInput extends Input {
    void setBoardId(String id);
    String getBoardId();
    void setStageName(String name);
    String getStageName();
}
