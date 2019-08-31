package com.practice.cleankanban.usecase;

import com.practice.cleankanban.adapter.gateway.kanbanboard.InMemoryBoardRepository;
import com.practice.cleankanban.adapter.gateway.kanbanboard.InMemoryStageRepository;
import com.practice.cleankanban.adapter.gateway.workItem.InMemoryWorkItemRepository;
import com.practice.cleankanban.adapter.presenter.kanbanboard.SingleBoardPresenter;
import com.practice.cleankanban.adapter.presenter.kanbanboard.SingleStageOfBoardPresenter;
import com.practice.cleankanban.domain.model.kanbanboard.board.Board;
import com.practice.cleankanban.domain.model.kanbanboard.stage.Stage;
import com.practice.cleankanban.usecase.kanbanboard.board.BoardRepository;
import com.practice.cleankanban.usecase.kanbanboard.board.CreateStageOfBoardInput;
import com.practice.cleankanban.usecase.kanbanboard.board.CreateStageOfBoardOutput;
import com.practice.cleankanban.usecase.kanbanboard.board.CreateStageOfBoardUseCase;
import com.practice.cleankanban.usecase.kanbanboard.board.add.AddBoardInput;
import com.practice.cleankanban.usecase.kanbanboard.board.add.AddBoardOutput;
import com.practice.cleankanban.usecase.kanbanboard.board.add.AddBoardUseCase;
import com.practice.cleankanban.usecase.kanbanboard.board.add.impl.AddBoardUseCaseImpl;
import com.practice.cleankanban.usecase.kanbanboard.board.impl.CreateStageOfBoardUseCaseImpl;
import com.practice.cleankanban.usecase.kanbanboard.stage.StageRepository;
import com.practice.cleankanban.usecase.workItem.WorkItemRepository;

public class KanbanboardTestUtility {

    private StageRepository stageRepository;
    private BoardRepository boardRepository;
    private WorkItemRepository workItemRepository;

    private Board kanbanboard;

    private Stage ready;
    private Stage analysis;
    private Stage development;
    private Stage test;
    private Stage fixBug;
    private Stage deploy;

    public KanbanboardTestUtility() {
        this(new InMemoryStageRepository(), new InMemoryBoardRepository(), new InMemoryWorkItemRepository());
    }

    public KanbanboardTestUtility(InMemoryStageRepository stageRepository, InMemoryBoardRepository boardRepository,
                                  InMemoryWorkItemRepository workItemRepository) {
        this.boardRepository =  boardRepository;
        this.stageRepository = stageRepository;
        this.workItemRepository = workItemRepository;
    }

    public StageRepository getStageRepository() {
        return stageRepository;
    }

    public BoardRepository getBoardRepository() {
        return boardRepository;
    }

    public WorkItemRepository getWorkItemRepository() {
        return workItemRepository;
    }

    public Board getKanbanboard() {
        return kanbanboard;
    }

    public void createKanbanBoardAndStage() {
        kanbanboard = createBoard("kanbanboard");

        CreateStageOfBoardUseCase useCase = new CreateStageOfBoardUseCaseImpl(this.getBoardRepository(),
                                                                                this.getStageRepository());
        CreateStageOfBoardInput input = CreateStageOfBoardUseCaseImpl.createInput();
        CreateStageOfBoardOutput output = new SingleStageOfBoardPresenter();

        input.setBoardId(kanbanboard.getId());

        input.setStageName("ready");
        useCase.execute(input, output);
        ready = stageRepository.findById(output.getStageId());

        input.setStageName("analysis");
        useCase.execute(input, output);
        analysis = stageRepository.findById(output.getStageId());

        input.setStageName("development");
        useCase.execute(input, output);
        development = stageRepository.findById(output.getStageId());

        input.setStageName("test");
        useCase.execute(input, output);
        test = stageRepository.findById(output.getStageId());

        input.setStageName("fixBug");
        useCase.execute(input, output);
        fixBug = stageRepository.findById(output.getStageId());

        input.setStageName("deploy");
        useCase.execute(input,output);
        deploy = stageRepository.findById(output.getStageId());
    }

    private Board createBoard(String aName) {
        AddBoardUseCase useCase = new AddBoardUseCaseImpl(this.getBoardRepository());
        AddBoardInput input = AddBoardUseCaseImpl.createInput();
        AddBoardOutput output = new SingleBoardPresenter();

        input.setBoardName(aName);

        useCase.execute(input, output);

        return boardRepository.findById(output.getBoardId());
    }

    public String getBoardId() {
        return kanbanboard.getId();
    }
}
