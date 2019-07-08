package com.practice.cleankanban.domain;

import java.io.Serializable;
import java.util.UUID;

public abstract class Entity implements Serializable {
    protected String name;
    protected String id;

    public Entity(String name) {
        this.name = name;
        this.id = UUID.randomUUID().toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }
}
