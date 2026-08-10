package com.dsavisualizer.app.learning;

import com.dsavisualizer.app.model.TechniqueContent;
import com.dsavisualizer.app.ui.visualization.VisualizationTechniqueRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

public class TheoryQuestionFactory {

    private static final int TARGET_COUNT = 50;

    public List<TheoryQuestion> build(String techniqueId, TechniqueContent content) {
        List<TheoryQuestion> questions = new ArrayList<>();
        if ("linear_search".equals(techniqueId)) {
            questions.addAll(buildLinearSearchTheory(content));
        } else {
            questions.addAll(buildGeneralTheory(techniqueId, content));
        }
        if (questions.isEmpty()) {
            questions.addAll(buildGeneralTheory(techniqueId, content));
        }
        List<TheoryQuestion> deduped = dedupe(questions);
        while (!deduped.isEmpty() && deduped.size() < TARGET_COUNT) {
            deduped.add(deduped.get(deduped.size() % Math.max(1, deduped.size())));
        }
        return deduped.size() > TARGET_COUNT
            ? new ArrayList<>(deduped.subList(0, TARGET_COUNT))
            : deduped;
    }

    private List<TheoryQuestion> buildLinearSearchTheory(TechniqueContent content) {
        List<TheoryQuestion> questions = new ArrayList<>();
        String title = resolveTitle(content, "Linear Search");
        add(questions, "What is " + title + "?",
            options("Divide and conquer algorithm", "Sequential search algorithm", "Recursive sorting method", "Tree traversal"),
            1, "Linear search checks elements one by one sequentially.");
        add(questions, "Time complexity of " + title + " in the worst case?",
            options("O(1)", "O(log n)", "O(n)", "O(n^2)"),
            2, "Worst case happens when the element is at the last position or absent.");
        add(questions, "Best case time complexity of " + title + "?",
            options("O(n)", "O(log n)", "O(1)", "O(n^2)"),
            2, "Best case happens when the element is found at the first position.");
        add(questions, "Average case time complexity of " + title + "?",
            options("O(n)", "O(log n)", "O(1)", "O(n^2)"),
            0, "On average, about n/2 comparisons are needed, which is O(n).");
        add(questions, title + " is especially suitable for:",
            options("Sorted data only", "Unsorted data", "Binary trees", "Graph traversal"),
            1, "It works on both sorted and unsorted data, but is commonly used when data is unsorted.");
        add(questions, "Which condition ends " + title + "?",
            options("When array is sorted", "When key is found or list ends", "When mid element is found", "When recursion ends"),
            1, "The scan stops when the key is found or the list finishes.");
        add(questions, "Number of comparisons in the worst case for " + title + "?",
            options("1", "n/2", "n", "log n"),
            2, "It may compare with every element once.");
        add(questions, "Which data structure is best suited for " + title + "?",
            options("Stack", "Queue", "Array / List", "Tree"),
            2, "Linear search is commonly applied on arrays and lists.");
        add(questions, title + " can be applied on:",
            options("Only arrays", "Only linked lists", "Arrays and linked lists", "Only trees"),
            2, "It works on any structure that can be traversed sequentially.");
        add(questions, "If the element is not present, " + title + " typically returns:",
            options("0", "-1", "n", "NULL"),
            1, "A common convention is to return -1 when the target is absent.");
        add(questions, "Which is faster for large sorted data?",
            options("Linear Search", "Binary Search", "Both same", "None"),
            1, "Binary search uses divide-and-conquer and is much faster on sorted data.");
        add(questions, title + " requires data to be:",
            options("Sorted", "Random", "Unsorted", "No requirement"),
            3, "It can operate without any ordering requirement.");
        add(questions, "Space complexity of " + title + "?",
            options("O(1)", "O(n)", "O(log n)", "O(n^2)"),
            0, "It only uses a constant number of extra variables.");
        add(questions, title + " compares:",
            options("Only first and last", "Adjacent elements", "Each element with the key", "Middle element"),
            2, "Each visited element is compared directly against the key.");
        add(questions, "In the best case, comparisons required by " + title + " are:",
            options("n", "n/2", "1", "log n"),
            2, "If the first element matches, only one comparison is needed.");
        add(questions, title + " is also called:",
            options("Binary search", "Sequential search", "Indexed search", "Jump search"),
            1, "Another common name is sequential search.");
        add(questions, "Which scenario gives the worst case for " + title + "?",
            options("Element at first", "Element at middle", "Element at last or absent", "Sorted array"),
            2, "The whole list may have to be scanned.");
        add(questions, title + " becomes inefficient when:",
            options("Data is small", "Data is large", "Data is unsorted", "Using a linked list"),
            1, "For large datasets, one-by-one checking becomes slow.");
        add(questions, "Average number of comparisons in " + title + " is closest to:",
            options("n", "n/2", "log n", "1"),
            1, "Average case is roughly half the list.");
        add(questions, title + " works well when:",
            options("Data is large and sorted", "Data is small", "Data is indexed", "Data is hierarchical"),
            1, "It is simple and practical for small datasets.");
        add(questions, "What is the core idea behind " + title + "?",
            options("Split the list repeatedly", "Check each item in order", "Swap neighboring values", "Build a heap"),
            1, "The algorithm scans from one item to the next until it finishes.");
        add(questions, "Which statement is true about " + title + "?",
            options("It always needs sorted input", "It uses recursion by default", "It can stop early once the key is found", "It examines only half the array"),
            2, "The algorithm stops immediately when a match is found.");
        add(questions, "If the first item matches the key in " + title + ", the case is:",
            options("Worst case", "Average case", "Best case", "Invalid case"),
            2, "Finding the key at the first position gives the best case.");
        add(questions, "What happens if the key is absent in " + title + "?",
            options("The algorithm loops forever", "The algorithm checks all elements", "The algorithm sorts the array first", "The algorithm jumps to the middle"),
            1, "It keeps scanning until the list ends.");
        add(questions, "Which one is a limitation of " + title + "?",
            options("Needs too much extra memory", "Slow on large datasets", "Works only on trees", "Cannot be implemented iteratively"),
            1, "Its sequential nature makes it slow for large inputs.");
        add(questions, "What is usually tracked during " + title + "?",
            options("A current index", "A recursion stack only", "A pivot", "A heap root"),
            0, "The current position is tracked as the scan moves through the structure.");
        add(questions, "Compared to binary search, " + title + " is:",
            options("Faster on sorted large arrays", "Simpler but slower on large sorted arrays", "Always recursive", "Unable to search lists"),
            1, "It is easier to implement but less efficient on large sorted data.");
        add(questions, "Which of these is NOT a typical use of " + title + "?",
            options("Searching a short list", "Checking an unsorted list", "Finding an item in a linked list", "Balancing a binary tree"),
            3, "Balancing a tree is unrelated to sequential search.");
        add(questions, "Why is " + title + " easy to implement?",
            options("It avoids all comparisons", "It follows a simple one-by-one scan", "It always uses recursion", "It needs a special data structure"),
            1, "Its logic is just sequential traversal and comparison.");
        add(questions, "If a list has n elements, the maximum comparisons for " + title + " are:",
            options("1", "n", "n log n", "n^2"),
            1, "At most every element is checked once.");
        add(questions, "Which input property most helps " + title + "?",
            options("Having the key near the front", "Being a complete tree", "Being a min heap", "Having duplicate pointers"),
            0, "The closer the key is to the front, the fewer comparisons are needed.");
        add(questions, "Which statement about duplicates is correct for " + title + "?",
            options("It cannot handle duplicates", "It returns the first matching occurrence it reaches", "It always returns the last occurrence", "It removes duplicates before searching"),
            1, "The first duplicate encountered during the scan is returned.");
        add(questions, title + " is generally preferred when:",
            options("The data is tiny or unsorted", "The data is huge and sorted", "A priority queue is required", "The graph is weighted"),
            0, "Its simplicity makes it useful for small or unsorted collections.");
        add(questions, "What kind of access pattern does " + title + " use?",
            options("Random middle access", "Sequential access", "Heap-based access", "Hash-based access"),
            1, "It visits items in order from start to end.");
        add(questions, "Which phrase best describes " + title + "?",
            options("Compare, move, repeat", "Split, recurse, merge", "Choose pivot, partition", "Relax edges repeatedly"),
            0, "The algorithm compares the current item, moves ahead, and repeats.");
        add(questions, "What does " + title + " NOT need?",
            options("A key to search", "A traversable collection", "Pre-sorted data", "A stopping condition"),
            2, "It works without requiring sorted input.");
        add(questions, "If the key is at the last position, the case for " + title + " is:",
            options("Best case", "Average case", "Worst case", "Impossible"),
            2, "The scan must reach the final position before succeeding.");
        add(questions, "What mainly determines the running time of " + title + "?",
            options("The number of elements inspected", "The height of a tree", "The number of recursive calls only", "The number of swaps"),
            0, "The algorithm’s cost depends on how many items are checked.");
        add(questions, "Which algorithm family does " + title + " belong to?",
            options("Searching", "Sorting", "Traversal balancing", "Dynamic programming"),
            0, "Linear search is a searching algorithm.");
        add(questions, "Why is " + title + " useful on linked lists?",
            options("Because binary search works better there", "Because sequential traversal is natural on linked lists", "Because linked lists are always sorted", "Because it uses random indexing"),
            1, "Linked lists are naturally traversed one node at a time.");
        add(questions, "Which statement is false about " + title + "?",
            options("It can be iterative", "It can search arrays", "It always runs in logarithmic time", "It uses direct key comparison"),
            2, "Its time is linear in the worst case, not logarithmic.");
        add(questions, "What is checked at each step in " + title + "?",
            options("Whether the current element equals the key", "Whether the array is balanced", "Whether the pivot is smallest", "Whether the queue is full"),
            0, "The current element is compared directly with the key.");
        add(questions, "Which output is common when " + title + " succeeds?",
            options("The index or position of the key", "A sorted list", "A tree root", "A graph path"),
            0, "A successful search usually returns the matching position.");
        add(questions, "Which metric is O(1) for " + title + "?",
            options("Worst-case time", "Average-case time", "Extra space", "Maximum comparisons"),
            2, "The algorithm uses constant extra space.");
        add(questions, "What is a practical advantage of " + title + "?",
            options("Extremely fast on massive sorted data", "Very simple to understand and code", "Needs no comparisons", "Uses divide and conquer"),
            1, "Its main strength is simplicity.");
        add(questions, "When teaching " + title + ", the first rule is usually:",
            options("Start from the first element and compare", "Start from the middle", "Sort first", "Build an index table"),
            0, "The scan begins at the front and advances sequentially.");
        add(questions, "What does the algorithm do after a mismatch in " + title + "?",
            options("Move to the next element", "Move to the middle element", "Swap two items", "Stop immediately"),
            0, "After a mismatch, the scan continues with the next element.");
        add(questions, "If n doubles, the worst-case work in " + title + " roughly:",
            options("Stays the same", "Halves", "Doubles", "Becomes logarithmic"),
            2, "Worst-case work grows linearly with n.");
        add(questions, "Which classroom example best fits " + title + "?",
            options("Checking roll numbers one by one", "Splitting a phone book in half", "Building a heap", "Balancing AVL rotations"),
            0, "Sequential checking is exactly how linear search behaves.");
        add(questions, "Which result is correct if the key is found early in " + title + "?",
            options("The scan can stop immediately", "The whole list must still be scanned", "The array must be sorted next", "The search becomes recursive"),
            0, "It stops as soon as a match is found.");
        add(questions, "Which phrase best summarizes the cost pattern of " + title + "?",
            options("Small best case, linear worst case", "Constant worst case", "Quadratic average case", "Logarithmic best case only"),
            0, "It has O(1) best case and O(n) worst case.");
        return questions;
    }

