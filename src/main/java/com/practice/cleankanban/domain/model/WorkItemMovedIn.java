package com.practice.cleankanban.domain.model;

public class WorkItemMovedIn extends FlowEvent{

    public WorkItemMovedIn(String sourceId,
                           String sourceName,
                           String stageId,
                           String miniStageId,
                           String swimLaneId,
                           String workItemId) {
        super(sourceId, sourceName, stageId, miniStageId, swimLaneId, workItemId);

    }
}
