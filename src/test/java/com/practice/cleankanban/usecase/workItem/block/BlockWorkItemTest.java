package com.practice.cleankanban.usecase.workItem.block;

import com.practice.cleankanban.adapter.gateway.domainEvent.InMemoryDomainEventRepository;
import com.practice.cleankanban.adapter.gateway.workItem.InMemoryWorkItemRepository;
import com.practice.cleankanban.adapter.presenter.SingleWorkItemPresenter;
import com.practice.cleankanban.domain.model.BlockEvent;
import com.practice.cleankanban.domain.model.kanbanboard.WipLimitExceedException;
import com.practice.cleankanban.domain.model.kanbanboard.stage.Stage;
import com.practice.cleankanban.domain.model.workItem.WorkItem;
import com.practice.cleankanban.usecase.KanbanboardTestUtility;
import com.practice.cleankanban.usecase.domainevent.DomainEventRepository;
import com.practice.cleankanban.usecase.kanbanboard.board.BoardRepository;
import com.practice.cleankanban.usecase.kanbanboard.stage.StageRepository;
import com.practice.cleankanban.usecase.workItem.WorkItemRepository;
import com.practice.cleankanban.usecase.workItem.block.impl.BlockWorkItemUseCaseImpl;
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
import org.springframework.cglib.core.Block;

import static org.junit.Assert.assertEquals;


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
    private String boardId;
    private Stage ready;
    private Stage test;
    private WorkItem posd;
    private WorkItem stv;
    private DomainEventRepository<BlockEvent> domainEventRepository;

    @Before
    public void setUp() throws WipLimitExceedException {
        utility = new KanbanboardTestUtility();
        utility.createKanbanBoardAndStage();
        boardRepository = utility.getBoardRepository();
        stageRepository = utility.getStageRepository();
        workItemRepository = new InMemoryWorkItemRepository();
        domainEventRepository = new InMemoryDomainEventRepository<>();
        boardId = utility.getBoardId();
        ready = stageRepository.findByStageName("ready");
        test = stageRepository.findByStageName("test");
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
//        assertEquals(posd.getName(), ready.getDefaultSwimLaneOfMiniStage().getCommittedWorkItems().get(0).getName());

        BlockWorkItemUseCase useCase = new BlockWorkItemUseCaseImpl(stageRepository, domainEventRepository);
        BlockWorkItemInput input = BlockWorkItemUseCaseImpl.createInput();
        BlockWorkItemOutput output = new SingleBlockWorkItemPresenter();

        input.setWorkItem(stv);
        input.setBlockNote("test issue");

        useCase.execute(input, output);

        assertEquals("test issue", output.getNote());

        MoveCommittedWorkItemUseCase moveCommittedWorkItemUseCase = new MoveCommittedWorkItemUseCaseImpl(workItemRepository, stageRepository);
        MoveCommittedWorkItemInput moveCommittedWorkItemInput = MoveCommittedWorkItemUseCaseImpl.createInput();
        MoveCommittedWorkItemOutput moveCommittedWorkItemOutput = null;

        moveCommittedWorkItemInput.setToStageId(test.getId());
        moveCommittedWorkItemInput.setToMiniStageId(test.getDefaultMiniStage().getId());
        moveCommittedWorkItemInput.setToSwimLaneId(test.getDefaultSwimLaneOfMiniStage().getId());
        moveCommittedWorkItemInput.setWorkItemId(stv.getId());

        try {
            moveCommittedWorkItemUseCase.execute(moveCommittedWorkItemInput, null);
        } catch (RuntimeException e) {
            assertEquals("work item : " + moveCommittedWorkItemInput.getWorkItemId() + " block.", e.getMessage());
        }

    }
}
