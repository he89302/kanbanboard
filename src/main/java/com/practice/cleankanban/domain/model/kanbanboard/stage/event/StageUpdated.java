package com.practice.cleankanban.domain.model.kanbanboard.stage.event;

import com.practice.cleankanban.domain.model.AbstractDomainEvent;

public class StageUpdated extends AbstractDomainEvent {
    public StageUpdated(String id, String name) {
        super(id, name);
    }
}
