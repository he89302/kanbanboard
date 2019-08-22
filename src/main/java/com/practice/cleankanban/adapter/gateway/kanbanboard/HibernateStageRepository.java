package com.practice.cleankanban.adapter.gateway.kanbanboard;

import com.practice.cleankanban.domain.model.kanbanboard.stage.Stage;
import com.practice.cleankanban.usecase.kanbanboard.stage.StageRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.hibernate.validator.internal.engine.groups.Group;

import java.util.List;

public class HibernateStageRepository extends com.saasovation.common.port.adapter.persistence.hibernate.AbstractHibernateSession implements StageRepository {
    @Override
    public List<Stage> findAll() {


        return null;
    }

    @Override
    public void save(Stage stage) {
        try {
            this.session().saveOrUpdate(stage);
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
}
