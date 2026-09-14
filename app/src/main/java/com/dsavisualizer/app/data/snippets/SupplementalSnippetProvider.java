package com.dsavisualizer.app.data.snippets;

public final class SupplementalSnippetProvider {

    private SupplementalSnippetProvider() {
    }

    public static String getSupplementalSnippet(String techniqueId, String style, String language) {
        switch (techniqueId) {
            case "pattern_matching":
                return getPatternMatchingSnippet(language, style);
            case "deque":
                return getDequeSnippet(language, style);
            case "traversal_using_stacks":
                return getTraversalUsingStacksSnippet(language, style);
            case "huffman_algorithm":
                return getHuffmanSnippet(language, style);
            case "spanning_tree":
                return getSpanningTreeSnippet(language, style);
            case "shortest_paths":
                return getShortestPathsSnippet(language, style);
            case "dijkstra_algorithm":
                return getDijkstraSnippet(language, style);
            case "avl_tree":
                return getAvlSnippet(language, style);
            case "trie":
                return getTrieSnippet(language, style);
            case "union_find":
                return getUnionFindSnippet(language, style);
            case "hash_collision_handling":
                return getHashCollisionSnippet(language, style);
            case "postfix_evaluation":
                return getPostfixEvaluationSnippet(language, style);
            case "infix_to_postfix":
                return getInfixToPostfixSnippet(language, style);
            case "infix_to_prefix":
                return getInfixToPrefixSnippet(language, style);
            case "recursion_basics":
                return getRecursionBasicsSnippet(language, style);
            case "dynamic_programming_basics":
                return getDynamicProgrammingSnippet(language, style);
            default:
                return null;
        }
    }

    private static String getPostfixEvaluationSnippet(String language, String style) {
        if ("Python".equalsIgnoreCase(language)) {
            if ("Recursion".equals(style)) {
                return "def apply_op(b, op, a):\n"
                    + "    if op == '+': return b + a\n"
                    + "    if op == '-': return b - a\n"
                    + "    if op == '*': return b * a\n"
                    + "    if op == '/': return b // a\n"
                    + "    raise ValueError('unknown operator')\n\n"
                    + "def eval_postfix(tokens, index=0, stack=None):\n"
                    + "    if stack is None:\n"
                    + "        stack = []\n"
                    + "        tokens = tokens + [')']\n"
                    + "    token = tokens[index]\n"
                    + "    if token == ')':\n"
                    + "        return stack[-1]\n"
                    + "    if token.lstrip('-').isdigit():\n"
                    + "        stack.append(int(token))\n"
                    + "    else:\n"
                    + "        a = stack.pop()\n"
                    + "        b = stack.pop()\n"
                    + "        stack.append(apply_op(b, token, a))\n"
                    + "    return eval_postfix(tokens, index + 1, stack)\n\n"
                    + "print(eval_postfix(['2', '3', '4', '*', '+']))";
            }
            return "def apply_op(b, op, a):\n"
                + "    if op == '+': return b + a\n"
                + "    if op == '-': return b - a\n"
                + "    if op == '*': return b * a\n"
                + "    if op == '/': return b // a\n"
                + "    raise ValueError('unknown operator')\n\n"
                + "def evaluate_postfix(tokens):\n"
                + "    stack = []\n"
                + "    for token in tokens + [')']:\n"
                + "        if token == ')':\n"
                + "            break\n"
                + "        if token.lstrip('-').isdigit():\n"
                + "            stack.append(int(token))\n"
                + "        else:\n"
                + "            a = stack.pop()\n"
                + "            b = stack.pop()\n"
                + "            stack.append(apply_op(b, token, a))\n"
                + "    return stack[-1]\n\n"
                + "print(evaluate_postfix(['2', '3', '4', '*', '+']))";
        }
        if ("C".equalsIgnoreCase(language)) {
            return "#include <stdio.h>\n"
                + "#include <ctype.h>\n\n"
                + "int applyOp(int b, char op, int a) {\n"
                + "    if (op == '+') return b + a;\n"
                + "    if (op == '-') return b - a;\n"
                + "    if (op == '*') return b * a;\n"
                + "    if (op == '/') return b / a;\n"
                + "    return 0;\n"
                + "}\n\n"
                + "int evaluatePostfix(const char expr[]) {\n"
                + "    int stack[100], top = -1;\n"
                + "    for (int i = 0; expr[i] != ')'; i++) {\n"
                + "        char token = expr[i];\n"
                + "        if (token == ' ') continue;\n"
                + "        if (isdigit(token)) stack[++top] = token - '0';\n"
                + "        else {\n"
                + "            int a = stack[top--];\n"
                + "            int b = stack[top--];\n"
                + "            stack[++top] = applyOp(b, token, a);\n"
                + "        }\n"
                + "    }\n"
                + "    return stack[top];\n"
                + "}\n\n"
                + "int main() {\n"
                + "    printf(\"%d\\n\", evaluatePostfix(\"234*+)\"));\n"
                + "    return 0;\n"
                + "}";
        }
        return "import java.util.ArrayDeque;\n"
            + "import java.util.Deque;\n"
            + "import java.util.Scanner;\n\n"
            + "public class PostfixEvaluation {\n"
            + "    static int applyOp(int b, char op, int a) {\n"
            + "        switch (op) {\n"
            + "            case '+': return b + a;\n"
            + "            case '-': return b - a;\n"
            + "            case '*': return b * a;\n"
            + "            case '/': return b / a;\n"
            + "            default: throw new IllegalArgumentException(\"Unknown operator\");\n"
            + "        }\n"
            + "    }\n\n"
            + "    static int evaluatePostfix(String expression) {\n"
            + "        Deque<Integer> stack = new ArrayDeque<>();\n"
            + "        String withSentinel = expression + \")\";\n"
            + "        for (int i = 0; i < withSentinel.length(); i++) {\n"
            + "            char token = withSentinel.charAt(i);\n"
            + "            if (token == ')') break;\n"
            + "            if (Character.isWhitespace(token)) continue;\n"
            + "            if (Character.isDigit(token)) stack.push(token - '0');\n"
            + "            else {\n"
            + "                int a = stack.pop();\n"
            + "                int b = stack.pop();\n"
            + "                stack.push(applyOp(b, token, a));\n"
            + "            }\n"
            + "        }\n"
            + "        return stack.peek();\n"
            + "    }\n\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        System.out.print(\"Enter postfix expression using single-digit operands: \");\n"
            + "        String expression = sc.nextLine().trim();\n"
            + "        System.out.println(\"Postfix result: \" + evaluatePostfix(expression));\n"
            + "    }\n"
            + "}";
    }

