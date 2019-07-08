package com.practice.cleankanban.usecase.kanbanboard.swimLane;

import com.practice.cleankanban.domain.model.kanbanboard.stage.MiniStage;
import com.practice.cleankanban.domain.model.kanbanboard.stage.Stage;
import com.practice.cleankanban.usecase.Utility;
import com.practice.cleankanban.usecase.kanbanboard.swimLane.add.impl.AddSwimLaneUseCaseImpl;
import com.practice.cleankanban.usecase.kanbanboard.stage.StageRepository;
import com.practice.cleankanban.usecase.kanbanboard.swimLane.add.AddSwimLaneInput;
import com.practice.cleankanban.usecase.kanbanboard.swimLane.add.AddSwimLaneOutput;
import com.practice.cleankanban.usecase.kanbanboard.swimLane.add.AddSwimLaneUseCase;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class addSwimLaneTest {
    @Test
    public void add_swim_lane_on_mini_stage() {
        Utility utility = new Utility();
        StageRepository stageRepository = utility.invoke();

        Stage stage = stageRepository.findByStageName("Doing");

        AddSwimLaneUseCase swimLaneUseCase = new AddSwimLaneUseCaseImpl(stageRepository);
        AddSwimLaneInput swimLaneInput = AddSwimLaneUseCaseImpl.createInput();
        AddSwimLaneOutput swimLaneOutput = null;

        swimLaneInput.setSwimLaneName("TeamA");
        swimLaneInput.setMiniStageId(stage.getMiniStages().get(0).getId());
        swimLaneInput.setStageId(stage.getId());
        swimLaneUseCase.execute(swimLaneInput, swimLaneOutput);


        MiniStage miniStage = stage.getMiniStages().get(0);

        assertEquals(2, miniStage.getContainSwimLaneSize());
    }
}