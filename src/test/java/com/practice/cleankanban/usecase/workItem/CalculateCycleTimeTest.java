package com.practice.cleankanban.usecase.workItem;

import com.practice.cleankanban.adapter.gateway.domainEvent.InMemoryDomainEventRepository;
import com.practice.cleankanban.adapter.gateway.kanbanboard.InMemoryStageRepository;
import com.practice.cleankanban.adapter.gateway.workItem.InMemoryBlockerRepository;
import com.practice.cleankanban.adapter.gateway.workItem.InMemoryWorkItemRepository;
import com.practice.cleankanban.adapter.presenter.SingleWorkItemPresenter;
import com.practice.cleankanban.adapter.presenter.kanbanboard.SingleStagePresenter;
import com.practice.cleankanban.domain.common.DateProvider;
import com.practice.cleankanban.domain.model.FlowEvent;
import com.practice.cleankanban.domain.model.kanbanboard.WipLimitExceedException;
import com.practice.cleankanban.domain.model.kanbanboard.stage.AbstractDomainEventTest;
import com.practice.cleankanban.domain.model.kanbanboard.stage.Stage;
import com.practice.cleankanban.domain.model.workItem.WorkItem;
import com.practice.cleankanban.usecase.domainevent.DomainEventRepository;
import com.practice.cleankanban.usecase.domainevent.flow.impl.RegisterFlowEventSourcingSubscriberUseCaseImpl;
import com.practice.cleankanban.usecase.kanbanboard.stage.StageRepository;
import com.practice.cleankanban.usecase.kanbanboard.stage.add.AddStageInput;
import com.practice.cleankanban.usecase.kanbanboard.stage.add.AddStageOutput;
import com.practice.cleankanban.usecase.kanbanboard.stage.add.AddStageUseCase;
import com.practice.cleankanban.usecase.kanbanboard.stage.add.impl.AddStageUseCaseImpl;
import com.practice.cleankanban.usecase.workItem.block.impl.BlockerRepository;
import com.practice.cleankanban.usecase.workItem.create.CreateWorkItemInput;
import com.practice.cleankanban.usecase.workItem.create.CreateWorkItemOutput;
import com.practice.cleankanban.usecase.workItem.create.CreateWorkItemUseCase;
import com.practice.cleankanban.usecase.workItem.create.impl.CreateWorkItemUseCaseImpl;
import com.practice.cleankanban.usecase.domainevent.flow.RegisterFlowEventSourcingSubscriberUseCase;
import com.practice.cleankanban.usecase.workItem.move.MoveCommittedWorkItemInput;
import com.practice.cleankanban.usecase.workItem.move.MoveCommittedWorkItemUseCase;
import com.practice.cleankanban.usecase.workItem.move.impl.MoveCommittedWorkItemUseCaseImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class CalculateCycleTimeTest extends AbstractDomainEventTest {

    private SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
    private Stage todo;
    private Stage doing;
    private Stage done;
    private WorkItem apple;
    private WorkItem fakeApple;

    @Mock
    private StageRepository stageRepository;
    private DomainEventRepository<FlowEvent> flowEventRepository;
    private WorkItemRepository workItemRepository;
    private BlockerRepository blockerRepository;


    @Before
    public void setUp() throws WipLimitExceedException, ParseException {
        DateProvider.setDate(dateFormat.parse("2019-06-01 00:00:00"));

        flowEventRepository = new InMemoryDomainEventRepository<FlowEvent>();
        RegisterFlowEventSourcingSubscriberUseCase useCase = new RegisterFlowEventSourcingSubscriberUseCaseImpl(flowEventRepository);
        useCase.execute(null, null);

        blockerRepository = new InMemoryBlockerRepository();
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

        CreateWorkItemInput createWorkItemInput = CreateWorkItemUseCaseImpl.createInput();
        CreateWorkItemOutput createWorkItemOutput = new SingleWorkItemPresenter();

        createWorkItemInput.setWorkItemName("apple");
        createWorkItemInput.setSwimLaneId(todo.getDefaultSwimLaneOfMiniStage().getId());
        createWorkItemInput.setMiniStageId(todo.getDefaultMiniStage().getId());
        createWorkItemInput.setStageId(todo.getId());

        appleUseCase.execute(createWorkItemInput, createWorkItemOutput);
        apple = workItemRepository.findWorkItemById(createWorkItemOutput.getWorkItemId());

        todo.committedWorkItemToSwimLaneById(todo.getDefaultSwimLaneOfMiniStage().getId(), apple.getId());

        CreateWorkItemUseCase fakeAppleUseCase = new CreateWorkItemUseCaseImpl(workItemRepository);
        createWorkItemInput.setWorkItemName("POSD");
        createWorkItemInput.setStageId(todo.getId());
        createWorkItemInput.setMiniStageId(todo.getDefaultMiniStage().getId());
        createWorkItemInput.setSwimLaneId(todo.getDefaultSwimLaneOfMiniStage().getId());
        fakeAppleUseCase.execute(createWorkItemInput, createWorkItemOutput);
        fakeApple = workItemRepository.findWorkItemById(createWorkItemOutput.getWorkItemId());
        todo.committedWorkItemToSwimLaneById(todo.getDefaultSwimLaneOfMiniStage().getId(), fakeApple.getId());

        assertEquals(2, flowEventRepository.findAll().size());
        assertThat(flowEventRepository.findAll().get(0).detail()).startsWith("WorkItemMovedIn['occurredOn='2019-06-01");
    }

    @Test
    public void calculate_cycle_time_when_work_item_move_to_done_from_todo() throws ParseException {
        DateProvider.setDate(dateFormat.parse("2019-06-08 09:10:24"));
        assertEquals(2, todo.getDefaultSwimLaneOfMiniStage().getCommittedWorkItems().size());
        assertEquals(2, flowEventRepository.findAll().size());

        MoveCommittedWorkItemUseCase moveCommittedWorkItemUseCase =
                new MoveCommittedWorkItemUseCaseImpl(workItemRepository, stageRepository, blockerRepository);

        MoveCommittedWorkItemInput input = MoveCommittedWorkItemUseCaseImpl.createInput();
        input.setToStageId(doing.getId());
        input.setToMiniStageId(doing.getDefaultMiniStage().getId());
        input.setToSwimLaneId(doing.getDefaultSwimLaneOfMiniStage().getId());
        input.setWorkItemId(apple.getId());

        moveCommittedWorkItemUseCase.execute(input, null);
        assertEquals(1, todo.getDefaultSwimLaneOfMiniStage().getCommittedWorkItems().size());
        assertEquals(1, doing.getDefaultSwimLaneOfMiniStage().getCommittedWorkItems().size());
        assertThat(flowEventRepository.findAll().get(2).detail()).startsWith("WorkItemMovedOut['occurredOn='2019-06-08");
        assertThat(flowEventRepository.findAll().get(3).detail()).startsWith("WorkItemMovedIn['occurredOn='2019-06-08");

        DateProvider.setDate(dateFormat.parse("2019-06-10 00:30:00"));

        input.setToStageId(done.getId());
        input.setToMiniStageId(done.getDefaultMiniStage().getId());
        input.setToSwimLaneId(done.getDefaultSwimLaneOfMiniStage().getId());
        input.setWorkItemId(apple.getId());

        moveCommittedWorkItemUseCase.execute(input, null);
        assertEquals(0, doing.getDefaultSwimLaneOfMiniStage().getCommittedWorkItems().size());
        assertEquals(1, done.getDefaultSwimLaneOfMiniStage().getCommittedWorkItems().size());
        assertThat(flowEventRepository.findAll().get(4).detail()).startsWith("WorkItemMovedOut['occurredOn='2019-06-10");
        assertThat(flowEventRepository.findAll().get(5).detail()).startsWith("WorkItemMovedIn['occurredOn='2019-06-10");

        DateProvider.setDate(dateFormat.parse("2019-06-10 12:30:00"));
        CycleTimeCalculator cycleTimeCalculator = new CycleTimeCalculator(flowEventRepository);
        List<FlowEntryPair> flowEntryPairs = cycleTimeCalculator.getCycleTime(apple.getId());

        System.out.println("work Item Name : [ " + apple.getName() + " ]");
        for (FlowEntryPair each:flowEntryPairs
             ) {
            Stage stage = stageRepository.findById(each.getStageId());
            System.out.print("[ " + stage.getName() + " ]");
            System.out.println(each.getCycleTime().toString());
        }
    }
}
