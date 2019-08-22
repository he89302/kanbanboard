package com.practice.cleankanban.usecase.kanbanboard.stage.update.impl;

import com.practice.cleankanban.domain.model.kanbanboard.stage.Stage;
import com.practice.cleankanban.usecase.kanbanboard.stage.StageRepository;
import com.practice.cleankanban.usecase.kanbanboard.stage.update.UpdateStageInput;
import com.practice.cleankanban.usecase.kanbanboard.stage.update.UpdateStageOutput;
import com.practice.cleankanban.usecase.kanbanboard.stage.update.UpdateStageUseCase;

public class UpdateStageUseCaseImpl implements UpdateStageUseCase {

    private StageRepository repository;

    public UpdateStageUseCaseImpl(StageRepository stageRepository) {
        this.repository = stageRepository;
    }

    public static UpdateStageInput createInput() {
        return new UpdateStageInputImpl();
    }

    @Override
    public void execute(UpdateStageInput input, UpdateStageOutput output) {
        Stage stage = repository.findById(input.getStageId());
        stage.setStageName(input.getName());

        repository.save(stage);
        output.setStageId(stage.getId());
    }

    private static class UpdateStageInputImpl implements UpdateStageInput {

        private String id;
        private String name;

        @Override
        public String getStageId() {
            return id;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public void setStageId(String id) {
            this.id = id;
        }

        @Override
        public void setName(String name) {
            this.name = name;
        }
    }
}
