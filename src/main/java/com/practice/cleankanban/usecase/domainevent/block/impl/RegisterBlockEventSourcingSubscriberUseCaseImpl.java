package com.practice.cleankanban.usecase.domainevent.block.impl;

import com.practice.cleankanban.domain.model.BlockDomainEvent;
import com.practice.cleankanban.domain.model.DomainEventPublisher;
import com.practice.cleankanban.usecase.domainevent.DomainEventRepository;
import com.practice.cleankanban.usecase.domainevent.block.BlockEventSubscriber;
import com.practice.cleankanban.usecase.domainevent.block.RegisterBlockEventSourcingSubscriberInput;
import com.practice.cleankanban.usecase.domainevent.block.RegisterBlockEventSourcingSubscriberOutput;
import com.practice.cleankanban.usecase.domainevent.block.RegisterBlockEventSourcingSubscriberUseCase;

public class RegisterBlockEventSourcingSubscriberUseCaseImpl implements RegisterBlockEventSourcingSubscriberUseCase {

    private DomainEventRepository<BlockDomainEvent> blockDomainEvenRepository;

    public RegisterBlockEventSourcingSubscriberUseCaseImpl(DomainEventRepository<BlockDomainEvent> domainEventRepository) {
        this.blockDomainEvenRepository = domainEventRepository;
    }

    @Override
    public void execute(RegisterBlockEventSourcingSubscriberInput input, RegisterBlockEventSourcingSubscriberOutput output) {
        BlockEventSubscriber subscriber = new BlockEventSubscriber(blockDomainEvenRepository);
        DomainEventPublisher.instance().subscribe(subscriber);
    }
}
