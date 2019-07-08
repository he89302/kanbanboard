package com.practice.cleankanban.usecase.workItem;

import com.practice.cleankanban.domain.model.FlowEvent;
import com.practice.cleankanban.usecase.domainevent.DomainEventRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CycleTimeCalculator {
    private DomainEventRepository<FlowEvent> flowEventRepository;

    public CycleTimeCalculator(DomainEventRepository<FlowEvent> flowEventRepository) {
        this.flowEventRepository = flowEventRepository;
    }

    public List<FlowEntryPair> getCycleTime(String workItemId) {
        List<FlowEntryPair> flowEntryPairs = new ArrayList<>();
        Stack<FlowEvent> flowEventStack = new Stack<>();

        for (FlowEvent each:flowEventRepository.findAll()) {
            if (!each.detail().contains(workItemId)) {
                continue;
            }
            if (flowEventStack.empty()) {
                flowEventStack.push(each);
            } else {
                FlowEvent moveIn = flowEventStack.pop();
                flowEntryPairs.add(new FlowEntryPair(moveIn, each));
            }
        }
        return flowEntryPairs;
    }
}
