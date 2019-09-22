package com.practice.cleankanban.usecase.workItem;

import com.practice.cleankanban.adapter.gateway.domainEvent.InMemoryDomainEventRepository;
import com.practice.cleankanban.adapter.gateway.workItem.InMemoryBlockerRepository;
import com.practice.cleankanban.adapter.gateway.workItem.InMemoryWorkItemRepository;
import com.practice.cleankanban.adapter.presenter.SingleWorkItemPresenter;
import com.practice.cleankanban.domain.model.BlockDomainEvent;
import com.practice.cleankanban.domain.model.FlowEvent;
import com.practice.cleankanban.domain.model.PersistentDomainEvent;
import com.practice.cleankanban.domain.model.kanbanboard.WipLimitExceedException;
import com.practice.cleankanban.domain.model.kanbanboard.stage.Stage;
import com.practice.cleankanban.domain.model.workItem.WorkItem;
import com.practice.cleankanban.usecase.KanbanboardTestUtility;
import com.practice.cleankanban.usecase.domainevent.DomainEventRepository;
import com.practice.cleankanban.usecase.domainevent.block.RegisterBlockEventSourcingSubscriberUseCase;
import com.practice.cleankanban.usecase.domainevent.block.impl.RegisterBlockEventSourcingSubscriberUseCaseImpl;
import com.practice.cleankanban.usecase.domainevent.flow.RegisterFlowEventSourcingSubscriberUseCase;
import com.practice.cleankanban.usecase.domainevent.flow.impl.RegisterFlowEventSourcingSubscriberUseCaseImpl;
import com.practice.cleankanban.usecase.domainevent.sourcing.RegisterEventSourcingSubscriberUseCase;
import com.practice.cleankanban.usecase.domainevent.sourcing.impl.RegisterEventSourcingSubscriberUseCaseImpl;
import com.practice.cleankanban.usecase.kanbanboard.board.BoardRepository;
import com.practice.cleankanban.usecase.kanbanboard.stage.StageRepository;
import com.practice.cleankanban.usecase.workItem.block.BlockWorkItemInput;
import com.practice.cleankanban.usecase.workItem.block.BlockWorkItemOutput;
import com.practice.cleankanban.usecase.workItem.block.BlockWorkItemUseCase;
import com.practice.cleankanban.adapter.presenter.kanbanboard.SingleBlockWorkItemPresenter;
import com.practice.cleankanban.usecase.workItem.block.impl.BlockWorkItemUseCaseImpl;
import com.practice.cleankanban.usecase.workItem.block.impl.BlockerRepository;
import com.practice.cleankanban.usecase.workItem.create.CreateWorkItemInput;
import com.practice.cleankanban.usecase.workItem.create.CreateWorkItemOutput;
import com.practice.cleankanban.usecase.workItem.create.CreateWorkItemUseCase;
import com.practice.cleankanban.usecase.workItem.create.impl.CreateWorkItemUseCaseImpl;
import com.practice.cleankanban.usecase.workItem.move.MoveCommittedWorkItemInput;
import com.practice.cleankanban.usecase.workItem.move.MoveCommittedWorkItemOutput;
import com.practice.cleankanban.usecase.workItem.move.MoveCommittedWorkItemUseCase;
import com.practice.cleankanban.usecase.workItem.move.impl.MoveCommittedWorkItemUseCaseImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


/*
    ready;
    analysis;
    development;
    test;
    fixBug;
    deploy;
 */

public class BlockWorkItemTest {

    private KanbanboardTestUtility utility;
    private BoardRepository boardRepository;
    private StageRepository stageRepository;
    private WorkItemRepository workItemRepository;
    private BlockerRepository blockerRepository;
    private String boardId;
    private Stage ready;
    private Stage test;
    private WorkItem posd;
    private WorkItem stv;
    private DomainEventRepository<PersistentDomainEvent> persistentDomainEventRepository;
    private DomainEventRepository<BlockDomainEvent> blockDomainEventRepository;
    private DomainEventRepository<FlowEvent> flowEventDomainEventRepository;

