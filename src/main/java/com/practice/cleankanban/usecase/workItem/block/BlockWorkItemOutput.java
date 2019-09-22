package com.practice.cleankanban.usecase.workItem.block;

import com.practice.cleankanban.domain.usecase.Output;

public interface BlockWorkItemOutput extends Output {
    void setNote(String note);
    String getNote();
}
