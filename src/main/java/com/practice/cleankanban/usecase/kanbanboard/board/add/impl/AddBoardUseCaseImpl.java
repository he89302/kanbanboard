package com.practice.cleankanban.usecase.kanbanboard.board.add.impl;

import com.practice.cleankanban.domain.model.kanbanboard.board.Board;
import com.practice.cleankanban.usecase.kanbanboard.board.BoardRepository;
import com.practice.cleankanban.usecase.kanbanboard.board.add.AddBoardInput;
import com.practice.cleankanban.usecase.kanbanboard.board.add.AddBoardOutput;
import com.practice.cleankanban.usecase.kanbanboard.board.add.AddBoardUseCase;

public class AddBoardUseCaseImpl implements AddBoardUseCase {

    private BoardRepository repository;

    public AddBoardUseCaseImpl(BoardRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(AddBoardInput input, AddBoardOutput output) {
        Board board = new Board(input.getBoardName());
        repository.save(board);

        output.setBoardId(board.getId());
        output.setBoardName(board.getName());
    }

    public static AddBoardInput createInput() {
        return new AddBoardInputImpl();
    }

    private static class AddBoardInputImpl implements AddBoardInput {
        String name;
        @Override
        public void setBoardName(String name) {
            this.name = name;
        }

        @Override
        public String getBoardName() {
            return name;
        }
    }
}
