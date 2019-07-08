package com.practice.cleankanban.usecase.domainevent;

import com.practice.cleankanban.adapter.gateway.domainEvent.InMemoryDomainEventRepository;
import com.practice.cleankanban.adapter.gateway.kanbanboard.InMemoryStageRepository;
import com.practice.cleankanban.adapter.gateway.workItem.InMemoryWorkItemRepository;
import com.practice.cleankanban.adapter.presenter.kanbanboard.SingleStagePresenter;
import com.practice.cleankanban.domain.model.PersistentDomainEvent;
import com.practice.cleankanban.domain.model.kanbanboard.stage.Stage;
import com.practice.cleankanban.domain.model.kanbanboard.WipLimitExceedException;
import com.practice.cleankanban.domain.model.kanbanboard.stage.AbstractDomainEventTest;
import com.practice.cleankanban.domain.model.workItem.WorkItem;
import com.practice.cleankanban.usecase.domainevent.sourcing.RegisterEventSourcingSubscriberUseCase;
import com.practice.cleankanban.usecase.domainevent.sourcing.impl.RegisterEventSourcingSubscriberUseCaseImpl;
import com.practice.cleankanban.usecase.kanbanboard.stage.StageRepository;
import com.practice.cleankanban.usecase.kanbanboard.stage.add.AddStageInput;
import com.practice.cleankanban.usecase.kanbanboard.stage.add.AddStageOutput;
import com.practice.cleankanban.usecase.kanbanboard.stage.add.AddStageUseCase;
import com.practice.cleankanban.usecase.kanbanboard.stage.add.impl.AddStageUseCaseImpl;
import com.practice.cleankanban.usecase.workItem.WorkItemRepository;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class EventSourcingSubscriberTest extends AbstractDomainEventTest {

    private Stage stage;
    private DomainEventRepository<PersistentDomainEvent> domainEventRepository;
    private String[] workItemNames = {"apple", "IR", "stv"};
    private WorkItemRepository workItemRepository;

    @Before
    public void setUp() {
        domainEventRepository = new InMemoryDomainEventRepository();
        RegisterEventSourcingSubscriberUseCase useCase = new RegisterEventSourcingSubscriberUseCaseImpl(domainEventRepository);
        useCase.execute(null, null);
    }

    @Test
    public void create_a_work_item_publishes_a_workItemCreated_event() throws WipLimitExceedException {
        StageRepository stageRepository = new InMemoryStageRepository();
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
        stage = stageRepository.findByStageName("ToDo");
        for (String each:workItemNames) {
            WorkItem workItem = new WorkItem(each,
                                    stage.getId(),
                                    stage.getDefaultMiniStage().getId(),
                                    stage.getDefaultSwimLaneOfMiniStage().getId());
            workItemRepository.save(workItem);
            stage.committedWorkItemToSwimLaneById(stage.getDefaultSwimLaneOfMiniStage().getId(), workItem.getId());
        }

        assertEquals(3, stage.getDefaultSwimLaneOfMiniStage().getCommittedWorkItems().size());
        assertEquals(15, domainEventRepository.findAll().size());
        assertThat(domainEventRepository.findAll().get(0).getDetail()).startsWith("StageCreated");
    }

}
