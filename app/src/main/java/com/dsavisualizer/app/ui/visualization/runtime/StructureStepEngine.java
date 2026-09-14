package com.dsavisualizer.app.ui.visualization.runtime;

public final class StructureStepEngine extends BaseStepEngine {
    public StructureStepEngine(VisualizationRuntimeHost host) {
        super(host);
    }

    @Override
    public Step next() {
        if (host == null) {
            return Step.idle(true, false);
        }
        advance(host);
        host.captureCurrentSnapshot();
        return Step.moved(false, host.isPlaybackComplete());
    }

    public static void advance(VisualizationRuntimeHost host) {
        if ("union_find".equals(host.getTechniqueId())) {
            UnionFindStepEngine.advance(host);
        } else if ("postfix_evaluation".equals(host.getTechniqueId())) {
            advancePostfixEvaluation(host);
        } else if ("infix_to_postfix".equals(host.getTechniqueId())) {
            advanceInfixToPostfix(host);
        } else if ("infix_to_prefix".equals(host.getTechniqueId())) {
            advanceInfixToPrefix(host);
        } else {
            advanceLinearStructureStep(host);
        }
    }

    private static void advancePostfixEvaluation(VisualizationRuntimeHost host) {
        String expression = normalizeExpression(host.getExpressionInput(), "2 3 4 * +");
        String[] tokens = tokenizePostfix(expression);
        int nextIndex = host.getStepIndex() + 1;
        host.setStepIndex(nextIndex);

        if (nextIndex >= tokens.length) {
            int[] finalStack = replayPostfixStack(tokens, tokens.length - 1);
            String finalValue = finalStack.length == 0 ? "" : String.valueOf(finalStack[finalStack.length - 1]);
            host.finish("Postfix expression completed. VALUE = " + finalValue + ".");
            return;
        }

        int[] stack = replayPostfixStack(tokens, nextIndex);
        host.setWorkingValues(stack);
        String token = tokens[nextIndex];
        int focusIndex = stack.length - 1;

        if (")".equals(token)) {
            host.setStructureMotion("", focusIndex);
            host.updatePointerState("token", nextIndex, "value", stack.length == 0 ? -1 : stack[stack.length - 1]);
            host.setHighlightedLines(5, 0);
            host.updateArrayVisualization(nextIndex, -1, -1);
            host.bindCurrentState("Sentinel ')' reached. VALUE is the top of STACK: " + (stack.length == 0 ? "" : stack[stack.length - 1]) + ".");
            host.syncScrollToFocus(nextIndex);
            host.finish("Postfix expression completed. VALUE = " + (stack.length == 0 ? "" : stack[stack.length - 1]) + ".");
            return;
        }

        if (isOperandToken(token)) {
            host.setStructureMotion("push", focusIndex);
            host.updatePointerState("token", nextIndex, "top", focusIndex);
            host.setHighlightedLines(3, 0);
            host.updateArrayVisualization(nextIndex, -1, -1);
            host.bindCurrentState("Token " + token + " is an operand, so it is pushed on STACK.");
        } else {
            host.setStructureMotion("pop", focusIndex);
            host.updatePointerState("token", nextIndex, "top", focusIndex);
            host.setHighlightedLines(4, 0);
            host.updateArrayVisualization(nextIndex, -1, -1);
            host.bindCurrentState("Token " + token + " is an operator. Pop A and B, evaluate B " + token + " A, then push the result.");
        }
        host.syncScrollToFocus(nextIndex);
    }

    private static void advanceInfixToPostfix(VisualizationRuntimeHost host) {
        advanceExpressionConversion(
            host,
            normalizeExpression(host.getExpressionInput(), "a+b*(c^d-e)"),
            false,
            "Infix to postfix conversion completed."
        );
    }

    private static void advanceInfixToPrefix(VisualizationRuntimeHost host) {
        advanceExpressionConversion(
            host,
            normalizeExpression(host.getExpressionInput(), "(e-d^c)*b+a"),
            true,
            "Infix to prefix conversion completed."
        );
    }

