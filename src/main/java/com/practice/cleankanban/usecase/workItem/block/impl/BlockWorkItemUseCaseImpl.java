package com.practice.cleankanban.usecase.workItem.block.impl;

import com.practice.cleankanban.domain.model.workItem.Blocker;
import com.practice.cleankanban.usecase.workItem.block.BlockWorkItemInput;
import com.practice.cleankanban.usecase.workItem.block.BlockWorkItemOutput;
import com.practice.cleankanban.usecase.workItem.block.BlockWorkItemUseCase;

public class BlockWorkItemUseCaseImpl implements BlockWorkItemUseCase {

    private BlockerRepository repository;

    public BlockWorkItemUseCaseImpl(BlockerRepository repository) {
        this.repository = repository;
    }

    public static BlockWorkItemInput createInput() {
        return new BlockWorkItemInputImpl();
    }

    @Override
    public void execute(BlockWorkItemInput input, BlockWorkItemOutput output) {
        Blocker blocker;
        if (repository.findAllBlocker().size() == 0) {
            blocker = new Blocker("Block", input.getWorkItemId());
        } else {
            blocker = repository.findAllBlocker().iterator().next();
        }

        blocker.setNote(input.getBlockNote());
        blocker.blockWorkItem(input.getWorkItemId());
        output.setNote(input.getBlockNote());

        repository.save(blocker);
    }

    private final static class BlockWorkItemInputImpl implements BlockWorkItemInput {

        private String id;
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
        public void setWorkItemId(String id) {
            this.id = id;
        }

        @Override
        public String getWorkItemId() {
            return id;
        }
    }
}
