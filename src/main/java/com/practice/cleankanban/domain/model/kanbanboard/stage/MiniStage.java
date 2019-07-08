package com.practice.cleankanban.domain.model.kanbanboard.stage;

import com.practice.cleankanban.domain.Entity;
import com.practice.cleankanban.domain.model.DomainEventPublisher;
import com.practice.cleankanban.domain.model.kanbanboard.stage.event.MiniStageCreated;

import java.util.ArrayList;
import java.util.List;

public class MiniStage extends Entity {
    private String stageId;
    private List<SwimLane> swimLanes = new ArrayList<>();

    public MiniStage(String name, String stageId) {
        super(name);

        this.stageId = stageId;

        DomainEventPublisher.instance()
                            .publish(new MiniStageCreated(
                                    this.getId(),
                                    this.getName()));

        addDefaultSwimLane();
    }

    private void addDefaultSwimLane() {
        swimLanes.add(new SwimLane(this.getStageId(), this.getId()));
    }

    public int getContainSwimLaneSize() {
        return swimLanes.size();
    }

    public void createSwimLane(String swimLaneName, String miniStageId) {
        swimLanes.add(new SwimLane(swimLaneName, miniStageId));
    }

    public SwimLane getDefaultSwimLane() {
        return swimLanes.get(0);
    }

    public SwimLane getSwimLaneById(String id) {
        for (SwimLane each:swimLanes
             ) {
            if (each.getId().equals(id)) {
                return each;
            }
        }
        throw new RuntimeException("Swim Lane does not found.");
    }

    public String getStageId() {
        return stageId;
    }

    public void setStageId(String id) {
        this.stageId = id;
    }
}
