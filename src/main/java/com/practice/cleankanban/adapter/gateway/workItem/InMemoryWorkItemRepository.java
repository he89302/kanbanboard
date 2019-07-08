package com.practice.cleankanban.adapter.gateway.workItem;

import com.practice.cleankanban.usecase.workItem.WorkItemRepository;
import com.practice.cleankanban.domain.model.workItem.WorkItem;

import java.util.ArrayList;
import java.util.List;

public class InMemoryWorkItemRepository implements WorkItemRepository {
    List<WorkItem> workItemList = new ArrayList<>();

    @Override
    public void save(WorkItem workItem) {
        workItemList.add(workItem);
    }

    @Override
    public WorkItem findWorkItemById(String workItemId) {
        for (WorkItem each:workItemList
             ) {
            if (each.getId().equals(workItemId)) {
                return each;
            }
        }
        throw new RuntimeException("Work Item Not Found : " + workItemId);
    }
}
