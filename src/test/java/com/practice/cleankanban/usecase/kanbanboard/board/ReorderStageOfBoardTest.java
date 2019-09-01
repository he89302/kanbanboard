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
        GetStagesUseCase getStagesUseCase = new GetStagesUseCaseImpl(boardRepository, stageRepository);
        GetStagesInput getStagesInput = GetStagesUseCaseImpl.createInput();
        GetStagesOutput getStagesOutput = new MultipleStagePresenter();

        getStagesInput.setBoardId(boardId);

        getStagesUseCase.execute(getStagesInput, getStagesOutput);

        assertEquals(1, getStagesOutput.getStages().get(0).getOrdering());//ready
        assertEquals(2, getStagesOutput.getStages().get(1).getOrdering());//analysis
        assertEquals(3, getStagesOutput.getStages().get(2).getOrdering());//development
        assertEquals(4, getStagesOutput.getStages().get(3).getOrdering());//test
        assertEquals(5, getStagesOutput.getStages().get(4).getOrdering());//fixBug
        assertEquals(6, getStagesOutput.getStages().get(5).getOrdering());//deploy

        MoveStageOfBoardUseCase useCase = new MoveStageOfBoardUseCaseImpl(boardRepository, stageRepository);
        MoveStageOfBoardInput input = MoveStageOfBoardUseCaseImpl.createInput();
        MoveStageOfBoardOutput output = new MoveStageOfBoardMessagePresenter();

        input.setBoardId(boardId);
        input.setStageId(stageRepository.findByStageName("analysis").getId());
        input.setOldPosition(2);
        input.setNewPosition(5);

        useCase.execute(input, output);

        getStagesUseCase.execute(getStagesInput, getStagesOutput);
        
        assertEquals(1, getStagesOutput.getStages().get(0).getOrdering());//ready
        assertEquals(2, getStagesOutput.getStages().get(2).getOrdering());//development
        assertEquals(3, getStagesOutput.getStages().get(3).getOrdering());//test
        assertEquals(4, getStagesOutput.getStages().get(4).getOrdering());//fixBug
        assertEquals(5, getStagesOutput.getStages().get(1).getOrdering());//analysis
        assertEquals(6, getStagesOutput.getStages().get(5).getOrdering());//deploy
    }

    @Test
    public void move_test_at_position_4_to_ready_at_position_1() {
        MoveStageOfBoardUseCase useCase = new MoveStageOfBoardUseCaseImpl(boardRepository, stageRepository);
        MoveStageOfBoardInput input = MoveStageOfBoardUseCaseImpl.createInput();
        MoveStageOfBoardOutput output = new MoveStageOfBoardMessagePresenter();

        input.setBoardId(boardId);
        input.setStageId(stageRepository.findByStageName("test").getId());
        input.setOldPosition(4);
        input.setNewPosition(1);

        useCase.execute(input, output);

        GetStagesUseCase getStagesUseCase = new GetStagesUseCaseImpl(boardRepository, stageRepository);
        GetStagesInput getStagesInput = GetStagesUseCaseImpl.createInput();
        GetStagesOutput getStagesOutput = new MultipleStagePresenter();

        getStagesInput.setBoardId(boardId);

        getStagesUseCase.execute(getStagesInput, getStagesOutput);
        
        assertEquals("move done", output.getMessage());
        assertEquals(1, getStagesOutput.getStages().get(3).getOrdering());//test
        assertEquals(2, getStagesOutput.getStages().get(0).getOrdering());//ready
        assertEquals(3, getStagesOutput.getStages().get(1).getOrdering());//analysis
        assertEquals(4, getStagesOutput.getStages().get(2).getOrdering());//development
        assertEquals(5, getStagesOutput.getStages().get(4).getOrdering());//fixBug
        assertEquals(6, getStagesOutput.getStages().get(5).getOrdering());//deploy
    }

    @Test
    public void move_test_at_position_4_to_same_position() {
        MoveStageOfBoardUseCase useCase = new MoveStageOfBoardUseCaseImpl(boardRepository, stageRepository);
        MoveStageOfBoardInput input = MoveStageOfBoardUseCaseImpl.createInput();
        MoveStageOfBoardOutput output = new MoveStageOfBoardMessagePresenter();

        input.setBoardId(boardId);
        input.setStageId(stageRepository.findByStageName("test").getId());
        input.setOldPosition(4);
        input.setNewPosition(4);

        useCase.execute(input, output);

        GetStagesUseCase getStagesUseCase = new GetStagesUseCaseImpl(boardRepository, stageRepository);
        GetStagesInput getStagesInput = GetStagesUseCaseImpl.createInput();
        GetStagesOutput getStagesOutput = new MultipleStagePresenter();

        getStagesInput.setBoardId(boardId);

        getStagesUseCase.execute(getStagesInput, getStagesOutput);

        assertEquals(1, getStagesOutput.getStages().get(0).getOrdering());//ready
        assertEquals(2, getStagesOutput.getStages().get(1).getOrdering());//analysis
        assertEquals(3, getStagesOutput.getStages().get(2).getOrdering());//development
        assertEquals(4, getStagesOutput.getStages().get(3).getOrdering());//test
        assertEquals(5, getStagesOutput.getStages().get(4).getOrdering());//fixBug
        assertEquals(6, getStagesOutput.getStages().get(5).getOrdering());//deploy
    }
}
