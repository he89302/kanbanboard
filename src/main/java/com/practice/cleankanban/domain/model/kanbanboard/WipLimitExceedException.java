package com.practice.cleankanban.domain.model.kanbanboard;

public class WipLimitExceedException extends Exception {
    public WipLimitExceedException(String msg) {
        super(msg);
    }
}
