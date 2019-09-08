package com.practice.cleankanban.domain.model;

import java.io.Serializable;

public class BlockEvent extends AbstractDomainEvent implements Serializable {

    private String sourceId;
    private String sourceName;
    private String workItemId;
    private String workItemName;

    public BlockEvent(String sourceId,
                      String sourceName,
                      String workItemId,
                      String workItemName) {
        super(sourceId, sourceName);
        this.sourceId = sourceId;
        this.sourceName = sourceName;
        this.workItemId = workItemId;
        this.workItemName = workItemName;
    }

    @Override
    public String detail() {
        String formatDate = String.format("occurredOn='%1$tY-%1$tm-%1$td %1$tH:%1$tM']", occurredOn());
        String format = String.format(
                "%s[#Number : %s WorkItem %s : %s was block on %s because %s] ",
                this.getClass().getSimpleName(),
                this.getSourceId(),
                this.getWorkItemName(),
                this.getWorkItemId(),
                formatDate,
                this.getSourceName());
        return format;
    }


    public String getSourceId() {
        return sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getWorkItemId() {
        return workItemId;
    }

    public String getWorkItemName() {
        return workItemName;
    }

}
