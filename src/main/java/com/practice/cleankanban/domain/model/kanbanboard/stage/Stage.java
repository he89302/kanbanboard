package com.practice.cleankanban.domain.model.kanbanboard.stage;

import com.practice.cleankanban.domain.Entity;
import com.practice.cleankanban.domain.model.DomainEventPublisher;
import com.practice.cleankanban.domain.model.kanbanboard.WipLimitExceedException;
import com.practice.cleankanban.domain.model.kanbanboard.stage.event.StageCreated;
import com.practice.cleankanban.domain.model.kanbanboard.stage.event.StageUpdated;
import de.cronn.reflection.util.immutable.ImmutableProxy;

import java.util.ArrayList;
import java.util.List;

public class Stage extends Entity {
    private String boardId;
    private List<MiniStage> miniStages;


    public Stage(String name, String boardId) {
        super(name);

        this.setBoardId(boardId);
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

        if (this.getBoardId() != null) {
            throw new IllegalStateException("The board id may not be changed.");
        }

        if (boardId == null) {
            throw new IllegalArgumentException("The board id may not be set to null.");
        }

        this.boardId = boardId;
    }

    public List<MiniStage> getMiniStages() {
        return getImmutableMiniStages();
    }

    private List<MiniStage> getImmutableMiniStages() {
        List<MiniStage> result = new ArrayList<>();
        for (MiniStage miniStage:miniStages) {
            result.add(ImmutableProxy.create(miniStage));
        }
        return result;
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

    public MiniStage getMiniStagById(String id) {
        return getImmutableMiniStage(doGetMiniStageById(id));
    }

    public SwimLane getDefaultSwimLaneOfMiniStage() {
        return getImmutableSwimLane(miniStages.get(0).getDefaultSwimLane());
    }

    public MiniStage getDefaultMiniStage() {
        return getImmutableMiniStage(miniStages.get(0));
    }

    public void committedWorkItemToSwimLaneById(String swimLaneId, String workItemId) throws WipLimitExceedException {
        SwimLane swimLane = doGetSwimLaneById(swimLaneId);
        swimLane.committedWorkItemById(workItemId);
    }

    public SwimLane getSwimLaneById(String id) {
        return getImmutableSwimLane(doGetSwimLaneById(id));
    }


    public SwimLane createSwimLaneForMiniStage(String miniStageId) {
        return getImmutableSwimLane(doGetMiniStageById(miniStageId).createSwimLane());
    }

    public boolean uncommittedWorkItemFromSwimLaneById(String workItemId, String swimLaneId) {
        SwimLane swimLane = doGetSwimLaneById(swimLaneId);
        return swimLane.uncommittedWorkItemById(workItemId);
    }

    private SwimLane getImmutableSwimLane(SwimLane originalSwimLane) {
        return ImmutableProxy.create(originalSwimLane);
    }

    private MiniStage getImmutableMiniStage(MiniStage originalMiniStage) {
        return ImmutableProxy.create(originalMiniStage);
    }

    private MiniStage doGetMiniStageById(String id) {
        for (MiniStage each:miniStages) {
            if (each.getId().equalsIgnoreCase(id)) {
                return each;
            }
        }
        throw new RuntimeException("Can't found mini stage by mini stage id : " + id);
    }

    private SwimLane doGetSwimLaneById(String id) {
        for (MiniStage each:miniStages
        ) {
            if (each.getSwimLaneById(id) != null) {
                return  each.getSwimLaneById(id);
            }
        }
        throw new RuntimeException("Swim Lane does not found by swim lane id : " + id);
    }

    public void setSwimLaneWipLimit(String id, int wipLimit) {
        doGetSwimLaneById(id).setWipLimit(wipLimit);
    }

    public void setStageName(String aName) {
        if (aName == null) {
            throw new IllegalArgumentException("The stage name may not be set to null.");
        }

        this.setName(aName);

        DomainEventPublisher.instance().
                publish(new StageUpdated(
                        this.getId(),
                        this.getName()));
    }
}
