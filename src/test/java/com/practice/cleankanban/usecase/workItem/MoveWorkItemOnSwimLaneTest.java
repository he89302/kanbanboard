package com.practice.cleankanban.usecase.workItem;

import com.practice.cleankanban.adapter.gateway.domainEvent.InMemoryDomainEventRepository;
import com.practice.cleankanban.adapter.gateway.kanbanboard.InMemoryStageRepository;
import com.practice.cleankanban.adapter.gateway.workItem.InMemoryWorkItemRepository;
import com.practice.cleankanban.adapter.presenter.SingleWorkItemPresenter;
import com.practice.cleankanban.adapter.presenter.kanbanboard.SingleStagePresenter;
import com.practice.cleankanban.domain.model.PersistentDomainEvent;
import com.practice.cleankanban.domain.model.kanbanboard.WipLimitExceedException;
import com.practice.cleankanban.domain.model.kanbanboard.stage.AbstractDomainEventTest;
import com.practice.cleankanban.domain.model.kanbanboard.stage.Stage;
import com.practice.cleankanban.domain.model.workItem.WorkItem;
import com.practice.cleankanban.usecase.domainevent.DomainEventRepository;
import com.practice.cleankanban.usecase.domainevent.sourcing.RegisterEventSourcingSubscriberUseCase;
import com.practice.cleankanban.usecase.domainevent.sourcing.impl.RegisterEventSourcingSubscriberUseCaseImpl;
import com.practice.cleankanban.usecase.kanbanboard.stage.StageRepository;
import com.practice.cleankanban.usecase.kanbanboard.stage.add.AddStageInput;
import com.practice.cleankanban.usecase.kanbanboard.stage.add.AddStageOutput;
import com.practice.cleankanban.usecase.kanbanboard.stage.add.AddStageUseCase;
import com.practice.cleankanban.usecase.kanbanboard.stage.add.impl.AddStageUseCaseImpl;
import com.practice.cleankanban.usecase.workItem.create.CreateWorkItemInput;
import com.practice.cleankanban.usecase.workItem.create.CreateWorkItemOutput;
import com.practice.cleankanban.usecase.workItem.create.CreateWorkItemUseCase;
import com.practice.cleankanban.usecase.workItem.create.impl.CreateWorkItemUseCaseImpl;
import com.practice.cleankanban.usecase.workItem.move.MoveCommittedWorkItemInput;
import com.practice.cleankanban.usecase.workItem.move.MoveCommittedWorkItemUseCase;
import com.practice.cleankanban.usecase.workItem.move.impl.MoveCommittedWorkItemUseCaseImpl;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;

public class MoveWorkItemOnSwimLaneTest extends AbstractDomainEventTest {

    private Stage todo;
    private Stage doing;
    private Stage done;
    private WorkItem apple;
    private WorkItem stv;
    private StageRepository stageRepository;
    private DomainEventRepository<PersistentDomainEvent> domainEventRepository;
    private WorkItemRepository workItemRepository;

    @Before
    public  void prepare_two_work_item_on_scrum_board() throws WipLimitExceedException{
        domainEventRepository = new InMemoryDomainEventRepository();
        RegisterEventSourcingSubscriberUseCase useCase = new RegisterEventSourcingSubscriberUseCaseImpl(domainEventRepository);
        useCase.execute(null, null);

        stageRepository = new InMemoryStageRepository();
        AddStageUseCase addStageUseCase =  new AddStageUseCaseImpl(stageRepository);
        AddStageInput addStageInputForDoing = AddStageUseCaseImpl.createInput();
        AddStageOutput addStageOutput = new SingleStagePresenter();

        AddStageInput addStageInputForToDo = AddStageUseCaseImpl.createInput();
        AddStageInput addStageInputForDone = AddStageUseCaseImpl.createInput();

        addStageInputForToDo.setStageName("ToDo");
        addStageInputForDoing.setBoardId("223-12dsf-63344-ddf");

        addStageInputForDoing.setStageName("Doing");
        addStageInputForDoing.setBoardId("223-12dsf-63344-ddf");

        addStageInputForDone.setStageName("Done");
        addStageInputForDone.setBoardId("223-12dsf-63344-ddf");

        addStageUseCase.execute(addStageInputForToDo, addStageOutput);
        addStageUseCase.execute(addStageInputForDoing, addStageOutput);
        addStageUseCase.execute(addStageInputForDone, addStageOutput);

        workItemRepository = new InMemoryWorkItemRepository();

        todo = stageRepository.findByStageName("ToDo");
        doing = stageRepository.findByStageName("Doing");
        done = stageRepository.findByStageName("Done");

        CreateWorkItemUseCase appleUseCase = new CreateWorkItemUseCaseImpl(workItemRepository);
        CreateWorkItemUseCase stvUseCase = new CreateWorkItemUseCaseImpl(workItemRepository);

        CreateWorkItemInput createWorkItemInput = CreateWorkItemUseCaseImpl.createInput();
        CreateWorkItemOutput createWorkItemOutput = new SingleWorkItemPresenter();

        createWorkItemInput.setWorkItemName("apple");
        createWorkItemInput.setSwimLaneId(todo.getDefaultSwimLaneOfMiniStage().getId());
        createWorkItemInput.setMiniStageId(todo.getDefaultMiniStage().getId());
        createWorkItemInput.setStageId(todo.getId());

        appleUseCase.execute(createWorkItemInput, createWorkItemOutput);
        apple = workItemRepository.findWorkItemById(createWorkItemOutput.getWorkItemId());

        createWorkItemInput.setWorkItemName("stv");
        createWorkItemInput.setSwimLaneId(todo.getDefaultSwimLaneOfMiniStage().getId());
        createWorkItemInput.setMiniStageId(todo.getDefaultMiniStage().getId());
        createWorkItemInput.setStageId(todo.getId());

        stvUseCase.execute(createWorkItemInput, createWorkItemOutput);
        stv = workItemRepository.findWorkItemById(createWorkItemOutput.getWorkItemId());

        todo.committedWorkItemToSwimLaneById(todo.getDefaultSwimLaneOfMiniStage().getId(), apple.getId());
        todo.committedWorkItemToSwimLaneById(todo.getDefaultSwimLaneOfMiniStage().getId(), stv.getId());
    }


    @Test
    public void move_applePay_to_doing() throws WipLimitExceedException, ParseException {
        storedSubscriber.expectedResults.clear();
        assertEquals(2, todo.getDefaultSwimLaneOfMiniStage().getCommittedWorkItems().size());
        assertEquals(0, doing.getDefaultSwimLaneOfMiniStage().getCommittedWorkItems().size());

        MoveCommittedWorkItemUseCase moveCommittedWorkItemUseCase = new MoveCommittedWorkItemUseCaseImpl(workItemRepository, stageRepository);
        MoveCommittedWorkItemInput input = MoveCommittedWorkItemUseCaseImpl.createInput();
        input.setWorkItemId(apple.getId());
        input.setToStageId(doing.getId());
        input.setToMiniStageId(doing.getDefaultMiniStage().getId());
        input.setToSwimLaneId(doing.getDefaultSwimLaneOfMiniStage().getId());

        moveCommittedWorkItemUseCase.execute(input, null);

        assertEquals(1, todo.getDefaultSwimLaneOfMiniStage().getCommittedWorkItems().size());
        assertEquals(1, doing.getDefaultSwimLaneOfMiniStage().getCommittedWorkItems().size());
    }
}