    private static void advanceExpressionConversion(
        VisualizationRuntimeHost host,
        String expression,
        boolean prefixMode,
        String completeMessage
    ) {
        int nextIndex = host.getStepIndex() + 1;
        host.setStepIndex(nextIndex);
        int lastTokenIndex = expression.length();
        if (nextIndex > lastTokenIndex) {
            host.finish(completeMessage);
            return;
        }

        ConversionState state = replayConversion(expression, nextIndex, prefixMode);
        int stackTop = Math.max(0, state.stack.length - 1);
        host.setWorkingValues(toCodePoints(state.stack));
        host.updatePointerState("token", Math.min(nextIndex, lastTokenIndex), "top", state.stack.length - 1);
        host.updateArrayVisualization(Math.min(nextIndex, lastTokenIndex), -1, -1);
        host.syncScrollToFocus(Math.min(nextIndex, lastTokenIndex));

        if (nextIndex == lastTokenIndex) {
            host.setStructureMotion(state.stack.length == 0 ? "" : "pop", stackTop);
            host.setHighlightedLines(prefixMode ? 8 : 7, 0);
            String finalResult = prefixMode ? reverse(state.output) : state.output;
            host.bindCurrentState(completeMessage + " Final result: " + finalResult + ".");
            host.finish(completeMessage + " Final result: " + finalResult + ".");
            return;
        }

        char token = expression.charAt(nextIndex);
        if (Character.isLetterOrDigit(token)) {
            host.setStructureMotion("", stackTop);
            host.setHighlightedLines(prefixMode ? 4 : 3, 0);
            host.bindCurrentState("Scanning token '" + token + "'. Operand goes directly to the result.");
        } else if (token == '(') {
            host.setStructureMotion("push", stackTop);
            host.setHighlightedLines(prefixMode ? 5 : 4, 0);
            host.bindCurrentState("Scanning token '('. Push it onto the operator stack.");
        } else if (token == ')') {
            host.setStructureMotion("pop", stackTop);
            host.setHighlightedLines(prefixMode ? 7 : 6, 0);
            host.bindCurrentState("Scanning token ')'. Pop operators until '(' is removed.");
        } else {
            host.setStructureMotion("push", stackTop);
            host.setHighlightedLines(prefixMode ? 6 : 5, 0);
            host.bindCurrentState("Scanning token '" + token + "'. Compare precedence before pushing it.");
        }
    }

    public static void advanceLinearStructureStep(VisualizationRuntimeHost host) {
        String techniqueId = host.getTechniqueId();
        if (isLinkedListTechnique(techniqueId)) {
            advanceLinkedListTechnique(host);
            return;
        }

        host.setStepIndex(host.getStepIndex() + 1);
        int[] workingValues = host.getWorkingValues();
        int stepIndex = host.getStepIndex();
        if (stepIndex >= workingValues.length) {
            host.finish("Structure walkthrough completed");
            return;
        }

        if (isStackTechnique(techniqueId)) {
            host.setStructureMotion("push", stepIndex);
            host.updatePointerState("top", stepIndex, "", -1);
            host.setHighlightedLines(2, 1);
            host.updateArrayVisualization(stepIndex, -1, -1);
            host.bindCurrentState("Top is now " + workingValues[stepIndex]);
        } else if (isQueueTechnique(techniqueId)) {
            host.setStructureMotion("enqueue", stepIndex);
            host.updatePointerState("front", 0, "rear", stepIndex);
            host.setHighlightedLines(2, 1);
            host.updateArrayVisualization(stepIndex, -1, -1);
            host.bindCurrentState("Front reaches " + workingValues[stepIndex]);
        } else {
            host.setStructureMotion("link", stepIndex);
            host.updatePointerState("node", stepIndex, "", -1);
            host.setHighlightedLines(2, 1);
            host.updateArrayVisualization(stepIndex, -1, -1);
            host.bindCurrentState("Visiting node " + workingValues[stepIndex]);
        }
        host.syncScrollToFocus(stepIndex);
    }

