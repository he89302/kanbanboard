package com.practice.cleankanban.usecase.workItem.move.impl;

import com.practice.cleankanban.domain.model.kanbanboard.WipLimitExceedException;
import com.practice.cleankanban.domain.model.kanbanboard.stage.Stage;
import com.practice.cleankanban.domain.model.kanbanboard.stage.SwimLane;
import com.practice.cleankanban.domain.model.workItem.Blocker;
import com.practice.cleankanban.domain.model.workItem.WorkItem;
import com.practice.cleankanban.usecase.kanbanboard.stage.StageRepository;
import com.practice.cleankanban.usecase.workItem.WorkItemRepository;
import com.practice.cleankanban.usecase.workItem.block.impl.BlockerRepository;
import com.practice.cleankanban.usecase.workItem.move.MoveCommittedWorkItemInput;
import com.practice.cleankanban.usecase.workItem.move.MoveCommittedWorkItemOutput;
import com.practice.cleankanban.usecase.workItem.move.MoveCommittedWorkItemUseCase;

public class MoveCommittedWorkItemUseCaseImpl implements MoveCommittedWorkItemUseCase {

    private WorkItemRepository workItemRepository;
    private StageRepository stageRepository;
    private BlockerRepository blockerRepository;

    public MoveCommittedWorkItemUseCaseImpl(WorkItemRepository workItemRepository,
                                            StageRepository stageRepository,
                                            BlockerRepository blockerRepository) {
        this.workItemRepository = workItemRepository;
        this.stageRepository = stageRepository;
        this.blockerRepository = blockerRepository;
    }

    @Override
    public void execute(MoveCommittedWorkItemInput input, MoveCommittedWorkItemOutput output) {

        WorkItem workItem = workItemRepository.findWorkItemById(input.getWorkItemId());
        Stage fromStage = stageRepository.findById(workItem.getStageId());
        Stage toStage = stageRepository.findById(input.getStageId());

        SwimLane fromSwimLane = fromStage.getSwimLaneById(workItem.getSwimLaneId());
        SwimLane toSwimLane = toStage.getSwimLaneById(input.getSwimLaneId());
        if (!workItemIsBlocked(workItem, blockerRepository)) {
            fromStage.uncommittedWorkItemFromSwimLaneById(input.getWorkItemId(), fromSwimLane.getId());
            try {
                toStage.committedWorkItemToSwimLaneById(input.getSwimLaneId(), workItem.getId());
                workItem.moveTo(toStage.getId(), toStage.getMiniStagById(input.getMiniStageId()).getId(), toSwimLane.getId());

                workItemRepository.save(workItem);
                stageRepository.save(fromStage);
                stageRepository.save(toStage);

            } catch (WipLimitExceedException e) {
                throw new RuntimeException(e.getMessage());
            }
        } else {
            throw new RuntimeException("Work Item : " + workItem.getName() + " is blocked.");
        }
    }

    private boolean workItemIsBlocked(WorkItem workItem, BlockerRepository blockerRepository) {
        Blocker blocker = blockerRepository.findAllBlocker().iterator().next();
        return blocker.isWorkItemBlocked(workItem.getId());
    }

    public static MoveCommittedWorkItemInput createInput() {
        return new MoveCommittedWorkItemInputImpl();
    }

    private static class MoveCommittedWorkItemInputImpl implements MoveCommittedWorkItemInput {
        private String stageId;
        private String miniStageId;
        private String swimLaneId;
        private String workItemId;

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

        @Override
        public void setWorkItemId(String id) {
            this.workItemId = id;
        }

        @Override
        public void setToStageId(String id) {
            this.stageId = id;
        }

        @Override
        public void setToMiniStageId(String id) {
            this.miniStageId = id;
        }

        @Override
        public void setToSwimLaneId(String id) {
            this.swimLaneId = id;
        }
    }
}
