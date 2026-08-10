package com.dsavisualizer.app.learning;

public class NodeState {

    public final String id;
    public final String label;
    public final boolean active;
    public final boolean visited;

    public NodeState(String id, String label, boolean active, boolean visited) {
        this.id = id == null ? "" : id;
        this.label = label == null ? "" : label;
        this.active = active;
        this.visited = visited;
    }
}