    private static String getInfixToPostfixSnippet(String language, String style) {
        if ("Python".equalsIgnoreCase(language)) {
            return "def precedence(op):\n"
                + "    return 1 if op in '+-' else 2 if op in '*/' else 0\n\n"
                + "def infix_to_postfix(q):\n"
                + "    stack = ['(']\n"
                + "    q = q + ')'\n"
                + "    p = []\n"
                + "    for token in q:\n"
                + "        if token == ' ':\n"
                + "            continue\n"
                + "        if token.isalnum():\n"
                + "            p.append(token)\n"
                + "        elif token == '(':\n"
                + "            stack.append(token)\n"
                + "        elif token in '+-*/':\n"
                + "            while stack and precedence(stack[-1]) >= precedence(token):\n"
                + "                p.append(stack.pop())\n"
                + "            stack.append(token)\n"
                + "        elif token == ')':\n"
                + "            while stack and stack[-1] != '(':\n"
                + "                p.append(stack.pop())\n"
                + "            stack.pop()\n"
                + "    return ''.join(p)\n\n"
                + "print(infix_to_postfix('A+B*C'))";
        }
        if ("C".equalsIgnoreCase(language)) {
            return "#include <stdio.h>\n"
                + "#include <ctype.h>\n"
                + "#include <string.h>\n\n"
                + "int precedence(char op) {\n"
                + "    if (op == '+' || op == '-') return 1;\n"
                + "    if (op == '*' || op == '/') return 2;\n"
                + "    return 0;\n"
                + "}\n\n"
                + "void infixToPostfix(const char q[], char p[]) {\n"
                + "    char stack[100]; int top = -1, k = 0;\n"
                + "    stack[++top] = '(';\n"
                + "    for (int i = 0; q[i] != '\\0'; i++) {\n"
                + "        char token = q[i];\n"
                + "        if (token == ' ') continue;\n"
                + "        if (isalnum(token)) p[k++] = token;\n"
                + "        else if (token == '(') stack[++top] = token;\n"
                + "        else if (token == '+' || token == '-' || token == '*' || token == '/') {\n"
                + "            while (precedence(stack[top]) >= precedence(token)) p[k++] = stack[top--];\n"
                + "            stack[++top] = token;\n"
                + "        } else if (token == ')') {\n"
                + "            while (stack[top] != '(') p[k++] = stack[top--];\n"
                + "            top--;\n"
                + "        }\n"
                + "    }\n"
                + "    while (top >= 0 && stack[top] != '(') p[k++] = stack[top--];\n"
                + "    p[k] = '\\0';\n"
                + "}\n\n"
                + "int main() {\n"
                + "    char input[100], output[100];\n"
                + "    printf(\"Enter infix expression: \");\n"
                + "    scanf(\"%99s\", input);\n"
                + "    infixToPostfix(input, output);\n"
                + "    printf(\"Postfix expression: %s\\n\", output);\n"
                + "    return 0;\n"
                + "}";
        }
        return "import java.util.ArrayDeque;\n"
            + "import java.util.Deque;\n"
            + "import java.util.Scanner;\n\n"
            + "public class InfixToPostfix {\n"
            + "    static int precedence(char op) {\n"
            + "        if (op == '+' || op == '-') return 1;\n"
            + "        if (op == '*' || op == '/') return 2;\n"
            + "        return 0;\n"
            + "    }\n\n"
            + "    static String infixToPostfix(String q) {\n"
            + "        Deque<Character> stack = new ArrayDeque<>();\n"
            + "        StringBuilder p = new StringBuilder();\n"
            + "        stack.push('(');\n"
            + "        String expression = q + ')';\n"
            + "        for (char token : expression.toCharArray()) {\n"
            + "            if (Character.isWhitespace(token)) continue;\n"
            + "            if (Character.isLetterOrDigit(token)) p.append(token);\n"
            + "            else if (token == '(') stack.push(token);\n"
            + "            else if (token == '+' || token == '-' || token == '*' || token == '/') {\n"
            + "                while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(token)) p.append(stack.pop());\n"
            + "                stack.push(token);\n"
            + "            } else if (token == ')') {\n"
            + "                while (!stack.isEmpty() && stack.peek() != '(') p.append(stack.pop());\n"
            + "                if (!stack.isEmpty()) stack.pop();\n"
            + "            }\n"
            + "        }\n"
            + "        return p.toString();\n"
            + "    }\n\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        System.out.print(\"Enter infix expression: \");\n"
            + "        String expression = sc.nextLine().trim();\n"
            + "        System.out.println(\"Postfix expression: \" + infixToPostfix(expression));\n"
            + "    }\n"
            + "}";
    }

