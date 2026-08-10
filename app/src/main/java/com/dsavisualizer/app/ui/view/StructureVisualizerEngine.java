package com.dsavisualizer.app.ui.view;

import android.graphics.Canvas;

/**
 * Linear data structures animate differently from arrays, especially stack
 * depth and queue/list flow, so they get their own renderer family.
 */
final class StructureVisualizerEngine implements VisualizerEngine {

    @Override
    public boolean supports(String techniqueId) {
        return "stack_basics".equals(techniqueId)
            || "linked_stack_push".equals(techniqueId)
            || "linked_stack_pop".equals(techniqueId)
            || "postfix_evaluation".equals(techniqueId)
            || "infix_to_postfix".equals(techniqueId)
            || "infix_to_prefix".equals(techniqueId)
            || "queue_basics".equals(techniqueId)
            || "circular_queue".equals(techniqueId)
            || "linked_queue".equals(techniqueId)
            || "deque".equals(techniqueId)
            || "priority_queue_array".equals(techniqueId)
            || "priority_queue_2d".equals(techniqueId)
            || "singly_linked_list".equals(techniqueId)
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
            || "delete_linked_list".equals(techniqueId)
            || "traverse_circular_header_list".equals(techniqueId)
            || "search_circular_header_list".equals(techniqueId)
            || "delete_circular_header_list".equals(techniqueId)
            || "union_find".equals(techniqueId)
            || "hash_collision_handling".equals(techniqueId)
            || "recursion_basics".equals(techniqueId)
            || "dynamic_programming_basics".equals(techniqueId);
    }

    @Override
    public void draw(Canvas canvas, TechniqueVisualizerView view, String techniqueId) {
        if ("union_find".equals(techniqueId)) {
            view.drawUnionFindScene(canvas);
            return;
        }
        if ("postfix_evaluation".equals(techniqueId)
            || "infix_to_postfix".equals(techniqueId)
            || "infix_to_prefix".equals(techniqueId)) {
            view.drawExpressionStackScene(canvas);
            return;
        }
        if ("stack_basics".equals(techniqueId)
            || "linked_stack_push".equals(techniqueId)
            || "linked_stack_pop".equals(techniqueId)) {
            view.drawVerticalStack(
                canvas,
                "linked_stack_push".equals(techniqueId) || "linked_stack_pop".equals(techniqueId)
            );
            return;
        }

        if ("circular_queue".equals(techniqueId)) {
            view.drawCircularStructure(canvas, false);
            return;
        }

        if ("traverse_circular_header_list".equals(techniqueId)
            || "search_circular_header_list".equals(techniqueId)
            || "delete_circular_header_list".equals(techniqueId)) {
            view.drawCircularStructure(canvas, true);
            return;
        }

        view.drawHorizontalBoxes(
            canvas,
            "linked_queue".equals(techniqueId)
                || "singly_linked_list".equals(techniqueId)
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
                || "delete_linked_list".equals(techniqueId)
                || "traverse_circular_header_list".equals(techniqueId)
                || "search_circular_header_list".equals(techniqueId)
                || "delete_circular_header_list".equals(techniqueId)
        );
    }

    @Override
    public int resolveDesiredWidth(TechniqueVisualizerView view, String techniqueId) {
        return view.resolveScreenWidthDesiredWidth();
    }

    @Override
    public int resolveDesiredHeight(TechniqueVisualizerView view, String techniqueId) {
        if ("stack_basics".equals(techniqueId)
            || "linked_stack_push".equals(techniqueId)
            || "linked_stack_pop".equals(techniqueId)
            || "postfix_evaluation".equals(techniqueId)
            || "infix_to_postfix".equals(techniqueId)
            || "infix_to_prefix".equals(techniqueId)) {
            return view.resolveStackDesiredHeight();
        }
        return view.resolveStandardDesiredHeight();
    }
}
