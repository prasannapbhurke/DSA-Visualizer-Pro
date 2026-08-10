package com.dsavisualizer.app.learning;

public class EdgeState {

    public final String fromId;
    public final String toId;
    public final boolean active;

    public EdgeState(String fromId, String toId, boolean active) {
        this.fromId = fromId == null ? "" : fromId;
        this.toId = toId == null ? "" : toId;
        this.active = active;
    }
}
