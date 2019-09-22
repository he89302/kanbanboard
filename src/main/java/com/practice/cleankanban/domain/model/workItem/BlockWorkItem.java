package com.practice.cleankanban.domain.model.workItem;

public class BlockWorkItem {

    private String blockerId;
    private String workItemId;
    private boolean status;

    public BlockWorkItem(String blockerId, String workItemId, boolean status) {
        this.blockerId = blockerId;
        this.workItemId = workItemId;
        this.status = status;
    }

    public String getBlockerId() {
        return blockerId;
    }

    public void setBlockerId(String blockerId) {
        this.blockerId = blockerId;
    }

    public String getWorkItemId() {
        return workItemId;
    }

    public void setWorkItemId(String workItemId) {
        this.workItemId = workItemId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
