package com.practice.cleankanban.usecase.domainevent;

import java.util.List;

public interface DomainEventRepository<T> {
    List<T> findAll();

    T save(T event);
}
