package com.practice.cleankanban.usecase.kanbanboard.stage;

import com.practice.cleankanban.adapter.gateway.domainEvent.InMemoryDomainEventRepository;
import com.practice.cleankanban.adapter.gateway.kanbanboard.InMemoryBoardRepository;
import com.practice.cleankanban.adapter.gateway.kanbanboard.InMemoryStageRepository;
import com.practice.cleankanban.adapter.presenter.kanbanboard.MultipleStagePresenter;
import com.practice.cleankanban.domain.model.PersistentDomainEvent;
import com.practice.cleankanban.usecase.KanbanboardTestUtility;
import com.practice.cleankanban.usecase.domainevent.DomainEventRepository;
import com.practice.cleankanban.usecase.domainevent.sourcing.RegisterEventSourcingSubscriberUseCase;
import com.practice.cleankanban.usecase.domainevent.sourcing.impl.RegisterEventSourcingSubscriberUseCaseImpl;
import com.practice.cleankanban.usecase.kanbanboard.board.BoardRepository;
import com.practice.cleankanban.usecase.kanbanboard.stage.get.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class GetStagesTest {

    private DomainEventRepository<PersistentDomainEvent> domainEventRepository;
    public ExpectedException exceptionRule = ExpectedException.none();
    private String boardId;
    private BoardRepository boardRepository;
    private StageRepository stageRepository;

    @Before
    public void setUp() {
        boardRepository = new InMemoryBoardRepository();
        stageRepository = new InMemoryStageRepository();
        domainEventRepository = new InMemoryDomainEventRepository();
        RegisterEventSourcingSubscriberUseCase useCase = new RegisterEventSourcingSubscriberUseCaseImpl(domainEventRepository);
        useCase.execute(null, null);
    }

    @Test
    public void test_get_stage_by_board_id() {
        KanbanboardTestUtility utility = new KanbanboardTestUtility();
        utility.createKanbanBoardAndStage();
        stageRepository = utility.getStageRepository();
        boardRepository = utility.getBoardRepository();
        boardId = boardRepository.findAll().get(0).getId();

        GetStagesUseCase useCase = new GetStagesUseCaseImpl(boardRepository, stageRepository);
        GetStagesInput input = GetStagesUseCaseImpl.createInput();
        GetStagesOutput output = new MultipleStagePresenter();

        input.setBoardId(boardId);

        useCase.execute(input, output);

        assertEquals(6, output.getStages().size());
        assertEquals(stageRepository.findAll().get(0).getName(), output.getStages().get(0).getName());

        assertEquals(stageRepository.findAll().get(0).getDefaultMiniStage().getId(),
                            output.getStages().get(0).getDefaultMiniStage().getId());

        assertEquals(stageRepository.findAll().get(0).getDefaultSwimLaneOfMiniStage().getId(),
                            output.getStages().get(0).getDefaultSwimLaneOfMiniStage().getSwimLaneId());


        assertEquals(stageRepository.findAll().get(1).getName(), output.getStages().get(1).getName());
        assertEquals(stageRepository.findAll().get(2).getName(), output.getStages().get(2).getName());
        assertEquals(stageRepository.findAll().get(3).getName(), output.getStages().get(3).getName());
        assertEquals(stageRepository.findAll().get(4).getName(), output.getStages().get(4).getName());
        assertEquals(stageRepository.findAll().get(5).getName(), output.getStages().get(5).getName());
    }
}
