package com.practice.cleankanban.adapter.gateway.kanbanboard;

import com.practice.cleankanban.domain.model.kanbanboard.stage.Stage;
import com.practice.cleankanban.usecase.kanbanboard.stage.StageRepository;

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
}
