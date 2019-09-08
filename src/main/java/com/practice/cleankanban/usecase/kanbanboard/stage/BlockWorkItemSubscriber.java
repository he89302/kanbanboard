package com.practice.cleankanban.usecase.kanbanboard.stage;

import com.practice.cleankanban.domain.model.BlockEvent;
import com.practice.cleankanban.domain.model.DomainEventSubscriber;
import com.practice.cleankanban.domain.model.kanbanboard.stage.CommittedWorkItem;
import com.practice.cleankanban.domain.model.kanbanboard.stage.MiniStage;
import com.practice.cleankanban.domain.model.kanbanboard.stage.Stage;
import com.practice.cleankanban.domain.model.kanbanboard.stage.SwimLane;
import com.practice.cleankanban.domain.model.workItem.WorkItem;
import com.practice.cleankanban.usecase.domainevent.DomainEventRepository;
import com.practice.cleankanban.usecase.workItem.WorkItemRepository;

import java.util.Objects;

public class BlockWorkItemSubscriber implements DomainEventSubscriber<BlockEvent> {

    private String result;
    private StageRepository stageRepository;
    private DomainEventRepository<BlockEvent> repository;

    public BlockWorkItemSubscriber(StageRepository stageRepository, DomainEventRepository<BlockEvent> repository) {
        this.stageRepository = stageRepository;
        this.repository = repository;
    }

    @Override
    public void handleEvent(BlockEvent domainEvent) {
        for (Stage each:stageRepository.findAll()
             ) {
            for (MiniStage miniStage:each.getMiniStages()
                 ) {
                for (SwimLane swimLane:miniStage.getSwimLanes()
                     ) {
                    for (CommittedWorkItem committedWorkItem:swimLane.getCommittedWorkItems()
                         ) {
                        if (committedWorkItem.getWorkItemId().equals(domainEvent.getWorkItemId())) {
                           committedWorkItem.setBlock(true);
                           stageRepository.save(each);
                        }
                    }
                }
            }
        }

        result = domainEvent.detail();
        repository.save(domainEvent);
    }

    @Override
    public Class subscribedToEventType() {
        return BlockEvent.class;
    }
}
