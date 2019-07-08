package com.practice.cleankanban.usecase.workItem;

import com.practice.cleankanban.domain.model.FlowEvent;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class FlowEntryPair {
    private String stageId;
    private String miniStageId;
    private String swimLaneId;
    private String workItemId;
    private Date occurredMoveIn;
    private Date occurredMoveOut;
    private final CycleTime cycleTime;

    public FlowEntryPair(FlowEvent movedIn, FlowEvent movedOut) {
        assert movedIn.getSwimLaneId() == movedOut.getSwimLaneId();
        assert movedIn.getMiniStageId() == movedOut.getMiniStageId();
        assert movedIn.getSwimLaneId() == movedOut.getSwimLaneId();
        assert movedIn.getWorkItemId() == movedOut.getWorkItemId();
        assert movedIn.occurredOn().before(movedOut.occurredOn());

        stageId = movedIn.getStageId();
        miniStageId = movedIn.getMiniStageId();
        swimLaneId = movedIn.getSwimLaneId();
        workItemId = movedIn.getWorkItemId();
        occurredMoveIn = movedIn.occurredOn();
        occurredMoveOut = movedOut.occurredOn();

        long diff = (occurredMoveOut.getTime() - occurredMoveIn.getTime())/1000;
        long diffDays = diff/(24*3600);
        long diffHours = diff %(24*3600)/3600;
        long diffMinutes = diff % 3600/60;
        long diffSeconds = diff % 60/60;

        cycleTime = new CycleTime(diffDays, diffHours, diffMinutes, diffSeconds);
    }

    public String getStageId() {
        return stageId;
    }

    public CycleTime getCycleTime() {
        return cycleTime;
    }
}
