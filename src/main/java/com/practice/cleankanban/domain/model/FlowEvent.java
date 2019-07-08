package com.practice.cleankanban.domain.model;

import java.io.Serializable;

public abstract class FlowEvent extends AbstractDomainEvent implements Serializable {
    private final String stageId;
    private final String miniStageId;
    private final String swimLaneId;
    private final String workItemId;

    public FlowEvent(String sourceId,
                     String sourceName,
                     String stageId,
                     String miniStageId,
                     String swimLaneId,
                     String workItemId) {
        super(sourceId, sourceName);

        this.stageId = stageId;
        this.miniStageId = miniStageId;
        this.swimLaneId = swimLaneId;
        this.workItemId = workItemId;
    }

    @Override
    public String detail() {
        String formatDate = String.format("occurredOn='%1$tY-%1$tm-%1$td %1$tH:%1$tM", occurredOn());
        String format = String.format(
                "%s['%s', stage id='%s', ministage id='%s', swimlane id='%s', workitem id ='%s', id='%s'] ",
                this.getClass().getSimpleName(),
                formatDate,
                stageId,
                miniStageId,
                swimLaneId,
                workItemId,
                getId());
        /* return format + formatDate; */
        return format;
    }

    public String getStageId() {
        return stageId;
    }

    public String getMiniStageId() {
        return miniStageId;
    }

    public String getSwimLaneId() {
        return swimLaneId;
    }

    public String getWorkItemId() {
        return workItemId;
    }
}
