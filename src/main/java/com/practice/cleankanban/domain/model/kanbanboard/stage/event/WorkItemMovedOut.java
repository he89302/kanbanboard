package com.practice.cleankanban.domain.model.kanbanboard.stage.event;

import com.practice.cleankanban.domain.model.FlowEvent;

public class WorkItemMovedOut extends FlowEvent {

    public WorkItemMovedOut(String sourceId,
                            String sourceName,
                            String stageId,
                            String miniStageId,
                            String swimLaneId,
                            String workItemId) {
        super(sourceId, sourceName, stageId, miniStageId, swimLaneId, workItemId);
    }
}
