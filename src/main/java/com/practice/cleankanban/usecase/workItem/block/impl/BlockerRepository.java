package com.practice.cleankanban.usecase.workItem.block.impl;

import com.practice.cleankanban.domain.model.workItem.Blocker;

import java.util.Collection;
import java.util.List;

public interface BlockerRepository {

    public void save(Blocker blocker);

    public void remove(Blocker blocker);

    public Collection<Blocker> findAllBlocker();
}
