package com.dsavisualizer.app;

public class StructureOperations {
    public static void handleStack(int type, int val, StructureView structureView) {
        if (type == 1) structureView.push(val);
        else if (type == 2) structureView.pop();
    }

    public static void handleQueue(int type, int val, String mode, StructureView structureView) {
        if (type == 1) structureView.enqueue(val);
        else if (type == 2) structureView.dequeue();
    }

    public static void handleDeque(int type, int val, StructureView structureView) {
        if (type == 1) structureView.addFirst(val);
        else if (type == 2) structureView.addLast(val);
        else if (type == 3) structureView.removeFirst();
        else if (type == 4) structureView.removeLast();
    }

    public static void handleLinkedList(int type, int val, int pos, StructureView structureView) {
        if (type == 1) structureView.addFirst(val);
        else if (type == 2) structureView.addLast(val);
        else if (type == 3) structureView.addAt(pos, val);
        else if (type == 4) structureView.removeFirst();
        else if (type == 5) structureView.removeLast();
    }
}