    private static String getInfixToPrefixSnippet(String language, String style) {
        if ("Python".equalsIgnoreCase(language)) {
            return "def precedence(op):\n"
                + "    return 1 if op in '+-' else 2 if op in '*/' else 0\n\n"
                + "def swap_parentheses(q):\n"
                + "    return ''.join(')' if ch == '(' else '(' if ch == ')' else ch for ch in q[::-1])\n\n"
                + "def infix_to_prefix(q):\n"
                + "    stack = ['(']\n"
                + "    modified = swap_parentheses(q) + ')'\n"
                + "    p = []\n"
                + "    for token in modified:\n"
                + "        if token == ' ': continue\n"
                + "        if token.isalnum(): p.append(token)\n"
                + "        elif token == '(': stack.append(token)\n"
                + "        elif token in '+-*/':\n"
                + "            while stack and precedence(stack[-1]) > precedence(token):\n"
                + "                p.append(stack.pop())\n"
                + "            stack.append(token)\n"
                + "        elif token == ')':\n"
                + "            while stack and stack[-1] != '(':\n"
                + "                p.append(stack.pop())\n"
                + "            stack.pop()\n"
                + "    return ''.join(p[::-1])\n\n"
                + "print(infix_to_prefix('A+B*C'))";
        }
        if ("C".equalsIgnoreCase(language)) {
            return "#include <stdio.h>\n"
                + "#include <ctype.h>\n"
                + "#include <string.h>\n\n"
                + "int precedence(char op) {\n"
                + "    if (op == '+' || op == '-') return 1;\n"
                + "    if (op == '*' || op == '/') return 2;\n"
                + "    return 0;\n"
                + "}\n\n"
                + "void reverse(char s[]) {\n"
                + "    int n = strlen(s);\n"
                + "    for (int i = 0; i < n / 2; i++) {\n"
                + "        char t = s[i]; s[i] = s[n - i - 1]; s[n - i - 1] = t;\n"
                + "    }\n"
                + "}\n\n"
                + "void infixToPrefix(char q[], char p[]) {\n"
                + "    reverse(q);\n"
                + "    for (int i = 0; q[i]; i++) if (q[i] == '(') q[i] = ')'; else if (q[i] == ')') q[i] = '(';\n"
                + "    char stack[100]; int top = -1, k = 0;\n"
                + "    stack[++top] = '(';\n"
                + "    strcat(q, \")\");\n"
                + "    for (int i = 0; q[i]; i++) {\n"
                + "        char token = q[i];\n"
                + "        if (isalnum(token)) p[k++] = token;\n"
                + "        else if (token == '(') stack[++top] = token;\n"
                + "        else if (token == '+' || token == '-' || token == '*' || token == '/') {\n"
                + "            while (precedence(stack[top]) > precedence(token)) p[k++] = stack[top--];\n"
                + "            stack[++top] = token;\n"
                + "        } else if (token == ')') {\n"
                + "            while (stack[top] != '(') p[k++] = stack[top--];\n"
                + "            top--;\n"
                + "        }\n"
                + "    }\n"
                + "    p[k] = '\\0';\n"
                + "    reverse(p);\n"
                + "}\n\n"
                + "int main() {\n"
                + "    char q[100] = \"A+B*C\", p[100];\n"
                + "    infixToPrefix(q, p);\n"
                + "    printf(\"%s\\n\", p);\n"
                + "    return 0;\n"
                + "}";
        }
        return "import java.util.ArrayDeque;\n"
            + "import java.util.Deque;\n"
            + "import java.util.Scanner;\n\n"
            + "public class InfixToPrefix {\n"
            + "    static int precedence(char op) {\n"
            + "        if (op == '+' || op == '-') return 1;\n"
            + "        if (op == '*' || op == '/') return 2;\n"
            + "        return 0;\n"
            + "    }\n\n"
            + "    static String reverseAndSwap(String q) {\n"
            + "        StringBuilder out = new StringBuilder();\n"
            + "        for (int i = q.length() - 1; i >= 0; i--) {\n"
            + "            char ch = q.charAt(i);\n"
            + "            out.append(ch == '(' ? ')' : ch == ')' ? '(' : ch);\n"
            + "        }\n"
            + "        return out.toString();\n"
            + "    }\n\n"
            + "    static String infixToPrefix(String q) {\n"
            + "        Deque<Character> stack = new ArrayDeque<>();\n"
            + "        StringBuilder p = new StringBuilder();\n"
            + "        stack.push('(');\n"
            + "        for (char token : (reverseAndSwap(q) + ')').toCharArray()) {\n"
            + "            if (Character.isLetterOrDigit(token)) p.append(token);\n"
            + "            else if (token == '(') stack.push(token);\n"
            + "            else if (token == '+' || token == '-' || token == '*' || token == '/') {\n"
            + "                while (!stack.isEmpty() && precedence(stack.peek()) > precedence(token)) p.append(stack.pop());\n"
            + "                stack.push(token);\n"
            + "            } else if (token == ')') {\n"
            + "                while (!stack.isEmpty() && stack.peek() != '(') p.append(stack.pop());\n"
            + "                if (!stack.isEmpty()) stack.pop();\n"
            + "            }\n"
            + "        }\n"
            + "        return p.reverse().toString();\n"
            + "    }\n\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        System.out.print(\"Enter infix expression: \");\n"
            + "        String expression = sc.nextLine().trim();\n"
            + "        System.out.println(\"Prefix expression: \" + infixToPrefix(expression));\n"
            + "    }\n"
            + "}";
    }

