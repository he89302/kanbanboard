package com.practice.cleankanban.adapter.presenter.kanbanboard;

import com.practice.cleankanban.adapter.gateway.kanbanboard.InMemoryBoardRepository;
import com.practice.cleankanban.usecase.kanbanboard.board.BoardRepository;
import com.practice.cleankanban.usecase.kanbanboard.board.add.AddBoardInput;
import com.practice.cleankanban.usecase.kanbanboard.board.add.AddBoardOutput;
import com.practice.cleankanban.usecase.kanbanboard.board.add.AddBoardUseCase;
import com.practice.cleankanban.usecase.kanbanboard.board.add.impl.AddBoardUseCaseImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Path;

@RestController
public class SingleBoardPresenter implements AddBoardOutput {

    private String boardId;
    private String boardName;
    private BoardRepository boardRepository;

    @RequestMapping(value = "board", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity doPostBoard(@RequestBody BoardModel boardModel) {
        AddBoardUseCase addBoardUseCase = new AddBoardUseCaseImpl(boardRepository);

        AddBoardInput input = AddBoardUseCaseImpl.createInput();
        AddBoardOutput output = new SingleBoardPresenter();
        input.setBoardName(boardModel.getBoardName());

        addBoardUseCase.execute(input, output);

        BoardModel model = new BoardModel();
        model.setBoardName(output.getBoardName());
        model.setBoardId(output.getBoardId());

        return ResponseEntity.status(HttpStatus.OK).body(model);
    }

    @Override
    public void setBoardName(String name) {
        this.boardName = name;
    }

    @Override
    public String getBoardName() {
        return boardName;
    }

    @Override
    public void setBoardId(String id) {
        this.boardId = id;
    }

    @Override
    public String getBoardId() {
        return boardId;
    }
}
