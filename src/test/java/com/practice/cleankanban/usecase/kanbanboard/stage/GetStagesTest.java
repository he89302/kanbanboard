package com.practice.cleankanban.usecase.kanbanboard.stage;

import com.practice.cleankanban.adapter.gateway.domainEvent.InMemoryDomainEventRepository;
import com.practice.cleankanban.domain.model.PersistentDomainEvent;
import com.practice.cleankanban.domain.model.kanbanboard.stage.Stage;
import com.practice.cleankanban.usecase.Utility;
import com.practice.cleankanban.usecase.domainevent.DomainEventRepository;
import com.practice.cleankanban.usecase.domainevent.sourcing.RegisterEventSourcingSubscriberUseCase;
import com.practice.cleankanban.usecase.domainevent.sourcing.impl.RegisterEventSourcingSubscriberUseCaseImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class GetStagesTest {
    private DomainEventRepository<PersistentDomainEvent> domainEventRepository;
    public ExpectedException exceptionRule = ExpectedException.none();
    private String boardId = "223-12dsf-63344-ddf";

    @Before
    public void setUp() {
        domainEventRepository = new InMemoryDomainEventRepository();
        RegisterEventSourcingSubscriberUseCase useCase = new RegisterEventSourcingSubscriberUseCaseImpl(domainEventRepository);
        useCase.execute(null, null);
    }

    @Test
    public void test_get_stage_by_board_id() {
        Utility utility = new Utility();
        StageRepository stageRepository = utility.invoke();
        Stage todo = stageRepository.findByStageName("ToDo");

        GetStagesUseCase useCase = new GetStagesUseCaseImpl();
        GetStagesInput input = GetStagesUseCaseImpl.createInput();
        GetStagesOutput output = MultipleStagePresenter();

        input.setBoardId(boardId);

        useCase.execute(input, output);

        assertEquals(1, output.getStages().size());
    }
}
