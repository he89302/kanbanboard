package com.practice.cleankanban.domain.model.kanbanboard.stage;

import com.practice.cleankanban.domain.Entity;
import com.practice.cleankanban.domain.model.DomainEventPublisher;
import com.practice.cleankanban.domain.model.kanbanboard.WipLimitExceedException;
import com.practice.cleankanban.domain.model.kanbanboard.stage.event.StageCreated;

import java.util.ArrayList;
import java.util.List;

public class Stage extends Entity {
    private String boardId;
    private List<MiniStage> miniStages;


    public Stage(String name, String boardId) {
        super(name);

        this.boardId = boardId;
        miniStages = new ArrayList<>();

        DomainEventPublisher.instance().
                publish(new StageCreated(
                        this.getId(),
                        this.getName()));

        addDefaultMiniStage();
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public List<MiniStage> getMiniStages() {
        return miniStages;
    }

    public void setMiniStages(List<MiniStage> miniStages) {
        this.miniStages = miniStages;
    }

    private void addDefaultMiniStage() {
        miniStages.add(new MiniStage("", this.getId()));
    }

    public void createMiniStage(String miniStageName) {
        miniStages.add(new MiniStage(miniStageName, this.getId()));
    }

    public MiniStage getMiniStagById(String miniStageId) {
        for (MiniStage each:miniStages) {
            if (each.getId().equals(miniStageId)) {
                return each;
            }
        }
        throw new RuntimeException("Can't found mini stage by mini stage id : " + miniStageId);
    }

    public SwimLane getDefaultSwimLaneOfMiniStage() {
        MiniStage miniStage = miniStages.get(0);
        return miniStage.getDefaultSwimLane();
    }

    public MiniStage getDefaultMiniStage() {
        return miniStages.get(0);
    }

    public void committedWorkItemToSwimLaneById(String swimLaneId, String workItemId) throws WipLimitExceedException {
        SwimLane swimLane = getSwimLaneById(swimLaneId);
        swimLane.committedWorkItemById(workItemId);
    }

    private SwimLane getSwimLaneById(String id) {
        for (MiniStage each:miniStages
             ) {
            if (each.getSwimLaneById(id) != null) {
                return  each.getSwimLaneById(id);
            }
        }
        throw new RuntimeException("Swim Lane does not found.");
    }

    public void createSwimLane(String swimLaneName, String miniStageId) {
        for (MiniStage each: miniStages
             ) {
            if (each.getId().equals(miniStageId)) {
                each.createSwimLane(swimLaneName, each.getId());
            }
        }
    }

    public void uncommittedWorkItemFromSwimLaneById(String workItemId, String swimLaneId) {
        SwimLane swimLane = this.getSwimLaneById(swimLaneId);
        swimLane.uncommittedWorkItemById(workItemId);
    }
}
