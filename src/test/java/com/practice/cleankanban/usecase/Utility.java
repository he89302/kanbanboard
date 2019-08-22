package com.practice.cleankanban.usecase;

import com.practice.cleankanban.adapter.gateway.kanbanboard.InMemoryStageRepository;
import com.practice.cleankanban.adapter.presenter.kanbanboard.SingleStagePresenter;
import com.practice.cleankanban.usecase.kanbanboard.stage.StageRepository;
import com.practice.cleankanban.usecase.kanbanboard.stage.add.AddStageInput;
import com.practice.cleankanban.usecase.kanbanboard.stage.add.AddStageOutput;
import com.practice.cleankanban.usecase.kanbanboard.stage.add.AddStageUseCase;
import com.practice.cleankanban.usecase.kanbanboard.stage.add.impl.AddStageUseCaseImpl;

public class Utility {
    public static StageRepository invoke() {
        StageRepository stageRepository = new InMemoryStageRepository();
        AddStageUseCase addStageUseCase =  new AddStageUseCaseImpl(stageRepository);
        AddStageInput addStageInputForDoing = AddStageUseCaseImpl.createInput();
        AddStageOutput addStageOutput = new SingleStagePresenter();

        AddStageInput addStageInputForToDo = AddStageUseCaseImpl.createInput();
        AddStageInput addStageInputForDone = AddStageUseCaseImpl.createInput();

        addStageInputForToDo.setStageName("ToDo");
        addStageInputForToDo.setBoardId("223-12dsf-63344-ddf");

        addStageInputForDoing.setStageName("Doing");
        addStageInputForDoing.setBoardId("223-12dsf-63344-ddf");

        addStageInputForDone.setStageName("Done");
        addStageInputForDone.setBoardId("223-12dsf-63344-ddf");

        addStageUseCase.execute(addStageInputForToDo, addStageOutput);
        addStageUseCase.execute(addStageInputForDoing, addStageOutput);
        addStageUseCase.execute(addStageInputForDone, addStageOutput);
        return stageRepository;
    }
}
