package com.practice.cleankanban.adapter.gateway.kanbanboard;

import com.practice.cleankanban.domain.model.kanbanboard.board.BoardStage;
import com.practice.cleankanban.domain.model.kanbanboard.stage.Stage;
import com.practice.cleankanban.usecase.kanbanboard.stage.StageRepository;
import org.w3c.dom.ls.LSInput;

import java.util.ArrayList;
import java.util.List;

public class InMemoryStageRepository implements StageRepository {
    private List<Stage> stageList = new ArrayList<>();

    @Override
    public List<Stage> findAll() {
        return stageList;
    }

    @Override
    public void save(Stage stage) {
        stageList.add(stage);
    }

    @Override
    public Stage findById(String stageId) throws RuntimeException {
        for (Stage each:stageList) {
            if (each.getId().equals(stageId)) {
                return  each;
            }
        }
        throw new RuntimeException("Can't find the stage id :" + stageId);
    }

    @Override
    public Stage findByStageName(String name) {
        for (Stage each:stageList) {
            if (each.getName().equals(name)) {
                return  each;
            }
        }
        throw new RuntimeException("Can't find the stage name :" + name);
    }

    @Override
    public List<Stage> findByBoardId(String boardId) {
        List<Stage> stages = new ArrayList<>();

        for (Stage each:stageList
             ) {
            if (each.getBoardId().equals(boardId)) {
                stages.add(each);
            }
        }

        if (!stages.isEmpty()) {
            return stages;
        } else {
            throw new RuntimeException("Can't find any stage by board id : " + boardId);
        }
    }

    @Override
    public void remove(Stage stage) {
        stageList.remove(stage);
    }
}