    @Before
    public void setUp() throws WipLimitExceedException {
        utility = new KanbanboardTestUtility();
        utility.createKanbanBoardAndStage();
        boardRepository = utility.getBoardRepository();
        stageRepository = utility.getStageRepository();
        workItemRepository = new InMemoryWorkItemRepository();
        blockerRepository = new InMemoryBlockerRepository();
        blockDomainEventRepository = new InMemoryDomainEventRepository<>();
        boardId = utility.getBoardId();
        ready = stageRepository.findByStageName("ready");
        test = stageRepository.findByStageName("test");

        persistentDomainEventRepository = new InMemoryDomainEventRepository<PersistentDomainEvent>();
        blockDomainEventRepository = new InMemoryDomainEventRepository<BlockDomainEvent>();
        flowEventDomainEventRepository = new InMemoryDomainEventRepository<FlowEvent>();

        RegisterEventSourcingSubscriberUseCase registerEventSourcingSubscriberUseCase =
                new RegisterEventSourcingSubscriberUseCaseImpl(persistentDomainEventRepository);
        registerEventSourcingSubscriberUseCase.execute(null, null);

        RegisterFlowEventSourcingSubscriberUseCase registerFlowEventSourcingSubscriberUseCase =
                new RegisterFlowEventSourcingSubscriberUseCaseImpl(flowEventDomainEventRepository);

        registerFlowEventSourcingSubscriberUseCase.execute(null, null);

        RegisterBlockEventSourcingSubscriberUseCase registerBlockEventSourcingUseCase =
                new RegisterBlockEventSourcingSubscriberUseCaseImpl(blockDomainEventRepository);
        registerBlockEventSourcingUseCase.execute(null, null);

        CreateWorkItemUseCase appleUseCase = new CreateWorkItemUseCaseImpl(workItemRepository);
        CreateWorkItemUseCase stvUseCase = new CreateWorkItemUseCaseImpl(workItemRepository);

        CreateWorkItemInput createWorkItemInput = CreateWorkItemUseCaseImpl.createInput();
        CreateWorkItemOutput createWorkItemOutput = new SingleWorkItemPresenter();

        createWorkItemInput.setWorkItemName("posd");
        createWorkItemInput.setSwimLaneId(ready.getDefaultSwimLaneOfMiniStage().getId());
        createWorkItemInput.setMiniStageId(ready.getDefaultMiniStage().getId());
        createWorkItemInput.setStageId(ready.getId());

        appleUseCase.execute(createWorkItemInput, createWorkItemOutput);

        posd = workItemRepository.findWorkItemById(createWorkItemOutput.getWorkItemId());

        createWorkItemInput.setWorkItemName("stv");
        createWorkItemInput.setSwimLaneId(ready.getDefaultSwimLaneOfMiniStage().getId());
        createWorkItemInput.setMiniStageId(ready.getDefaultMiniStage().getId());
        createWorkItemInput.setStageId(ready.getId());

        stvUseCase.execute(createWorkItemInput, createWorkItemOutput);

        stv = workItemRepository.findWorkItemById(createWorkItemOutput.getWorkItemId());
        ready.committedWorkItemToSwimLaneById(ready.getDefaultSwimLaneOfMiniStage().getId(), posd.getId());
        ready.committedWorkItemToSwimLaneById(ready.getDefaultSwimLaneOfMiniStage().getId(), stv.getId());
        assertEquals(stv.getId(), ready.getDefaultSwimLaneOfMiniStage().getCommittedWorkItems().get(1).getWorkItemId());
    }

    @Test
    public void block_posd_on_ready_stage() {
        assertEquals(2, ready.getDefaultSwimLaneOfMiniStage().getCommittedWorkItems().size());


        assertEquals(posd.getName(), workItemRepository.findWorkItemById(
                                                        ready.getDefaultSwimLaneOfMiniStage().
                                                                getCommittedWorkItems().get(0).
                                                                getWorkItemId()).getName());

        BlockWorkItemUseCase useCase = new BlockWorkItemUseCaseImpl(blockerRepository);
        BlockWorkItemInput input = BlockWorkItemUseCaseImpl.createInput();
        BlockWorkItemOutput output = new SingleBlockWorkItemPresenter();

        input.setWorkItemId(stv.getId());
        input.setBlockNote("test issue");

        useCase.execute(input, output);

        assertEquals("test issue", output.getNote());

        MoveCommittedWorkItemUseCase moveCommittedWorkItemUseCase = new MoveCommittedWorkItemUseCaseImpl(workItemRepository, stageRepository, blockerRepository);
        MoveCommittedWorkItemInput moveCommittedWorkItemInput = MoveCommittedWorkItemUseCaseImpl.createInput();
        MoveCommittedWorkItemOutput moveCommittedWorkItemOutput = null;

        moveCommittedWorkItemInput.setToStageId(test.getId());
        moveCommittedWorkItemInput.setToMiniStageId(test.getDefaultMiniStage().getId());
        moveCommittedWorkItemInput.setToSwimLaneId(test.getDefaultSwimLaneOfMiniStage().getId());
        moveCommittedWorkItemInput.setWorkItemId(stv.getId());

        try {
            moveCommittedWorkItemUseCase.execute(moveCommittedWorkItemInput, null);
        }catch (RuntimeException e) {
            assertEquals("Work Item : stv is blocked.", e.getMessage());
        }

        assertEquals(2, flowEventDomainEventRepository.findAll().size());
        assertEquals(5, blockDomainEventRepository.findAll().size());
    }
}
