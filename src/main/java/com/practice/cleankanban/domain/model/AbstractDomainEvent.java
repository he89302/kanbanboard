package com.practice.cleankanban.domain.model;

import com.practice.cleankanban.domain.common.DateProvider;

import java.util.Date;
import java.util.UUID;

public class AbstractDomainEvent implements DomainEvent {

    private final Date occurredOn;
    private final String sourceId;
    private final String sourceName;
    private final String id;

    public AbstractDomainEvent(String sourceId, String sourceName) {
        super();

        this.sourceId = sourceId;
        this.sourceName = sourceName;
        this.occurredOn = DateProvider.now();
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public int eventVersion() {
        return EventVersion.NUMBER;
    }

    @Override
    public Date occurredOn() {
        return occurredOn;
    }

    @Override
    public String detail() {
        String formatDate = String.format("occurredOn='%1$tY-%1$tm-%1$td %1$tH:%1$tM']", occurredOn());
        String format = String.format(
                "%s[name='%s', id='%s'] ",
                this.getClass().getSimpleName(),
                this.getSourceName(), this.getSourceId());
        return format + formatDate;
    }

    private String getSourceId() {
        return sourceId;
    }

    private String getSourceName() {
        return sourceName;
    }

    protected String getId() {
        return id;
    }
}
