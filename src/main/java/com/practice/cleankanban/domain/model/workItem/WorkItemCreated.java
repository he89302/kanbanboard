package com.practice.cleankanban.domain.model.workItem;

import com.practice.cleankanban.domain.model.AbstractDomainEvent;

public class WorkItemCreated extends AbstractDomainEvent {
    public WorkItemCreated(String id, String name) {
        super(id, name);

    }
}