    private static String getPatternMatchingSnippet(String language, String style) {
        if ("C".equalsIgnoreCase(language)) return String.join("\n", "#include <stdio.h>", "#include <string.h>", "int matchAt(char text[], char pattern[], int start) { int j = 0; while (pattern[j] != '\\0') { if (text[start + j] != pattern[j]) return 0; j++; } return 1; }", "int findPattern(char text[], char pattern[], int index) { if (text[index] == '\\0') return -1; if (matchAt(text, pattern, index)) return index; return " + ("Recursion".equals(style) ? "findPattern(text, pattern, index + 1);" : "-1;") + " }", "int main() { char text[100], pattern[100]; scanf(\"%99s\", text); scanf(\"%99s\", pattern); int loc = " + ("Recursion".equals(style) ? "findPattern(text, pattern, 0)" : "({ int ans = -1; for (int i = 0; text[i] != '\\0'; i++) { if (matchAt(text, pattern, i)) { ans = i; break; } } ans; })") + "; printf(\"%d\\n\", loc); return 0; }");
        if ("Python".equalsIgnoreCase(language)) return String.join("\n", "text = input().strip()", "pattern = input().strip()", "def match_at(start):", "    j = 0", "    while j < len(pattern):", "        if start + j >= len(text) or text[start + j] != pattern[j]:", "            return False", "        j += 1", "    return True", "def search_rec(i):", "    if i > len(text) - len(pattern):", "        return -1", "    if match_at(i):", "        return i", "    return search_rec(i + 1)", "loc = search_rec(0) if \"" + style + "\" == \"Recursion\" else next((i for i in range(len(text) - len(pattern) + 1) if match_at(i)), -1)", "print(loc)");
        return String.join("\n",
            "import java.util.*;",
            "public class Main {",
            "    static boolean matchAt(String text, String pattern, int start) {",
            "        for (int j = 0; j < pattern.length(); j++) {",
            "            if (start + j >= text.length() || text.charAt(start + j) != pattern.charAt(j)) return false;",
            "        }",
            "        return true;",
            "    }",
            "",
            "    static int searchRec(String text, String pattern, int index) {",
            "        if (index > text.length() - pattern.length()) return -1;",
            "        if (matchAt(text, pattern, index)) return index;",
            "        return searchRec(text, pattern, index + 1);",
            "    }",
            "",
            "    public static void main(String[] args) {",
            "        Scanner sc = new Scanner(System.in);",
            "        System.out.print(\"Enter text: \");",
            "        String text = sc.nextLine().trim();",
            "        System.out.print(\"Enter pattern: \");",
            "        String pattern = sc.nextLine().trim();",
            "        int loc = -1;",
                         "        " + ("Recursion".equals(style) ? "loc = searchRec(text, pattern, 0);" : "for (int i = 0; i <= text.length() - pattern.length(); i++) { if (matchAt(text, pattern, i)) { loc = i; break; } }"),
            "        System.out.println(\"Pattern location: \" + loc);",
            "    }",
            "}");
    }

    private static String getDequeSnippet(String language, String style) {
        if ("C".equalsIgnoreCase(language)) return String.join("\n", "#include <stdio.h>", "int main() { int n, item; scanf(\"%d\", &n); int dq[100], front = 50, rear = 49; for (int i = 0; i < n; i++) { scanf(\"%d\", &item); dq[++rear] = item; } scanf(\"%d\", &item); dq[--front] = item; dq[++rear] = item + 1; printf(\"%d %d\\n\", dq[front], dq[rear]); return 0; }");
        if ("Python".equalsIgnoreCase(language)) return String.join("\n", "from collections import deque", "arr = deque(map(int, input().split()))", "x = int(input())", "arr.appendleft(x)", "arr.append(x + 1)", "print(arr.popleft(), arr.pop())");
        return String.join("\n", "import java.util.*;", "public class Main {", "    public static void main(String[] args) { Scanner sc = new Scanner(System.in); ArrayDeque<Integer> dq = new ArrayDeque<>(); System.out.print(\"Enter number of initial deque elements: \"); int n = sc.nextInt(); System.out.println(\"Enter deque elements:\"); for (int i = 0; i < n; i++) dq.addLast(sc.nextInt()); System.out.print(\"Enter item to insert at front: \"); int x = sc.nextInt(); dq.addFirst(x); dq.addLast(x + 1); System.out.println(\"Deleted front and rear items: \" + dq.removeFirst() + \" \" + dq.removeLast()); }", "}");
    }

    private static String getTraversalUsingStacksSnippet(String language, String style) {
        if ("C".equalsIgnoreCase(language)) return String.join("\n", "#include <stdio.h>", "int main() { int tree[7] = {50,30,70,20,40,60,80}; int stack[20], top = -1; stack[++top] = 0; while (top >= 0) { int idx = stack[top--]; printf(\"%d \", tree[idx]); if (2 * idx + 2 < 7) stack[++top] = 2 * idx + 2; if (2 * idx + 1 < 7) stack[++top] = 2 * idx + 1; } return 0; }");
        if ("Python".equalsIgnoreCase(language)) return String.join("\n", "tree = [50, 30, 70, 20, 40, 60, 80]", "stack = [0]", "while stack:", "    idx = stack.pop()", "    print(tree[idx], end=' ')", "    if 2 * idx + 2 < len(tree):", "        stack.append(2 * idx + 2)", "    if 2 * idx + 1 < len(tree):", "        stack.append(2 * idx + 1)");
        return String.join("\n",
            "import java.util.*;",
            "public class Main {",
            "    public static void main(String[] args) {",
            "        Scanner sc = new Scanner(System.in);",
            "        System.out.print(\"Enter number of nodes in array-based tree: \");",
            "        int n = sc.nextInt();",
            "        int[] tree = new int[n];",
            "        System.out.println(\"Enter node values in array representation:\");",
            "        for (int i = 0; i < n; i++) tree[i] = sc.nextInt();",
            "        Stack<Integer> stack = new Stack<>();",
            "        stack.push(0);",
            "        System.out.print(\"Traversal using stack: \");",
            "        while (!stack.isEmpty()) {",
            "            int idx = stack.pop();",
            "            System.out.print(tree[idx] + \" \");",
            "            if (2 * idx + 2 < tree.length) stack.push(2 * idx + 2);",
            "            if (2 * idx + 1 < tree.length) stack.push(2 * idx + 1);",
            "        }",
            "    }",
            "}");
    }

