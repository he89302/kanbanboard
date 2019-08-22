package com.practice.cleankanban.usecase.kanbanboard.board.impl;

import com.practice.cleankanban.domain.model.kanbanboard.board.Board;
import com.practice.cleankanban.domain.model.kanbanboard.stage.Stage;
import com.practice.cleankanban.usecase.kanbanboard.board.BoardRepository;
import com.practice.cleankanban.usecase.kanbanboard.board.CreateStageOfBoardInput;
import com.practice.cleankanban.usecase.kanbanboard.board.CreateStageOfBoardOutput;
import com.practice.cleankanban.usecase.kanbanboard.board.CreateStageOfBoardUseCase;
import com.practice.cleankanban.usecase.kanbanboard.stage.StageRepository;

public class CreateStageOfBoardUseCaseImpl implements CreateStageOfBoardUseCase {

    private BoardRepository boardRepository;
    private StageRepository stageRepository;

    public CreateStageOfBoardUseCaseImpl(BoardRepository boardRepository, StageRepository stageRepository) {
        this.boardRepository = boardRepository;
        this.stageRepository = stageRepository;
    }

    public static CreateStageOfBoardInput createInput() {
        return new CreateStageOfBoardInputImpl();
    }

    @Override
    public void execute(CreateStageOfBoardInput input, CreateStageOfBoardOutput output) {
        Board board = boardRepository.findById(input.getBoardId());
        Stage stage = board.createStage(input.getStageName());

        stageRepository.save(stage);

        board.addStage(stage);
        boardRepository.save(board);

        output.setStageId(stage.getId());
    }

    private static class CreateStageOfBoardInputImpl implements CreateStageOfBoardInput {

        private String boardId = "";
        private String stageName = "";

        @Override
        public void setBoardId(String id) {
            this.boardId = id;
        }

        @Override
        public String getBoardId() {
            return boardId;
        }

        @Override
        public void setStageName(String name) {
            this.stageName = name;
        }

        @Override
        public String getStageName() {
            return stageName;
        }
    }
}
