package com.practice.cleankanban.adapter.presenter.kanbanboard;

import com.practice.cleankanban.adapter.gateway.kanbanboard.InMemoryStageRepository;
import com.practice.cleankanban.adapter.presenter.kanbanboard.modal.stage.StageModel;
import com.practice.cleankanban.usecase.kanbanboard.stage.StageRepository;
import com.practice.cleankanban.usecase.kanbanboard.stage.add.AddStageInput;
import com.practice.cleankanban.usecase.kanbanboard.stage.add.AddStageOutput;
import com.practice.cleankanban.usecase.kanbanboard.stage.add.AddStageUseCase;
import com.practice.cleankanban.usecase.kanbanboard.stage.add.impl.AddStageUseCaseImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



public class SingleStagePresenter implements AddStageOutput {
    private String stageId;
    private String stageName;
    private String miniStageId;
    private String swimLaneId;

    public ResponseEntity getStageRestfulAPI(@PathVariable String stageId) {
        return (ResponseEntity) ResponseEntity.status(HttpStatus.CONFLICT);
    }

    public static StageModel createStageModel(AddStageOutput output, String boardId) {
        StageModel modal = new StageModel();
        modal.setBoardId(boardId);
        modal.setStageId(output.getStageId());
        modal.setMiniStageId(output.getMiniStageId());
        modal.setName(output.getStageName());
        modal.setSwimLaneId(output.getSwimLaneId());
        return modal;
    }

    public String getStageId() {
        return stageId;
    }

    @Override
    public void setStageId(String stageId) {
        this.stageId = stageId;
    }

    @Override
    public String getStageName() {
        return stageName;
    }

    @Override
    public void setStageName(String name) {
        this.stageName = name;
    }

    @Override
    public String getMiniStageId() {
        return miniStageId;
    }

    @Override
    public void setMiniStageId(String miniStageId) {
        this.miniStageId = miniStageId;
    }

    @Override
    public String getSwimLaneId() {
        return swimLaneId;
    }

    @Override
    public void setSwimLaneId(String swimLaneId) {
        this.swimLaneId = swimLaneId;
    }
}