    private List<TheoryQuestion> buildGeneralTheory(String techniqueId, TechniqueContent content) {
        List<TheoryQuestion> questions = new ArrayList<>();
        String title = resolveTitle(content, prettifyTechniqueId(techniqueId));
        String definition = safe(content == null ? "" : content.definition, title + " is a DSA technique.");
        String best = safe(content == null ? "" : content.bestTime, "O(1)");
        String average = safe(content == null ? "" : content.averageTime, best);
        String worst = safe(content == null ? "" : content.worstTime, average);
        String space = safe(content == null ? "" : content.spaceComplexity, "O(1)");
        String family = resolveFamilyLabel(techniqueId);
        String useCase = resolveUseCase(techniqueId, title);
        String prerequisite = resolvePrerequisite(techniqueId);
        String stopCondition = resolveStopCondition(techniqueId);
        String coreIdea = resolveCoreIdea(techniqueId, title);

        add(questions, "What is " + title + " mainly used for?",
            options(useCase, "Balancing any tree automatically", "Encrypting input data", "Managing operating system processes"),
            0, definition);
        add(questions, title + " belongs to which DSA category?",
            options(family, "Computer networking", "Database normalization", "Compiler optimization"),
            0, title + " is primarily taught under the " + family + " category.");
        add(questions, "Which statement best describes " + title + "?",
            options(definition, "It is only a hardware concept", "It avoids all comparisons", "It can never be implemented iteratively"),
            0, definition);
        add(questions, "Worst-case time complexity of " + title + "?",
            options("O(1)", worst, "O(n^2) + O(log n)", "Not applicable"),
            1, "The content for this topic lists worst-case time as " + worst + ".");
        add(questions, "Best-case time complexity of " + title + "?",
            options(best, "O(n^2)", "O(n!)", "Undefined"),
            0, "The content for this topic lists best-case time as " + best + ".");
        add(questions, "Average-case time complexity of " + title + "?",
            options("O(1)", "O(log log n)", average, "O(n^3)"),
            2, "The content for this topic lists average-case time as " + average + ".");
        add(questions, "Space complexity of " + title + "?",
            options(space, "O(n^3)", "O(2^n)", "Not measurable"),
            0, "The content for this topic lists space complexity as " + space + ".");
        add(questions, "Which prerequisite best matches " + title + "?",
            options(prerequisite, "Must always use recursion", "Needs a GPU", "Works only on encrypted input"),
            0, prerequisite);
        add(questions, "Which stopping condition is most relevant for " + title + "?",
            options(stopCondition, "When the monitor refreshes", "When random memory is cleared", "When power is disconnected"),
            0, stopCondition);
        add(questions, "What is the core idea behind " + title + "?",
            options(coreIdea, "Avoid all traversal", "Always sort twice", "Use only constant zero values"),
            0, coreIdea);
        add(questions, "Which scenario best suits " + title + "?",
            options(resolvePreferredScenario(techniqueId), "Rendering a movie file", "Charging a battery", "Formatting a printer"),
            0, resolvePreferredScenario(techniqueId));
        add(questions, "Which statement is true about " + title + "?",
            options(resolveTrueStatement(techniqueId, title), "It never uses data comparison", "It belongs to operating systems, not DSA", "It cannot be explained step by step"),
            0, resolveTrueStatement(techniqueId, title));
        add(questions, "What should a learner focus on first in " + title + "?",
            options(resolveLearningFocus(techniqueId), "UI colors only", "Battery level", "Keyboard shortcuts"),
            0, resolveLearningFocus(techniqueId));
        add(questions, "Which output is commonly associated with " + title + "?",
            options(resolveTypicalOutput(techniqueId), "A compiled executable file", "A network packet", "A wallpaper theme"),
            0, resolveTypicalOutput(techniqueId));
        add(questions, "What usually makes " + title + " slower?",
            options(resolveSlowdownFactor(techniqueId), "Using a shorter variable name", "Changing the app theme", "Increasing screen brightness"),
            0, resolveSlowdownFactor(techniqueId));
        add(questions, "Which statement is false about " + title + "?",
            options(resolveFalseOption(techniqueId, title), title + " is a DSA topic", title + " can be reasoned about step by step", title + " has a definable time complexity"),
            0, resolveFalseOption(techniqueId, title));

        String[] pairs = {
            "definition", definition,
            "best case", best,
            "average case", average,
            "worst case", worst,
            "space", space,
            "family", family,
            "use case", useCase,
            "prerequisite", prerequisite,
            "stop condition", stopCondition,
            "core idea", coreIdea
        };
        for (int i = 0; i < pairs.length - 1; i += 2) {
            add(questions, "Which phrase matches the " + pairs[i] + " of " + title + "?",
                options(pairs[i + 1], "Completely unrelated hardware operation", "A random social-media feature", "An astronomy formula"),
                0, pairs[i].substring(0, 1).toUpperCase() + pairs[i].substring(1) + " for " + title + " is " + pairs[i + 1] + ".");
        }

        while (questions.size() < TARGET_COUNT) {
            int number = questions.size() + 1;
            add(questions, "Concept check " + number + " for " + title + ": which statement fits best?",
                options(resolveRotatingCorrect(number, title, family, coreIdea, useCase),
                    "It is unrelated to DSA fundamentals",
                    "It has no use cases at all",
                    "It cannot be analyzed for complexity"),
                0, resolveRotatingCorrect(number, title, family, coreIdea, useCase));
        }
        return questions;
    }

