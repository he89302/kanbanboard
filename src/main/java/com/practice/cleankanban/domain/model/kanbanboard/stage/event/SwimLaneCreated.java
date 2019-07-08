package com.practice.cleankanban.domain.model.kanbanboard.stage.event;

import com.practice.cleankanban.domain.model.AbstractDomainEvent;

public class SwimLaneCreated extends AbstractDomainEvent {

    public SwimLaneCreated(String id, String name) {
        super(id, name);
    }
}
