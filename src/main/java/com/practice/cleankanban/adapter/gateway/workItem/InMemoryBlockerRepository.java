package com.practice.cleankanban.adapter.gateway.workItem;

import com.practice.cleankanban.domain.model.workItem.Blocker;
import com.practice.cleankanban.usecase.workItem.block.impl.BlockerRepository;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class InMemoryBlockerRepository implements BlockerRepository {

    Map<String, Blocker> blockerMap;

    public InMemoryBlockerRepository() {
        this.blockerMap = new LinkedHashMap<>();
    }

    @Override
    public void save(Blocker blocker) {
        blockerMap.put(blocker.getId(), blocker);
    }

    @Override
    public void remove(Blocker blocker) {
        blockerMap.remove(blocker.getId());
    }

    @Override
    public Collection<Blocker> findAllBlocker() {
        return blockerMap.values();
    }
}
