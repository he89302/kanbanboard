package com.practice.cleankanban.usecase.domainevent.block;

import com.practice.cleankanban.domain.model.BlockDomainEvent;
import com.practice.cleankanban.domain.model.DomainEvent;
import com.practice.cleankanban.domain.model.DomainEventSubscriber;
import com.practice.cleankanban.usecase.domainevent.DomainEventRepository;

public class BlockEventSubscriber implements DomainEventSubscriber<DomainEvent> {

    private DomainEventRepository<BlockDomainEvent> repository;

    public BlockEventSubscriber(DomainEventRepository<BlockDomainEvent> repository) {
        this.repository = repository;
    }

    @Override
    public void handleEvent(DomainEvent domainEvent) {
        if (repository != null) {
            repository.save(new BlockDomainEvent(domainEvent));
        }  else {
            System.err.println("DomainEventRepository instance is null. The Domain event is not stored." + domainEvent.detail());
        }
    }

    @Override
    public Class<DomainEvent> subscribedToEventType() {
        return DomainEvent.class;
    }
}
