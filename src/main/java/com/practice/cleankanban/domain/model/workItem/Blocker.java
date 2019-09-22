package com.practice.cleankanban.domain.model.workItem;

import com.practice.cleankanban.domain.Entity;
import com.practice.cleankanban.domain.model.DomainEventPublisher;
import com.practice.cleankanban.domain.model.workItem.event.WorkItemBlocked;

import java.util.ArrayList;
import java.util.List;

@javax.persistence.Entity
public class Blocker extends Entity {

    private String note;
    private List<BlockWorkItem> blockWorkItems;

    public Blocker(String name, String workItemId) {
        super("name");
        blockWorkItems = new ArrayList<>();
        blockWorkItems.add(new BlockWorkItem(this.getId(), workItemId, Statue.UNBLOCK));
    }

    public void blockWorkItem(String workItemId) {
        DomainEventPublisher.instance().publish(new WorkItemBlocked(
                                                this.getId(),
                                                this.getNote(),
                                                workItemId));

        for (BlockWorkItem each:blockWorkItems
             ) {
            if (each.getWorkItemId().equals(workItemId)) {
                each.setStatus(Statue.BLOCK);
            }
        }
    }

    public void setNote(String blockNote) {
        this.note = blockNote;
    }

    public String getNote() {
        return note;
    }

    public boolean isWorkItemBlocked(String id) {
        for (BlockWorkItem each:blockWorkItems) {
            if (each.getWorkItemId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    private class Statue {
        public static final boolean UNBLOCK = true;
        public static final boolean BLOCK = false;
    }
}
