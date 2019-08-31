package com.practice.cleankanban.usecase.kanbanboard.board;

import static org.junit.Assert.assertEquals;

import com.practice.cleankanban.adapter.gateway.kanbanboard.InMemoryBoardRepository;
import com.practice.cleankanban.adapter.gateway.kanbanboard.InMemoryStageRepository;
import com.practice.cleankanban.adapter.presenter.kanbanboard.MoveStageOfBoardMessagePresenter;
import com.practice.cleankanban.adapter.presenter.kanbanboard.MultipleStagePresenter;
import com.practice.cleankanban.domain.model.kanbanboard.stage.AbstractDomainEventTest;
import com.practice.cleankanban.usecase.KanbanboardTestUtility;
import com.practice.cleankanban.usecase.kanbanboard.board.move_stage.MoveStageOfBoardInput;
import com.practice.cleankanban.usecase.kanbanboard.board.move_stage.MoveStageOfBoardOutput;
import com.practice.cleankanban.usecase.kanbanboard.board.move_stage.MoveStageOfBoardUseCase;
import com.practice.cleankanban.usecase.kanbanboard.board.move_stage.MoveStageOfBoardUseCaseImpl;
import com.practice.cleankanban.usecase.kanbanboard.stage.StageRepository;
import com.practice.cleankanban.usecase.kanbanboard.stage.get.GetStagesInput;
import com.practice.cleankanban.usecase.kanbanboard.stage.get.GetStagesOutput;
import com.practice.cleankanban.usecase.kanbanboard.stage.get.GetStagesUseCase;
import com.practice.cleankanban.usecase.kanbanboard.stage.get.GetStagesUseCaseImpl;

import org.junit.Before;
import org.junit.Test;


/*
    ready;
    analysis;
    development;
    test;
    fixBug;
    deploy;
 */
public class ReorderStageOfBoardTest extends AbstractDomainEventTest {

    private KanbanboardTestUtility utility;
    private BoardRepository boardRepository;
    private StageRepository stageRepository;
    private String boardId;

    @Before
    public void setUp() {
        utility = new KanbanboardTestUtility();
        utility.createKanbanBoardAndStage();
        boardRepository = utility.getBoardRepository();
        stageRepository = utility.getStageRepository();
        boardId = utility.getBoardId();
    }

    @Test
    public void move_analysis_at_position_2_to_fixBug_at_position_5() {
        MoveStageOfBoardUseCase useCase = new MoveStageOfBoardUseCaseImpl(boardRepository, stageRepository);
        MoveStageOfBoardInput input = MoveStageOfBoardUseCaseImpl.createInput();
        MoveStageOfBoardOutput output = new MoveStageOfBoardMessagePresenter();

        input.setBoardId(boardId);
        input.setStageId(stageRepository.findByStageName("analysis").getId());
        input.setOldPosition(2);
        input.setNewPosition(5);

        useCase.execute(input, output);

        GetStagesUseCase getStagesUseCase = new GetStagesUseCaseImpl(boardRepository, stageRepository);
        GetStagesInput getStagesInput = GetStagesUseCaseImpl.createInput();
        GetStagesOutput getStagesOutput = new MultipleStagePresenter();

        getStagesInput.setBoardId(boardId);

        getStagesUseCase.execute(getStagesInput, getStagesOutput);
        assertEquals(stageRepository.findAll().get(0).getName(), getStagesOutput.getStages().get(0).getName());

        assertEquals(stageRepository.findAll().get(0).getDefaultMiniStage().getId(),
        getStagesOutput.getStages().get(0).getDefaultMiniStage().getId());

        assertEquals(stageRepository.findAll().get(0).getDefaultSwimLaneOfMiniStage().getId(),
        getStagesOutput.getStages().get(0).getDefaultSwimLaneOfMiniStage().getSwimLaneId());


        assertEquals(stageRepository.findAll().get(1).getName(), "fixBug");
        assertEquals(stageRepository.findAll().get(2).getName(), getStagesOutput.getStages().get(2).getName());
        assertEquals(stageRepository.findAll().get(3).getName(), getStagesOutput.getStages().get(3).getName());
        assertEquals(stageRepository.findAll().get(4).getName(), "analysis");
        assertEquals(stageRepository.findAll().get(5).getName(), getStagesOutput.getStages().get(5).getName());
    }
}
