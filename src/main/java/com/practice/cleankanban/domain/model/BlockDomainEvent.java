package com.practice.cleankanban.domain.model;

import java.io.Serializable;
import java.util.Date;

public class BlockDomainEvent  implements Serializable {

    private Long id;
    private int eventVersion;
    private String eventBody;
    private Date occurredOn;

    public BlockDomainEvent(DomainEvent domainEvent) {
        super();
        this.eventVersion = domainEvent.eventVersion();
        this.eventBody = domainEvent.detail();
        this.occurredOn = domainEvent.occurredOn();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getEventVersion() {
        return eventVersion;
    }

    public void setEventVersion(int eventVersion) {
        this.eventVersion = eventVersion;
    }

    public String getEventBody() {
        return eventBody;
    }

    public void setEventBody(String eventBody) {
        this.eventBody = eventBody;
    }

    @Override
    public String toString() {
        return String.format(
                "BlockDomainEvent[id=%d, detail='%s', occurredOn='%s']",
                id, eventBody, occurredOn);
    }

}
