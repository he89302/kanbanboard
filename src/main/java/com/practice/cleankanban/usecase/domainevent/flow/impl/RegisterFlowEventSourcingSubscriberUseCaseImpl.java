package com.practice.cleankanban.usecase.domainevent.flow.impl;

import com.practice.cleankanban.domain.model.DomainEventPublisher;
import com.practice.cleankanban.domain.model.FlowEvent;
import com.practice.cleankanban.usecase.domainevent.DomainEventRepository;
import com.practice.cleankanban.usecase.domainevent.flow.FlowEventSubscriber;
import com.practice.cleankanban.usecase.domainevent.flow.RegisterFlowEventSourcingSubscribeInput;
import com.practice.cleankanban.usecase.domainevent.flow.RegisterFlowEventSourcingSubscriberOutput;
import com.practice.cleankanban.usecase.domainevent.flow.RegisterFlowEventSourcingSubscriberUseCase;

public class RegisterFlowEventSourcingSubscriberUseCaseImpl implements RegisterFlowEventSourcingSubscriberUseCase {
    private DomainEventRepository<FlowEvent> repository;

    public RegisterFlowEventSourcingSubscriberUseCaseImpl(DomainEventRepository<FlowEvent> flowEventRepository) {
        this.repository = flowEventRepository;
    }

    @Override
    public void execute(RegisterFlowEventSourcingSubscribeInput input, RegisterFlowEventSourcingSubscriberOutput output) {
        FlowEventSubscriber subscriber =
                new FlowEventSubscriber(repository);

        DomainEventPublisher.instance().subscribe(subscriber);
    }

    public static RegisterFlowEventSourcingSubscribeInput createInput(){
        return new RegisterFlowEventSubscriberInputImpl();
    }

    private static class RegisterFlowEventSubscriberInputImpl implements RegisterFlowEventSourcingSubscribeInput {
    }
}
