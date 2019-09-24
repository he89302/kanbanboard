package com.practice.cleankanban.adapter.resource;

import com.practice.cleankanban.adapter.gateway.kanbanboard.InMemoryBoardRepository;
import com.practice.cleankanban.adapter.gateway.kanbanboard.InMemoryStageRepository;
import com.practice.cleankanban.adapter.presenter.kanbanboard.MultipleStagePresenter;
import com.practice.cleankanban.adapter.presenter.kanbanboard.SingleStagePresenter;
import com.practice.cleankanban.adapter.presenter.kanbanboard.StageInfo;
import com.practice.cleankanban.adapter.presenter.kanbanboard.modal.stage.StageModel;
import com.practice.cleankanban.usecase.kanbanboard.board.BoardRepository;
import com.practice.cleankanban.usecase.kanbanboard.stage.StageRepository;
import com.practice.cleankanban.usecase.kanbanboard.stage.add.AddStageInput;
import com.practice.cleankanban.usecase.kanbanboard.stage.add.AddStageOutput;
import com.practice.cleankanban.usecase.kanbanboard.stage.add.AddStageUseCase;
import com.practice.cleankanban.usecase.kanbanboard.stage.add.impl.AddStageUseCaseImpl;
import com.practice.cleankanban.usecase.kanbanboard.stage.get.GetStagesInput;
import com.practice.cleankanban.usecase.kanbanboard.stage.get.GetStagesOutput;
import com.practice.cleankanban.usecase.kanbanboard.stage.get.GetStagesUseCase;
import com.practice.cleankanban.usecase.kanbanboard.stage.get.GetStagesUseCaseImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class StageResource {

    private StageRepository stageRepository;
    private BoardRepository boardRepository;

    @RequestMapping(value = "stage", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity doPostStage(@RequestBody StageModel stageModal) {
    AddStageUseCase useCase = new AddStageUseCaseImpl(stageRepository);
    AddStageInput input = AddStageUseCaseImpl.createInput();
    AddStageOutput output = new SingleStagePresenter();

    input.setBoardId(stageModal.getBoardId());
    input.setStageName(stageModal.getName());

    useCase.execute(input, output);

    if (!output.getStageId().isEmpty()) {
        StageModel model = SingleStagePresenter.createStageModel(output, input.getBoardId());
        return ResponseEntity.status(HttpStatus.OK).body(model);
    }

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @RequestMapping(value = "/stages/{boardId}", method = RequestMethod.GET)
    public List<ResponseEntity<StageInfo>> doGetMultipleStage(@PathVariable String boardId) {
        List<StageInfo> stageInfo = new ArrayList<>();
        GetStagesUseCase useCase = new GetStagesUseCaseImpl(boardRepository, stageRepository);
        GetStagesInput input = GetStagesUseCaseImpl.createInput();
        GetStagesOutput output = new MultipleStagePresenter();

        input.setBoardId(boardId);

        useCase.execute(input, output);

        stageInfo = StageInfoConverter.convertStageInfo(output.getStages());

        return (List<ResponseEntity<StageInfo>>) ResponseEntity.status(HttpStatus.OK).body(stageInfo);

    }
}
