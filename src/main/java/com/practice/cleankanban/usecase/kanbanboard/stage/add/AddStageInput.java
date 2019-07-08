package com.practice.cleankanban.usecase.kanbanboard.stage.add;

import com.practice.cleankanban.domain.usecase.Input;

public interface AddStageInput extends Input {
    void setStageName(String name);
    String getStageName();
    void setBoardId(String s);
    String getBoardId();
}
