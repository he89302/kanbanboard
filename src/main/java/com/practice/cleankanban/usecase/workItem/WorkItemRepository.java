package com.practice.cleankanban.usecase.workItem;

import com.practice.cleankanban.domain.model.workItem.WorkItem;

public interface WorkItemRepository {
    void save(WorkItem workItem);

    WorkItem findWorkItemById(String workItemId);
}
