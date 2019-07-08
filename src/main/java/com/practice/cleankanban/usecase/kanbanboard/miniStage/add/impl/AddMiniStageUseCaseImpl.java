package com.practice.cleankanban.usecase.kanbanboard.miniStage.add.impl;

import com.practice.cleankanban.domain.model.kanbanboard.stage.Stage;
import com.practice.cleankanban.usecase.kanbanboard.miniStage.add.AddMiniStageInput;
import com.practice.cleankanban.usecase.kanbanboard.miniStage.add.AddMiniStageOutput;
import com.practice.cleankanban.usecase.kanbanboard.miniStage.add.AddMiniStageUseCase;
import com.practice.cleankanban.usecase.kanbanboard.stage.StageRepository;

public class AddMiniStageUseCaseImpl implements AddMiniStageUseCase {
    private StageRepository stageRepository;
    public AddMiniStageUseCaseImpl(StageRepository stageRepository) {
        this.stageRepository = stageRepository;
    }

    public static AddMiniStageInput createInput() {
        return new AddMiniStageInputImpl();
    }

    @Override
    public void execute(AddMiniStageInput input, AddMiniStageOutput output) {
        Stage stage = stageRepository.findById(input.getStageId());
        stage.createMiniStage(input.getMiniStageName());
        stageRepository.save(stage);
    }

    private static class AddMiniStageInputImpl implements AddMiniStageInput{
        private String name;
        private String stageId;

        @Override
        public String getStageId() {
            return stageId;
        }

        @Override
        public void setStageId(String id) {
            this.stageId = id;
        }

        @Override
        public void setMiniStageName(String name) {
            this.name = name;
        }

        @Override
        public String getMiniStageName() {
            return name;
        }
    }
}
