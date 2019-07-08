package com.practice.cleankanban.usecase.kanbanboard.miniStage;

import static org.junit.Assert.*;

import com.practice.cleankanban.domain.model.kanbanboard.stage.MiniStage;
import com.practice.cleankanban.usecase.Utility;
import com.practice.cleankanban.usecase.kanbanboard.miniStage.add.AddMiniStageInput;
import com.practice.cleankanban.usecase.kanbanboard.miniStage.add.AddMiniStageOutput;
import com.practice.cleankanban.usecase.kanbanboard.miniStage.add.AddMiniStageUseCase;
import com.practice.cleankanban.usecase.kanbanboard.miniStage.add.impl.AddMiniStageUseCaseImpl;
import com.practice.cleankanban.usecase.kanbanboard.stage.StageRepository;
import org.junit.Test;

import java.util.List;

public class addMiniStageTest {

    @Test
    public void add_miniStage_on_stage() {
        Utility utility = new Utility();
        StageRepository stageRepository = utility.invoke();

        AddMiniStageUseCase addMiniStageUseCase = new AddMiniStageUseCaseImpl(stageRepository);
        AddMiniStageInput addMiniStageInput = AddMiniStageUseCaseImpl.createInput();
        AddMiniStageOutput addMiniStageOutput = null;

        addMiniStageInput.setMiniStageName("Done");
        addMiniStageInput.setStageId(stageRepository.findAll().get(0).getId());

        addMiniStageUseCase.execute(addMiniStageInput, addMiniStageOutput);
        List<MiniStage> miniStageList = stageRepository.findAll().get(0).getMiniStages();
        assertEquals(2, miniStageList.size());
    }
}