    private List<TheoryQuestion> dedupe(List<TheoryQuestion> source) {
        List<TheoryQuestion> result = new ArrayList<>();
        LinkedHashSet<String> seen = new LinkedHashSet<>();
        for (TheoryQuestion question : source) {
            String key = question.prompt + "|" + question.correctIndex + "|" + question.explanation;
            if (seen.add(key)) {
                result.add(question);
            }
        }
        return result;
    }

    private void add(List<TheoryQuestion> target, String prompt, List<String> options, int correctIndex, String explanation) {
        target.add(new TheoryQuestion(prompt, options, correctIndex, explanation));
    }

    private List<String> options(String a, String b, String c, String d) {
        return Arrays.asList(a, b, c, d);
    }

    private String resolveTitle(TechniqueContent content, String fallback) {
        if (content != null && content.title != null && !content.title.trim().isEmpty()) {
            return content.title;
        }
        return fallback;
    }

    private String prettifyTechniqueId(String techniqueId) {
        return techniqueId == null ? "Technique" : techniqueId.replace('_', ' ');
    }

    private String safe(String value, String fallback) {
        return value == null || value.trim().isEmpty() ? fallback : value.trim();
    }

    private String resolveFamilyLabel(String techniqueId) {
        if (VisualizationTechniqueRegistry.isSearching(techniqueId)) return "Searching";
        if (VisualizationTechniqueRegistry.isSorting(techniqueId)) return "Sorting";
        if (VisualizationTechniqueRegistry.isStructure(techniqueId)) return "Linear Data Structures";
        if (VisualizationTechniqueRegistry.isTree(techniqueId)) return "Trees";
        if (VisualizationTechniqueRegistry.isGraph(techniqueId)) return "Graphs";
        return "DSA";
    }

