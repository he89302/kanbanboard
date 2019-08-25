package com.practice.cleankanban.usecase.kanbanboard.stage.get;

import com.practice.cleankanban.domain.model.kanbanboard.stage.Stage;
import com.practice.cleankanban.usecase.kanbanboard.stage.StageRepository;

import java.util.List;

public class GetStagesUseCaseImpl implements GetStagesUseCase {

    private StageRepository repository;

    public GetStagesUseCaseImpl(StageRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(GetStagesInput input, GetStagesOutput output) {

        List<Stage> stages = repository.findByBoardId(input.getBoardId());
        output.setStages(stages);
    }

    public static GetStagesInput createInput() {
        return new GetStagesInputImpl();
    }

    private static class GetStagesInputImpl implements GetStagesInput {

        private String boardId;
        private boolean getAll;

        @Override
        public void setBoardId(String boardId) {
            this.boardId = boardId;
        }

        @Override
        public String getBoardId() {
            return boardId;
        }

        @Override
        public void setGetAllStage(boolean arg) {
            this.getAll = arg;
        }

        @Override
        public boolean isGetAllStage() {
            return getAll;
        }
    }
}