    private static void advanceLinkedListTechnique(VisualizationRuntimeHost host) {
        String techniqueId = host.getTechniqueId();
        int[] workingValues = host.getWorkingValues();
        int stepIndex = host.getStepIndex() + 1;
        host.setStepIndex(stepIndex);

        if ("insert_at_beginning_linked_list".equals(techniqueId)) {
            int insertedValue = host.getTarget();
            int[] updated = prependValue(workingValues, insertedValue);
            host.setWorkingValues(updated);
            host.setStructureMotion("push", 0);
            host.updatePointerState("start", 0, "next", updated.length > 1 ? 1 : -1);
            host.setHighlightedLines(4, 0);
            host.updateArrayVisualization(0, updated.length > 1 ? 1 : -1, -1);
            host.bindCurrentState("Inserted " + insertedValue + " at the beginning and updated START.");
            host.syncScrollToFocus(0);
            host.finish("Inserted " + insertedValue + " at the beginning.");
            return;
        }

        if ("insert_after_given_node_linked_list".equals(techniqueId)) {
            int locationIndex = Math.max(0, Math.min(workingValues.length - 1, 1));
            if (workingValues.length == 0) {
                int[] updated = new int[] {host.getTarget()};
                host.setWorkingValues(updated);
                host.setStructureMotion("push", 0);
                host.updatePointerState("start", 0, "", -1);
                host.setHighlightedLines(4, 0);
                host.updateArrayVisualization(0, -1, -1);
                host.bindCurrentState("The list was empty, so " + host.getTarget() + " became the first node.");
                host.finish("Inserted " + host.getTarget() + " into the empty list.");
                return;
            }
            if (stepIndex <= locationIndex) {
                host.setStructureMotion("visit", stepIndex);
                host.updatePointerState("ptr", stepIndex, "", -1);
                host.setHighlightedLines(3, 0);
                host.updateArrayVisualization(stepIndex, -1, -1);
                host.bindCurrentState("Reached the given node " + workingValues[stepIndex] + ". New links will be attached after it.");
                host.syncScrollToFocus(stepIndex);
                return;
            }

            int insertIndex = locationIndex + 1;
            int[] updated = insertValue(workingValues, insertIndex, host.getTarget());
            host.setWorkingValues(updated);
            host.setStructureMotion("push", insertIndex);
            host.updatePointerState("loc", locationIndex, "new", insertIndex);
            host.setHighlightedLines(4, 0);
            host.updateArrayVisualization(insertIndex, locationIndex, -1);
            host.bindCurrentState("Inserted " + host.getTarget() + " after node " + workingValues[locationIndex] + ".");
            host.syncScrollToFocus(insertIndex);
            host.finish("Inserted " + host.getTarget() + " after the given node.");
            return;
        }

        if ("insert_into_sorted_linked_list".equals(techniqueId)) {
            int target = host.getTarget();
            int insertIndex = 0;
            while (insertIndex < workingValues.length && workingValues[insertIndex] < target) {
                insertIndex++;
            }

            if (stepIndex < insertIndex && stepIndex < workingValues.length) {
                host.setStructureMotion("visit", stepIndex);
                host.updatePointerState("ptr", stepIndex, "next", Math.min(stepIndex + 1, workingValues.length - 1));
                host.setHighlightedLines(5, 0);
                host.updateArrayVisualization(stepIndex, Math.min(stepIndex + 1, workingValues.length - 1), -1);
                host.bindCurrentState("Keep moving right because " + workingValues[stepIndex] + " is still smaller than " + target + ".");
                host.syncScrollToFocus(stepIndex);
                return;
            }

            int[] updated = insertValue(workingValues, insertIndex, target);
            host.setWorkingValues(updated);
            host.setStructureMotion("push", insertIndex);
            host.updatePointerState("save", Math.max(0, insertIndex - 1), "new", insertIndex);
            host.setHighlightedLines(1, 0);
            host.updateArrayVisualization(insertIndex, Math.max(0, insertIndex - 1), -1);
            host.bindCurrentState("Inserted " + target + " at the sorted position so the order stays increasing.");
            host.syncScrollToFocus(insertIndex);
            host.finish("Inserted " + target + " into the sorted linked list.");
            return;
        }

        if ("delete_node_given_location_linked_list".equals(techniqueId)) {
            if (workingValues.length == 0) {
                host.finish("Linked list is empty.");
                return;
            }
            int deleteIndex = Math.max(0, Math.min(workingValues.length - 1, host.getTarget()));
            int previousIndex = deleteIndex - 1;
            if (previousIndex >= 0 && stepIndex <= previousIndex) {
                host.setStructureMotion("visit", stepIndex);
                host.updatePointerState("locp", stepIndex, "loc", deleteIndex);
                host.setHighlightedLines(1, 0);
                host.updateArrayVisualization(stepIndex, deleteIndex, -1);
                host.bindCurrentState("Move to the predecessor before reconnecting around the node at location " + deleteIndex + ".");
                host.syncScrollToFocus(stepIndex);
                return;
            }

            int removedValue = workingValues[deleteIndex];
            int[] updated = removeAt(workingValues, deleteIndex);
            host.setWorkingValues(updated);
            host.setStructureMotion("", -1);
            int focusIndex = updated.length == 0 ? -1 : Math.max(0, Math.min(deleteIndex, updated.length - 1));
            host.updatePointerState(previousIndex >= 0 ? "locp" : "start", previousIndex >= 0 ? previousIndex : focusIndex, "", -1);
            host.setHighlightedLines(1, 0);
            host.updateArrayVisualization(focusIndex, previousIndex, -1);
            host.bindCurrentState("Removed node " + removedValue + " from location " + deleteIndex + " and relinked the list.");
            host.syncScrollToFocus(focusIndex);
            host.finish("Deleted node " + removedValue + " from location " + deleteIndex + ".");
            return;
        }

        if ("delete_node_by_item_linked_list".equals(techniqueId) || "delete_linked_list".equals(techniqueId)) {
            int foundIndex = indexOf(workingValues, host.getTarget());
            if (foundIndex < 0) {
                if (stepIndex >= workingValues.length) {
                    host.finish("Item " + host.getTarget() + " was not found in the linked list.");
                    return;
                }
                visitLinkedNode(host, workingValues, stepIndex, "Item " + host.getTarget() + " is not here, so move to the next node.");
                return;
            }
            if (stepIndex < foundIndex) {
                visitLinkedNode(host, workingValues, stepIndex, "Searching node " + workingValues[stepIndex] + " before deleting the target item.");
                return;
            }
            if (stepIndex == foundIndex) {
                host.setStructureMotion("visit", foundIndex);
                host.updatePointerState("loc", foundIndex, "locp", foundIndex > 0 ? foundIndex - 1 : -1);
                host.setHighlightedLines(2, 0);
                host.updateArrayVisualization(foundIndex, foundIndex > 0 ? foundIndex - 1 : -1, -1);
                host.bindCurrentState("Found item " + host.getTarget() + ". The next step reconnects the links around this node.");
                host.syncScrollToFocus(foundIndex);
                return;
            }

            int[] updated = removeAt(workingValues, foundIndex);
            host.setWorkingValues(updated);
            host.setStructureMotion("", -1);
            int focusIndex = updated.length == 0 ? -1 : Math.max(0, Math.min(foundIndex, updated.length - 1));
            host.updatePointerState(foundIndex > 0 ? "locp" : "start", foundIndex > 0 ? foundIndex - 1 : focusIndex, "", -1);
            host.setHighlightedLines(3, 0);
            host.updateArrayVisualization(focusIndex, foundIndex > 0 ? foundIndex - 1 : -1, -1);
            host.bindCurrentState("Deleted item " + host.getTarget() + " and linked the previous node to the next node.");
            host.syncScrollToFocus(focusIndex);
            host.finish("Deleted item " + host.getTarget() + " from the linked list.");
            return;
        }

        if ("count_nodes_linked_list".equals(techniqueId)) {
            if (stepIndex >= workingValues.length) {
                host.finish("Total nodes counted: " + workingValues.length + ".");
                return;
            }
            host.setStructureMotion("visit", stepIndex);
            host.updatePointerState("ptr", stepIndex, "count", stepIndex + 1);
            host.setHighlightedLines(2, 0);
            host.updateArrayVisualization(stepIndex, -1, -1);
            host.bindCurrentState("Count = " + (stepIndex + 1) + " after visiting node " + workingValues[stepIndex] + ".");
            host.syncScrollToFocus(stepIndex);
            if (stepIndex == workingValues.length - 1) {
                host.finish("Total nodes counted: " + workingValues.length + ".");
            }
            return;
        }

        if ("search_in_linked_list".equals(techniqueId) || "find_linked_list".equals(techniqueId)) {
            if (stepIndex >= workingValues.length) {
                host.finish("Item " + host.getTarget() + " was not found in the linked list.");
                return;
            }
            int value = workingValues[stepIndex];
            host.setStructureMotion("visit", stepIndex);
            host.updatePointerState("ptr", stepIndex, "", -1);
            host.setHighlightedLines(2, 0);
            if (value == host.getTarget()) {
                host.setFoundIndex(stepIndex);
                host.updateArrayVisualization(stepIndex, -1, stepIndex);
                host.bindCurrentState("Found item " + host.getTarget() + " at node " + stepIndex + ".");
                host.syncScrollToFocus(stepIndex);
                host.finish("Found item " + host.getTarget() + " at node " + stepIndex + ".");
            } else {
                host.updateArrayVisualization(stepIndex, -1, -1);
                host.bindCurrentState("Checked node " + value + " and did not find " + host.getTarget() + ", so move next.");
                host.syncScrollToFocus(stepIndex);
            }
            return;
        }

        if ("print_linked_list".equals(techniqueId)) {
            if (stepIndex >= workingValues.length) {
                host.finish("Printed all linked-list nodes in order.");
                return;
            }
            visitLinkedNode(host, workingValues, stepIndex, "Printed node " + workingValues[stepIndex] + " and moved to the next link.");
            if (stepIndex == workingValues.length - 1) {
                host.finish("Printed all linked-list nodes in order.");
            }
            return;
        }

        if (stepIndex >= workingValues.length) {
            host.finish("Linked-list walkthrough completed.");
            return;
        }
        visitLinkedNode(host, workingValues, stepIndex, "Visiting node " + workingValues[stepIndex] + " and following the next link.");
        if (stepIndex == workingValues.length - 1
            && ("traversing_linked_list".equals(techniqueId) || "singly_linked_list".equals(techniqueId))) {
            host.finish("Reached the end of the linked list.");
        }
    }

