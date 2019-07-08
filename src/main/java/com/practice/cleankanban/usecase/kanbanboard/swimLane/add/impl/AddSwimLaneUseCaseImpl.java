package com.practice.cleankanban.usecase.kanbanboard.swimLane.add.impl;

import com.practice.cleankanban.domain.model.kanbanboard.stage.Stage;
import com.practice.cleankanban.usecase.kanbanboard.stage.StageRepository;
import com.practice.cleankanban.usecase.kanbanboard.swimLane.add.AddSwimLaneInput;
import com.practice.cleankanban.usecase.kanbanboard.swimLane.add.AddSwimLaneOutput;
import com.practice.cleankanban.usecase.kanbanboard.swimLane.add.AddSwimLaneUseCase;

public class AddSwimLaneUseCaseImpl implements AddSwimLaneUseCase {
    private StageRepository repository;

    public AddSwimLaneUseCaseImpl(StageRepository stageRepository) {
        this.repository = stageRepository;
    }

    @Override
    public void execute(AddSwimLaneInput input, AddSwimLaneOutput output) {
        Stage stage = repository.findById(input.getStageId());
        stage.createSwimLane(input.getSwimLaneName(), input.getMiniStageId());
        repository.save(stage);
    }

    public static AddSwimLaneInput createInput() {
        return new AddSwimLaneInputImpl();
    }

    private static class AddSwimLaneInputImpl implements AddSwimLaneInput{
        private String name;
        private String stageId;
        private String miniStageId;

        @Override
        public void setSwimLaneName(String name) {
            this.name = name;
        }

        @Override
        public String getSwimLaneName() {
            return name;
        }

        @Override
        public void setMiniStageId(String miniStageId) {
            this.miniStageId = miniStageId;
        }

        @Override
        public String getMiniStageId() {
            return miniStageId;
        }

        @Override
        public void setStageId(String stageId) {
            this.stageId = stageId;
        }

        @Override
        public String getStageId() {
            return stageId;
        }
    }
}
