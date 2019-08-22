package com.practice.cleankanban.usecase.kanbanboard.stage;

import com.practice.cleankanban.adapter.gateway.domainEvent.InMemoryDomainEventRepository;
import com.practice.cleankanban.adapter.gateway.kanbanboard.HibernateStageRepository;
import com.practice.cleankanban.adapter.gateway.kanbanboard.InMemoryStageRepository;
import com.practice.cleankanban.adapter.presenter.kanbanboard.SingleStagePresenter;
import com.practice.cleankanban.domain.model.PersistentDomainEvent;
import com.practice.cleankanban.domain.model.kanbanboard.stage.Stage;
import com.practice.cleankanban.usecase.Utility;
import com.practice.cleankanban.usecase.domainevent.DomainEventRepository;
import com.practice.cleankanban.usecase.domainevent.sourcing.RegisterEventSourcingSubscriberUseCase;
import com.practice.cleankanban.usecase.domainevent.sourcing.impl.RegisterEventSourcingSubscriberUseCaseImpl;
import com.practice.cleankanban.usecase.kanbanboard.miniStage.add.AddMiniStageUseCase;
import com.practice.cleankanban.usecase.kanbanboard.miniStage.add.impl.AddMiniStageUseCaseImpl;
import com.practice.cleankanban.usecase.kanbanboard.stage.add.AddStageInput;
import com.practice.cleankanban.usecase.kanbanboard.stage.add.AddStageOutput;
import com.practice.cleankanban.usecase.kanbanboard.stage.add.AddStageUseCase;
import com.practice.cleankanban.usecase.kanbanboard.stage.add.impl.AddStageUseCaseImpl;
import com.practice.cleankanban.usecase.kanbanboard.stage.update.SingleUpdateStagePresenter;
import com.practice.cleankanban.usecase.kanbanboard.stage.update.UpdateStageInput;
import com.practice.cleankanban.usecase.kanbanboard.stage.update.UpdateStageOutput;
import com.practice.cleankanban.usecase.kanbanboard.stage.update.UpdateStageUseCase;
import com.practice.cleankanban.usecase.kanbanboard.stage.update.impl.UpdateStageUseCaseImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;


public class AddStageTest {

    @Test
    public void  add_Deploy_stage_to_a_board() {
        Utility utility = new Utility();
        StageRepository stageRepository = utility.invoke();

//        StageRepository stageRepository = new HibernateStageRepository();

        AddStageUseCase useCase = new AddStageUseCaseImpl(stageRepository);

        AddStageInput input = AddStageUseCaseImpl.createInput();
        AddStageOutput output = new SingleStagePresenter();

        input.setBoardId("223-12dsf-63344-ddf");
        input.setStageName("Deploy");

        useCase.execute(input, output);

        assertNotNull(output.getStageId());
        assertEquals("Deploy", stageRepository.findById(output.getStageId()).getName());
        assertEquals(4, stageRepository.findAll().size());
        assertEquals("ToDo", stageRepository.findAll().get(0).getName());
        assertEquals("Doing", stageRepository.findAll().get(1).getName());
        assertEquals("Deploy", stageRepository.findAll().get(3).getName());
    }

    @Test
    public void test_stage_of_board_id_Immutable() throws Exception{
        StageRepository stageRepository = new InMemoryStageRepository();

        AddStageUseCase useCase = new AddStageUseCaseImpl(stageRepository);

        AddStageInput input = AddStageUseCaseImpl.createInput();
        AddStageOutput output = new SingleStagePresenter();

        input.setBoardId("223-12dsf-63344-ddf");
        input.setStageName("Deploy");

        useCase.execute(input, output);

        List<Stage> stages = stageRepository.findByBoardId("223-12dsf-63344-ddf");

        Stage doing = stages.get(0);

        try {
            doing.setBoardId("test");
        } catch (IllegalStateException e) {
           assertEquals("The board id may not be changed.", e.getMessage());
        } catch (IllegalArgumentException e) {
            assertEquals("The board id may not be set to null.", e.getMessage());
        }


    }

}
