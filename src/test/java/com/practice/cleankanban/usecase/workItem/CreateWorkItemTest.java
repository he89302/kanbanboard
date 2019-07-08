package com.practice.cleankanban.usecase.workItem;

import com.practice.cleankanban.adapter.gateway.workItem.InMemoryWorkItemRepository;
import com.practice.cleankanban.adapter.presenter.SingleWorkItemPresenter;
import com.practice.cleankanban.domain.model.kanbanboard.stage.MiniStage;
import com.practice.cleankanban.domain.model.kanbanboard.stage.Stage;
import com.practice.cleankanban.usecase.Utility;
import com.practice.cleankanban.usecase.kanbanboard.stage.StageRepository;
import com.practice.cleankanban.usecase.workItem.create.CreateWorkItemInput;
import com.practice.cleankanban.usecase.workItem.create.CreateWorkItemOutput;
import com.practice.cleankanban.usecase.workItem.create.CreateWorkItemUseCase;
import com.practice.cleankanban.usecase.workItem.create.impl.CreateWorkItemUseCaseImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreateWorkItemTest {
    StageRepository stageRepository;
    WorkItemRepository workItemRepository = new InMemoryWorkItemRepository();
    Stage todo;

    @Before
    public void create_scrum_board() {
        Utility utility = new Utility();
        stageRepository = utility.invoke();
        todo = stageRepository.findByStageName("ToDo");

    }

    @Test
    public void create_work_item_does_not_committed_swim_lane() {
        MiniStage miniStage = todo.getDefaultMiniStage();

        CreateWorkItemUseCase createWorkItem = new CreateWorkItemUseCaseImpl(workItemRepository);
        CreateWorkItemInput createWorkItemInput = CreateWorkItemUseCaseImpl.createInput();
        CreateWorkItemOutput createWorkItemOutput = new SingleWorkItemPresenter();

        createWorkItemInput.setWorkItemName("Implements SA practice.");
        createWorkItemInput.setSwimLaneId(todo.getDefaultSwimLaneOfMiniStage().getId());
        createWorkItemInput.setMiniStageId(miniStage.getId());
        createWorkItemInput.setStageId(todo.getId());

        createWorkItem.execute(createWorkItemInput, createWorkItemOutput);

        assertEquals("Implements SA practice.", createWorkItemOutput.getName());
    }
}
