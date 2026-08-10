package com.dsavisualizer.app.learning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StepState {

    public final String algorithmId;
    public final String algorithmTitle;
    public final StructureType structureType;
    public final OperationType operationType;
    public final int stepNumber;
    public final List<Integer> linearData;
    public final List<NodeState> nodes;
    public final List<EdgeState> edges;
    public final List<StepPointer> pointers;
    public final DecisionContext decisionContext;
    public final Map<String, String> metadata;
    public final String summary;
    public final String hint;
    public final ActionType correctAction;

    private StepState(Builder builder) {
        this.algorithmId = builder.algorithmId;
        this.algorithmTitle = builder.algorithmTitle;
        this.structureType = builder.structureType;
        this.operationType = builder.operationType;
        this.stepNumber = builder.stepNumber;
        this.linearData = Collections.unmodifiableList(new ArrayList<>(builder.linearData));
        this.nodes = Collections.unmodifiableList(new ArrayList<>(builder.nodes));
        this.edges = Collections.unmodifiableList(new ArrayList<>(builder.edges));
        this.pointers = Collections.unmodifiableList(new ArrayList<>(builder.pointers));
        this.decisionContext = builder.decisionContext;
        this.metadata = Collections.unmodifiableMap(new HashMap<>(builder.metadata));
        this.summary = builder.summary;
        this.hint = builder.hint;
        this.correctAction = builder.correctAction;
    }

    public String getMetadata(String key) {
        String value = metadata.get(key);
        return value == null ? "" : value;
    }

    public static class Builder {
        private String algorithmId = "";
        private String algorithmTitle = "";
        private StructureType structureType = StructureType.ARRAY;
        private OperationType operationType = OperationType.START;
        private int stepNumber = 0;
        private final List<Integer> linearData = new ArrayList<>();
        private final List<NodeState> nodes = new ArrayList<>();
        private final List<EdgeState> edges = new ArrayList<>();
        private final List<StepPointer> pointers = new ArrayList<>();
        private DecisionContext decisionContext;
        private final Map<String, String> metadata = new HashMap<>();
        private String summary = "";
        private String hint = "";
        private ActionType correctAction = ActionType.COMPLETE;

        public Builder setAlgorithmId(String algorithmId) {
            this.algorithmId = algorithmId == null ? "" : algorithmId;
            return this;
        }

        public Builder setAlgorithmTitle(String algorithmTitle) {
            this.algorithmTitle = algorithmTitle == null ? "" : algorithmTitle;
            return this;
        }

        public Builder setStructureType(StructureType structureType) {
            this.structureType = structureType == null ? StructureType.ARRAY : structureType;
            return this;
        }

        public Builder setOperationType(OperationType operationType) {
            this.operationType = operationType == null ? OperationType.START : operationType;
            return this;
        }

        public Builder setStepNumber(int stepNumber) {
            this.stepNumber = Math.max(0, stepNumber);
            return this;
        }

        public Builder setLinearData(List<Integer> values) {
            this.linearData.clear();
            if (values != null) {
                this.linearData.addAll(values);
            }
            return this;
        }

        public Builder addPointer(String label, int index) {
            this.pointers.add(new StepPointer(label, index));
            return this;
        }

        public Builder addNode(String id, String label, boolean active, boolean visited) {
            this.nodes.add(new NodeState(id, label, active, visited));
            return this;
        }

        public Builder addEdge(String fromId, String toId, boolean active) {
            this.edges.add(new EdgeState(fromId, toId, active));
            return this;
        }

        public Builder setDecisionContext(DecisionContext decisionContext) {
            this.decisionContext = decisionContext;
            return this;
        }

        public Builder putMetadata(String key, String value) {
            if (key != null && value != null) {
                this.metadata.put(key, value);
            }
            return this;
        }

        public Builder setSummary(String summary) {
            this.summary = summary == null ? "" : summary;
            return this;
        }

        public Builder setHint(String hint) {
            this.hint = hint == null ? "" : hint;
            return this;
        }

        public Builder setCorrectAction(ActionType correctAction) {
            this.correctAction = correctAction == null ? ActionType.COMPLETE : correctAction;
            return this;
        }

        public StepState build() {
            if (decisionContext == null) {
                decisionContext = new DecisionContext(
                    DecisionType.NEXT_STEP,
                    "What should happen next?",
                    "",
                    Collections.singletonList(correctAction)
                );
            }
            return new StepState(this);
        }
    }
}
