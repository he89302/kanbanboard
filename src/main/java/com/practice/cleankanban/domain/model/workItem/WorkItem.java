package com.practice.cleankanban.domain.model.workItem;

import com.practice.cleankanban.domain.Entity;
import com.practice.cleankanban.domain.model.DomainEventPublisher;

public class WorkItem extends Entity {
    private String stageId;
    private String miniStageId;
    private String swimLaneId;

    public WorkItem(String name, String stageId, String miniStageId, String swimLaneId) {
        super(name);

        this.stageId = stageId;
        this.miniStageId = miniStageId;
        this.swimLaneId = swimLaneId;

        DomainEventPublisher
                .instance()
                .publish(new WorkItemCreated(
                        this.getId(),
                        this.getName()));
    }

    public String getStageId() {
        return stageId;
    }

    public void setStageId(String stageId) {
        this.stageId = stageId;
    }

    public String getMiniStageId() {
        return miniStageId;
    }

    public void setMiniStageId(String miniStageId) {
        this.miniStageId = miniStageId;
    }

    public String getSwimLaneId() {
        return swimLaneId;
    }

    public void setSwimLaneId(String swimLaneId) {
        this.swimLaneId = swimLaneId;
    }

    public void moveTo(String stageId, String miniStageId, String swimLaneId) {
        this.stageId = stageId;
        this.miniStageId = miniStageId;
        this.swimLaneId = swimLaneId;
    }
}
