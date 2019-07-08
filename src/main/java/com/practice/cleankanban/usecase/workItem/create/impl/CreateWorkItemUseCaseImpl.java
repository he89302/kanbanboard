package com.practice.cleankanban.usecase.workItem.create.impl;

import com.practice.cleankanban.domain.model.workItem.WorkItem;
import com.practice.cleankanban.usecase.workItem.WorkItemRepository;
import com.practice.cleankanban.usecase.workItem.create.CreateWorkItemInput;
import com.practice.cleankanban.usecase.workItem.create.CreateWorkItemOutput;
import com.practice.cleankanban.usecase.workItem.create.CreateWorkItemUseCase;

public class CreateWorkItemUseCaseImpl implements CreateWorkItemUseCase {
    WorkItemRepository workItemRepository;

    public CreateWorkItemUseCaseImpl(WorkItemRepository workItemRepository) {
        this.workItemRepository = workItemRepository;
    }

    @Override
    public void execute(CreateWorkItemInput input, CreateWorkItemOutput output) {
        WorkItem workItem = new WorkItem(input.getWorkItemName(),
                                            input.getStageId(),
                                            input.getMiniStageId(),
                                            input.getSwimLaneId());

        workItemRepository.save(workItem);

        output.setName(workItem.getName());
        output.setWorkItemId(workItem.getId());
    }

    public static CreateWorkItemInput createInput() {
        return new CreateWorkItemInputImpl();
    }


    private static class CreateWorkItemInputImpl implements CreateWorkItemInput {
        private String name;
        private String stageId;
        private String miniStageId;
        private String swimLaneId;

        public String getStageId() {
            return stageId;
        }

        public String getMiniStageId() {
            return miniStageId;
        }

        public String getSwimLaneId() {
            return swimLaneId;
        }

        @Override
        public void setWorkItemName(String name) {
            this.name = name;
        }

        @Override
        public String getWorkItemName() {
            return name;
        }

        @Override
        public void setSwimLaneId(String id) {
            this.swimLaneId = id;
        }

        @Override
        public void setMiniStageId(String id) {
            this.miniStageId = id;
        }

        @Override
        public void setStageId(String id) {
            this.stageId = id;
        }
    }
}
