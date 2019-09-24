package com.practice.cleankanban.usecase.kanbanboard.stage.add.impl;

import com.practice.cleankanban.domain.model.kanbanboard.stage.Stage;
import com.practice.cleankanban.usecase.kanbanboard.stage.StageRepository;
import com.practice.cleankanban.usecase.kanbanboard.stage.add.AddStageInput;
import com.practice.cleankanban.usecase.kanbanboard.stage.add.AddStageOutput;
import com.practice.cleankanban.usecase.kanbanboard.stage.add.AddStageUseCase;

public class AddStageUseCaseImpl implements AddStageUseCase {
    private  StageRepository repository;
    public AddStageUseCaseImpl(StageRepository stageRepository) {
        this.repository = stageRepository;
    }

    @Override
    public void execute(AddStageInput input, AddStageOutput output) {
        Stage stage = new Stage(input.getStageName(), input.getBoardId());
        repository.save(stage);

        output.setStageId(stage.getId());
        output.setStageName(stage.getName());
        output.setMiniStageId(stage.getDefaultMiniStage().getId());
        output.setSwimLaneId(stage.getDefaultSwimLaneOfMiniStage().getId());
    }

    public static AddStageInput createInput() {
        return new AddStageInputImpl();
    }

    private static class AddStageInputImpl implements AddStageInput {
        private String stageName;
        private String boardId;

        @Override
        public String getStageName() {
            return stageName;
        }

        @Override
        public void setStageName(String stageName) {
            this.stageName = stageName;
        }

        @Override
        public String getBoardId() {
            return boardId;
        }

        @Override
        public void setBoardId(String boardId) {
            this.boardId = boardId;
        }
    }
}
