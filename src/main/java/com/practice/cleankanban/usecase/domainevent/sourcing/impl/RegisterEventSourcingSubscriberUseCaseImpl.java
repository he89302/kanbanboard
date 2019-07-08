package com.practice.cleankanban.usecase.domainevent.sourcing.impl;

import com.practice.cleankanban.domain.model.DomainEventPublisher;
import com.practice.cleankanban.domain.model.PersistentDomainEvent;
import com.practice.cleankanban.usecase.domainevent.DomainEventRepository;
import com.practice.cleankanban.usecase.domainevent.sourcing.EventSourcingSubscriber;
import com.practice.cleankanban.usecase.domainevent.sourcing.RegisterEventSourcingSubscriberInput;
import com.practice.cleankanban.usecase.domainevent.sourcing.RegisterEventSourcingSubscriberOutput;
import com.practice.cleankanban.usecase.domainevent.sourcing.RegisterEventSourcingSubscriberUseCase;

public class RegisterEventSourcingSubscriberUseCaseImpl implements RegisterEventSourcingSubscriberUseCase {

    private DomainEventRepository<PersistentDomainEvent> repository;

    public RegisterEventSourcingSubscriberUseCaseImpl(DomainEventRepository<PersistentDomainEvent>
                                                              domainEventRepository) {
        this.repository = domainEventRepository;
    }

    public static RegisterEventSourcingSubscriberInput createInput() {
        return new RegisterEventSourcingSubscriberInputImpl();
    }

    @Override
    public void execute(RegisterEventSourcingSubscriberInput input, RegisterEventSourcingSubscriberOutput output) {
        EventSourcingSubscriber subscriber = new EventSourcingSubscriber(repository);

        DomainEventPublisher.instance().subscribe(subscriber);
    }

    private static class RegisterEventSourcingSubscriberInputImpl implements RegisterEventSourcingSubscriberInput {

    }

}
