package com.practice.cleankanban.usecase.workItem.block;

import com.practice.cleankanban.domain.model.workItem.WorkItem;
import com.practice.cleankanban.domain.usecase.Input;

public interface BlockWorkItemInput extends Input {
    void setBlockNote(String note);
    String getBlockNote();

    void setWorkItem(WorkItem workItem);
    WorkItem getWorkItem();
}