    private static void visitLinkedNode(VisualizationRuntimeHost host, int[] values, int stepIndex, String message) {
        host.setStructureMotion("visit", stepIndex);
        host.updatePointerState("ptr", stepIndex, stepIndex + 1 < values.length ? "next" : "", stepIndex + 1 < values.length ? stepIndex + 1 : -1);
        host.setHighlightedLines(2, 0);
        host.updateArrayVisualization(stepIndex, -1, -1);
        host.bindCurrentState(message);
        host.syncScrollToFocus(stepIndex);
    }

    private static boolean isLinkedListTechnique(String techniqueId) {
        return "singly_linked_list".equals(techniqueId)
            || "traversing_linked_list".equals(techniqueId)
            || "print_linked_list".equals(techniqueId)
            || "count_nodes_linked_list".equals(techniqueId)
            || "search_in_linked_list".equals(techniqueId)
            || "find_linked_list".equals(techniqueId)
            || "insert_at_beginning_linked_list".equals(techniqueId)
            || "insert_after_given_node_linked_list".equals(techniqueId)
            || "insert_into_sorted_linked_list".equals(techniqueId)
            || "delete_node_given_location_linked_list".equals(techniqueId)
            || "delete_node_by_item_linked_list".equals(techniqueId)
            || "delete_linked_list".equals(techniqueId);
    }