    private String resolveUseCase(String techniqueId, String title) {
        if (VisualizationTechniqueRegistry.isSearching(techniqueId)) return "Finding a target element or state";
        if (VisualizationTechniqueRegistry.isSorting(techniqueId)) return "Reordering data into a required sequence";
        if ("stack_basics".equals(techniqueId) || techniqueId.contains("stack")) return "Managing LIFO operations";
        if ("queue_basics".equals(techniqueId) || techniqueId.contains("queue") || "deque".equals(techniqueId)) return "Managing FIFO or double-ended processing";
        if (VisualizationTechniqueRegistry.isStructure(techniqueId)) return "Storing and updating linked or indexed data relationships";
        if (VisualizationTechniqueRegistry.isTree(techniqueId)) return "Hierarchical traversal or ordered tree updates";
        if (VisualizationTechniqueRegistry.isGraph(techniqueId)) return "Connectivity, traversal, or path reasoning";
        return "Solving a classic data-structure or algorithm problem using " + title;
    }

    private String resolvePrerequisite(String techniqueId) {
        if ("binary_search".equals(techniqueId) || "jump_search".equals(techniqueId) || "topological_sort".equals(techniqueId)) {
            return "The input must satisfy its ordering or dependency requirement";
        }
        if (VisualizationTechniqueRegistry.isSorting(techniqueId)) {
            return "A collection of comparable values is required";
        }
        if (VisualizationTechniqueRegistry.isGraph(techniqueId)) {
            return "A graph representation such as adjacency data is required";
        }
        if (VisualizationTechniqueRegistry.isTree(techniqueId)) {
            return "A valid tree or heap structure is required";
        }
        return "A traversable data structure and a clear operation rule are required";
    }