    private static String getHuffmanSnippet(String language, String style) {
        if ("C".equalsIgnoreCase(language)) return String.join("\n", "#include <stdio.h>", "int main() { int freq[6] = {5,9,12,13,16,45}; int n = 6; while (n > 1) { int a = 0, b = 1; if (freq[b] < freq[a]) { int t = a; a = b; b = t; } for (int i = 2; i < n; i++) { if (freq[i] < freq[a]) { b = a; a = i; } else if (freq[i] < freq[b]) { b = i; } } int sum = freq[a] + freq[b]; freq[a] = sum; freq[b] = freq[n - 1]; n--; } printf(\"%d\\n\", freq[0]); return 0; }");
        if ("Python".equalsIgnoreCase(language)) return String.join("\n", "import heapq", "freq = list(map(int, input().split()))", "heapq.heapify(freq)", "while len(freq) > 1:", "    a = heapq.heappop(freq)", "    b = heapq.heappop(freq)", "    heapq.heappush(freq, a + b)", "print(freq[0])");
        return String.join("\n", "import java.util.*;", "public class Main {", "    public static void main(String[] args) { Scanner sc = new Scanner(System.in); PriorityQueue<Integer> pq = new PriorityQueue<>(); System.out.print(\"Enter number of frequencies: \"); int n = sc.nextInt(); System.out.println(\"Enter frequencies:\"); for (int i = 0; i < n; i++) pq.add(sc.nextInt()); while (pq.size() > 1) { int a = pq.remove(); int b = pq.remove(); pq.add(a + b); } System.out.println(\"Total Huffman cost: \" + pq.remove()); }", "}");
    }

    private static String getSpanningTreeSnippet(String language, String style) {
        if ("C".equalsIgnoreCase(language)) return String.join("\n", "#include <stdio.h>", "int main() { int n = 5; int g[5][5] = {{0,1,1,0,0},{1,0,1,1,0},{1,1,0,0,1},{0,1,0,0,1},{0,0,1,1,0}}; int vis[5] = {1,0,0,0,0}, count = 1; while (count < n) { for (int i = 0; i < n; i++) if (vis[i]) for (int j = 0; j < n; j++) if (!vis[j] && g[i][j]) { printf(\"%d-%d\\n\", i, j); vis[j] = 1; count++; i = n; break; } } return 0; }");
        if ("Python".equalsIgnoreCase(language)) return String.join("\n", "g = [[0,1,1,0,0],[1,0,1,1,0],[1,1,0,0,1],[0,1,0,0,1],[0,0,1,1,0]]", "visited = {0}", "while len(visited) < len(g):", "    for i in list(visited):", "        for j, edge in enumerate(g[i]):", "            if edge and j not in visited:", "                print(i, j)", "                visited.add(j)", "                break", "        else:", "            continue", "        break");
        return String.join("\n",
            "import java.util.*;",
            "public class Main {",
            "    public static void main(String[] args) {",
            "        Scanner sc = new Scanner(System.in);",
            "        System.out.print(\"Enter number of vertices: \");",
            "        int n = sc.nextInt();",
            "        int[][] g = new int[n][n];",
            "        System.out.println(\"Enter adjacency matrix:\");",
            "        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) g[i][j] = sc.nextInt();",
            "        boolean[] vis = new boolean[n];",
            "        vis[0] = true;",
            "        int count = 1;",
            "        System.out.println(\"Spanning tree edges:\");",
            "        while (count < n) {",
            "            boolean edgeAdded = false;",
            "            for (int i = 0; i < n && !edgeAdded; i++) if (vis[i]) {",
            "                for (int j = 0; j < n; j++) if (!vis[j] && g[i][j] == 1) {",
            "                    System.out.println(i + \"-\" + j);",
            "                    vis[j] = true;",
            "                    count++;",
            "                    edgeAdded = true;",
            "                    break;",
            "                }",
            "            }",
            "            if (!edgeAdded) break;",
            "        }",
            "    }",
            "}");
    }

    private static String getShortestPathsSnippet(String language, String style) {
        if ("C".equalsIgnoreCase(language)) return String.join("\n", "#include <stdio.h>", "#define INF 9999", "int main() { int d[4][4] = {{0,5,INF,10},{INF,0,3,INF},{INF,INF,0,1},{INF,INF,INF,0}}; for (int k = 0; k < 4; k++) for (int i = 0; i < 4; i++) for (int j = 0; j < 4; j++) if (d[i][k] + d[k][j] < d[i][j]) d[i][j] = d[i][k] + d[k][j]; for (int i = 0; i < 4; i++) { for (int j = 0; j < 4; j++) printf(\"%d \", d[i][j]); printf(\"\\n\"); } return 0; }");
        if ("Python".equalsIgnoreCase(language)) return String.join("\n", "INF = 9999", "d = [[0,5,INF,10],[INF,0,3,INF],[INF,INF,0,1],[INF,INF,INF,0]]", "for k in range(4):", "    for i in range(4):", "        for j in range(4):", "            d[i][j] = min(d[i][j], d[i][k] + d[k][j])", "for row in d:", "    print(*row)");
        return String.join("\n",
            "import java.util.*;",
            "public class Main {",
            "    public static void main(String[] args) {",
            "        Scanner sc = new Scanner(System.in);",
            "        final int INF = 9999;",
            "        System.out.print(\"Enter number of vertices: \");",
            "        int n = sc.nextInt();",
            "        int[][] d = new int[n][n];",
            "        System.out.println(\"Enter weighted adjacency matrix (use 9999 for INF):\");",
            "        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) d[i][j] = sc.nextInt();",
            "        for (int k = 0; k < n; k++)",
            "            for (int i = 0; i < n; i++)",
            "                for (int j = 0; j < n; j++)",
            "                    d[i][j] = Math.min(d[i][j], d[i][k] + d[k][j]);",
            "        System.out.println(\"All-pairs shortest distances:\");",
            "        for (int i = 0; i < n; i++) {",
            "            for (int j = 0; j < n; j++) System.out.print(d[i][j] + \" \");",
            "            System.out.println();",
            "        }",
            "    }",
            "}");
    }

