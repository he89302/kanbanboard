package com.practice.cleankanban.domain.model.kanbanboard.stage;

import com.practice.cleankanban.domain.Entity;
import com.practice.cleankanban.domain.model.DomainEvent;
import com.practice.cleankanban.domain.model.DomainEventPublisher;
import com.practice.cleankanban.domain.model.WorkItemMovedIn;
import com.practice.cleankanban.domain.model.kanbanboard.WipLimitExceedException;
import com.practice.cleankanban.domain.model.kanbanboard.stage.event.SwimLaneCreated;
import com.practice.cleankanban.domain.model.kanbanboard.stage.event.WorkItemMovedOut;
import com.practice.cleankanban.domain.model.workItem.WorkItem;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.List;

public class SwimLane extends Entity {
    public static final String DEFAULT_NAME = "";
    public static final int NO_WIP_LIMIT = -1;
    private String stageId;
    private String miniStageId;
    private int wipLimit;
    private List<String> committedWorkItems;

    public SwimLane(String stageId, String miniStageId) {
        super(DEFAULT_NAME);

        this.stageId = stageId;
        this.miniStageId = miniStageId;
        wipLimit = NO_WIP_LIMIT;
        committedWorkItems = new ArrayList<>();

        DomainEventPublisher.instance().publish(new SwimLaneCreated(
                                                this.getId(),
                                                this.getName()));
    }

    private void setCommittedWorkItems(List<String> workItemIdsCopy) {
        this.committedWorkItems = workItemIdsCopy;
    }


    public void setWipLimit(int wipLimit) {
        this.wipLimit = wipLimit;
    }

    public int getWipLimit() {
        return wipLimit;
    }

    public List<String> getCommittedWorkItems() {
        return committedWorkItems;
    }

    public void committedWorkItemById(String workItemId) throws WipLimitExceedException {
        if (isReachWipLimit()) {
            throw new WipLimitExceedException("Exceeds WIP Exception : " + wipLimit);
        }

        committedWorkItems.add(workItemId);

        DomainEventPublisher.instance().
                            publish(new WorkItemMovedIn(
                                    this.getId(),
                                    this.getName(),
                                    this.getStageId(),
                                    this.getMiniStageId(),
                                    this.getId(),
                                    workItemId));
    }

    private boolean isReachWipLimit() {
        if (committedWorkItems.size() == (wipLimit)) {
            return true;
        }
        return false;
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

    public boolean uncommittedWorkItemById(String workItemId) {
        for (String each:committedWorkItems
             ) {
            if (each.equals(workItemId)) {
                committedWorkItems.remove(workItemId);

                DomainEventPublisher.instance().
                        publish(new WorkItemMovedOut(
                                this.getId(),
                                this.getName(),
                                this.getStageId(),
                                this.getMiniStageId(),
                                this.getId(),
                                workItemId));
                return true;
            }
        }return false;
    }
}
