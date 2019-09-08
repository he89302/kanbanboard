package com.practice.cleankanban.usecase.workItem.block.impl;

import com.practice.cleankanban.domain.model.BlockEvent;
import com.practice.cleankanban.domain.model.DomainEventPublisher;
import com.practice.cleankanban.domain.model.workItem.BlockWorkItem;
import com.practice.cleankanban.domain.model.workItem.WorkItem;
import com.practice.cleankanban.usecase.domainevent.DomainEventRepository;
import com.practice.cleankanban.usecase.kanbanboard.stage.BlockWorkItemSubscriber;
import com.practice.cleankanban.usecase.kanbanboard.stage.StageRepository;
import com.practice.cleankanban.usecase.workItem.WorkItemRepository;
import com.practice.cleankanban.usecase.workItem.block.BlockWorkItemInput;
import com.practice.cleankanban.usecase.workItem.block.BlockWorkItemOutput;
import com.practice.cleankanban.usecase.workItem.block.BlockWorkItemUseCase;

import java.util.Collections;
import java.util.Objects;

public class BlockWorkItemUseCaseImpl implements BlockWorkItemUseCase {

    private StageRepository repository;
    private DomainEventRepository<BlockEvent> blockEventDomainEventRepository;

    public BlockWorkItemUseCaseImpl(StageRepository stageRepository,
                                    DomainEventRepository<BlockEvent> blockEventDomainEventRepository) {
        this.repository = stageRepository;
        this.blockEventDomainEventRepository = blockEventDomainEventRepository;
    }

    public static BlockWorkItemInput createInput() {
        return new BlockWorkItemInputImpl();
    }

    @Override
    public void execute(BlockWorkItemInput input, BlockWorkItemOutput output) {
        DomainEventPublisher.instance().subscribe(new BlockWorkItemSubscriber(repository, blockEventDomainEventRepository));
        BlockWorkItem blockWorkItem = new BlockWorkItem("Block");
        blockWorkItem.setNote(input.getBlockNote());
        blockWorkItem.blockWorkItem(input.getWorkItem());
        output.setNote(input.getBlockNote());
    }

    private final static class BlockWorkItemInputImpl implements BlockWorkItemInput {

        private WorkItem workItem;
        private String note;

        @Override
        public void setBlockNote(String note) {
            this.note = note;
        }

        @Override
        public String getBlockNote() {
            return note;
        }

        @Override
        public void setWorkItem(WorkItem workItem) {
            this.workItem = workItem;
        }

        @Override
        public WorkItem getWorkItem() {
            return workItem;
        }
    }
}
