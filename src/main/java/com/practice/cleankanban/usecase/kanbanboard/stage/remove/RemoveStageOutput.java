package com.practice.cleankanban.usecase.kanbanboard.stage.remove;

import com.practice.cleankanban.domain.usecase.Output;

public interface RemoveStageOutput extends Output {
    String getMessage();
    void setMessage(String message);
}