    private String resolveStopCondition(String techniqueId) {
        if (VisualizationTechniqueRegistry.isSearching(techniqueId)) {
            return "Stop when the target is found or the searchable range ends";
        }
        if (VisualizationTechniqueRegistry.isSorting(techniqueId)) {
            return "Stop when all required ordering passes are complete";
        }
        if (VisualizationTechniqueRegistry.isGraph(techniqueId)) {
            return "Stop when the frontier or pending work becomes empty";
        }
        if (VisualizationTechniqueRegistry.isTree(techniqueId)) {
            return "Stop when traversal or structural repair reaches completion";
        }
        return "Stop when the intended operation is fully completed";
    }

    private String resolveCoreIdea(String techniqueId, String title) {
        if (VisualizationTechniqueRegistry.isSearching(techniqueId)) return "Search by following the rule that narrows or advances the check";
        if (VisualizationTechniqueRegistry.isSorting(techniqueId)) return "Repeatedly compare and reposition values until order is achieved";
        if ("stack_basics".equals(techniqueId) || techniqueId.contains("stack")) return "Operate on the most recently added item first";
        if ("queue_basics".equals(techniqueId) || techniqueId.contains("queue") || "deque".equals(techniqueId)) return "Process elements from the front or the chosen end in order";
        if (VisualizationTechniqueRegistry.isStructure(techniqueId)) return "Update links or positions while preserving structure rules";
        if (VisualizationTechniqueRegistry.isTree(techniqueId)) return "Move through parent-child relationships according to a traversal or update rule";
        if (VisualizationTechniqueRegistry.isGraph(techniqueId)) return "Explore nodes and edges while maintaining frontier or path information";
        return "Understand the rule-based step sequence of " + title;
    }

