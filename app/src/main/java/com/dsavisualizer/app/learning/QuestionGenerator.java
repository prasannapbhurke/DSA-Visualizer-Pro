package com.dsavisualizer.app.learning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

public class QuestionGenerator {

    public LearningQuestion generate(StepState stepState, DifficultyLevel difficultyLevel) {
        List<ActionType> actions = new ArrayList<>(new LinkedHashSet<>(stepState.decisionContext.plausibleActions));
        if (!actions.contains(stepState.correctAction)) {
            actions.add(0, stepState.correctAction);
        }
        actions = resolveActionsForDifficulty(actions, stepState.correctAction, difficultyLevel, stepState.structureType);

        List<QuestionOption> options = new ArrayList<>();
        for (int i = 0; i < actions.size(); i++) {
            ActionType actionType = actions.get(i);
            options.add(new QuestionOption(
                String.valueOf((char) ('A' + i)),
                actionType,
                ActionLabelResolver.toLabel(actionType),
                actionType == stepState.correctAction
            ));
        }

        String hint = difficultyLevel == DifficultyLevel.GUIDED
            ? resolveHint(stepState)
            : difficultyLevel == DifficultyLevel.INTERMEDIATE
                ? resolveMinimalHint(stepState)
                : "";

        return new LearningQuestion(
            resolvePrompt(stepState, difficultyLevel),
            options,
            hint,
            difficultyLevel
        );
    }

    private String resolvePrompt(StepState stepState, DifficultyLevel difficultyLevel) {
        String basePrompt = stepState.decisionContext.prompt.isEmpty()
            ? "What should happen next?"
            : stepState.decisionContext.prompt;

        if (difficultyLevel == DifficultyLevel.GUIDED) {
            return "Guided: " + basePrompt;
        }
        if (difficultyLevel == DifficultyLevel.INTERMEDIATE) {
            return "Level 2: " + basePrompt;
        }
        return "Expert quiz: predict the exact next operation.";
    }

    private String resolveHint(StepState stepState) {
        if (!stepState.hint.isEmpty()) {
            return stepState.hint;
        }
        if (stepState.structureType == StructureType.SEARCH) {
            return "Watch the target and the current comparison value carefully.";
        }
        if (stepState.structureType == StructureType.SORT) {
            return "Check whether the current pair is already in the correct order.";
        }
        if (stepState.structureType == StructureType.TREE) {
            return "Follow the traversal rule one move at a time.";
        }
        if (stepState.structureType == StructureType.GRAPH) {
            return "Think about the queue or frontier before choosing the next move.";
        }
        return "Focus on the active pointer or highlighted element.";
    }

    private String resolveMinimalHint(StepState stepState) {
        if (stepState.pointers.isEmpty()) {
            return "";
        }
        StringBuilder builder = new StringBuilder("Active: ");
        for (int i = 0; i < stepState.pointers.size(); i++) {
            StepPointer pointer = stepState.pointers.get(i);
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(pointer.label).append("=").append(pointer.index);
        }
        return builder.toString();
    }

    public List<ActionType> resolveActionsForDifficulty(
        List<ActionType> actions,
        ActionType correctAction,
        DifficultyLevel difficultyLevel,
        StructureType structureType
    ) {
        List<ActionType> prioritized = new ArrayList<>();
        prioritized.add(correctAction);
        for (ActionType action : actions) {
            if (action != correctAction && !prioritized.contains(action)) {
                prioritized.add(action);
            }
        }

        appendDistractors(prioritized, correctAction, structureType);

        int optionCount;
        if (difficultyLevel == DifficultyLevel.GUIDED) {
            optionCount = Math.min(2, prioritized.size());
            List<ActionType> selected = new ArrayList<>(prioritized.subList(0, optionCount));
            Collections.shuffle(selected);
            return selected;
        }

        if (difficultyLevel == DifficultyLevel.INTERMEDIATE) {
            optionCount = Math.min(3, prioritized.size());
        } else {
            optionCount = Math.min(Math.max(4, actions.size()), prioritized.size());
        }

        List<ActionType> selected = new ArrayList<>(prioritized.subList(0, optionCount));
        Collections.shuffle(selected);
        return selected;
    }

