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


@RestController
public class SingleStagePresenter implements AddStageOutput {
    private String stageId;
    private String stageName;
    private String miniStageId;

    @RequestMapping(value = "stage", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addStageRestfulAPI(@RequestBody StageModel stageModal) {
        StageRepository repository = new InMemoryStageRepository();
        AddStageUseCase useCase = new AddStageUseCaseImpl(repository);
        AddStageInput input = AddStageUseCaseImpl.createInput();
        AddStageOutput output = this;

        input.setBoardId(stageModal.getBoardId());
        input.setStageName(stageModal.getName());

        useCase.execute(input, output);

        StageModel model = SingleStagePresenter.createStageModel(output);

        return ResponseEntity.status(HttpStatus.OK).body(model);
    }

    public ResponseEntity getStageRestfulAPI(@PathVariable String stageId) {
        return (ResponseEntity) ResponseEntity.status(HttpStatus.CONFLICT);
    }

    private static StageModel createStageModel(AddStageOutput output) {
        StageModel modal = new StageModel();
        modal.setStageId(output.getStageId());
        modal.setMiniStageId(output.getMiniStageId());
        modal.setName(output.getStageName());

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
}
