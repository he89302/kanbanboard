package com.practice.cleankanban.domain.model.kanbanboard.stage;

import com.practice.cleankanban.domain.Entity;

public class CommittedWorkItem extends Entity {

    private static final String DEFAULT_NAME = "";

    private String stageId;
    private String miniStageId;
    private String swimLaneId;
    private String workItemId;
    private int position;
    private boolean isBlock;

    public CommittedWorkItem(String stageId, String miniStageId, String swimLaneId, String workItemId, int position) {
        super(DEFAULT_NAME);
        this.stageId = stageId;
        this.miniStageId = miniStageId;
        this.swimLaneId = swimLaneId;
        this.workItemId = workItemId;
        this.position = position;
        this.isBlock = false;
    }

    @Override
    public boolean equals(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
            CommittedWorkItem typedObject = (CommittedWorkItem) anObject;
            equalObjects =
                    this.getStageId().equals(typedObject.getStageId()) &&
                            this.getMiniStageId().equals(typedObject.getMiniStageId()) &&
                            this.getSwimLaneId().equals(typedObject.getSwimLaneId()) &&
                            this.getWorkItemId().equals(typedObject.getWorkItemId());
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
                + (5327 * 17)
                        + this.getStageId().hashCode()
                        + this.getMiniStageId().hashCode()
                        + this.getSwimLaneId().hashCode()
                        + this.getWorkItemId().hashCode();

        return hashCodeValue;
    }

    public void moveForwardPosition() {
        position--;
    }

    public void moveBackPosition() {
        position++;
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

    public int getPosition() {
        return position;
    }

    public boolean isBlock() {
        return isBlock;
    }

    public void setBlock(boolean block) {
        isBlock = block;
    }
}
