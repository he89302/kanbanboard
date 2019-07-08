package com.practice.cleankanban.usecase.domainevent.flow;

import com.practice.cleankanban.domain.model.DomainEvent;
import com.practice.cleankanban.domain.model.DomainEventSubscriber;
import com.practice.cleankanban.domain.model.FlowEvent;
import com.practice.cleankanban.usecase.domainevent.DomainEventRepository;

public class FlowEventSubscriber implements DomainEventSubscriber<DomainEvent> {

    private DomainEventRepository<FlowEvent> repository;

    public FlowEventSubscriber(DomainEventRepository<FlowEvent> repository) {
        this.repository = repository;
    }

    @Override
    public void handleEvent(DomainEvent domainEvent) {
        if (domainEvent instanceof FlowEvent) {
            if (repository != null) {
                repository.save((FlowEvent) domainEvent);
            } else {
                System.err.println("DomainEventRepository instance is null. The Domain event is not stored." + domainEvent.detail());
            }
        }
    }

    @Override
    public Class<DomainEvent> subscribedToEventType() {
        return DomainEvent.class;
    }
}