    private static String getDijkstraSnippet(String language, String style) {
        if ("C".equalsIgnoreCase(language)) return String.join("\n", "#include <stdio.h>", "#define INF 9999", "int main() { int g[5][5] = {{0,4,1,0,0},{4,0,2,5,0},{1,2,0,8,10},{0,5,8,0,2},{0,0,10,2,0}}, dist[5] = {0,INF,INF,INF,INF}, vis[5] = {0}; for (int c = 0; c < 5; c++) { int u = -1, best = INF; for (int i = 0; i < 5; i++) if (!vis[i] && dist[i] < best) { best = dist[i]; u = i; } if (u == -1) break; vis[u] = 1; for (int v = 0; v < 5; v++) if (g[u][v] && dist[u] + g[u][v] < dist[v]) dist[v] = dist[u] + g[u][v]; } for (int i = 0; i < 5; i++) printf(\"%d \", dist[i]); return 0; }");
        if ("Python".equalsIgnoreCase(language)) return String.join("\n", "import heapq", "g = {0:[(1,4),(2,1)],1:[(0,4),(2,2),(3,5)],2:[(0,1),(1,2),(3,8),(4,10)],3:[(1,5),(2,8),(4,2)],4:[(2,10),(3,2)]}", "dist = {i: 10**9 for i in g}", "dist[0] = 0", "pq = [(0, 0)]", "while pq:", "    d, u = heapq.heappop(pq)", "    if d != dist[u]:", "        continue", "    for v, w in g[u]:", "        nd = d + w", "        if nd < dist[v]:", "            dist[v] = nd", "            heapq.heappush(pq, (nd, v))", "print(*(dist[i] for i in sorted(dist)))");
        return String.join("\n",
            "import java.util.*;",
            "public class Main {",
            "    public static void main(String[] args) {",
            "        Scanner sc = new Scanner(System.in);",
            "        final int INF = 9999;",
            "        System.out.print(\"Enter number of vertices: \");",
            "        int n = sc.nextInt();",
            "        int[][] g = new int[n][n];",
            "        System.out.println(\"Enter weighted adjacency matrix (use 0 where there is no edge):\");",
            "        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) g[i][j] = sc.nextInt();",
            "        System.out.print(\"Enter source vertex: \");",
            "        int source = sc.nextInt();",
            "        int[] dist = new int[n];",
            "        boolean[] vis = new boolean[n];",
            "        Arrays.fill(dist, INF);",
            "        dist[source] = 0;",
            "        for (int c = 0; c < n; c++) {",
            "            int u = -1, best = INF;",
            "            for (int i = 0; i < n; i++) if (!vis[i] && dist[i] < best) { best = dist[i]; u = i; }",
            "            if (u == -1) break;",
            "            vis[u] = true;",
            "            for (int v = 0; v < n; v++) if (g[u][v] > 0 && dist[u] + g[u][v] < dist[v]) dist[v] = dist[u] + g[u][v];",
            "        }",
            "        System.out.print(\"Shortest distances from source \" + source + \": \");",
            "        for (int value : dist) System.out.print(value + \" \");",
            "    }",
            "}");
    }

