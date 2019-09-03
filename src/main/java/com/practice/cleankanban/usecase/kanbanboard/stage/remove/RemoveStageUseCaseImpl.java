package com.practice.cleankanban.usecase.kanbanboard.stage.remove;

import com.practice.cleankanban.domain.model.kanbanboard.board.Board;
import com.practice.cleankanban.domain.model.kanbanboard.stage.Stage;
import com.practice.cleankanban.usecase.kanbanboard.board.BoardRepository;
import com.practice.cleankanban.usecase.kanbanboard.stage.StageRepository;

public class RemoveStageUseCaseImpl implements RemoveStageUseCase {

    private BoardRepository boardRepository;
    private StageRepository stageRepository;

    public RemoveStageUseCaseImpl(BoardRepository boardRepository, StageRepository stageRepository) {
        this.boardRepository = boardRepository;
        this.stageRepository = stageRepository;
	}

	@Override
    public void execute(RemoveStageInput input, RemoveStageOutput output) {
        Board board = boardRepository.findById(input.getBoardId());
        Stage stage = stageRepository.findById(input.getStageId());
        
        board.removeStageOnBoard(stage);
        
        stageRepository.remove(stage);
        boardRepository.save(board);
    }

	public static RemoveStageInput createInput() {
		return new RemoveStageInputImpl();
    }
    
    public static class RemoveStageInputImpl implements RemoveStageInput {

        private String boardId;
        private String stageId;

        @Override
        public void setBoardId(String boardId) {
            this.boardId = boardId;
        }

        @Override
        public String getBoardId() {
            return boardId;
        }

        @Override
        public void setStageId(String stageId) {
            this.stageId = stageId;
        }

        @Override
        public String getStageId() {
            return stageId;
        }

    }

}