package com.practice.cleankanban.usecase.kanbanboard.stage;

import com.practice.cleankanban.adapter.gateway.domainEvent.InMemoryDomainEventRepository;
import com.practice.cleankanban.domain.model.PersistentDomainEvent;
import com.practice.cleankanban.domain.model.kanbanboard.stage.Stage;
import com.practice.cleankanban.usecase.Utility;
import com.practice.cleankanban.usecase.domainevent.DomainEventRepository;
import com.practice.cleankanban.usecase.domainevent.sourcing.RegisterEventSourcingSubscriberUseCase;
import com.practice.cleankanban.usecase.domainevent.sourcing.impl.RegisterEventSourcingSubscriberUseCaseImpl;
import com.practice.cleankanban.usecase.kanbanboard.stage.update.*;
import com.practice.cleankanban.usecase.kanbanboard.stage.update.impl.UpdateStageUseCaseImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class UpdateStageTest {

    private DomainEventRepository<PersistentDomainEvent> domainEventRepository;
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setUp() {
        domainEventRepository = new InMemoryDomainEventRepository();
        RegisterEventSourcingSubscriberUseCase useCase = new RegisterEventSourcingSubscriberUseCaseImpl(domainEventRepository);
        useCase.execute(null, null);
    }


    @Test
    public void update_stage_name_then_get_the_stage_change_name() {
        Utility utility = new Utility();
        StageRepository stageRepository = utility.invoke();
        Stage todo = stageRepository.findByStageName("ToDo");

        UpdateStageUseCase useCase = new UpdateStageUseCaseImpl(stageRepository);
        UpdateStageInput input = UpdateStageUseCaseImpl.createInput();
        UpdateStageOutput output = new SingleUpdateStagePresenter();

        input.setStageId(todo.getId());
        input.setName("editName");

        useCase.execute(input, output);

        assertNotNull(output.getStageId());
        assertEquals("editName", stageRepository.findById(output.getStageId()).getName());
        assertThat(domainEventRepository.findAll().get(9).getDetail()).startsWith("StageUpdated");
    }

    @Test(expected = IllegalArgumentException.class)
    public void update_stage_null_name_then_get_IllegalArgumentException() {
        Utility utility = new Utility();
        StageRepository stageRepository = utility.invoke();
        Stage todo = stageRepository.findByStageName("ToDo");

        UpdateStageUseCase useCase = new UpdateStageUseCaseImpl(stageRepository);
        UpdateStageInput input = UpdateStageUseCaseImpl.createInput();
        UpdateStageOutput output = new SingleUpdateStagePresenter();

        input.setName(null);
        input.setStageId(todo.getId());

        useCase.execute(input, output);
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("The stage name may not be set to null.");
    }
}