    private static String getAvlSnippet(String language, String style) {
        if ("C".equalsIgnoreCase(language)) return String.join("\n", "#include <stdio.h>", "#include <stdlib.h>", "struct Node { int key, height; struct Node *left, *right; }; int h(struct Node* n){ return n ? n->height : 0; } int max(int a,int b){ return a>b?a:b; } struct Node* newNode(int key){ struct Node* node = malloc(sizeof(struct Node)); node->key=key; node->height=1; node->left=node->right=NULL; return node; } struct Node* rightRotate(struct Node* y){ struct Node* x=y->left; struct Node* t=x->right; x->right=y; y->left=t; y->height=max(h(y->left),h(y->right))+1; x->height=max(h(x->left),h(x->right))+1; return x; } struct Node* leftRotate(struct Node* x){ struct Node* y=x->right; struct Node* t=y->left; y->left=x; x->right=t; x->height=max(h(x->left),h(x->right))+1; y->height=max(h(y->left),h(y->right))+1; return y; } struct Node* insert(struct Node* node,int key){ if(!node) return newNode(key); if(key<node->key) node->left=insert(node->left,key); else node->right=insert(node->right,key); node->height=max(h(node->left),h(node->right))+1; int bal=h(node->left)-h(node->right); if(bal>1 && key<node->left->key) return rightRotate(node); if(bal<-1 && key>node->right->key) return leftRotate(node); if(bal>1 && key>node->left->key){ node->left=leftRotate(node->left); return rightRotate(node);} if(bal<-1 && key<node->right->key){ node->right=rightRotate(node->right); return leftRotate(node);} return node; } void preorder(struct Node* root){ if(!root) return; printf(\"%d \", root->key); preorder(root->left); preorder(root->right);} int main(){ int arr[5]={30,20,40,10,25}; struct Node* root=NULL; for(int i=0;i<5;i++) root=insert(root,arr[i]); preorder(root); return 0; }");
        if ("Python".equalsIgnoreCase(language)) return String.join("\n", "class Node:", "    def __init__(self, key):", "        self.key = key", "        self.left = None", "        self.right = None", "        self.height = 1", "def h(node): return node.height if node else 0", "def right_rotate(y):", "    x = y.left; t = x.right; x.right = y; y.left = t; y.height = max(h(y.left), h(y.right)) + 1; x.height = max(h(x.left), h(x.right)) + 1; return x", "def left_rotate(x):", "    y = x.right; t = y.left; y.left = x; x.right = t; x.height = max(h(x.left), h(x.right)) + 1; y.height = max(h(y.left), h(y.right)) + 1; return y", "def insert(node, key):", "    if not node: return Node(key)", "    if key < node.key: node.left = insert(node.left, key)", "    else: node.right = insert(node.right, key)", "    node.height = max(h(node.left), h(node.right)) + 1", "    bal = h(node.left) - h(node.right)", "    if bal > 1 and key < node.left.key: return right_rotate(node)", "    if bal < -1 and key > node.right.key: return left_rotate(node)", "    if bal > 1 and key > node.left.key: node.left = left_rotate(node.left); return right_rotate(node)", "    if bal < -1 and key < node.right.key: node.right = right_rotate(node.right); return left_rotate(node)", "    return node", "root = None", "for value in [30, 20, 40, 10, 25]:", "    root = insert(root, value)", "print(root.key)");
        return String.join("\n",
            "import java.util.*;",
            "public class Main {",
            "    static class Node { int key, height = 1; Node left, right; Node(int key) { this.key = key; } }",
            "    static int h(Node n) { return n == null ? 0 : n.height; }",
            "    static int max(int a, int b) { return a > b ? a : b; }",
            "    static Node rightRotate(Node y) { Node x = y.left, t = x.right; x.right = y; y.left = t; y.height = max(h(y.left), h(y.right)) + 1; x.height = max(h(x.left), h(x.right)) + 1; return x; }",
            "    static Node leftRotate(Node x) { Node y = x.right, t = y.left; y.left = x; x.right = t; x.height = max(h(x.left), h(x.right)) + 1; y.height = max(h(y.left), h(y.right)) + 1; return y; }",
            "    static Node insert(Node node, int key) {",
            "        if (node == null) return new Node(key);",
            "        if (key < node.key) node.left = insert(node.left, key); else node.right = insert(node.right, key);",
            "        node.height = max(h(node.left), h(node.right)) + 1;",
            "        int bal = h(node.left) - h(node.right);",
            "        if (bal > 1 && key < node.left.key) return rightRotate(node);",
            "        if (bal < -1 && key > node.right.key) return leftRotate(node);",
            "        if (bal > 1 && key > node.left.key) { node.left = leftRotate(node.left); return rightRotate(node); }",
            "        if (bal < -1 && key < node.right.key) { node.right = rightRotate(node.right); return leftRotate(node); }",
            "        return node;",
            "    }",
            "    static void preorder(Node root) { if (root == null) return; System.out.print(root.key + \" \"); preorder(root.left); preorder(root.right); }",
            "    public static void main(String[] args) {",
            "        Scanner sc = new Scanner(System.in);",
            "        Node root = null;",
            "        System.out.print(\"Enter number of AVL nodes: \");",
            "        int n = sc.nextInt();",
            "        System.out.println(\"Enter node values:\");",
            "        for (int i = 0; i < n; i++) root = insert(root, sc.nextInt());",
            "        System.out.print(\"AVL preorder traversal: \");",
            "        preorder(root);",
            "        System.out.println();",
            "        System.out.println(\"AVL root: \" + (root != null ? root.key : \"None\"));",
            "    }",
            "}");
    }

    private static String getTrieSnippet(String language, String style) {
        if ("C".equalsIgnoreCase(language)) return String.join("\n", "#include <stdio.h>", "#include <stdlib.h>", "#define SIZE 26", "struct Node { struct Node* child[SIZE]; int end; }; struct Node* newNode(){ struct Node* n = calloc(1,sizeof(struct Node)); return n; } void insert(struct Node* root, char word[]){ int i = 0; while(word[i]){ int idx = word[i]-'a'; if(!root->child[idx]) root->child[idx] = newNode(); root = root->child[idx]; i++; } root->end = 1; } int main(){ struct Node* root = newNode(); char word[] = \"cat\"; insert(root, word); printf(\"%d\\n\", root->child[2] != NULL); return 0; }");
        if ("Python".equalsIgnoreCase(language)) return String.join("\n", "class Node:", "    def __init__(self):", "        self.child = {}", "        self.end = False", "def insert(root, word):", "    cur = root", "    for ch in word:", "        cur = cur.child.setdefault(ch, Node())", "    cur.end = True", "root = Node()", "insert(root, 'cat')", "print('c' in root.child)");
        return String.join("\n",
            "import java.util.*;",
            "public class Main {",
            "    static class Node { Map<Character, Node> child = new HashMap<>(); boolean end; }",
            "    static void insert(Node root, String word) { Node cur = root; for (char ch : word.toCharArray()) cur = cur.child.computeIfAbsent(ch, c -> new Node()); cur.end = true; }",
            "    static boolean search(Node root, String word) { Node cur = root; for (char ch : word.toCharArray()) { cur = cur.child.get(ch); if (cur == null) return false; } return cur.end; }",
            "    public static void main(String[] args) {",
            "        Scanner sc = new Scanner(System.in);",
            "        Node root = new Node();",
            "        System.out.print(\"Enter number of words to insert: \");",
            "        int n = sc.nextInt();",
            "        System.out.println(\"Enter lowercase words:\");",
            "        for (int i = 0; i < n; i++) insert(root, sc.next());",
            "        System.out.print(\"Enter word to search: \");",
            "        String query = sc.next();",
            "        System.out.println(\"Word found: \" + search(root, query));",
            "    }",
            "}");
    }

