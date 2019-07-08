package com.practice.cleankanban.domain.model.kanbanboard.stage.event;

import com.practice.cleankanban.domain.model.AbstractDomainEvent;

public class MiniStageCreated extends AbstractDomainEvent {

    public MiniStageCreated(String id, String name) {
        super(id, name);
    }
}
