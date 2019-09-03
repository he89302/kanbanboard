package com.practice.cleankanban.usecase.kanbanboard.stage;

import static org.junit.Assert.assertEquals;

import com.practice.cleankanban.adapter.presenter.RemoveStagePresenter;
import com.practice.cleankanban.adapter.presenter.kanbanboard.MultipleStagePresenter;
import com.practice.cleankanban.usecase.KanbanboardTestUtility;
import com.practice.cleankanban.usecase.kanbanboard.board.BoardRepository;
import com.practice.cleankanban.usecase.kanbanboard.stage.get.GetStagesInput;
import com.practice.cleankanban.usecase.kanbanboard.stage.get.GetStagesOutput;
import com.practice.cleankanban.usecase.kanbanboard.stage.get.GetStagesUseCase;
import com.practice.cleankanban.usecase.kanbanboard.stage.get.GetStagesUseCaseImpl;
import com.practice.cleankanban.usecase.kanbanboard.stage.remove.RemoveStageInput;
import com.practice.cleankanban.usecase.kanbanboard.stage.remove.RemoveStageOutput;
import com.practice.cleankanban.usecase.kanbanboard.stage.remove.RemoveStageUseCase;
import com.practice.cleankanban.usecase.kanbanboard.stage.remove.RemoveStageUseCaseImpl;

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
public class RemoveStageTest {


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
    public void remove_test_stage_on_board() {
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

        RemoveStageUseCase useCase = new RemoveStageUseCaseImpl(boardRepository, stageRepository);
        RemoveStageInput input = RemoveStageUseCaseImpl.createInput();
        RemoveStageOutput output = new RemoveStagePresenter();

        input.setBoardId(boardId);
        input.setStageId(stageRepository.findByStageName("test").getId());

        useCase.execute(input, output);

        getStagesUseCase.execute(getStagesInput, getStagesOutput);

        assertEquals(1, getStagesOutput.getStages().get(0).getOrdering());//ready
        assertEquals(2, getStagesOutput.getStages().get(1).getOrdering());//analysis
        assertEquals(3, getStagesOutput.getStages().get(2).getOrdering());//development
        assertEquals(4, getStagesOutput.getStages().get(3).getOrdering());//fixBug
        assertEquals(5, getStagesOutput.getStages().get(4).getOrdering());//deploy
        //assertEquals(6, getStagesOutput.getStages().get(5).getOrdering());//test
        assertEquals("ready", getStagesOutput.getStages().get(0).getName());//ready
        assertEquals("analysis", getStagesOutput.getStages().get(1).getName());//analysis
        assertEquals("development", getStagesOutput.getStages().get(2).getName());//development
        assertEquals("fixBug", getStagesOutput.getStages().get(3).getName());//fixBug
        assertEquals("deploy", getStagesOutput.getStages().get(4).getName());//deploy
        //assertEquals("test", getStagesOutput.getStages().get(5).getName());//test
    }
}