    private static boolean isStackTechnique(String techniqueId) {
        return "stack_basics".equals(techniqueId)
            || "linked_stack_push".equals(techniqueId)
            || "linked_stack_pop".equals(techniqueId)
            || "postfix_evaluation".equals(techniqueId)
            || "infix_to_postfix".equals(techniqueId)
            || "infix_to_prefix".equals(techniqueId);
    }

    private static int[] replayPostfixStack(String[] tokens, int uptoIndex) {
        int[] temp = new int[tokens.length];
        int top = -1;
        for (int i = 0; i <= uptoIndex && i < tokens.length; i++) {
            String token = tokens[i];
            if (")".equals(token)) {
                break;
            }
            if (isOperandToken(token)) {
                temp[++top] = Integer.parseInt(token);
            } else if (top >= 1) {
                int a = temp[top--];
                int b = temp[top--];
                temp[++top] = applyOperator(b, token, a);
            }
        }
        int[] stack = new int[top + 1];
        System.arraycopy(temp, 0, stack, 0, stack.length);
        return stack;
    }

    private static boolean isOperandToken(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }
        int start = token.charAt(0) == '-' && token.length() > 1 ? 1 : 0;
        for (int i = start; i < token.length(); i++) {
            if (!Character.isDigit(token.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static int applyOperator(int b, String operator, int a) {
        if ("+".equals(operator)) {
            return b + a;
        }
        if ("-".equals(operator)) {
            return b - a;
        }
        if ("*".equals(operator)) {
            return b * a;
        }
        if ("/".equals(operator) && a != 0) {
            return b / a;
        }
        if ("^".equals(operator)) {
            return (int) Math.pow(b, a);
        }
        return 0;
    }

    private static ConversionState replayConversion(String expression, int uptoIndex, boolean prefixMode) {
        char[] stackTemp = new char[expression.length() + 2];
        StringBuilder output = new StringBuilder();
        int top = -1;

        for (int i = 0; i < uptoIndex && i < expression.length(); i++) {
            char token = expression.charAt(i);
            if (Character.isWhitespace(token)) {
                continue;
            }
            if (Character.isLetterOrDigit(token)) {
                output.append(token);
            } else if (token == '(') {
                stackTemp[++top] = token;
            } else if (token == ')') {
                while (top >= 0 && stackTemp[top] != '(') {
                    output.append(stackTemp[top--]);
                }
                if (top >= 0 && stackTemp[top] == '(') {
                    top--;
                }
            } else if (isOperator(token)) {
                while (top >= 0
                    && stackTemp[top] != '('
                    && (prefixMode
                        ? precedence(stackTemp[top]) > precedence(token)
                        : precedence(stackTemp[top]) >= precedence(token))) {
                    output.append(stackTemp[top--]);
                }
                stackTemp[++top] = token;
            }
        }

        if (uptoIndex >= expression.length()) {
            while (top >= 0) {
                if (stackTemp[top] != '(') {
                    output.append(stackTemp[top]);
                }
                top--;
            }
        }

        char[] stack = new char[top + 1];
        System.arraycopy(stackTemp, 0, stack, 0, stack.length);
        return new ConversionState(stack, output.toString());
    }

    private static boolean isOperator(char token) {
        return token == '+' || token == '-' || token == '*' || token == '/' || token == '^';
    }

    private static int precedence(char operator) {
        if (operator == '+' || operator == '-') {
            return 1;
        }
        if (operator == '*' || operator == '/') {
            return 2;
        }
        if (operator == '^') {
            return 3;
        }
        return 0;
    }

    private static int[] toCodePoints(char[] chars) {
        int[] values = new int[chars.length];
        for (int i = 0; i < chars.length; i++) {
            values[i] = chars[i];
        }
        return values;
    }

    private static String reverse(String text) {
        return new StringBuilder(text == null ? "" : text).reverse().toString();
    }

    private static String normalizeExpression(String expression, String fallback) {
        if (expression == null) {
            return fallback;
        }
        String trimmed = expression.trim();
        return trimmed.isEmpty() ? fallback : trimmed;
    }

    private static String[] tokenizePostfix(String expression) {
        String normalized = expression == null ? "" : expression.trim();
        if (normalized.isEmpty()) {
            return new String[] {"2", "3", "4", "*", "+", ")"};
        }
        if (normalized.contains(" ")) {
            String[] parts = normalized.split("\\s+");
            if (parts.length == 0) {
                return new String[] {"2", "3", "4", "*", "+", ")"};
            }
            if (!")".equals(parts[parts.length - 1])) {
                String[] withSentinel = new String[parts.length + 1];
                System.arraycopy(parts, 0, withSentinel, 0, parts.length);
                withSentinel[parts.length] = ")";
                return withSentinel;
            }
            return parts;
        }
        String[] tokens = new String[normalized.length() + (normalized.endsWith(")") ? 0 : 1)];
        for (int i = 0; i < normalized.length(); i++) {
            tokens[i] = String.valueOf(normalized.charAt(i));
        }
        if (!normalized.endsWith(")")) {
            tokens[tokens.length - 1] = ")";
        }
        return tokens;
    }

    private static final class ConversionState {
        final char[] stack;
        final String output;

        ConversionState(char[] stack, String output) {
            this.stack = stack;
            this.output = output;
        }
    }

    private static boolean isQueueTechnique(String techniqueId) {
        return "queue_basics".equals(techniqueId)
            || "circular_queue".equals(techniqueId)
            || "linked_queue".equals(techniqueId)
            || "priority_queue_array".equals(techniqueId)
            || "priority_queue_2d".equals(techniqueId);
    }

    private static int indexOf(int[] values, int target) {
        for (int i = 0; i < values.length; i++) {
            if (values[i] == target) {
                return i;
            }
        }
        return -1;
    }

    private static int[] prependValue(int[] values, int value) {
        int[] updated = new int[values.length + 1];
        updated[0] = value;
        System.arraycopy(values, 0, updated, 1, values.length);
        return updated;
    }

    private static int[] insertValue(int[] values, int index, int value) {
        int safeIndex = Math.max(0, Math.min(values.length, index));
        int[] updated = new int[values.length + 1];
        System.arraycopy(values, 0, updated, 0, safeIndex);
        updated[safeIndex] = value;
        System.arraycopy(values, safeIndex, updated, safeIndex + 1, values.length - safeIndex);
        return updated;
    }

    private static int[] removeAt(int[] values, int index) {
        if (values.length == 0) {
            return values;
        }
        int safeIndex = Math.max(0, Math.min(values.length - 1, index));
        int[] updated = new int[values.length - 1];
        System.arraycopy(values, 0, updated, 0, safeIndex);
        System.arraycopy(values, safeIndex + 1, updated, safeIndex, values.length - safeIndex - 1);
        return updated;
    }
}
