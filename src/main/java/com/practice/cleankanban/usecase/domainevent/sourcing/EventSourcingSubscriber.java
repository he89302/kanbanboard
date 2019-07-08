package com.practice.cleankanban.usecase.domainevent.sourcing;

import com.practice.cleankanban.domain.model.DomainEvent;
import com.practice.cleankanban.domain.model.PersistentDomainEvent;
import com.practice.cleankanban.domain.model.DomainEventSubscriber;
import com.practice.cleankanban.usecase.domainevent.DomainEventRepository;

public class EventSourcingSubscriber implements DomainEventSubscriber<DomainEvent> {
    private DomainEventRepository<PersistentDomainEvent> repository;

    public EventSourcingSubscriber(DomainEventRepository<PersistentDomainEvent> repository) {
        this.repository = repository;
    }

    @Override
    public void handleEvent(DomainEvent domainEvent) {
        if (repository != null) {
            repository.save(new PersistentDomainEvent(domainEvent));
        } else  {
            System.err.println("DomainEventRepository instance is null. The Domain event is not stored." + domainEvent.detail());
        }
    }

    @Override
    public Class<DomainEvent> subscribedToEventType() {
        return DomainEvent.class;
    }
}
