package com.practice.cleankanban.usecase.kanbanboard.board;

import com.practice.cleankanban.adapter.gateway.kanbanboard.InMemoryBoardRepository;
import com.practice.cleankanban.adapter.presenter.kanbanboard.SingleBoardPresenter;
import com.practice.cleankanban.usecase.kanbanboard.board.add.AddBoardInput;
import com.practice.cleankanban.usecase.kanbanboard.board.add.AddBoardOutput;
import com.practice.cleankanban.usecase.kanbanboard.board.add.AddBoardUseCase;
import com.practice.cleankanban.usecase.kanbanboard.board.add.impl.AddBoardUseCaseImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AddBoardTest {

    @Before
    public void setUp() {

    }

    @Test
    public void add_the_first_board_to_kanban() {
        BoardRepository repository = new InMemoryBoardRepository();

        AddBoardUseCase addBoardUseCase = new AddBoardUseCaseImpl(repository);

        AddBoardInput input = AddBoardUseCaseImpl.createInput();
        AddBoardOutput output = new SingleBoardPresenter();
        input.setBoardName("StandardBoard");

        addBoardUseCase.execute(input, output);

        assertNotNull(output.getBoardId());
        assertEquals("StandardBoard", output.getBoardName());
        assertEquals(1, repository.findAll().size());
        assertEquals("StandardBoard", repository.findFirstBoardByName("StandardBoard").getName());
    }
}