    private static String getUnionFindSnippet(String language, String style) {
        if ("C".equalsIgnoreCase(language)) return String.join("\n", "#include <stdio.h>", "int parent[6], rankv[6]; int findSet(int x){ if(parent[x]==x) return x; parent[x]=findSet(parent[x]); return parent[x]; } void unionSet(int a,int b){ int pa=findSet(a), pb=findSet(b); if(pa==pb) return; if(rankv[pa]<rankv[pb]) parent[pa]=pb; else if(rankv[pb]<rankv[pa]) parent[pb]=pa; else { parent[pb]=pa; rankv[pa]++; } } int main(){ for(int i=0;i<6;i++) parent[i]=i; unionSet(0,1); unionSet(1,2); printf(\"%d\\n\", findSet(2)); return 0; }");
        if ("Python".equalsIgnoreCase(language)) return String.join("\n", "parent = list(range(6))", "rankv = [0] * 6", "def find(x):", "    if parent[x] != x:", "        parent[x] = find(parent[x])", "    return parent[x]", "def union(a, b):", "    pa, pb = find(a), find(b)", "    if pa == pb:", "        return", "    if rankv[pa] < rankv[pb]:", "        parent[pa] = pb", "    elif rankv[pb] < rankv[pa]:", "        parent[pb] = pa", "    else:", "        parent[pb] = pa", "        rankv[pa] += 1", "union(0, 1)", "union(1, 2)", "print(find(2))");
        return String.join("\n",
            "import java.util.*;",
            "public class Main {",
            "    static int[] parent, rankv;",
            "    static int find(int x) { if (parent[x] != x) parent[x] = find(parent[x]); return parent[x]; }",
            "    static void union(int a, int b) { int pa = find(a), pb = find(b); if (pa == pb) return; if (rankv[pa] < rankv[pb]) parent[pa] = pb; else if (rankv[pb] < rankv[pa]) parent[pb] = pa; else { parent[pb] = pa; rankv[pa]++; } }",
            "    public static void main(String[] args) {",
            "        Scanner sc = new Scanner(System.in);",
            "        System.out.print(\"Enter number of elements: \");",
            "        int n = sc.nextInt();",
            "        parent = new int[n];",
            "        rankv = new int[n];",
            "        for (int i = 0; i < n; i++) parent[i] = i;",
            "        System.out.print(\"Enter number of union operations: \");",
            "        int unions = sc.nextInt();",
            "        System.out.println(\"Enter union pairs:\");",
            "        for (int i = 0; i < unions; i++) union(sc.nextInt(), sc.nextInt());",
            "        System.out.print(\"Enter item to find representative: \");",
            "        int query = sc.nextInt();",
            "        System.out.println(\"Set representative: \" + find(query));",
            "    }",
            "}");
    }

    private static String getHashCollisionSnippet(String language, String style) {
        if ("C".equalsIgnoreCase(language)) return String.join("\n", "#include <stdio.h>", "int main(){ int table[11]; for(int i=0;i<11;i++) table[i]=-1; int values[5]={21,32,43,54,65}; for(int k=0;k<5;k++){ int idx=values[k]%11; while(table[idx]!=-1) idx=(idx+1)%11; table[idx]=values[k]; } for(int i=0;i<11;i++) printf(\"%d \", table[i]); return 0; }");
        if ("Python".equalsIgnoreCase(language)) return String.join("\n", "table = [-1] * 11", "for value in [21, 32, 43, 54, 65]:", "    idx = value % 11", "    while table[idx] != -1:", "        idx = (idx + 1) % 11", "    table[idx] = value", "print(*table)");
        return String.join("\n",
            "import java.util.*;",
            "public class Main {",
            "    public static void main(String[] args) {",
            "        Scanner sc = new Scanner(System.in);",
            "        System.out.print(\"Enter hash table size: \");",
            "        int size = sc.nextInt();",
            "        int[] table = new int[size];",
            "        Arrays.fill(table, -1);",
            "        System.out.print(\"Enter number of values: \");",
            "        int n = sc.nextInt();",
            "        System.out.println(\"Enter values to insert:\");",
            "        for (int i = 0; i < n; i++) {",
            "            int value = sc.nextInt();",
            "            int idx = value % size;",
            "            while (table[idx] != -1) idx = (idx + 1) % size;",
            "            table[idx] = value;",
            "        }",
            "        System.out.print(\"Hash table after insertion: \");",
            "        for (int value : table) System.out.print(value + \" \");",
            "    }",
            "}");
    }

    private static String getRecursionBasicsSnippet(String language, String style) {
        if ("C".equalsIgnoreCase(language)) return String.join("\n", "#include <stdio.h>", "int fact(int n){ if(n<=1) return 1; return n*fact(n-1); }", "int main(){ int n; scanf(\"%d\", &n); printf(\"%d\\n\", fact(n)); return 0; }");
        if ("Python".equalsIgnoreCase(language)) return String.join("\n", "def fact(n):", "    if n <= 1:", "        return 1", "    return n * fact(n - 1)", "print(fact(int(input())))");
        return String.join("\n", "import java.util.*;", "public class Main {", "    static int fact(int n) { if (n <= 1) return 1; return n * fact(n - 1); }", "    public static void main(String[] args) { Scanner sc = new Scanner(System.in); System.out.print(\"Enter n: \"); System.out.println(\"Factorial: \" + fact(sc.nextInt())); }", "}");
    }

    private static String getDynamicProgrammingSnippet(String language, String style) {
        if ("C".equalsIgnoreCase(language)) return String.join("\n", "#include <stdio.h>", "int main(){ int n; scanf(\"%d\", &n); int dp[100]; dp[0]=0; dp[1]=1; for(int i=2;i<=n;i++) dp[i]=dp[i-1]+dp[i-2]; printf(\"%d\\n\", dp[n]); return 0; }");
        if ("Python".equalsIgnoreCase(language)) return String.join("\n", "n = int(input())", "dp = [0] * (max(2, n + 1))", "dp[1] = 1", "for i in range(2, n + 1):", "    dp[i] = dp[i - 1] + dp[i - 2]", "print(dp[n])");
        return String.join("\n", "import java.util.*;", "public class Main {", "    public static void main(String[] args) { Scanner sc = new Scanner(System.in); System.out.print(\"Enter n: \"); int n = sc.nextInt(); int[] dp = new int[Math.max(2, n + 1)]; dp[1] = 1; for (int i = 2; i <= n; i++) dp[i] = dp[i - 1] + dp[i - 2]; System.out.println(\"Computed value: \" + dp[n]); }", "}");
    }

}