    private void appendDistractors(List<ActionType> prioritized, ActionType correctAction, StructureType structureType) {
        List<ActionType> distractors = buildDistractorPool(correctAction, structureType);
        Collections.shuffle(distractors);
        for (ActionType action : distractors) {
            if (!prioritized.contains(action)) {
                prioritized.add(action);
            }
        }
    }

    private List<ActionType> buildDistractorPool(ActionType correctAction, StructureType structureType) {
        List<ActionType> pool = new ArrayList<>();
        switch (correctAction) {
            case MOVE_LEFT:
                pool.add(ActionType.MOVE_RIGHT);
                pool.add(ActionType.MOVE_NEXT);
                break;
            case MOVE_RIGHT:
                pool.add(ActionType.MOVE_LEFT);
                pool.add(ActionType.MOVE_NEXT);
                break;
            case SWAP:
                pool.add(ActionType.NO_SWAP);
                pool.add(ActionType.MOVE_NEXT);
                break;
            case NO_SWAP:
                pool.add(ActionType.SWAP);
                pool.add(ActionType.MOVE_NEXT);
                break;
            case PUSH_ITEM:
                pool.add(ActionType.POP_ITEM);
                pool.add(ActionType.MOVE_NEXT);
                break;
            case POP_ITEM:
                pool.add(ActionType.PUSH_ITEM);
                pool.add(ActionType.MOVE_PREVIOUS);
                break;
            case ENQUEUE_NEIGHBOR:
                pool.add(ActionType.DEQUEUE_NODE);
                pool.add(ActionType.MOVE_NEXT);
                break;
            case DEQUEUE_NODE:
                pool.add(ActionType.ENQUEUE_NEIGHBOR);
                pool.add(ActionType.MOVE_NEXT);
                break;
            case VISIT_LEFT:
                pool.add(ActionType.VISIT_RIGHT);
                pool.add(ActionType.VISIT_NODE);
                break;
            case VISIT_RIGHT:
                pool.add(ActionType.VISIT_LEFT);
                pool.add(ActionType.VISIT_NODE);
                break;
            case VISIT_NODE:
                pool.add(ActionType.VISIT_LEFT);
                pool.add(ActionType.VISIT_RIGHT);
                break;
            case FOUND:
                pool.add(ActionType.MOVE_NEXT);
                pool.add(ActionType.COMPLETE);
                break;
            case RECURSE_DEEPER:
                pool.add(ActionType.BACKTRACK_STEP);
                pool.add(ActionType.TAKE_EXCLUDE);
                break;
            case BACKTRACK_STEP:
                pool.add(ActionType.RECURSE_DEEPER);
                pool.add(ActionType.TAKE_INCLUDE);
                break;
            case TAKE_INCLUDE:
                pool.add(ActionType.TAKE_EXCLUDE);
                pool.add(ActionType.RECURSE_DEEPER);
                break;
            case TAKE_EXCLUDE:
                pool.add(ActionType.TAKE_INCLUDE);
                pool.add(ActionType.BACKTRACK_STEP);
                break;
            case INSERT_NODE:
                pool.add(ActionType.DELETE);
                pool.add(ActionType.MOVE_NEXT);
                break;
            case DELETE:
                pool.add(ActionType.INSERT_NODE);
                pool.add(ActionType.MOVE_PREVIOUS);
                break;
            case COMPLETE:
            default:
                pool.add(ActionType.MOVE_NEXT);
                pool.add(ActionType.COMPLETE);
                break;
        }

        if (structureType == StructureType.GRAPH) {
            pool.add(ActionType.VISIT_NODE);
        } else if (structureType == StructureType.TREE) {
            pool.add(ActionType.VISIT_LEFT);
            pool.add(ActionType.VISIT_RIGHT);
        } else if (structureType == StructureType.SORT) {
            pool.add(ActionType.SWAP);
            pool.add(ActionType.NO_SWAP);
        }

        return new ArrayList<>(new LinkedHashSet<>(pool));
    }
}