    private String resolvePreferredScenario(String techniqueId) {
        if ("linear_search".equals(techniqueId)) return "Small or unsorted data collections";
        if ("binary_search".equals(techniqueId)) return "Large sorted collections";
        if (VisualizationTechniqueRegistry.isSorting(techniqueId)) return "When data needs to be arranged into order";
        if (VisualizationTechniqueRegistry.isGraph(techniqueId)) return "When relationships between connected nodes matter";
        if (VisualizationTechniqueRegistry.isTree(techniqueId)) return "When hierarchical structure matters";
        return "When this topic’s operation rule matches the problem structure";
    }

    private String resolveTrueStatement(String techniqueId, String title) {
        if (VisualizationTechniqueRegistry.isSearching(techniqueId)) return title + " uses comparisons to guide search progress";
        if (VisualizationTechniqueRegistry.isSorting(techniqueId)) return title + " is about establishing a desired order";
        if (VisualizationTechniqueRegistry.isGraph(techniqueId)) return title + " depends on node-edge relationships";
        if (VisualizationTechniqueRegistry.isTree(techniqueId)) return title + " uses parent-child relationships";
        return title + " can be studied using definitions, complexity, and operation rules";
    }

    private String resolveLearningFocus(String techniqueId) {
        if (VisualizationTechniqueRegistry.isSearching(techniqueId)) return "Targets, comparisons, and stopping conditions";
        if (VisualizationTechniqueRegistry.isSorting(techniqueId)) return "Comparisons, swaps, and ordering invariants";
        if (VisualizationTechniqueRegistry.isGraph(techniqueId)) return "Frontier, visited state, and edge meaning";
        if (VisualizationTechniqueRegistry.isTree(techniqueId)) return "Traversal order and parent-child logic";
        return "The key operation rule and why each step happens";
    }

    private String resolveTypicalOutput(String techniqueId) {
        if (VisualizationTechniqueRegistry.isSearching(techniqueId)) return "A found position, match result, or failure result";
        if (VisualizationTechniqueRegistry.isSorting(techniqueId)) return "A reordered sequence";
        if (VisualizationTechniqueRegistry.isGraph(techniqueId)) return "Traversal order, reachability, or shortest-path information";
        if (VisualizationTechniqueRegistry.isTree(techniqueId)) return "Traversal order or a valid updated tree";
        return "A valid result that follows the topic’s rule";
    }

    private String resolveSlowdownFactor(String techniqueId) {
        if (VisualizationTechniqueRegistry.isSearching(techniqueId)) return "A larger search space or weaker ordering advantage";
        if (VisualizationTechniqueRegistry.isSorting(techniqueId)) return "More values or more disorder";
        if (VisualizationTechniqueRegistry.isGraph(techniqueId)) return "More nodes, edges, or pending frontier work";
        if (VisualizationTechniqueRegistry.isTree(techniqueId)) return "More nodes or more structural work";
        return "Larger input size or more steps to complete the rule";
    }

    private String resolveFalseOption(String techniqueId, String title) {
        if (VisualizationTechniqueRegistry.isSearching(techniqueId)) return title + " always works in O(1) time";
        if (VisualizationTechniqueRegistry.isSorting(techniqueId)) return title + " never compares values";
        if (VisualizationTechniqueRegistry.isGraph(techniqueId)) return title + " ignores node relationships";
        if (VisualizationTechniqueRegistry.isTree(techniqueId)) return title + " has no parent-child logic";
        return title + " has no definable behavior";
    }

    private String resolveRotatingCorrect(int number, String title, String family, String coreIdea, String useCase) {
        switch (number % 4) {
            case 0:
                return title + " belongs to the " + family + " family";
            case 1:
                return coreIdea;
            case 2:
                return useCase;
            default:
                return "Understanding " + title + " involves both concept and step-by-step reasoning";
        }
    }
}
