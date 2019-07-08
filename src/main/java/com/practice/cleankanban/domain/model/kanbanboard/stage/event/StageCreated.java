package com.practice.cleankanban.domain.model.kanbanboard.stage.event;

import com.practice.cleankanban.domain.model.AbstractDomainEvent;

public class StageCreated extends AbstractDomainEvent {

    public StageCreated(String id, String name) {
        super(id, name);
    }
}
