package com.practice.cleankanban.usecase.kanbanboard.board.move_stage;

import com.practice.cleankanban.domain.model.kanbanboard.board.Board;
import com.practice.cleankanban.domain.model.kanbanboard.board.BoardStage;
import com.practice.cleankanban.usecase.kanbanboard.board.BoardRepository;
import com.practice.cleankanban.usecase.kanbanboard.board.move_stage.MoveStageOfBoardInput;
import com.practice.cleankanban.usecase.kanbanboard.board.move_stage.MoveStageOfBoardOutput;
import com.practice.cleankanban.usecase.kanbanboard.board.move_stage.MoveStageOfBoardUseCase;
import com.practice.cleankanban.usecase.kanbanboard.stage.StageRepository;

public class MoveStageOfBoardUseCaseImpl implements MoveStageOfBoardUseCase {

    private StageRepository stageRepository;
    private BoardRepository boardRepository;
    private boolean isMove = false;

    public MoveStageOfBoardUseCaseImpl(BoardRepository boardRepository, StageRepository stageRepository) {
        this.boardRepository = boardRepository;
        this.stageRepository = stageRepository;
    }

    @Override
    public void execute(MoveStageOfBoardInput input, MoveStageOfBoardOutput output) {
        Board board = boardRepository.findById(input.getBoardId());
        try{
            board.reorderBoardStage(input.getStageId(), input.getOldPosition(), input.getNewPosition());
            setMoveActive(true);
            output.setMessage("move done");
        }catch(Exception e) {
            output.setMessage("move failed");
        }
        
        boardRepository.save(board);
	}

	private boolean setMoveActive(boolean arg) {
        isMove = arg;
        return isMove;
    }

    public static MoveStageOfBoardInput createInput() {
		return new MoveStageOfBoardInputImpl();
    }
    
    private static class MoveStageOfBoardInputImpl implements MoveStageOfBoardInput {

        private String boardId;
        private String stageId;
        private int oldPosition = -1;
        private int newPosition = -1;

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

        @Override
        public void setOldPosition(int position) {
            this.oldPosition = position;
        }

        @Override
        public int getOldPosition() {
            return oldPosition;
        }

        @Override
        public void setNewPosition(int position) {
            this.newPosition = position;
        }

        @Override
        public int getNewPosition() {
            return newPosition;
		}

    }
}