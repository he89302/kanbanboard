package com.practice.cleankanban.domain.model.workItem;

import com.practice.cleankanban.domain.Entity;
import com.practice.cleankanban.domain.model.DomainEventPublisher;
import com.practice.cleankanban.domain.model.workItem.event.WorkItemBlocked;

import java.util.ArrayList;
import java.util.List;

@javax.persistence.Entity
public class BlockWorkItem extends Entity {

    private WorkItem blockWorkItem;
    private List<String> blockWorkItemIds;
    private String note;

    public BlockWorkItem(String name) {
        super("name");
        blockWorkItemIds = new ArrayList<>();
    }


    public void blockWorkItem(WorkItem workItem) {
        blockWorkItemIds.add(workItem.getId());
        DomainEventPublisher.instance().publish(new WorkItemBlocked(
                                                this.getId(),
                                                this.getNote(),
                                                workItem.getId(),
                                                workItem.getName()));
    }

    public void setNote(String blockNote) {
        this.note = blockNote;
    }

    public String getNote() {
        return note;
    }
}
