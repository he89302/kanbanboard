package com.practice.cleankanban.adapter.gateway.domainEvent;

import com.practice.cleankanban.usecase.domainevent.DomainEventRepository;

import java.util.ArrayList;
import java.util.List;


public class InMemoryDomainEventRepository<T> implements DomainEventRepository<T> {

    private List<T> events;

    public InMemoryDomainEventRepository() {
        events = new ArrayList<T>();
    }

    @Override
    public List<T> findAll() {
        return events;
    }

    @Override
    public T save(T arg) {
        if (events.contains(arg)) {
            return arg;
        } else if(events.add(arg)) {
            return arg;
        } else {
            return null;
        }
    }
}
