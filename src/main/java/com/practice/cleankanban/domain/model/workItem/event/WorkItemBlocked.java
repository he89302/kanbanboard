package com.practice.cleankanban.domain.model.workItem.event;

import com.practice.cleankanban.domain.model.AbstractDomainEvent;

public class WorkItemBlocked extends AbstractDomainEvent {
    public WorkItemBlocked(String id, String note, String workItemId) {
        super(id, workItemId);
    }
}
