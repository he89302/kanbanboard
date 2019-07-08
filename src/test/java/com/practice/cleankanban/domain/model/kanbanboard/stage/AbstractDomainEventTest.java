package com.practice.cleankanban.domain.model.kanbanboard.stage;

import com.practice.cleankanban.domain.model.DomainEvent;
import com.practice.cleankanban.domain.model.DomainEventPublisher;
import com.practice.cleankanban.domain.model.DomainEventSubscriber;
import com.practice.cleankanban.domain.model.kanbanboard.WipLimitExceedException;
import org.junit.Before;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class AbstractDomainEventTest {

    protected FakeSubscriber subscriber;
    protected FakeStoredSubscriber storedSubscriber;

    @Before
    public void setUp() throws WipLimitExceedException, ParseException {
        subscriber = new FakeSubscriber();
        storedSubscriber = new FakeStoredSubscriber();

        DomainEventPublisher.instance().subscribe(subscriber);
        DomainEventPublisher.instance().subscribe(storedSubscriber);
    }

    protected class FakeSubscriber implements DomainEventSubscriber<DomainEvent> {
        public String expectedResult;
        @Override
        public Class<DomainEvent> subscribedToEventType() {
            return DomainEvent.class;
        }
        @Override
        public void handleEvent(DomainEvent domainEvent) {
            expectedResult = domainEvent.detail();
        }
    }


    protected class FakeStoredSubscriber implements DomainEventSubscriber<DomainEvent> {
        public List<String> expectedResults = new ArrayList<>();

        @Override
        public Class<DomainEvent> subscribedToEventType() {
            return DomainEvent.class;
        }

        @Override
        public void handleEvent(DomainEvent domainEvent) {
            expectedResults.add(domainEvent.detail());
        }
    }

}

