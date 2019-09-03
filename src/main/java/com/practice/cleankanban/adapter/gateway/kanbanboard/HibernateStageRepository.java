package com.practice.cleankanban.adapter.gateway.kanbanboard;

import com.practice.cleankanban.domain.model.kanbanboard.stage.Stage;
import com.practice.cleankanban.usecase.kanbanboard.stage.StageRepository;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

public class HibernateStageRepository implements StageRepository {
    @Override
    public List<Stage> findAll() {


        return null;
    }

    @Override
    public void save(Stage stage) {
        try {
        } catch (ConstraintViolationException e) {
            throw new IllegalStateException("Group is not unique.", e);
        }
    }

    @Override
    public Stage findById(String stageId) {
        return null;
    }

    @Override
    public Stage findByStageName(String name) {
        return null;
    }

    @Override
    public List<Stage> findByBoardId(String boardId) {
        return null;
    }

    @Override
    public void remove(Stage stage) {

    }
}
