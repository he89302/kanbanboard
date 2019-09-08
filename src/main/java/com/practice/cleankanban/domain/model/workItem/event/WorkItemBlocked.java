package com.practice.cleankanban.domain.model.workItem.event;

import com.practice.cleankanban.domain.model.AbstractDomainEvent;
import com.practice.cleankanban.domain.model.BlockEvent;

public class WorkItemBlocked extends BlockEvent {
    public WorkItemBlocked(String id, String note, String workItemId, String name) {
        super(id, note, workItemId, name
        );
    }
}
