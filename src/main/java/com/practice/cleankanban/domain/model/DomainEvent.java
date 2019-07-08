package com.practice.cleankanban.domain.model;

import java.util.Date;

public interface DomainEvent {

    int eventVersion();

    Date occurredOn();

    String detail();
}

