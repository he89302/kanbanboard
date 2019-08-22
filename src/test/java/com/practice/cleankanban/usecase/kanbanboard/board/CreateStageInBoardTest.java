package com.practice.cleankanban.usecase.kanbanboard.board;

import com.practice.cleankanban.adapter.gateway.kanbanboard.InMemoryBoardRepository;
import com.practice.cleankanban.adapter.gateway.kanbanboard.InMemoryStageRepository;
import com.practice.cleankanban.adapter.presenter.kanbanboard.SingleBoardPresenter;
import com.practice.cleankanban.adapter.presenter.kanbanboard.SingleStageOfBoardPresenter;
import com.practice.cleankanban.domain.model.kanbanboard.board.Board;
import com.practice.cleankanban.usecase.kanbanboard.board.add.AddBoardInput;
import com.practice.cleankanban.usecase.kanbanboard.board.add.AddBoardOutput;
import com.practice.cleankanban.usecase.kanbanboard.board.add.AddBoardUseCase;
import com.practice.cleankanban.usecase.kanbanboard.board.add.impl.AddBoardUseCaseImpl;
import com.practice.cleankanban.usecase.kanbanboard.board.impl.CreateStageOfBoardUseCaseImpl;
import com.practice.cleankanban.usecase.kanbanboard.stage.StageRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreateStageInBoardTest {

    BoardRepository boardRepository;
    StageRepository stageRepository;

    @Before
    public void setUp() {
        boardRepository = new InMemoryBoardRepository();
        stageRepository = new InMemoryStageRepository();
    }

    @Test
    public void create_stage_to_empty_board() {
        Board emptyBoard = createBoard("scrum_board", boardRepository);

        CreateStageOfBoardUseCase useCase = new CreateStageOfBoardUseCaseImpl(boardRepository, stageRepository);
        CreateStageOfBoardInput input = CreateStageOfBoardUseCaseImpl.createInput();
        input.setBoardId(emptyBoard.getId());
        input.setStageName("Todo");

        CreateStageOfBoardOutput output = new SingleStageOfBoardPresenter();
        useCase.execute(input, output);

        assertTrue(1 == emptyBoard.getBoardStages().size());
        assertEquals(output.getStageId(), emptyBoard.getBoardStages().iterator().next().getStageId());
    }

    private Board createBoard(String name, BoardRepository repository) {
        AddBoardUseCase addBoardUseCase = new AddBoardUseCaseImpl(repository);

        AddBoardInput input = AddBoardUseCaseImpl.createInput();
        AddBoardOutput output = new SingleBoardPresenter();
        input.setBoardName(name);

        addBoardUseCase.execute(input, output);
        return repository.findById(output.getBoardId());
    }
}
