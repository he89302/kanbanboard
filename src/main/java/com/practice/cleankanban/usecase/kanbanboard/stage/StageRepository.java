package com.practice.cleankanban.usecase.kanbanboard.stage;

import com.practice.cleankanban.domain.model.kanbanboard.stage.Stage;

import java.util.List;

public interface StageRepository {
    List<Stage> findAll();

    void save(Stage stage);

    Stage findById(String stageId);

    Stage findByStageName(String name);

    List<Stage> findByBoardId(String boardId);

	void remove(Stage stage);
}
