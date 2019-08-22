package com.practice.cleankanban.domain.model.kanbanboard.board;

import com.practice.cleankanban.domain.Entity;
import com.practice.cleankanban.domain.model.kanbanboard.stage.Stage;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Board extends Entity {

    Set<BoardStage> boardStages;

    public Board(String name) {
        super(name);

        boardStages = new HashSet<BoardStage>();
    }

    public Stage createStage(String stageName) {
        Stage stage = new Stage(stageName, this.getId());
        return stage;
    }

    public Set<BoardStage> getBoardStages() {
        return Collections.unmodifiableSet(boardStages);
    }

    public void addStage(Stage stage) {
        BoardStage boardStage = new BoardStage(this.getId(), stage.getId());
        boardStage.setOrdering(boardStages.size() + 1);
        boardStages.add(boardStage);
    }
}
