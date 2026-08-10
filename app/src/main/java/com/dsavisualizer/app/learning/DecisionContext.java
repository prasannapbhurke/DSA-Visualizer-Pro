package com.dsavisualizer.app.learning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DecisionContext {

    public final DecisionType type;
    public final String prompt;
    public final String expectedReason;
    public final List<ActionType> plausibleActions;

    public DecisionContext(
        DecisionType type,
        String prompt,
        String expectedReason,
        List<ActionType> plausibleActions
    ) {
        this.type = type == null ? DecisionType.NEXT_STEP : type;
        this.prompt = prompt == null ? "" : prompt;
        this.expectedReason = expectedReason == null ? "" : expectedReason;
        this.plausibleActions = plausibleActions == null
            ? Collections.emptyList()
            : Collections.unmodifiableList(new ArrayList<>(plausibleActions));
    }
}
