package com.dsavisualizer.app.data;

/**
 * Returns code snippets for the Code tab in multiple languages.
 * The provider keeps the text-generation logic separate from repository data.
 */
public final class CodeSnippetProvider {

    private CodeSnippetProvider() {
    }

    public static String getCodeSnippet(String techniqueId, String style, String language) {
        String safeStyle = normalizeStyle(style);
        String safeLanguage = language == null ? "Java" : language.trim();

        String supplemental = getSupplementalSnippet(techniqueId, safeStyle, safeLanguage);
        if (supplemental != null) {
            return formatCodeSnippet(safeLanguage, supplemental);
        }

        if ("C".equalsIgnoreCase(safeLanguage)) {
            return formatCodeSnippet(safeLanguage, getCCodeSnippet(techniqueId, safeStyle));
        }
        if ("Python".equalsIgnoreCase(safeLanguage)) {
            return formatCodeSnippet(safeLanguage, getPythonCodeSnippet(techniqueId, safeStyle));
        }
        return formatCodeSnippet(safeLanguage, getJavaCodeSnippet(techniqueId, safeStyle));
    }

    private static String normalizeStyle(String style) {
        if ("Functions".equalsIgnoreCase(style) || "Function".equalsIgnoreCase(style)) {
            return "Functions";
        }
        if ("Recursion".equalsIgnoreCase(style)) {
            return "Recursion";
        }
        return "Iterative";
    }

    private static String getSupplementalSnippet(String techniqueId, String style, String language) {
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

    private static String formatCodeSnippet(String language, String code) {
        if (code == null) {
            return "";
        }
        if ("Python".equalsIgnoreCase(language)) {
            return code.trim();
        }
        String formatted = formatCJavaSnippet(code);
        if ("C".equalsIgnoreCase(language)) {
            return injectCInputPrompts(formatted);
        }
        return formatted;
    }

    private static String formatCJavaSnippet(String code) {
        String normalized = normalizeWhitespace(code);
        StringBuilder staged = new StringBuilder();
        boolean inString = false;
        boolean inChar = false;
        boolean inLineComment = false;
        boolean inBlockComment = false;

        for (int i = 0; i < normalized.length(); i++) {
            char c = normalized.charAt(i);
            char next = i + 1 < normalized.length() ? normalized.charAt(i + 1) : '\0';

            if (inLineComment) {
                staged.append(c);
                if (c == '\n') {
                    inLineComment = false;
                }
                continue;
            }
            if (inBlockComment) {
                staged.append(c);
                if (c == '*' && next == '/') {
                    staged.append(next);
                    i++;
                    inBlockComment = false;
                }
                continue;
            }
            if (inString) {
                staged.append(c);
                if (c == '\\' && next != '\0') {
                    staged.append(next);
                    i++;
                    continue;
                }
                if (c == '"') {
                    inString = false;
                }
                continue;
            }
            if (inChar) {
                staged.append(c);
                if (c == '\\' && next != '\0') {
                    staged.append(next);
                    i++;
                    continue;
                }
                if (c == '\'') {
                    inChar = false;
                }
                continue;
            }

            if (c == '/' && next == '/') {
                staged.append("//");
                i++;
                inLineComment = true;
                continue;
            }
            if (c == '/' && next == '*') {
                staged.append("/*");
                i++;
                inBlockComment = true;
                continue;
            }
            if (c == '"') {
                staged.append(c);
                inString = true;
                continue;
            }
            if (c == '\'') {
                staged.append(c);
                inChar = true;
                continue;
            }

            if (c == '{') {
                trimTrailingSpace(staged);
                staged.append(" {");
                staged.append('\n');
                continue;
            }
            if (c == '}') {
                trimTrailingSpace(staged);
                staged.append('\n');
                staged.append('}');
                staged.append('\n');
                continue;
            }
            if (c == ';') {
                staged.append(';');
                staged.append('\n');
                continue;
            }
            staged.append(c);
        }

        String[] lines = staged.toString().split("\n");
        StringBuilder result = new StringBuilder();
        int indent = 0;
        boolean indentNextLine = false;
        for (String rawLine : lines) {
            String line = rawLine.trim();
            if (line.isEmpty()) {
                continue;
            }
            if (line.startsWith("}")) {
                indent = Math.max(0, indent - 1);
            }
            int effectiveIndent = indent + (indentNextLine ? 1 : 0);
            appendIndent(result, effectiveIndent);
            result.append(line);
            result.append('\n');

            if (indentNextLine) {
                indentNextLine = false;
            }
            if (line.endsWith("{")) {
                indent++;
                continue;
            }
            if (startsWithControl(line) && !line.endsWith("{")) {
                indentNextLine = true;
            }
        }
        trimTrailingNewline(result);
        return result.toString();
    }

    private static String normalizeWhitespace(String code) {
        String normalized = code == null ? "" : code.replace("\r", "").replace("\t", " ");
        StringBuilder builder = new StringBuilder();
        boolean inString = false;
        boolean inChar = false;
        boolean inLineComment = false;
        boolean inBlockComment = false;
        boolean pendingSpace = false;

        for (int i = 0; i < normalized.length(); i++) {
            char c = normalized.charAt(i);
            char next = i + 1 < normalized.length() ? normalized.charAt(i + 1) : '\0';

            if (inLineComment) {
                builder.append(c);
                if (c == '\n') {
                    inLineComment = false;
                    pendingSpace = false;
                }
                continue;
            }
            if (inBlockComment) {
                builder.append(c);
                if (c == '*' && next == '/') {
                    builder.append(next);
                    i++;
                    inBlockComment = false;
                }
                continue;
            }
            if (inString) {
                builder.append(c);
                if (c == '\\' && next != '\0') {
                    builder.append(next);
                    i++;
                    continue;
                }
                if (c == '"') {
                    inString = false;
                }
                continue;
            }
            if (inChar) {
                builder.append(c);
                if (c == '\\' && next != '\0') {
                    builder.append(next);
                    i++;
                    continue;
                }
                if (c == '\'') {
                    inChar = false;
                }
                continue;
            }

            if (c == '/' && next == '/') {
                builder.append("//");
                i++;
                inLineComment = true;
                continue;
            }
            if (c == '/' && next == '*') {
                builder.append("/*");
                i++;
                inBlockComment = true;
                continue;
            }
            if (c == '"') {
                if (pendingSpace) {
                    builder.append(' ');
                    pendingSpace = false;
                }
                builder.append(c);
                inString = true;
                continue;
            }
            if (c == '\'') {
                if (pendingSpace) {
                    builder.append(' ');
                    pendingSpace = false;
                }
                builder.append(c);
                inChar = true;
                continue;
            }

            if (Character.isWhitespace(c)) {
                pendingSpace = true;
                if (c == '\n') {
                    builder.append('\n');
                    pendingSpace = false;
                }
                continue;
            }

            if (pendingSpace) {
                builder.append(' ');
                pendingSpace = false;
            }
            builder.append(c);
        }

        return builder.toString();
    }

    private static void appendIndent(StringBuilder builder, int indent) {
        for (int i = 0; i < indent; i++) {
            builder.append("    ");
        }
    }

    private static boolean startsWithControl(String line) {
        String trimmed = line.trim();
        return trimmed.startsWith("if(")
            || trimmed.startsWith("if (")
            || trimmed.startsWith("for(")
            || trimmed.startsWith("for (")
            || trimmed.startsWith("while(")
            || trimmed.startsWith("while (")
            || trimmed.startsWith("else if(")
            || trimmed.startsWith("else if (")
            || trimmed.startsWith("else");
    }

    private static void trimTrailingSpace(StringBuilder builder) {
        int length = builder.length();
        while (length > 0 && builder.charAt(length - 1) == ' ') {
            builder.deleteCharAt(length - 1);
            length--;
        }
    }

    private static void trimTrailingNewline(StringBuilder builder) {
        int length = builder.length();
        while (length > 0 && builder.charAt(length - 1) == '\n') {
            builder.deleteCharAt(length - 1);
            length--;
        }
    }

    private static String injectCInputPrompts(String code) {
        return code;
    }

    private static String leadingWhitespace(String line) {
        int index = 0;
        while (index < line.length() && Character.isWhitespace(line.charAt(index))) {
            index++;
        }
        return line.substring(0, index);
    }

    private static boolean containsScanf(String line) {
        return line.contains("scanf(");
    }

    private static boolean containsEnterPrompt(String line) {
        return line.contains("printf(\"Enter value:");
    }

    private static boolean isLoopHeader(String trimmed) {
        return trimmed.startsWith("for (")
            || trimmed.startsWith("for(")
            || trimmed.startsWith("while (")
            || trimmed.startsWith("while(");
    }

    private static int findNextNonEmpty(String[] lines, int start) {
        for (int i = start; i < lines.length; i++) {
            if (!lines[i].trim().isEmpty()) {
                return i;
            }
        }
        return -1;
    }

    private static String addIndent(String line, int level) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < level; i++) {
            builder.append("    ");
        }
        builder.append(line.trim());
        return builder.toString();
    }

    private static String getJavaCodeSnippet(String techniqueId, String style) {
        if ("selection_sort".equals(techniqueId)) {
            return getJavaSelectionSortPdf(style);
        }
        if ("insertion_sort".equals(techniqueId)) {
            return getJavaInsertionSortPdf(style);
        }
        if ("merging".equals(techniqueId)) {
            return getJavaMergingPdf(style);
        }
        if ("merge_sort".equals(techniqueId)) {
            return getJavaMergeSortPdf(style);
        }
        if ("radix_sort".equals(techniqueId)) {
            return getJavaRadixSortPdf(style);
        }
        if ("find_linked_list".equals(techniqueId)) {
            return getJavaSearchInLinkedList(style);
        }
        if ("delete_linked_list".equals(techniqueId)) {
            return getJavaDeleteNodeByItemLinkedList(style);
        }
        switch (techniqueId) {
            case "linear_search":
                if ("Functions".equals(style)) {
                    return "import java.util.Scanner;\n"
                        + "class Main {\n"
                        + "    static int linear(int arr[], int n, int item) {\n"
                        + "        int loc = 1;\n"
                        + "        arr[n + 1] = item;\n\n"
                        + "        while(arr[loc] != item)\n"
                        + "            loc++;\n\n"
                        + "        if(loc == n + 1)\n"
                        + "            return 0;\n"
                        + "        else\n"
                        + "            return loc;\n"
                        + "    }\n\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Scanner sc = new Scanner(System.in);\n\n"
                        + "        System.out.print(\"Enter number of elements: \");\n"
                        + "        int n = sc.nextInt();\n"
                        + "        int arr[] = new int[n + 2];\n\n"
                        + "        System.out.println(\"Enter array elements:\");\n"
                        + "        for(int i = 1; i <= n; i++)\n"
                        + "            arr[i] = sc.nextInt();\n\n"
                        + "        System.out.print(\"Enter item to search: \");\n"
                        + "        int item = sc.nextInt();\n\n"
                        + "        int loc = linear(arr, n, item);\n\n"
                        + "        System.out.println(\"Location: \" + loc);\n"
                        + "    }\n"
                        + "}";
                }
                if ("Recursion".equals(style)) {
                    return "import java.util.Scanner;\n"
                        + "class Main {\n"
                        + "    static int linear(int arr[], int n, int item, int loc) {\n"
                        + "        if(arr[loc] == item) {\n"
                        + "            if(loc == n + 1)\n"
                        + "                return 0;\n"
                        + "            else\n"
                        + "                return loc;\n"
                        + "        }\n"
                        + "        return linear(arr, n, item, loc + 1);\n"
                        + "    }\n\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Scanner sc = new Scanner(System.in);\n\n"
                        + "        System.out.print(\"Enter number of elements: \");\n"
                        + "        int n = sc.nextInt();\n"
                        + "        int arr[] = new int[n + 2];\n\n"
                        + "        System.out.println(\"Enter array elements:\");\n"
                        + "        for(int i = 1; i <= n; i++)\n"
                        + "            arr[i] = sc.nextInt();\n\n"
                        + "        System.out.print(\"Enter item to search: \");\n"
                        + "        int item = sc.nextInt();\n"
                        + "        arr[n + 1] = item;\n\n"
                        + "        int loc = linear(arr, n, item, 1);\n\n"
                        + "        System.out.println(\"Location: \" + loc);\n"
                        + "    }\n"
                        + "}";
                }
                return "import java.util.Scanner;\n"
                    + "class Main {\n"
                    + "    public static void main(String[] args) {\n"
                    + "        Scanner sc = new Scanner(System.in);\n"
                    + "        System.out.print(\"Enter number of elements: \");\n"
                    + "        int n = sc.nextInt();\n"
                    + "        int arr[] = new int[n + 2];\n\n"
                    + "        System.out.println(\"Enter array elements:\");\n"
                    + "        for(int i = 1; i <= n; i++)\n"
                    + "            arr[i] = sc.nextInt();\n\n"
                    + "        System.out.print(\"Enter item to search: \");\n"
                    + "        int item = sc.nextInt();\n"
                    + "        arr[n + 1] = item;\n"
                    + "        int loc = 1;\n\n"
                    + "        while(arr[loc] != item)\n"
                    + "            loc++;\n\n"
                    + "        if(loc == n + 1)\n"
                    + "            loc = 0;\n\n"
                    + "        System.out.println(\"Location: \" + loc);\n"
                    + "    }\n"
                    + "}";
            case "binary_search":
                if ("Functions".equals(style)) {
                    return "import java.util.Scanner;\n"
                        + "class Main {\n"
                        + "    static int binary(int arr[], int lb, int ub, int item) {\n"
                        + "        int beg = lb, end = ub;\n\n"
                        + "        while(beg <= end) {\n"
                        + "            int mid = (beg + end) / 2;\n\n"
                        + "            if(arr[mid] == item)\n"
                        + "                return mid;\n"
                        + "            else if(item < arr[mid])\n"
                        + "                end = mid - 1;\n"
                        + "            else\n"
                        + "                beg = mid + 1;\n"
                        + "        }\n"
                        + "        return -1;\n"
                        + "    }\n\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Scanner sc = new Scanner(System.in);\n\n"
                        + "        System.out.print(\"Enter number of elements: \");\n"
                        + "        int n = sc.nextInt();\n"
                        + "        int arr[] = new int[n];\n\n"
                        + "        System.out.println(\"Enter sorted array elements:\");\n"
                        + "        for(int i = 0; i < n; i++)\n"
                        + "            arr[i] = sc.nextInt();\n\n"
                        + "        System.out.print(\"Enter item to search: \");\n"
                        + "        int item = sc.nextInt();\n\n"
                        + "        int loc = binary(arr, 0, n - 1, item);\n\n"
                        + "        System.out.println(\"Location: \" + loc);\n"
                        + "    }\n"
                        + "}";
                }
                if ("Recursion".equals(style)) {
                    return "import java.util.Scanner;\n"
                        + "class Main {\n"
                        + "    static int binary(int arr[], int beg, int end, int item) {\n"
                        + "        if(beg > end)\n"
                        + "            return -1;\n\n"
                        + "        int mid = (beg + end) / 2;\n\n"
                        + "        if(arr[mid] == item)\n"
                        + "            return mid;\n"
                        + "        else if(item < arr[mid])\n"
                        + "            return binary(arr, beg, mid - 1, item);\n"
                        + "        else\n"
                        + "            return binary(arr, mid + 1, end, item);\n"
                        + "    }\n\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Scanner sc = new Scanner(System.in);\n\n"
                        + "        System.out.print(\"Enter number of elements: \");\n"
                        + "        int n = sc.nextInt();\n"
                        + "        int arr[] = new int[n];\n\n"
                        + "        System.out.println(\"Enter sorted array elements:\");\n"
                        + "        for(int i = 0; i < n; i++)\n"
                        + "            arr[i] = sc.nextInt();\n\n"
                        + "        System.out.print(\"Enter item to search: \");\n"
                        + "        int item = sc.nextInt();\n\n"
                        + "        int loc = binary(arr, 0, n - 1, item);\n\n"
                        + "        System.out.println(\"Location: \" + loc);\n"
                        + "    }\n"
                        + "}";
                }
                return "import java.util.Scanner;\n"
                    + "class Main {\n"
                    + "    public static void main(String[] args) {\n"
                    + "        Scanner sc = new Scanner(System.in);\n"
                    + "        System.out.print(\"Enter number of elements: \");\n"
                    + "        int n = sc.nextInt();\n"
                    + "        int arr[] = new int[n];\n\n"
                    + "        System.out.println(\"Enter sorted array elements:\");\n"
                    + "        for(int i = 0; i < n; i++)\n"
                    + "            arr[i] = sc.nextInt();\n\n"
                    + "        System.out.print(\"Enter item to search: \");\n"
                    + "        int item = sc.nextInt();\n\n"
                    + "        int beg = 0, end = n - 1, mid;\n"
                    + "        mid = (beg + end) / 2;\n\n"
                    + "        while(beg <= end && arr[mid] != item) {\n"
                    + "            if(item < arr[mid])\n"
                    + "                end = mid - 1;\n"
                    + "            else\n"
                    + "                beg = mid + 1;\n\n"
                    + "            mid = (beg + end) / 2;\n"
                    + "        }\n\n"
                    + "        if(beg <= end && arr[mid] == item)\n"
                    + "            System.out.println(\"Location: \" + mid);\n"
                    + "        else\n"
                    + "            System.out.println(\"Location: -1\");\n"
                    + "    }\n"
                    + "}";
            case "jump_search":
                if ("Functions".equals(style)) {
                    return "public int jumpSearch(int[] arr, int target) {\n"
                        + "    int blockSize = getBlockSize(arr.length);\n"
                        + "    int start = findBlockStart(arr, target, blockSize);\n"
                        + "    if (start == -1) {\n"
                        + "        return -1;\n"
                        + "    }\n"
                        + "    return scanBlock(arr, target, start, Math.min(start + blockSize, arr.length));\n"
                        + "}\n\n"
                        + "private int getBlockSize(int length) {\n"
                        + "    return (int) Math.sqrt(length);\n"
                        + "}\n\n"
                        + "private int findBlockStart(int[] arr, int target, int blockSize) {\n"
                        + "    int start = 0;\n"
                        + "    while (start < arr.length) {\n"
                        + "        int end = Math.min(start + blockSize, arr.length) - 1;\n"
                        + "        if (arr[end] >= target) {\n"
                        + "            return start;\n"
                        + "        }\n"
                        + "        start += blockSize;\n"
                        + "    }\n"
                        + "    return -1;\n"
                        + "}\n\n"
                        + "private int scanBlock(int[] arr, int target, int start, int endExclusive) {\n"
                        + "    for (int i = start; i < endExclusive; i++) {\n"
                        + "        if (arr[i] == target) {\n"
                        + "            return i;\n"
                        + "        }\n"
                        + "    }\n"
                        + "    return -1;\n"
                        + "}";
                }
                if ("Recursion".equals(style)) {
                    return "public int jumpSearchRecursive(int[] arr, int target, int start, int step) {\n"
                        + "    int end = Math.min(start + step, arr.length);\n"
                        + "    if (start >= arr.length) {\n"
                        + "        return -1;\n"
                        + "    }\n"
                        + "    if (arr[end - 1] < target) {\n"
                        + "        return jumpSearchRecursive(arr, target, end, step);\n"
                        + "    }\n"
                        + "    return scanBlock(arr, target, start, end - 1);\n"
                        + "}\n\n"
                        + "private int scanBlock(int[] arr, int target, int index, int end) {\n"
                        + "    if (index > end) {\n"
                        + "        return -1;\n"
                        + "    }\n"
                        + "    if (arr[index] == target) {\n"
                        + "        return index;\n"
                        + "    }\n"
                        + "    return scanBlock(arr, target, index + 1, end);\n"
                        + "}";
                }
                return "public int jumpSearch(int[] arr, int target) {\n"
                    + "    int step = (int) Math.sqrt(arr.length);\n"
                    + "    int prev = 0;\n\n"
                    + "    while (prev < arr.length && arr[Math.min(step, arr.length) - 1] < target) {\n"
                    + "        prev = step;\n"
                    + "        step += (int) Math.sqrt(arr.length);\n"
                    + "        if (prev >= arr.length) {\n"
                    + "            return -1;\n"
                    + "        }\n"
                    + "    }\n\n"
                    + "    while (prev < Math.min(step, arr.length)) {\n"
                    + "        if (arr[prev] == target) {\n"
                    + "            return prev;\n"
                    + "        }\n"
                    + "        prev++;\n"
                    + "    }\n"
                    + "    return -1;\n"
                    + "}";
            case "bubble_sort":
                if ("Functions".equals(style)) {
                    return "import java.util.Scanner;\n"
                        + "class Main {\n\n"
                        + "    static void bubble(int arr[], int n) {\n"
                        + "        for(int k = 1; k <= n - 1; k++) {\n"
                        + "            int ptr = 0;\n"
                        + "            while(ptr <= n - k - 1) {\n"
                        + "                if(arr[ptr] < arr[ptr + 1]) {\n"
                        + "                    int temp = arr[ptr];\n"
                        + "                    arr[ptr] = arr[ptr + 1];\n"
                        + "                    arr[ptr + 1] = temp;\n"
                        + "                }\n"
                        + "                ptr++;\n"
                        + "            }\n"
                        + "        }\n"
                        + "    }\n\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Scanner sc = new Scanner(System.in);\n\n"
                        + "        System.out.print(\"Enter number of elements: \");\n"
                        + "        int n = sc.nextInt();\n"
                        + "        int arr[] = new int[n];\n\n"
                        + "        System.out.println(\"Enter array elements:\");\n"
                        + "        for(int i = 0; i < n; i++)\n"
                        + "            arr[i] = sc.nextInt();\n\n"
                        + "        bubble(arr, n);\n\n"
                        + "        System.out.println(\"Sorted array:\");\n"
                        + "        for(int i = 0; i < n; i++)\n"
                        + "            System.out.print(arr[i] + \" \");\n"
                        + "    }\n"
                        + "}";
                }
                if ("Recursion".equals(style)) {
                    return "import java.util.Scanner;\n"
                        + "class Main {\n\n"
                        + "    static void bubble(int arr[], int n) {\n"
                        + "        if(n == 1)\n"
                        + "            return;\n\n"
                        + "        int ptr = 0;\n\n"
                        + "        while(ptr <= n - 2) {\n"
                        + "            if(arr[ptr] < arr[ptr + 1]) {\n"
                        + "                int temp = arr[ptr];\n"
                        + "                arr[ptr] = arr[ptr + 1];\n"
                        + "                arr[ptr + 1] = temp;\n"
                        + "            }\n"
                        + "            ptr++;\n"
                        + "        }\n\n"
                        + "        bubble(arr, n - 1);\n"
                        + "    }\n\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Scanner sc = new Scanner(System.in);\n\n"
                        + "        System.out.print(\"Enter number of elements: \");\n"
                        + "        int n = sc.nextInt();\n"
                        + "        int arr[] = new int[n];\n\n"
                        + "        System.out.println(\"Enter array elements:\");\n"
                        + "        for(int i = 0; i < n; i++)\n"
                        + "            arr[i] = sc.nextInt();\n\n"
                        + "        bubble(arr, n);\n\n"
                        + "        System.out.println(\"Sorted array:\");\n"
                        + "        for(int i = 0; i < n; i++)\n"
                        + "            System.out.print(arr[i] + \" \");\n"
                        + "    }\n"
                        + "}";
                }
                return "import java.util.Scanner;\n"
                    + "class Main {\n"
                    + "    public static void main(String[] args) {\n"
                    + "        Scanner sc = new Scanner(System.in);\n\n"
                    + "        System.out.print(\"Enter number of elements: \");\n"
                    + "        int n = sc.nextInt();\n"
                    + "        int arr[] = new int[n];\n\n"
                    + "        System.out.println(\"Enter array elements:\");\n"
                    + "        for(int i = 0; i < n; i++)\n"
                    + "            arr[i] = sc.nextInt();\n\n"
                    + "        for(int k = 1; k <= n - 1; k++) {\n"
                    + "            int ptr = 0;\n"
                    + "            while(ptr <= n - k - 1) {\n"
                    + "                if(arr[ptr] < arr[ptr + 1]) {\n"
                    + "                    int temp = arr[ptr];\n"
                    + "                    arr[ptr] = arr[ptr + 1];\n"
                    + "                    arr[ptr + 1] = temp;\n"
                    + "                }\n"
                    + "                ptr++;\n"
                    + "            }\n"
                    + "        }\n\n"
                    + "        System.out.println(\"Sorted array:\");\n"
                    + "        for(int i = 0; i < n; i++)\n"
                    + "            System.out.print(arr[i] + \" \");\n"
                    + "    }\n"
                    + "}";
            case "selection_sort":
                if ("Functions".equals(style)) {
                    return "public void selectionSort(int[] arr) {\n"
                        + "    for (int i = 0; i < arr.length - 1; i++) {\n"
                        + "        int minIndex = findMinIndex(arr, i);\n"
                        + "        swap(arr, i, minIndex);\n"
                        + "    }\n"
                        + "}\n\n"
                        + "private int findMinIndex(int[] arr, int start) {\n"
                        + "    int minIndex = start;\n"
                        + "    for (int i = start + 1; i < arr.length; i++) {\n"
                        + "        if (arr[i] < arr[minIndex]) {\n"
                        + "            minIndex = i;\n"
                        + "        }\n"
                        + "    }\n"
                        + "    return minIndex;\n"
                        + "}\n\n"
                        + "private void swap(int[] arr, int left, int right) {\n"
                        + "    int temp = arr[left];\n"
                        + "    arr[left] = arr[right];\n"
                        + "    arr[right] = temp;\n"
                        + "}";
                }
                if ("Recursion".equals(style)) {
                    return "public void selectionSortRecursive(int[] arr, int start) {\n"
                        + "    if (start >= arr.length - 1) {\n"
                        + "        return;\n"
                        + "    }\n"
                        + "    int minIndex = start;\n"
                        + "    for (int i = start + 1; i < arr.length; i++) {\n"
                        + "        if (arr[i] < arr[minIndex]) {\n"
                        + "            minIndex = i;\n"
                        + "        }\n"
                        + "    }\n"
                        + "    int temp = arr[start];\n"
                        + "    arr[start] = arr[minIndex];\n"
                        + "    arr[minIndex] = temp;\n"
                        + "    selectionSortRecursive(arr, start + 1);\n"
                        + "}";
                }
                return "public void selectionSort(int[] arr) {\n"
                    + "    for (int i = 0; i < arr.length - 1; i++) {\n"
                    + "        int minIndex = i;\n"
                    + "        for (int j = i + 1; j < arr.length; j++) {\n"
                    + "            if (arr[j] < arr[minIndex]) {\n"
                    + "                minIndex = j;\n"
                    + "            }\n"
                    + "        }\n"
                    + "        int temp = arr[i];\n"
                    + "        arr[i] = arr[minIndex];\n"
                    + "        arr[minIndex] = temp;\n"
                    + "    }\n"
                    + "}";
            case "insertion_sort":
                if ("Functions".equals(style)) {
                    return "public void insertionSort(int[] arr) {\n"
                        + "    for (int i = 1; i < arr.length; i++) {\n"
                        + "        placeValue(arr, i);\n"
                        + "    }\n"
                        + "}\n\n"
                        + "private void placeValue(int[] arr, int index) {\n"
                        + "    int key = arr[index];\n"
                        + "    int j = index - 1;\n"
                        + "    while (j >= 0 && arr[j] > key) {\n"
                        + "        arr[j + 1] = arr[j];\n"
                        + "        j--;\n"
                        + "    }\n"
                        + "    arr[j + 1] = key;\n"
                        + "}";
                }
                if ("Recursion".equals(style)) {
                    return "public void insertionSortRecursive(int[] arr, int n) {\n"
                        + "    if (n <= 1) {\n"
                        + "        return;\n"
                        + "    }\n"
                        + "    insertionSortRecursive(arr, n - 1);\n"
                        + "    int key = arr[n - 1];\n"
                        + "    int j = n - 2;\n"
                        + "    while (j >= 0 && arr[j] > key) {\n"
                        + "        arr[j + 1] = arr[j];\n"
                        + "        j--;\n"
                        + "    }\n"
                        + "    arr[j + 1] = key;\n"
                        + "}";
                }
                return "public void insertionSort(int[] arr) {\n"
                    + "    for (int i = 1; i < arr.length; i++) {\n"
                    + "        int key = arr[i];\n"
                    + "        int j = i - 1;\n"
                    + "        while (j >= 0 && arr[j] > key) {\n"
                    + "            arr[j + 1] = arr[j];\n"
                    + "            j--;\n"
                    + "        }\n"
                    + "        arr[j + 1] = key;\n"
                    + "    }\n"
                    + "}";
            case "stack_basics":
                return getJavaStackBasics(style);
            case "linked_stack_push":
                return getJavaLinkedStackPush(style);
            case "linked_stack_pop":
                return getJavaLinkedStackPop(style);
            case "quicksort":
                return getJavaQuicksort(style);
            case "queue_basics":
                return getJavaLinearQueue(style);
            case "circular_queue":
                return getJavaCircularQueue(style);
            case "linked_queue":
                return getJavaLinkedQueue(style);
            case "priority_queue_array":
                return getJavaPriorityQueueArray(style);
            case "priority_queue_2d":
                return getJavaPriorityQueue2d(style);
            case "preorder_traversal":
                return getJavaPreorderTraversal(style);
            case "postorder_traversal":
                return getJavaPostorderTraversal(style);
            case "bst_search_parent_tracking":
                return getJavaBstSearchParentTracking(style);
            case "bst_insertion":
                return getJavaBstInsertion(style);
            case "bst_deletion_case_a":
                return getJavaBstDeletionCaseA(style);
            case "bst_deletion_case_b":
                return getJavaBstDeletionCaseB(style);
            case "bst_deletion_del":
                return getJavaBstDeletionDel(style);
            case "max_heap_insertion":
                return getJavaMaxHeapInsertion(style);
            case "max_heap_deletion":
                return getJavaMaxHeapDeletion(style);
            case "heapsort":
                return getJavaHeapSort(style);
            case "singly_linked_list":
                if ("Functions".equals(style)) {
                    return "class Node {\n"
                        + "    int data;\n"
                        + "    Node next;\n\n"
                        + "    Node(int data) {\n"
                        + "        this.data = data;\n"
                        + "    }\n"
                        + "}\n\n"
                        + "public Node insertAtEnd(Node head, int value) {\n"
                        + "    if (head == null) {\n"
                        + "        return new Node(value);\n"
                        + "    }\n"
                        + "    Node tail = getTail(head);\n"
                        + "    tail.next = new Node(value);\n"
                        + "    return head;\n"
                        + "}\n\n"
                        + "private Node getTail(Node node) {\n"
                        + "    while (node.next != null) {\n"
                        + "        node = node.next;\n"
                        + "    }\n"
                        + "    return node;\n"
                        + "}";
                }
                if ("Recursion".equals(style)) {
                    return "class Node {\n"
                        + "    int data;\n"
                        + "    Node next;\n\n"
                        + "    Node(int data) {\n"
                        + "        this.data = data;\n"
                        + "    }\n"
                        + "}\n\n"
                        + "public Node insertAtEndRecursive(Node head, int value) {\n"
                        + "    if (head == null) {\n"
                        + "        return new Node(value);\n"
                        + "    }\n"
                        + "    head.next = insertAtEndRecursive(head.next, value);\n"
                        + "    return head;\n"
                        + "}";
                }
                return "class Node {\n"
                    + "    int data;\n"
                    + "    Node next;\n\n"
                    + "    Node(int data) {\n"
                    + "        this.data = data;\n"
                    + "    }\n"
                    + "}\n\n"
                    + "public void insertAtEnd(Node head, int value) {\n"
                    + "    Node current = head;\n"
                    + "    while (current.next != null) {\n"
                    + "        current = current.next;\n"
                    + "    }\n"
                    + "    current.next = new Node(value);\n"
                    + "}";
            case "traversing_linked_list":
            case "print_linked_list":
                if ("Functions".equals(style)) {
                    return "import java.util.Scanner;\n"
                        + "class Node {\n"
                        + "    int data;\n"
                        + "    Node next;\n"
                        + "    Node(int data) { this.data = data; }\n"
                        + "}\n"
                        + "class Main {\n"
                        + "    static void printList(Node ptr) {\n"
                        + "        while(ptr != null) {\n"
                        + "            System.out.print(ptr.data + \" \");\n"
                        + "            ptr = ptr.next;\n"
                        + "        }\n"
                        + "    }\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Scanner sc = new Scanner(System.in);\n"
                        + "        int n = sc.nextInt();\n"
                        + "        Node head = null, temp = null;\n"
                        + "        for(int i = 0; i < n; i++) {\n"
                        + "            Node newNode = new Node(sc.nextInt());\n"
                        + "            if(head == null) head = temp = newNode;\n"
                        + "            else { temp.next = newNode; temp = newNode; }\n"
                        + "        }\n"
                        + "        printList(head);\n"
                        + "    }\n"
                        + "}";
                }
                if ("Recursion".equals(style)) {
                    return "import java.util.Scanner;\n"
                        + "class Node {\n"
                        + "    int data;\n"
                        + "    Node next;\n"
                        + "    Node(int data) { this.data = data; }\n"
                        + "}\n"
                        + "class Main {\n"
                        + "    static void printList(Node ptr) {\n"
                        + "        if(ptr == null) return;\n"
                        + "        System.out.print(ptr.data + \" \");\n"
                        + "        printList(ptr.next);\n"
                        + "    }\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Scanner sc = new Scanner(System.in);\n"
                        + "        int n = sc.nextInt();\n"
                        + "        Node head = null, temp = null;\n"
                        + "        for(int i = 0; i < n; i++) {\n"
                        + "            Node newNode = new Node(sc.nextInt());\n"
                        + "            if(head == null) head = temp = newNode;\n"
                        + "            else { temp.next = newNode; temp = newNode; }\n"
                        + "        }\n"
                        + "        printList(head);\n"
                        + "    }\n"
                        + "}";
                }
                return "import java.util.Scanner;\n"
                    + "class Node {\n"
                    + "    int data;\n"
                    + "    Node next;\n"
                    + "    Node(int data) { this.data = data; this.next = null; }\n"
                    + "}\n"
                    + "class Main {\n"
                    + "    public static void main(String[] args) {\n"
                    + "        Scanner sc = new Scanner(System.in);\n"
                    + "        int n = sc.nextInt();\n"
                    + "        Node head = null, temp = null;\n"
                    + "        for(int i = 0; i < n; i++) {\n"
                    + "            int val = sc.nextInt();\n"
                    + "            Node newNode = new Node(val);\n"
                    + "            if(head == null) head = temp = newNode;\n"
                    + "            else { temp.next = newNode; temp = newNode; }\n"
                    + "        }\n"
                    + "        Node ptr = head;\n"
                    + "        while(ptr != null) {\n"
                    + "            System.out.print(ptr.data + \" \");\n"
                    + "            ptr = ptr.next;\n"
                    + "        }\n"
                    + "    }\n"
                    + "}";
            case "count_nodes_linked_list":
                return getJavaCountNodesLinkedList(style);
            case "search_in_linked_list":
                return getJavaSearchInLinkedList(style);
            case "insert_at_beginning_linked_list":
                return getJavaInsertAtBeginningLinkedList(style);
            case "insert_after_given_node_linked_list":
                return getJavaInsertAfterGivenNodeLinkedList(style);
            case "insert_into_sorted_linked_list":
                return getJavaInsertIntoSortedLinkedList(style);
            case "delete_node_given_location_linked_list":
                return getJavaDeleteNodeGivenLocationLinkedList(style);
            case "delete_node_by_item_linked_list":
                return getJavaDeleteNodeByItemLinkedList(style);
            case "traverse_circular_header_list":
                return getJavaTraverseCircularHeaderList(style);
            case "search_circular_header_list":
                return getJavaSearchCircularHeaderList(style);
            case "delete_circular_header_list":
                return getJavaDeleteCircularHeaderList(style);
            case "tree_traversal":
                if ("Functions".equals(style)) {
                    return "class Node {\n"
                        + "    int data;\n"
                        + "    Node left;\n"
                        + "    Node right;\n"
                        + "}\n\n"
                        + "public void preorder(Node root) {\n"
                        + "    visit(root);\n"
                        + "}\n\n"
                        + "private void visit(Node node) {\n"
                        + "    if (node == null) {\n"
                        + "        return;\n"
                        + "    }\n"
                        + "    System.out.print(node.data + \" \");\n"
                        + "    visit(node.left);\n"
                        + "    visit(node.right);\n"
                        + "}";
                }
                if ("Recursion".equals(style)) {
                    return "public void inorder(Node root) {\n"
                        + "    if (root == null) {\n"
                        + "        return;\n"
                        + "    }\n"
                        + "    inorder(root.left);\n"
                        + "    System.out.print(root.data + \" \");\n"
                        + "    inorder(root.right);\n"
                        + "}";
                }
                return "public void inorderIterative(Node root) {\n"
                    + "    Stack<Node> stack = new Stack<>();\n"
                    + "    Node current = root;\n\n"
                    + "    while (current != null || !stack.isEmpty()) {\n"
                    + "        while (current != null) {\n"
                    + "            stack.push(current);\n"
                    + "            current = current.left;\n"
                    + "        }\n"
                    + "        current = stack.pop();\n"
                    + "        System.out.print(current.data + \" \");\n"
                    + "        current = current.right;\n"
                    + "    }\n"
                    + "}";
            case "graph_traversal":
                if ("Functions".equals(style)) {
                    return "public void bfs(int start, List<List<Integer>> graph) {\n"
                        + "    boolean[] visited = new boolean[graph.size()];\n"
                        + "    Queue<Integer> queue = new LinkedList<>();\n"
                        + "    markVisited(start, visited, queue);\n\n"
                        + "    while (!queue.isEmpty()) {\n"
                        + "        int node = queue.poll();\n"
                        + "        System.out.print(node + \" \");\n"
                        + "        addNeighbors(node, graph, visited, queue);\n"
                        + "    }\n"
                        + "}\n\n"
                        + "private void markVisited(int node, boolean[] visited, Queue<Integer> queue) {\n"
                        + "    visited[node] = true;\n"
                        + "    queue.offer(node);\n"
                        + "}\n\n"
                        + "private void addNeighbors(int node, List<List<Integer>> graph, boolean[] visited, Queue<Integer> queue) {\n"
                        + "    for (int next : graph.get(node)) {\n"
                        + "        if (!visited[next]) {\n"
                        + "            markVisited(next, visited, queue);\n"
                        + "        }\n"
                        + "    }\n"
                        + "}";
                }
                if ("Recursion".equals(style)) {
                    return "public void dfs(int node, List<List<Integer>> graph, boolean[] visited) {\n"
                        + "    visited[node] = true;\n"
                        + "    System.out.print(node + \" \");\n"
                        + "    for (int next : graph.get(node)) {\n"
                        + "        if (!visited[next]) {\n"
                        + "            dfs(next, graph, visited);\n"
                        + "        }\n"
                        + "    }\n"
                        + "}";
                }
                return "public void bfs(int start, List<List<Integer>> graph) {\n"
                    + "    boolean[] visited = new boolean[graph.size()];\n"
                    + "    Queue<Integer> queue = new LinkedList<>();\n"
                    + "    queue.offer(start);\n"
                    + "    visited[start] = true;\n\n"
                    + "    while (!queue.isEmpty()) {\n"
                    + "        int node = queue.poll();\n"
                    + "        System.out.print(node + \" \");\n"
                    + "        for (int next : graph.get(node)) {\n"
                    + "            if (!visited[next]) {\n"
                    + "                visited[next] = true;\n"
                    + "                queue.offer(next);\n"
                        + "            }\n"
                        + "        }\n"
                        + "    }\n"
                        + "}";
            case "warshall_algorithm":
                return getJavaWarshallAlgorithm(style);
            case "floyd_algorithm":
                return getJavaFloydAlgorithm(style);
            case "find_edge_graph":
                return getJavaFindEdgeGraph(style);
            case "insert_node_graph":
                return getJavaInsertNodeGraph(style);
            case "insert_edge_graph":
                return getJavaInsertEdgeGraph(style);
            case "delete_edge_graph":
                return getJavaDeleteEdgeGraph(style);
            case "delete_vertex_graph":
                return getJavaDeleteVertexGraph(style);
            case "breadth_first_search":
                return getJavaBreadthFirstSearch(style);
            case "depth_first_search":
                return getJavaDepthFirstSearch(style);
            case "topological_sort":
                return getJavaTopologicalSort(style);
            default:
                return "// Java code for this topic will be added here.";
        }
    }

    private static String getCCodeSnippet(String techniqueId, String style) {
        if ("selection_sort".equals(techniqueId)) {
            return getCSelectionSortPdf(style);
        }
        if ("insertion_sort".equals(techniqueId)) {
            return getCInsertionSortPdf(style);
        }
        if ("merging".equals(techniqueId)) {
            return getCMergingPdf(style);
        }
        if ("merge_sort".equals(techniqueId)) {
            return getCMergeSortPdf(style);
        }
        if ("radix_sort".equals(techniqueId)) {
            return getCRadixSortPdf(style);
        }
        if ("find_linked_list".equals(techniqueId)) {
            return getCSearchInLinkedList(style);
        }
        if ("delete_linked_list".equals(techniqueId)) {
            return getCDeleteNodeByItemLinkedList(style);
        }
        switch (techniqueId) {
            case "linear_search":
                if ("Functions".equals(style)) {
                    return "#include <stdio.h>\n"
                        + "int linear(int arr[], int n, int item) {\n"
                        + "    int loc = 1;\n"
                        + "    arr[n + 1] = item;\n"
                        + "    while(arr[loc] != item)\n"
                        + "        loc++;\n\n"
                        + "    if(loc == n + 1)\n"
                        + "        return 0;\n"
                        + "    else\n"
                        + "        return loc;\n"
                        + "}\n"
                        + "int main() {\n"
                        + "    int n, i, item, loc;\n"
                        + "    printf(\"Enter number of elements: \");\n"
                        + "    scanf(\"%d\" , &n);\n\n"
                        + "    int arr[n + 2];\n\n"
                        + "    printf(\"Enter elements:\\n\");\n"
                        + "    for(i = 1; i <= n; i++)\n"
                        + "        scanf(\"%d\" , &arr[i]);\n\n"
                        + "    printf(\"Enter item to search: \");\n"
                        + "    scanf(\"%d\" , &item);\n\n"
                        + "    loc = linear(arr, n, item);\n\n"
                        + "    printf(\"Location: %d\\n\" , loc);\n"
                        + "}";
                }
                if ("Recursion".equals(style)) {
                    return "#include <stdio.h>\n"
                        + "int linear(int arr[], int n, int item, int loc) {\n"
                        + "    if(arr[loc] == item) {\n"
                        + "        if(loc == n + 1)\n"
                        + "            return 0;\n"
                        + "        else\n"
                        + "            return loc;\n"
                        + "    }\n"
                        + "    return linear(arr, n, item, loc + 1);\n"
                        + "}\n"
                        + "int main() {\n"
                        + "    int n, i, item, loc;\n"
                        + "    printf(\"Enter number of elements: \");\n"
                        + "    scanf(\"%d\" , &n);\n\n"
                        + "    int arr[n + 2];\n\n"
                        + "    printf(\"Enter elements:\\n\");\n"
                        + "    for(i = 1; i <= n; i++)\n"
                        + "        scanf(\"%d\" , &arr[i]);\n\n"
                        + "    printf(\"Enter item to search: \");\n"
                        + "    scanf(\"%d\" , &item);\n\n"
                        + "    arr[n + 1] = item;\n"
                        + "    loc = linear(arr, n, item, 1);\n\n"
                        + "    printf(\"Location: %d\\n\" , loc);\n"
                        + "}";
                }
                return "#include <stdio.h>\n"
                    + "int main() {\n"
                    + "    int n, i, item, loc;\n"
                    + "    printf(\"Enter number of elements: \");\n"
                    + "    scanf(\"%d\" , &n);\n\n"
                    + "    int arr[n + 1];\n\n"
                    + "    printf(\"Enter elements:\\n\");\n"
                    + "    for(i = 1; i <= n; i++)\n"
                    + "        scanf(\"%d\" , &arr[i]);\n\n"
                    + "    printf(\"Enter item to search: \");\n"
                    + "    scanf(\"%d\" , &item);\n\n"
                    + "    arr[n + 1] = item;\n"
                    + "    loc = 1;\n\n"
                    + "    while(arr[loc] != item)\n"
                    + "        loc++;\n\n"
                    + "    if(loc == n + 1)\n"
                    + "        loc = 0;\n\n"
                    + "    printf(\"Location: %d\\n\" , loc);\n"
                    + "}";
            case "binary_search":
                if ("Functions".equals(style)) {
                    return "#include <stdio.h>\n"
                        + "int binary(int arr[], int lb, int ub, int item) {\n"
                        + "    int beg = lb, end = ub, mid;\n"
                        + "    while(beg <= end) {\n"
                        + "        mid = (beg + end) / 2;\n\n"
                        + "        if(arr[mid] == item)\n"
                        + "            return mid;\n"
                        + "        else if(item < arr[mid])\n"
                        + "            end = mid - 1;\n"
                        + "        else\n"
                        + "            beg = mid + 1;\n"
                        + "    }\n"
                        + "    return -1;\n"
                        + "}\n"
                        + "int main() {\n"
                        + "    int n, i, item;\n"
                        + "    printf(\"Enter number of elements: \");\n"
                        + "    scanf(\"%d\" , &n);\n\n"
                        + "    int arr[n];\n\n"
                        + "    printf(\"Enter sorted elements:\\n\");\n"
                        + "    for(i = 0; i < n; i++)\n"
                        + "        scanf(\"%d\" , &arr[i]);\n\n"
                        + "    printf(\"Enter item to search: \");\n"
                        + "    scanf(\"%d\" , &item);\n\n"
                        + "    int loc = binary(arr, 0, n - 1, item);\n\n"
                        + "    printf(\"Location: %d\\n\" , loc);\n"
                        + "}";
                }
                if ("Recursion".equals(style)) {
                    return "#include <stdio.h>\n"
                        + "int binary(int arr[], int beg, int end, int item) {\n"
                        + "    if(beg > end)\n"
                        + "        return -1;\n"
                        + "    int mid = (beg + end) / 2;\n\n"
                        + "    if(arr[mid] == item)\n"
                        + "        return mid;\n"
                        + "    else if(item < arr[mid])\n"
                        + "        return binary(arr, beg, mid - 1, item);\n"
                        + "    else\n"
                        + "        return binary(arr, mid + 1, end, item);\n"
                        + "}\n"
                        + "int main() {\n"
                        + "    int n, i, item;\n"
                        + "    printf(\"Enter number of elements: \");\n"
                        + "    scanf(\"%d\" , &n);\n\n"
                        + "    int arr[n];\n\n"
                        + "    printf(\"Enter sorted elements:\\n\");\n"
                        + "    for(i = 0; i < n; i++)\n"
                        + "        scanf(\"%d\" , &arr[i]);\n\n"
                        + "    printf(\"Enter item to search: \");\n"
                        + "    scanf(\"%d\" , &item);\n\n"
                        + "    int loc = binary(arr, 0, n - 1, item);\n\n"
                        + "    printf(\"Location: %d\\n\" , loc);\n"
                        + "}";
                }
                return "#include <stdio.h>\n"
                    + "int main() {\n"
                    + "    int n, i, item, loc = -1;\n"
                    + "    printf(\"Enter number of elements: \");\n"
                    + "    scanf(\"%d\" , &n);\n\n"
                    + "    int arr[n];\n\n"
                    + "    printf(\"Enter sorted elements:\\n\");\n"
                    + "    for(i = 0; i < n; i++)\n"
                    + "        scanf(\"%d\" , &arr[i]);\n\n"
                    + "    printf(\"Enter item to search: \");\n"
                    + "    scanf(\"%d\" , &item);\n\n"
                    + "    int beg = 0, end = n - 1, mid;\n"
                    + "    mid = (beg + end) / 2;\n\n"
                    + "    while(beg <= end && arr[mid] != item) {\n"
                    + "        if(item < arr[mid])\n"
                    + "            end = mid - 1;\n"
                    + "        else\n"
                    + "            beg = mid + 1;\n\n"
                    + "        mid = (beg + end) / 2;\n"
                    + "    }\n\n"
                    + "    if(beg <= end && arr[mid] == item)\n"
                    + "        loc = mid;\n"
                    + "    else\n"
                    + "        loc = -1;\n\n"
                    + "    printf(\"Location: %d\\n\" , loc);\n"
                    + "}";
            case "jump_search":
                if ("Functions".equals(style)) {
                    return "#include <math.h>\n\n"
                        + "int get_block_size(int n) {\n"
                        + "    return (int)sqrt(n);\n"
                        + "}\n\n"
                        + "int scan_block(int arr[], int target, int start, int end) {\n"
                        + "    for (int i = start; i < end; i++) {\n"
                        + "        if (arr[i] == target) {\n"
                        + "            return i;\n"
                        + "        }\n"
                        + "    }\n"
                        + "    return -1;\n"
                        + "}\n\n"
                        + "int jump_search(int arr[], int n, int target) {\n"
                        + "    int block_size = get_block_size(n);\n"
                        + "    int start = 0;\n"
                        + "    while (start < n && arr[(start + block_size < n ? start + block_size : n) - 1] < target) {\n"
                        + "        start += block_size;\n"
                        + "    }\n"
                        + "    return scan_block(arr, target, start, (start + block_size < n ? start + block_size : n));\n"
                        + "}";
                }
                if ("Recursion".equals(style)) {
                    return "#include <math.h>\n\n"
                        + "int recursive_scan(int arr[], int target, int index, int end) {\n"
                        + "    if (index > end) {\n"
                        + "        return -1;\n"
                        + "    }\n"
                        + "    if (arr[index] == target) {\n"
                        + "        return index;\n"
                        + "    }\n"
                        + "    return recursive_scan(arr, target, index + 1, end);\n"
                        + "}\n\n"
                        + "int jump_search_recursive(int arr[], int n, int target, int start, int block_size) {\n"
                        + "    if (start >= n) {\n"
                        + "        return -1;\n"
                        + "    }\n"
                        + "    int end = start + block_size < n ? start + block_size : n;\n"
                        + "    if (arr[end - 1] >= target) {\n"
                        + "        return recursive_scan(arr, target, start, end - 1);\n"
                        + "    }\n"
                        + "    return jump_search_recursive(arr, n, target, end, block_size);\n"
                        + "}";
                }
                return "int jump_search(int arr[], int n, int target) {\n"
                    + "    int step = (int)sqrt(n);\n"
                    + "    int prev = 0;\n"
                    + "    while (prev < n && arr[(step < n ? step : n) - 1] < target) {\n"
                    + "        prev = step;\n"
                    + "        step += (int)sqrt(n);\n"
                    + "        if (prev >= n) {\n"
                    + "            return -1;\n"
                    + "        }\n"
                    + "    }\n"
                    + "    while (prev < n && prev < step) {\n"
                    + "        if (arr[prev] == target) {\n"
                    + "            return prev;\n"
                    + "        }\n"
                    + "        prev++;\n"
                    + "    }\n"
                    + "    return -1;\n"
                    + "}";
            case "bubble_sort":
                if ("Functions".equals(style)) {
                    return "#include <stdio.h>\n\n"
                        + "void bubble(int arr[], int n) {\n"
                        + "    int k, ptr, temp;\n"
                        + "    for(k = 1; k <= n - 1; k++) {\n"
                        + "        ptr = 0;\n"
                        + "        while(ptr <= n - k - 1) {\n"
                        + "            if(arr[ptr] < arr[ptr + 1]) {\n"
                        + "                temp = arr[ptr];\n"
                        + "                arr[ptr] = arr[ptr + 1];\n"
                        + "                arr[ptr + 1] = temp;\n"
                        + "            }\n"
                        + "            ptr++;\n"
                        + "        }\n"
                        + "    }\n"
                        + "}\n\n"
                        + "int main() {\n"
                        + "    int n, i;\n"
                        + "    printf(\"Enter number of elements: \");\n"
                        + "    scanf(\"%d\", &n);\n\n"
                        + "    int arr[n];\n"
                        + "    printf(\"Enter elements:\\n\");\n"
                        + "    for(i = 0; i < n; i++)\n"
                        + "        scanf(\"%d\", &arr[i]);\n\n"
                        + "    bubble(arr, n);\n\n"
                        + "    printf(\"Sorted array:\\n\");\n"
                        + "    for(i = 0; i < n; i++)\n"
                        + "        printf(\"%d \", arr[i]);\n"
                        + "}";
                }
                if ("Recursion".equals(style)) {
                    return "#include <stdio.h>\n\n"
                        + "void bubble(int arr[], int n) {\n"
                        + "    if(n == 1)\n"
                        + "        return;\n\n"
                        + "    int ptr = 0, temp;\n\n"
                        + "    while(ptr <= n - 2) {\n"
                        + "        if(arr[ptr] < arr[ptr + 1]) {\n"
                        + "            temp = arr[ptr];\n"
                        + "            arr[ptr] = arr[ptr + 1];\n"
                        + "            arr[ptr + 1] = temp;\n"
                        + "        }\n"
                        + "        ptr++;\n"
                        + "    }\n\n"
                        + "    bubble(arr, n - 1);\n"
                        + "}\n\n"
                        + "int main() {\n"
                        + "    int n, i;\n"
                        + "    printf(\"Enter number of elements: \");\n"
                        + "    scanf(\"%d\", &n);\n\n"
                        + "    int arr[n];\n"
                        + "    printf(\"Enter elements:\\n\");\n"
                        + "    for(i = 0; i < n; i++)\n"
                        + "        scanf(\"%d\", &arr[i]);\n\n"
                        + "    bubble(arr, n);\n\n"
                        + "    printf(\"Sorted array:\\n\");\n"
                        + "    for(i = 0; i < n; i++)\n"
                        + "        printf(\"%d \", arr[i]);\n"
                        + "}";
                }
                return "#include <stdio.h>\n"
                    + "int main() {\n"
                    + "    int n, i, k, temp;\n\n"
                    + "    printf(\"Enter number of elements: \");\n"
                    + "    scanf(\"%d\", &n);\n\n"
                    + "    int arr[n];\n"
                    + "    printf(\"Enter elements:\\n\");\n"
                    + "    for(i = 0; i < n; i++)\n"
                    + "        scanf(\"%d\", &arr[i]);\n\n"
                    + "    for(k = 1; k <= n - 1; k++) {\n"
                    + "        int ptr = 0;\n"
                    + "        while(ptr <= n - k - 1) {\n"
                    + "            if(arr[ptr] < arr[ptr + 1]) {\n"
                    + "                temp = arr[ptr];\n"
                    + "                arr[ptr] = arr[ptr + 1];\n"
                    + "                arr[ptr + 1] = temp;\n"
                    + "            }\n"
                    + "            ptr++;\n"
                    + "        }\n"
                    + "    }\n\n"
                    + "    printf(\"Sorted array:\\n\");\n"
                    + "    for(i = 0; i < n; i++)\n"
                    + "        printf(\"%d \", arr[i]);\n"
                    + "}";
            case "selection_sort":
                if ("Functions".equals(style)) {
                    return "int find_min_index(int arr[], int n, int start) {\n"
                        + "    int min_index = start;\n"
                        + "    for (int i = start + 1; i < n; i++) {\n"
                        + "        if (arr[i] < arr[min_index]) {\n"
                        + "            min_index = i;\n"
                        + "        }\n"
                        + "    }\n"
                        + "    return min_index;\n"
                        + "}\n\n"
                        + "void swap_values(int arr[], int left, int right) {\n"
                        + "    int temp = arr[left];\n"
                        + "    arr[left] = arr[right];\n"
                        + "    arr[right] = temp;\n"
                        + "}\n\n"
                        + "void selection_sort(int arr[], int n) {\n"
                        + "    for (int i = 0; i < n - 1; i++) {\n"
                        + "        int min_index = find_min_index(arr, n, i);\n"
                        + "        swap_values(arr, i, min_index);\n"
                        + "    }\n"
                        + "}";
                }
                if ("Recursion".equals(style)) {
                    return "void selection_sort_recursive(int arr[], int n, int start) {\n"
                        + "    if (start >= n - 1) {\n"
                        + "        return;\n"
                        + "    }\n"
                        + "    int min_index = start;\n"
                        + "    for (int i = start + 1; i < n; i++) {\n"
                        + "        if (arr[i] < arr[min_index]) {\n"
                        + "            min_index = i;\n"
                        + "        }\n"
                        + "    }\n"
                        + "    int temp = arr[start];\n"
                        + "    arr[start] = arr[min_index];\n"
                        + "    arr[min_index] = temp;\n"
                        + "    selection_sort_recursive(arr, n, start + 1);\n"
                        + "}";
                }
                return "void selection_sort(int arr[], int n) {\n"
                    + "    for (int i = 0; i < n - 1; i++) {\n"
                    + "        int min_index = i;\n"
                    + "        for (int j = i + 1; j < n; j++) {\n"
                    + "            if (arr[j] < arr[min_index]) {\n"
                    + "                min_index = j;\n"
                    + "            }\n"
                    + "        }\n"
                    + "        int temp = arr[i];\n"
                    + "        arr[i] = arr[min_index];\n"
                    + "        arr[min_index] = temp;\n"
                    + "    }\n"
                    + "}";
            case "insertion_sort":
                if ("Functions".equals(style)) {
                    return "void place_value(int arr[], int index) {\n"
                        + "    int key = arr[index];\n"
                        + "    int j = index - 1;\n"
                        + "    while (j >= 0 && arr[j] > key) {\n"
                        + "        arr[j + 1] = arr[j];\n"
                        + "        j--;\n"
                        + "    }\n"
                        + "    arr[j + 1] = key;\n"
                        + "}\n\n"
                        + "void insertion_sort(int arr[], int n) {\n"
                        + "    for (int i = 1; i < n; i++) {\n"
                        + "        place_value(arr, i);\n"
                        + "    }\n"
                        + "}";
                }
                if ("Recursion".equals(style)) {
                    return "void insertion_sort_recursive(int arr[], int n) {\n"
                        + "    if (n <= 1) {\n"
                        + "        return;\n"
                        + "    }\n"
                        + "    insertion_sort_recursive(arr, n - 1);\n"
                        + "    int key = arr[n - 1];\n"
                        + "    int j = n - 2;\n"
                        + "    while (j >= 0 && arr[j] > key) {\n"
                        + "        arr[j + 1] = arr[j];\n"
                        + "        j--;\n"
                        + "    }\n"
                        + "    arr[j + 1] = key;\n"
                        + "}";
                }
                return "void insertion_sort(int arr[], int n) {\n"
                    + "    for (int i = 1; i < n; i++) {\n"
                    + "        int key = arr[i];\n"
                    + "        int j = i - 1;\n"
                    + "        while (j >= 0 && arr[j] > key) {\n"
                    + "            arr[j + 1] = arr[j];\n"
                    + "            j--;\n"
                    + "        }\n"
                    + "        arr[j + 1] = key;\n"
                    + "    }\n"
                    + "}";
            case "stack_basics":
                return getCStackBasics(style);
            case "linked_stack_push":
                return getCLinkedStackPush(style);
            case "linked_stack_pop":
                return getCLinkedStackPop(style);
            case "quicksort":
                return getCQuicksort(style);
            case "queue_basics":
                return getCLinearQueue(style);
            case "circular_queue":
                return getCCircularQueue(style);
            case "linked_queue":
                return getCLinkedQueue(style);
            case "priority_queue_array":
                return getCPriorityQueueArray(style);
            case "priority_queue_2d":
                return getCPriorityQueue2d(style);
            case "preorder_traversal":
                return getCPreorderTraversal(style);
            case "postorder_traversal":
                return getCPostorderTraversal(style);
            case "bst_search_parent_tracking":
                return getCBstSearchParentTracking(style);
            case "bst_insertion":
                return getCBstInsertion(style);
            case "bst_deletion_case_a":
                return getCBstDeletionCaseA(style);
            case "bst_deletion_case_b":
                return getCBstDeletionCaseB(style);
            case "bst_deletion_del":
                return getCBstDeletionDel(style);
            case "max_heap_insertion":
                return getCMaxHeapInsertion(style);
            case "max_heap_deletion":
                return getCMaxHeapDeletion(style);
            case "heapsort":
                return getCHeapSort(style);
            case "singly_linked_list":
                if ("Functions".equals(style)) {
                    return "typedef struct Node {\n"
                        + "    int data;\n"
                        + "    struct Node *next;\n"
                        + "} Node;\n\n"
                        + "Node *create_node(int value) {\n"
                        + "    Node *node = (Node *)malloc(sizeof(Node));\n"
                        + "    node->data = value;\n"
                        + "    node->next = NULL;\n"
                        + "    return node;\n"
                        + "}\n\n"
                        + "Node *get_tail(Node *head) {\n"
                        + "    while (head->next != NULL) {\n"
                        + "        head = head->next;\n"
                        + "    }\n"
                        + "    return head;\n"
                        + "}\n\n"
                        + "Node *insert_at_end(Node *head, int value) {\n"
                        + "    if (head == NULL) {\n"
                        + "        return create_node(value);\n"
                        + "    }\n"
                        + "    get_tail(head)->next = create_node(value);\n"
                        + "    return head;\n"
                        + "}";
                }
                if ("Recursion".equals(style)) {
                    return "typedef struct Node {\n"
                        + "    int data;\n"
                        + "    struct Node *next;\n"
                        + "} Node;\n\n"
                        + "Node *insert_at_end_recursive(Node *head, int value) {\n"
                        + "    if (head == NULL) {\n"
                        + "        Node *node = (Node *)malloc(sizeof(Node));\n"
                        + "        node->data = value;\n"
                        + "        node->next = NULL;\n"
                        + "        return node;\n"
                        + "    }\n"
                        + "    head->next = insert_at_end_recursive(head->next, value);\n"
                        + "    return head;\n"
                        + "}";
                }
                return "typedef struct Node {\n"
                    + "    int data;\n"
                    + "    struct Node *next;\n"
                    + "} Node;\n\n"
                    + "void insert_at_end(Node *head, int value) {\n"
                    + "    Node *current = head;\n"
                    + "    while (current->next != NULL) {\n"
                    + "        current = current->next;\n"
                    + "    }\n"
                    + "    current->next = malloc(sizeof(Node));\n"
                    + "    current->next->data = value;\n"
                    + "    current->next->next = NULL;\n"
                    + "}";
            case "traversing_linked_list":
            case "print_linked_list":
                if ("Functions".equals(style)) {
                    return "#include <stdio.h>\n#include <stdlib.h>\n"
                        + "struct node {\nint data;\nstruct node* next;\n};\n"
                        + "void printList(struct node* ptr) {\nwhile(ptr != NULL) {\nprintf(\"%d \", ptr->data);\nptr = ptr->next;\n}\n}\n"
                        + "int main() {\nint n, i;\nprintf(\"Enter number of nodes: \");\nscanf(\"%d\" , &n);\n"
                        + "struct node *head = NULL, *temp, *newNode;\n"
                        + "for(i = 0; i < n; i++) {\nnewNode = (struct node*)malloc(sizeof(struct node));\nprintf(\"Enter data: \");\nscanf(\"%d\" , &newNode->data);\nnewNode->next = NULL;\nif(head == NULL)\nhead = temp = newNode;\nelse {\ntemp->next = newNode;\ntemp = newNode;\n}\n}\nprintList(head);\n}";
                }
                if ("Recursion".equals(style)) {
                    return "#include <stdio.h>\n#include <stdlib.h>\n"
                        + "struct node {\nint data;\nstruct node* next;\n};\n"
                        + "void printList(struct node* ptr) {\nif(ptr == NULL)\nreturn;\nprintf(\"%d \", ptr->data);\nprintList(ptr->next);\n}\n"
                        + "int main() {\nint n, i;\nprintf(\"Enter number of nodes: \");\nscanf(\"%d\" , &n);\n"
                        + "struct node *head = NULL, *temp, *newNode;\n"
                        + "for(i = 0; i < n; i++) {\nnewNode = (struct node*)malloc(sizeof(struct node));\nprintf(\"Enter data: \");\nscanf(\"%d\" , &newNode->data);\nnewNode->next = NULL;\nif(head == NULL)\nhead = temp = newNode;\nelse {\ntemp->next = newNode;\ntemp = newNode;\n}\n}\nprintList(head);\n}";
                }
                return "#include <stdio.h>\n#include <stdlib.h>\n"
                    + "struct node {\nint data;\nstruct node* next;\n};\n"
                    + "int main() {\nint n, i;\nprintf(\"Enter number of nodes: \");\nscanf(\"%d\" , &n);\nstruct node *head = NULL, *temp, *newNode;\n"
                    + "for(i = 0; i < n; i++) {\nnewNode = (struct node*)malloc(sizeof(struct node));\nprintf(\"Enter data: \");\nscanf(\"%d\" , &newNode->data);\nnewNode->next = NULL;\nif(head == NULL)\nhead = temp = newNode;\nelse {\ntemp->next = newNode;\ntemp = newNode;\n}\n}\nstruct node* ptr = head;\nwhile(ptr != NULL) {\nprintf(\"%d \", ptr->data);\nptr = ptr->next;\n}\n}";
            case "count_nodes_linked_list":
                return getCCountNodesLinkedList(style);
            case "search_in_linked_list":
                return getCSearchInLinkedList(style);
            case "insert_at_beginning_linked_list":
                return getCInsertAtBeginningLinkedList(style);
            case "insert_after_given_node_linked_list":
                return getCInsertAfterGivenNodeLinkedList(style);
            case "insert_into_sorted_linked_list":
                return getCInsertIntoSortedLinkedList(style);
            case "delete_node_given_location_linked_list":
                return getCDeleteNodeGivenLocationLinkedList(style);
            case "delete_node_by_item_linked_list":
                return getCDeleteNodeByItemLinkedList(style);
            case "traverse_circular_header_list":
                return getCTraverseCircularHeaderList(style);
            case "search_circular_header_list":
                return getCSearchCircularHeaderList(style);
            case "delete_circular_header_list":
                return getCDeleteCircularHeaderList(style);
            case "tree_traversal":
                if ("Functions".equals(style)) {
                    return "void visit_node(struct Node *node) {\n"
                        + "    if (node == NULL) {\n"
                        + "        return;\n"
                        + "    }\n"
                        + "    printf(\"%d \", node->data);\n"
                        + "}\n\n"
                        + "void preorder(struct Node *root) {\n"
                        + "    if (root == NULL) {\n"
                        + "        return;\n"
                        + "    }\n"
                        + "    visit_node(root);\n"
                        + "    preorder(root->left);\n"
                        + "    preorder(root->right);\n"
                        + "}";
                }
                if ("Recursion".equals(style)) {
                    return "void inorder(struct Node *root) {\n"
                        + "    if (root == NULL) {\n"
                        + "        return;\n"
                        + "    }\n"
                        + "    inorder(root->left);\n"
                        + "    printf(\"%d \", root->data);\n"
                        + "    inorder(root->right);\n"
                        + "}";
                }
                return "#include <stdio.h>\n\n"
                    + "void inorder_iterative(struct Node *root) {\n"
                    + "    struct Node *stack[100];\n"
                    + "    int top = -1;\n"
                    + "    struct Node *current = root;\n\n"
                    + "    while (current != NULL || top != -1) {\n"
                    + "        while (current != NULL) {\n"
                    + "            stack[++top] = current;\n"
                    + "            current = current->left;\n"
                    + "        }\n"
                    + "        current = stack[top--];\n"
                    + "        printf(\"%d \", current->data);\n"
                    + "        current = current->right;\n"
                    + "    }\n"
                    + "}";
            case "graph_traversal":
                if ("Functions".equals(style)) {
                    return "void mark_visited(int visited[], int node, int queue[], int *rear) {\n"
                        + "    visited[node] = 1;\n"
                        + "    queue[++(*rear)] = node;\n"
                        + "}\n\n"
                        + "void add_neighbors(int graph[10][10], int size, int node, int visited[], int queue[], int *rear) {\n"
                        + "    for (int next = 0; next < size; next++) {\n"
                        + "        if (graph[node][next] && !visited[next]) {\n"
                        + "            mark_visited(visited, next, queue, rear);\n"
                        + "        }\n"
                        + "    }\n"
                        + "}\n\n"
                        + "void bfs(int start, int graph[10][10], int size) {\n"
                        + "    int visited[10] = {0};\n"
                        + "    int queue[10];\n"
                        + "    int front = 0;\n"
                        + "    int rear = -1;\n"
                        + "    mark_visited(visited, start, queue, &rear);\n"
                        + "    while (front <= rear) {\n"
                        + "        int node = queue[front++];\n"
                        + "        printf(\"%d \", node);\n"
                        + "        add_neighbors(graph, size, node, visited, queue, &rear);\n"
                        + "    }\n"
                        + "}";
                }
                if ("Recursion".equals(style)) {
                    return "void dfs(int node, int graph[10][10], int visited[], int size) {\n"
                        + "    visited[node] = 1;\n"
                        + "    printf(\"%d \", node);\n"
                        + "    for (int next = 0; next < size; next++) {\n"
                        + "        if (graph[node][next] && !visited[next]) {\n"
                        + "            dfs(next, graph, visited, size);\n"
                        + "        }\n"
                        + "    }\n"
                        + "}";
                }
                return "void bfs(int start, int graph[10][10], int size) {\n"
                    + "    int visited[10] = {0};\n"
                    + "    int queue[10];\n"
                    + "    int front = 0;\n"
                    + "    int rear = -1;\n"
                    + "    queue[++rear] = start;\n"
                    + "    visited[start] = 1;\n"
                    + "    while (front <= rear) {\n"
                    + "        int node = queue[front++];\n"
                    + "        printf(\"%d \", node);\n"
                    + "        for (int next = 0; next < size; next++) {\n"
                    + "            if (graph[node][next] && !visited[next]) {\n"
                    + "                visited[next] = 1;\n"
                    + "                queue[++rear] = next;\n"
                    + "            }\n"
                    + "        }\n"
                    + "    }\n"
                    + "}";
            case "warshall_algorithm":
                return getCWarshallAlgorithm(style);
            case "floyd_algorithm":
                return getCFloydAlgorithm(style);
            case "find_edge_graph":
                return getCFindEdgeGraph(style);
            case "insert_node_graph":
                return getCInsertNodeGraph(style);
            case "insert_edge_graph":
                return getCInsertEdgeGraph(style);
            case "delete_edge_graph":
                return getCDeleteEdgeGraph(style);
            case "delete_vertex_graph":
                return getCDeleteVertexGraph(style);
            case "breadth_first_search":
                return getCBreadthFirstSearch(style);
            case "depth_first_search":
                return getCDepthFirstSearch(style);
            case "topological_sort":
                return getCTopologicalSort(style);
            default:
                return "/* C code for this topic will be added here. */";
        }
    }

    private static String getPythonCodeSnippet(String techniqueId, String style) {
        if ("selection_sort".equals(techniqueId)) {
            return getPythonSelectionSortPdf(style);
        }
        if ("insertion_sort".equals(techniqueId)) {
            return getPythonInsertionSortPdf(style);
        }
        if ("merging".equals(techniqueId)) {
            return getPythonMergingPdf(style);
        }
        if ("merge_sort".equals(techniqueId)) {
            return getPythonMergeSortPdf(style);
        }
        if ("radix_sort".equals(techniqueId)) {
            return getPythonRadixSortPdf(style);
        }
        if ("find_linked_list".equals(techniqueId)) {
            return getPythonSearchInLinkedList(style);
        }
        if ("delete_linked_list".equals(techniqueId)) {
            return getPythonDeleteNodeByItemLinkedList(style);
        }
        switch (techniqueId) {
            case "linear_search":
                if ("Functions".equals(style)) {
                    return "def linear(arr, n, item):\n"
                        + "    loc = 1\n"
                        + "    arr[n + 1] = item\n"
                        + "    while arr[loc] != item:\n"
                        + "        loc += 1\n\n"
                        + "    if loc == n + 1:\n"
                        + "        return 0\n"
                        + "    else:\n"
                        + "        return loc\n\n"
                        + "n = int(input())\n"
                        + "arr = [0] + list(map(int, input().split())) + [0]\n"
                        + "item = int(input())\n"
                        + "loc = linear(arr, n, item)\n"
                        + "print(\"Location:\", loc)";
                }
                if ("Recursion".equals(style)) {
                    return "def linear(arr, n, item, loc):\n"
                        + "    if arr[loc] == item:\n"
                        + "        if loc == n + 1:\n"
                        + "            return 0\n"
                        + "        else:\n"
                        + "            return loc\n"
                        + "    return linear(arr, n, item, loc + 1)\n\n"
                        + "n = int(input())\n"
                        + "arr = [0] + list(map(int, input().split())) + [0]\n"
                        + "item = int(input())\n"
                        + "arr[n + 1] = item\n"
                        + "loc = linear(arr, n, item, 1)\n"
                        + "print(\"Location:\", loc)";
                }
                return "n = int(input())\n"
                    + "arr = [0] + list(map(int, input().split())) + [0]\n"
                    + "item = int(input())\n\n"
                    + "arr[n + 1] = item\n"
                    + "loc = 1\n"
                    + "while arr[loc] != item:\n"
                    + "    loc += 1\n\n"
                    + "if loc == n + 1:\n"
                    + "    loc = 0\n\n"
                    + "print(\"Location:\", loc)";
            case "binary_search":
                if ("Functions".equals(style)) {
                    return "def binary(arr, lb, ub, item):\n"
                        + "    beg = lb\n"
                        + "    end = ub\n"
                        + "    while beg <= end:\n"
                        + "        mid = (beg + end) // 2\n\n"
                        + "        if arr[mid] == item:\n"
                        + "            return mid\n"
                        + "        elif item < arr[mid]:\n"
                        + "            end = mid - 1\n"
                        + "        else:\n"
                        + "            beg = mid + 1\n\n"
                        + "    return -1\n\n"
                        + "n = int(input())\n"
                        + "arr = list(map(int, input().split()))\n"
                        + "item = int(input())\n"
                        + "loc = binary(arr, 0, n - 1, item)\n"
                        + "print(\"Location:\", loc)";
                }
                if ("Recursion".equals(style)) {
                    return "def binary(arr, beg, end, item):\n"
                        + "    if beg > end:\n"
                        + "        return -1\n"
                        + "    mid = (beg + end) // 2\n\n"
                        + "    if arr[mid] == item:\n"
                        + "        return mid\n"
                        + "    elif item < arr[mid]:\n"
                        + "        return binary(arr, beg, mid - 1, item)\n"
                        + "    else:\n"
                        + "        return binary(arr, mid + 1, end, item)\n\n"
                        + "n = int(input())\n"
                        + "arr = list(map(int, input().split()))\n"
                        + "item = int(input())\n"
                        + "loc = binary(arr, 0, n - 1, item)\n"
                        + "print(\"Location:\", loc)";
                }
                return "n = int(input())\n"
                    + "arr = list(map(int, input().split()))\n"
                    + "item = int(input())\n\n"
                    + "beg = 0\n"
                    + "end = n - 1\n"
                    + "mid = (beg + end) // 2\n"
                    + "while beg <= end and arr[mid] != item:\n"
                    + "    if item < arr[mid]:\n"
                    + "        end = mid - 1\n"
                    + "    else:\n"
                    + "        beg = mid + 1\n"
                    + "    mid = (beg + end) // 2\n"
                    + "if beg <= end and arr[mid] == item:\n"
                    + "    print(\"Location:\", mid)\n"
                    + "else:\n"
                    + "    print(\"Location: -1\")";
            case "jump_search":
                if ("Functions".equals(style)) {
                    return "import math\n\n"
                        + "def get_block_size(length):\n"
                        + "    return int(math.sqrt(length))\n\n"
                        + "def scan_block(arr, target, start, end):\n"
                        + "    for index in range(start, end):\n"
                        + "        if arr[index] == target:\n"
                        + "            return index\n"
                        + "    return -1\n\n"
                        + "def jump_search(arr, target):\n"
                        + "    block_size = get_block_size(len(arr))\n"
                        + "    start = 0\n"
                        + "    while start < len(arr) and arr[min(start + block_size, len(arr)) - 1] < target:\n"
                        + "        start += block_size\n"
                        + "    return scan_block(arr, target, start, min(start + block_size, len(arr)))";
                }
                if ("Recursion".equals(style)) {
                    return "import math\n\n"
                        + "def recursive_scan(arr, target, index, end):\n"
                        + "    if index > end:\n"
                        + "        return -1\n"
                        + "    if arr[index] == target:\n"
                        + "        return index\n"
                        + "    return recursive_scan(arr, target, index + 1, end)\n\n"
                        + "def jump_search_recursive(arr, target, start=0, block_size=None):\n"
                        + "    if block_size is None:\n"
                        + "        block_size = int(math.sqrt(len(arr)))\n"
                        + "    if start >= len(arr):\n"
                        + "        return -1\n"
                        + "    end = min(start + block_size, len(arr))\n"
                        + "    if arr[end - 1] >= target:\n"
                        + "        return recursive_scan(arr, target, start, end - 1)\n"
                        + "    return jump_search_recursive(arr, target, end, block_size)";
                }
                return "import math\n\n"
                    + "def jump_search(arr, target):\n"
                    + "    step = int(math.sqrt(len(arr)))\n"
                    + "    prev = 0\n"
                    + "    while prev < len(arr) and arr[min(step, len(arr)) - 1] < target:\n"
                    + "        prev = step\n"
                    + "        step += int(math.sqrt(len(arr)))\n"
                    + "        if prev >= len(arr):\n"
                    + "            return -1\n"
                    + "    while prev < min(step, len(arr)):\n"
                    + "        if arr[prev] == target:\n"
                    + "            return prev\n"
                    + "        prev += 1\n"
                    + "    return -1";
            case "bubble_sort":
                if ("Functions".equals(style)) {
                    return "def bubble(arr, n):\n"
                        + "    for k in range(1, n):\n"
                        + "        ptr = 0\n"
                        + "        while ptr <= n - k - 1:\n"
                        + "            if arr[ptr] < arr[ptr + 1]:\n"
                        + "                arr[ptr], arr[ptr + 1] = arr[ptr + 1], arr[ptr]\n"
                        + "            ptr += 1\n\n"
                        + "n = int(input())\n"
                        + "arr = list(map(int, input().split()))\n\n"
                        + "bubble(arr, n)\n"
                        + "print(*arr)";
                }
                if ("Recursion".equals(style)) {
                    return "def bubble(arr, n):\n"
                        + "    if n == 1:\n"
                        + "        return\n\n"
                        + "    ptr = 0\n"
                        + "    while ptr <= n - 2:\n"
                        + "        if arr[ptr] < arr[ptr + 1]:\n"
                        + "            arr[ptr], arr[ptr + 1] = arr[ptr + 1], arr[ptr]\n"
                        + "        ptr += 1\n\n"
                        + "    bubble(arr, n - 1)\n\n"
                        + "n = int(input())\n"
                        + "arr = list(map(int, input().split()))\n\n"
                        + "bubble(arr, n)\n"
                        + "print(*arr)";
                }
                return "n = int(input())\n"
                    + "arr = list(map(int, input().split()))\n\n"
                    + "for k in range(1, n):\n"
                    + "    ptr = 0\n"
                    + "    while ptr <= n - k - 1:\n"
                    + "        if arr[ptr] < arr[ptr + 1]:\n"
                    + "            arr[ptr], arr[ptr + 1] = arr[ptr + 1], arr[ptr]\n"
                    + "        ptr += 1\n\n"
                    + "print(*arr)";
            case "selection_sort":
                if ("Functions".equals(style)) {
                    return "def find_min_index(arr, start):\n"
                        + "    min_index = start\n"
                        + "    for index in range(start + 1, len(arr)):\n"
                        + "        if arr[index] < arr[min_index]:\n"
                        + "            min_index = index\n"
                        + "    return min_index\n\n"
                        + "def selection_sort(arr):\n"
                        + "    for i in range(len(arr) - 1):\n"
                        + "        min_index = find_min_index(arr, i)\n"
                        + "        arr[i], arr[min_index] = arr[min_index], arr[i]";
                }
                if ("Recursion".equals(style)) {
                    return "def selection_sort_recursive(arr, start=0):\n"
                        + "    if start >= len(arr) - 1:\n"
                        + "        return\n"
                        + "    min_index = start\n"
                        + "    for index in range(start + 1, len(arr)):\n"
                        + "        if arr[index] < arr[min_index]:\n"
                        + "            min_index = index\n"
                        + "    arr[start], arr[min_index] = arr[min_index], arr[start]\n"
                        + "    selection_sort_recursive(arr, start + 1)";
                }
                return "def selection_sort(arr):\n"
                    + "    for i in range(len(arr) - 1):\n"
                    + "        min_index = i\n"
                    + "        for j in range(i + 1, len(arr)):\n"
                    + "            if arr[j] < arr[min_index]:\n"
                    + "                min_index = j\n"
                    + "        arr[i], arr[min_index] = arr[min_index], arr[i]";
            case "insertion_sort":
                if ("Functions".equals(style)) {
                    return "def place_value(arr, index):\n"
                        + "    key = arr[index]\n"
                        + "    j = index - 1\n"
                        + "    while j >= 0 and arr[j] > key:\n"
                        + "        arr[j + 1] = arr[j]\n"
                        + "        j -= 1\n"
                        + "    arr[j + 1] = key\n\n"
                        + "def insertion_sort(arr):\n"
                        + "    for index in range(1, len(arr)):\n"
                        + "        place_value(arr, index)";
                }
                if ("Recursion".equals(style)) {
                    return "def insertion_sort_recursive(arr, n=None):\n"
                        + "    if n is None:\n"
                        + "        n = len(arr)\n"
                        + "    if n <= 1:\n"
                        + "        return\n"
                        + "    insertion_sort_recursive(arr, n - 1)\n"
                        + "    key = arr[n - 1]\n"
                        + "    j = n - 2\n"
                        + "    while j >= 0 and arr[j] > key:\n"
                        + "        arr[j + 1] = arr[j]\n"
                        + "        j -= 1\n"
                        + "    arr[j + 1] = key";
                }
                return "def insertion_sort(arr):\n"
                    + "    for i in range(1, len(arr)):\n"
                    + "        key = arr[i]\n"
                    + "        j = i - 1\n"
                    + "        while j >= 0 and arr[j] > key:\n"
                    + "            arr[j + 1] = arr[j]\n"
                    + "            j -= 1\n"
                    + "        arr[j + 1] = key";
            case "stack_basics":
                return getPythonStackBasics(style);
            case "linked_stack_push":
                return getPythonLinkedStackPush(style);
            case "linked_stack_pop":
                return getPythonLinkedStackPop(style);
            case "quicksort":
                return getPythonQuicksort(style);
            case "queue_basics":
                return getPythonLinearQueue(style);
            case "circular_queue":
                return getPythonCircularQueue(style);
            case "linked_queue":
                return getPythonLinkedQueue(style);
            case "priority_queue_array":
                return getPythonPriorityQueueArray(style);
            case "priority_queue_2d":
                return getPythonPriorityQueue2d(style);
            case "preorder_traversal":
                return getPythonPreorderTraversal(style);
            case "postorder_traversal":
                return getPythonPostorderTraversal(style);
            case "bst_search_parent_tracking":
                return getPythonBstSearchParentTracking(style);
            case "bst_insertion":
                return getPythonBstInsertion(style);
            case "bst_deletion_case_a":
                return getPythonBstDeletionCaseA(style);
            case "bst_deletion_case_b":
                return getPythonBstDeletionCaseB(style);
            case "bst_deletion_del":
                return getPythonBstDeletionDel(style);
            case "max_heap_insertion":
                return getPythonMaxHeapInsertion(style);
            case "max_heap_deletion":
                return getPythonMaxHeapDeletion(style);
            case "heapsort":
                return getPythonHeapSort(style);
            case "singly_linked_list":
                if ("Functions".equals(style)) {
                    return "class Node:\n"
                        + "    def __init__(self, data):\n"
                        + "        self.data = data\n"
                        + "        self.next = None\n\n"
                        + "def get_tail(node):\n"
                        + "    while node.next is not None:\n"
                        + "        node = node.next\n"
                        + "    return node\n\n"
                        + "def insert_at_end(head, value):\n"
                        + "    if head is None:\n"
                        + "        return Node(value)\n"
                        + "    get_tail(head).next = Node(value)\n"
                        + "    return head";
                }
                if ("Recursion".equals(style)) {
                    return "class Node:\n"
                        + "    def __init__(self, data):\n"
                        + "        self.data = data\n"
                        + "        self.next = None\n\n"
                        + "def insert_at_end_recursive(head, value):\n"
                        + "    if head is None:\n"
                        + "        return Node(value)\n"
                        + "    head.next = insert_at_end_recursive(head.next, value)\n"
                        + "    return head";
                }
                return "class Node:\n"
                    + "    def __init__(self, data):\n"
                    + "        self.data = data\n"
                    + "        self.next = None\n\n"
                    + "def insert_at_end(head, value):\n"
                    + "    current = head\n"
                    + "    while current.next is not None:\n"
                    + "        current = current.next\n"
                    + "    current.next = Node(value)";
            case "traversing_linked_list":
            case "print_linked_list":
                if ("Functions".equals(style)) {
                    return "class Node:\n"
                        + "    def __init__(self, data):\n"
                        + "        self.data = data\n"
                        + "        self.next = None\n\n"
                        + "def printList(ptr):\n"
                        + "    while ptr:\n"
                        + "        print(ptr.data, end=\" \")\n"
                        + "        ptr = ptr.next\n\n"
                        + "n = int(input())\n"
                        + "head = None\n"
                        + "temp = None\n"
                        + "for _ in range(n):\n"
                        + "    val = int(input())\n"
                        + "    newNode = Node(val)\n"
                        + "    if head is None:\n"
                        + "        head = temp = newNode\n"
                        + "    else:\n"
                        + "        temp.next = newNode\n"
                        + "        temp = newNode\n"
                        + "printList(head)";
                }
                if ("Recursion".equals(style)) {
                    return "class Node:\n"
                        + "    def __init__(self, data):\n"
                        + "        self.data = data\n"
                        + "        self.next = None\n\n"
                        + "def printList(ptr):\n"
                        + "    if ptr is None:\n"
                        + "        return\n"
                        + "    print(ptr.data, end=\" \")\n"
                        + "    printList(ptr.next)\n\n"
                        + "n = int(input())\n"
                        + "head = None\n"
                        + "temp = None\n"
                        + "for _ in range(n):\n"
                        + "    val = int(input())\n"
                        + "    newNode = Node(val)\n"
                        + "    if head is None:\n"
                        + "        head = temp = newNode\n"
                        + "    else:\n"
                        + "        temp.next = newNode\n"
                        + "        temp = newNode\n"
                        + "printList(head)";
                }
                return "class Node:\n"
                    + "    def __init__(self, data):\n"
                    + "        self.data = data\n"
                    + "        self.next = None\n\n"
                    + "n = int(input())\n"
                    + "head = None\n"
                    + "temp = None\n"
                    + "for _ in range(n):\n"
                    + "    val = int(input())\n"
                    + "    newNode = Node(val)\n"
                    + "    if head is None:\n"
                    + "        head = temp = newNode\n"
                    + "    else:\n"
                    + "        temp.next = newNode\n"
                    + "        temp = newNode\n"
                    + "ptr = head\n"
                    + "while ptr:\n"
                    + "    print(ptr.data, end=\" \")\n"
                    + "    ptr = ptr.next";
            case "count_nodes_linked_list":
                return getPythonCountNodesLinkedList(style);
            case "search_in_linked_list":
                return getPythonSearchInLinkedList(style);
            case "insert_at_beginning_linked_list":
                return getPythonInsertAtBeginningLinkedList(style);
            case "insert_after_given_node_linked_list":
                return getPythonInsertAfterGivenNodeLinkedList(style);
            case "insert_into_sorted_linked_list":
                return getPythonInsertIntoSortedLinkedList(style);
            case "delete_node_given_location_linked_list":
                return getPythonDeleteNodeGivenLocationLinkedList(style);
            case "delete_node_by_item_linked_list":
                return getPythonDeleteNodeByItemLinkedList(style);
            case "traverse_circular_header_list":
                return getPythonTraverseCircularHeaderList(style);
            case "search_circular_header_list":
                return getPythonSearchCircularHeaderList(style);
            case "delete_circular_header_list":
                return getPythonDeleteCircularHeaderList(style);
            case "tree_traversal":
                if ("Functions".equals(style)) {
                    return "def visit(node):\n"
                        + "    if node is None:\n"
                        + "        return\n"
                        + "    print(node.data, end=' ')\n\n"
                        + "def preorder(root):\n"
                        + "    if root is None:\n"
                        + "        return\n"
                        + "    visit(root)\n"
                        + "    preorder(root.left)\n"
                        + "    preorder(root.right)";
                }
                if ("Recursion".equals(style)) {
                    return "def inorder(root):\n"
                        + "    if root is None:\n"
                        + "        return\n"
                        + "    inorder(root.left)\n"
                        + "    print(root.data, end=' ')\n"
                        + "    inorder(root.right)";
                }
                return "def inorder_iterative(root):\n"
                    + "    stack = []\n"
                    + "    current = root\n"
                    + "    while current is not None or stack:\n"
                    + "        while current is not None:\n"
                    + "            stack.append(current)\n"
                    + "            current = current.left\n"
                    + "        current = stack.pop()\n"
                    + "        print(current.data, end=' ')\n"
                    + "        current = current.right";
            case "graph_traversal":
                if ("Functions".equals(style)) {
                    return "from collections import deque\n\n"
                        + "def mark_visited(node, visited, queue):\n"
                        + "    visited.add(node)\n"
                        + "    queue.append(node)\n\n"
                        + "def add_neighbors(node, graph, visited, queue):\n"
                        + "    for next_node in graph[node]:\n"
                        + "        if next_node not in visited:\n"
                        + "            mark_visited(next_node, visited, queue)\n\n"
                        + "def bfs(start, graph):\n"
                        + "    visited = set()\n"
                        + "    queue = deque()\n"
                        + "    mark_visited(start, visited, queue)\n"
                        + "    while queue:\n"
                        + "        node = queue.popleft()\n"
                        + "        print(node, end=' ')\n"
                        + "        add_neighbors(node, graph, visited, queue)";
                }
                if ("Recursion".equals(style)) {
                    return "def dfs(node, graph, visited=None):\n"
                        + "    if visited is None:\n"
                        + "        visited = set()\n"
                        + "    visited.add(node)\n"
                        + "    print(node, end=' ')\n"
                        + "    for next_node in graph[node]:\n"
                        + "        if next_node not in visited:\n"
                        + "            dfs(next_node, graph, visited)";
                }
                return "from collections import deque\n\n"
                    + "def bfs(start, graph):\n"
                    + "    visited = {start}\n"
                    + "    queue = deque([start])\n"
                    + "    while queue:\n"
                    + "        node = queue.popleft()\n"
                    + "        print(node, end=' ')\n"
                    + "        for next_node in graph[node]:\n"
                    + "            if next_node not in visited:\n"
                    + "                visited.add(next_node)\n"
                    + "                queue.append(next_node)";
            case "warshall_algorithm":
                return getPythonWarshallAlgorithm(style);
            case "floyd_algorithm":
                return getPythonFloydAlgorithm(style);
            case "find_edge_graph":
                return getPythonFindEdgeGraph(style);
            case "insert_node_graph":
                return getPythonInsertNodeGraph(style);
            case "insert_edge_graph":
                return getPythonInsertEdgeGraph(style);
            case "delete_edge_graph":
                return getPythonDeleteEdgeGraph(style);
            case "delete_vertex_graph":
                return getPythonDeleteVertexGraph(style);
            case "breadth_first_search":
                return getPythonBreadthFirstSearch(style);
            case "depth_first_search":
                return getPythonDepthFirstSearch(style);
            case "topological_sort":
                return getPythonTopologicalSort(style);
            default:
                return "# Python code for this topic will be added here.";
        }
    }

    private static String getJavaCountNodesLinkedList(String style) {
        if ("Functions".equals(style)) {
            return "import java.util.Scanner;\n"
                + "class Node {\n"
                + "    int data;\n"
                + "    Node next;\n"
                + "    Node(int data) {\n"
                + "        this.data = data;\n"
                + "    }\n"
                + "}\n"
                + "class Main {\n"
                + "    static int count(Node ptr) {\n"
                + "        int num = 0;\n"
                + "        while(ptr != null) {\n"
                + "            num++;\n"
                + "            ptr = ptr.next;\n"
                + "        }\n"
                + "        return num;\n"
                + "    }\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        int n = sc.nextInt();\n"
                + "        Node head = null, temp = null;\n"
                + "        for(int i = 0; i < n; i++) {\n"
                + "            Node newNode = new Node(sc.nextInt());\n"
                + "            if(head == null)\n"
                + "                head = temp = newNode;\n"
                + "            else {\n"
                + "                temp.next = newNode;\n"
                + "                temp = newNode;\n"
                + "            }\n"
                + "        }\n"
                + "        int num = count(head);\n"
                + "        System.out.println(\"Number of nodes: \" + num);\n"
                + "    }\n"
                + "}";
        }
        if ("Recursion".equals(style)) {
            return "import java.util.Scanner;\n"
                + "class Node {\n"
                + "    int data;\n"
                + "    Node next;\n"
                + "    Node(int data) {\n"
                + "        this.data = data;\n"
                + "    }\n"
                + "}\n"
                + "class Main {\n"
                + "    static int count(Node ptr) {\n"
                + "        if(ptr == null)\n"
                + "            return 0;\n"
                + "        return 1 + count(ptr.next);\n"
                + "    }\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        int n = sc.nextInt();\n"
                + "        Node head = null, temp = null;\n"
                + "        for(int i = 0; i < n; i++) {\n"
                + "            Node newNode = new Node(sc.nextInt());\n"
                + "            if(head == null)\n"
                + "                head = temp = newNode;\n"
                + "            else {\n"
                + "                temp.next = newNode;\n"
                + "                temp = newNode;\n"
                + "            }\n"
                + "        }\n"
                + "        int num = count(head);\n"
                + "        System.out.println(\"Number of nodes: \" + num);\n"
                + "    }\n"
                + "}";
        }
        return "import java.util.Scanner;\n"
            + "class Node {\n"
            + "    int data;\n"
            + "    Node next;\n"
            + "    Node(int data) {\n"
            + "        this.data = data;\n"
            + "    }\n"
            + "}\n"
            + "class Main {\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        int n = sc.nextInt();\n"
            + "        Node head = null, temp = null;\n"
            + "        for(int i = 0; i < n; i++) {\n"
            + "            Node newNode = new Node(sc.nextInt());\n"
            + "            if(head == null)\n"
            + "                head = temp = newNode;\n"
            + "            else {\n"
            + "                temp.next = newNode;\n"
            + "                temp = newNode;\n"
            + "            }\n"
            + "        }\n"
            + "        int count = 0;\n"
            + "        Node ptr = head;\n"
            + "        while(ptr != null) {\n"
            + "            count++;\n"
            + "            ptr = ptr.next;\n"
            + "        }\n"
            + "        System.out.println(\"Number of nodes: \" + count);\n"
            + "    }\n"
            + "}";
    }

    private static String getJavaStackBasics(String style) {
        if ("Functions".equals(style)) {
            return "import java.util.*;\n"
                + "public class StackOps {\n"
                + "    static int push(int[] stack, int top, int max, int item) {\n"
                + "        if (top == max) {\n"
                + "            System.out.println(\"OVERFLOW\");\n"
                + "            return top;\n"
                + "        }\n"
                + "        top++;\n"
                + "        stack[top] = item;\n"
                + "        return top;\n"
                + "    }\n"
                + "    static int pop(int[] stack, int top) {\n"
                + "        if (top == 0) {\n"
                + "            System.out.println(\"UNDERFLOW\");\n"
                + "            return top;\n"
                + "        }\n"
                + "        System.out.println(\"Popped: \" + stack[top]);\n"
                + "        return top - 1;\n"
                + "    }\n"
                + "    static void display(int[] stack, int top) {\n"
                + "        if (top == 0) {\n"
                + "            System.out.println(\"Stack is empty\");\n"
                + "            return;\n"
                + "        }\n"
                + "        System.out.print(\"Stack elements: \");\n"
                + "        for (int i = top; i >= 1; i--) {\n"
                + "            System.out.print(stack[i] + \" \");\n"
                + "        }\n"
                + "        System.out.println();\n"
                + "    }\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        System.out.print(\"Enter stack capacity: \");\n"
                + "        int max = sc.nextInt();\n"
                + "        int[] stack = new int[max + 1];\n"
                + "        int top = 0;\n"
                + "        System.out.print(\"Enter number of items to push: \");\n"
                + "        int n = sc.nextInt();\n"
                + "        for (int i = 1; i <= n; i++) {\n"
                + "            System.out.print(\"Enter item \" + i + \": \");\n"
                + "            int item = sc.nextInt();\n"
                + "            top = push(stack, top, max, item);\n"
                + "        }\n"
                + "        display(stack, top);\n"
                + "        top = pop(stack, top);\n"
                + "        display(stack, top);\n"
                + "    }\n"
                + "}";
        }
        if ("Recursion".equals(style)) {
            return "import java.util.*;\n"
                + "public class StackOps {\n"
                + "    public static void main(String[] args) {\n"
                + "        System.out.println(\"Recursion not applicable for basic PUSH and POP operations\");\n"
                + "    }\n"
                + "}";
        }
        return "import java.util.*;\n"
            + "public class StackOps {\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        System.out.print(\"Enter stack capacity: \");\n"
            + "        int max = sc.nextInt();\n"
            + "        int[] stack = new int[max + 1];\n"
            + "        int top = 0;\n"
            + "        System.out.print(\"Enter number of items to push: \");\n"
            + "        int n = sc.nextInt();\n"
            + "        for (int i = 1; i <= n; i++) {\n"
            + "            System.out.print(\"Enter item \" + i + \": \");\n"
            + "            int item = sc.nextInt();\n"
            + "            if (top == max) {\n"
            + "                System.out.println(\"OVERFLOW\");\n"
            + "            } else {\n"
            + "                top++;\n"
            + "                stack[top] = item;\n"
            + "                System.out.println(\"Item pushed\");\n"
            + "            }\n"
            + "        }\n"
            + "        if (top == 0) {\n"
            + "            System.out.println(\"Stack is empty\");\n"
            + "        } else {\n"
            + "            System.out.print(\"Stack elements: \");\n"
            + "            for (int i = top; i >= 1; i--) {\n"
            + "                System.out.print(stack[i] + \" \");\n"
            + "            }\n"
            + "            System.out.println();\n"
            + "        }\n"
            + "        if (top == 0) {\n"
            + "            System.out.println(\"UNDERFLOW\");\n"
            + "        } else {\n"
            + "            int popItem = stack[top];\n"
            + "            top--;\n"
            + "            System.out.println(\"Popped: \" + popItem);\n"
            + "        }\n"
            + "        if (top == 0) {\n"
            + "            System.out.println(\"Stack is empty after pop\");\n"
            + "        } else {\n"
            + "            System.out.print(\"Stack after pop: \");\n"
            + "            for (int i = top; i >= 1; i--) {\n"
            + "                System.out.print(stack[i] + \" \");\n"
            + "            }\n"
            + "            System.out.println();\n"
            + "        }\n"
            + "    }\n"
            + "}";
    }

    private static String getJavaLinkedStackPush(String style) {
        if ("Functions".equals(style)) {
            return "import java.util.*;\n"
                + "class Node {\n"
                + "    int data;\n"
                + "    Node next;\n"
                + "    Node(int data) { this.data = data; this.next = null; }\n"
                + "}\n"
                + "public class LinkedStackPush {\n"
                + "    static Node push(Node top, int item) {\n"
                + "        Node newNode = new Node(item);\n"
                + "        newNode.next = top;\n"
                + "        top = newNode;\n"
                + "        return top;\n"
                + "    }\n"
                + "    static void display(Node top) {\n"
                + "        if (top == null) {\n"
                + "            System.out.println(\"Linked stack is empty\");\n"
                + "            return;\n"
                + "        }\n"
                + "        System.out.print(\"Linked stack elements: \");\n"
                + "        for (Node ptr = top; ptr != null; ptr = ptr.next) {\n"
                + "            System.out.print(ptr.data + \" \");\n"
                + "        }\n"
                + "        System.out.println();\n"
                + "    }\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        Node top = null;\n"
                + "        System.out.print(\"Enter number of items to push: \");\n"
                + "        int n = sc.nextInt();\n"
                + "        for (int i = 1; i <= n; i++) {\n"
                + "            System.out.print(\"Enter item \" + i + \": \");\n"
                + "            int item = sc.nextInt();\n"
                + "            top = push(top, item);\n"
                + "        }\n"
                + "        System.out.println(\"Items pushed to linked stack\");\n"
                + "        display(top);\n"
                + "    }\n"
                + "}";
        }
        if ("Recursion".equals(style)) {
            return "import java.util.*;\n"
                + "public class LinkedStackPush {\n"
                + "    public static void main(String[] args) {\n"
                + "        System.out.println(\"Recursion not applicable for linked stack PUSH\");\n"
                + "    }\n"
                + "}";
        }
        return "import java.util.*;\n"
            + "class Node {\n"
            + "    int data;\n"
            + "    Node next;\n"
            + "    Node(int data) { this.data = data; this.next = null; }\n"
            + "}\n"
            + "public class LinkedStackPush {\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        Node top = null;\n"
            + "        System.out.print(\"Enter number of items to push: \");\n"
            + "        int n = sc.nextInt();\n"
            + "        for (int i = 1; i <= n; i++) {\n"
            + "            System.out.print(\"Enter item \" + i + \": \");\n"
            + "            int item = sc.nextInt();\n"
            + "            Node newNode = new Node(item);\n"
            + "            newNode.next = top;\n"
            + "            top = newNode;\n"
            + "            System.out.println(\"Item pushed to linked stack\");\n"
            + "        }\n"
            + "        if (top == null) {\n"
            + "            System.out.println(\"Linked stack is empty\");\n"
            + "        } else {\n"
            + "            System.out.print(\"Linked stack elements: \");\n"
            + "            for (Node ptr = top; ptr != null; ptr = ptr.next) {\n"
            + "                System.out.print(ptr.data + \" \");\n"
            + "            }\n"
            + "            System.out.println();\n"
            + "        }\n"
            + "    }\n"
            + "}";
    }

    private static String getJavaLinkedStackPop(String style) {
        if ("Functions".equals(style)) {
            return "import java.util.*;\n"
                + "class Node {\n"
                + "    int data;\n"
                + "    Node next;\n"
                + "    Node(int data) { this.data = data; this.next = null; }\n"
                + "}\n"
                + "public class LinkedStackPop {\n"
                + "    static Node pop(Node top) {\n"
                + "        if(top == null) {\n"
                + "            System.out.println(\"UNDERFLOW\");\n"
                + "            return null;\n"
                + "        }\n"
                + "        System.out.println(\"Popped: \" + top.data);\n"
                + "        return top.next;\n"
                + "    }\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        Node top = null;\n"
                + "        System.out.print(\"Enter number of items already in the stack: \");\n"
                + "        int n = sc.nextInt();\n"
                + "        for(int i = 0; i < n; i++) {\n"
                + "            System.out.print(\"Enter stack item \" + (i + 1) + \": \");\n"
                + "            int val = sc.nextInt();\n"
                + "            Node temp = new Node(val);\n"
                + "            temp.next = top;\n"
                + "            top = temp;\n"
                + "        }\n"
                + "        System.out.print(\"Initial linked stack: \");\n"
                + "        for (Node ptr = top; ptr != null; ptr = ptr.next) {\n"
                + "            System.out.print(ptr.data + \" \");\n"
                + "        }\n"
                + "        System.out.println();\n"
                + "        top = pop(top);\n"
                + "    }\n"
                + "}";
        }
        if ("Recursion".equals(style)) {
            return "import java.util.*;\n"
                + "public class LinkedStackPop {\n"
                + "    public static void main(String[] args) {\n"
                + "        System.out.println(\"Recursion not applicable for linked stack POP\");\n"
                + "    }\n"
                + "}";
        }
        return "import java.util.*;\n"
            + "class Node {\n"
            + "    int data;\n"
            + "    Node next;\n"
            + "    Node(int data) { this.data = data; this.next = null; }\n"
            + "}\n"
            + "public class LinkedStackPop {\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        Node top = null;\n"
            + "        System.out.print(\"Enter number of items already in the stack: \");\n"
            + "        int n = sc.nextInt();\n"
            + "        for(int i = 0; i < n; i++) {\n"
            + "            System.out.print(\"Enter stack item \" + (i + 1) + \": \");\n"
            + "            int val = sc.nextInt();\n"
            + "            Node temp = new Node(val);\n"
            + "            temp.next = top;\n"
            + "            top = temp;\n"
            + "        }\n"
            + "        if (top == null) {\n"
            + "            System.out.println(\"Linked stack is empty\");\n"
            + "        } else {\n"
            + "            System.out.print(\"Initial linked stack: \");\n"
            + "            for (Node ptr = top; ptr != null; ptr = ptr.next) {\n"
            + "                System.out.print(ptr.data + \" \");\n"
            + "            }\n"
            + "            System.out.println();\n"
            + "        }\n"
            + "        if(top == null) {\n"
            + "            System.out.println(\"UNDERFLOW\");\n"
            + "        } else {\n"
            + "            int item = top.data;\n"
            + "            top = top.next;\n"
            + "            System.out.println(\"Popped: \" + item);\n"
            + "        }\n"
            + "    }\n"
            + "}";
    }

    private static String getJavaQuicksort(String style) {
        String shared = "import java.util.*;\n"
            + "public class QuickSort {\n"
            + "    static int partition(int[] A, int low, int high) {\n"
            + "        int pivot = A[low];\n"
            + "        int i = low + 1;\n"
            + "        int j = high;\n"
            + "        while (i <= j) {\n"
            + "            while (i <= high && A[i] <= pivot) i++;\n"
            + "            while (A[j] > pivot) j--;\n"
            + "            if (i < j) {\n"
            + "                int temp = A[i];\n"
            + "                A[i] = A[j];\n"
            + "                A[j] = temp;\n"
            + "            }\n"
            + "        }\n"
            + "        int temp = A[low];\n"
            + "        A[low] = A[j];\n"
            + "        A[j] = temp;\n"
            + "        return j;\n"
            + "    }\n";
        if ("Iterative".equals(style)) {
            return shared
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        int n = sc.nextInt();\n"
                + "        int[] A = new int[n];\n"
                + "        for (int i = 0; i < n; i++) {\n"
                + "            A[i] = sc.nextInt();\n"
                + "        }\n"
                + "        int[] lower = new int[n];\n"
                + "        int[] upper = new int[n];\n"
                + "        int top = -1;\n"
                + "        if (n > 1) {\n"
                + "            top++;\n"
                + "            lower[top] = 0;\n"
                + "            upper[top] = n - 1;\n"
                + "        }\n"
                + "        while (top >= 0) {\n"
                + "            int beg = lower[top];\n"
                + "            int end = upper[top];\n"
                + "            top--;\n"
                + "            int loc = partition(A, beg, end);\n"
                + "            if (beg < loc - 1) {\n"
                + "                top++;\n"
                + "                lower[top] = beg;\n"
                + "                upper[top] = loc - 1;\n"
                + "            }\n"
                + "            if (loc + 1 < end) {\n"
                + "                top++;\n"
                + "                lower[top] = loc + 1;\n"
                + "                upper[top] = end;\n"
                + "            }\n"
                + "        }\n"
                + "        for (int x : A)\n"
                + "            System.out.print(x + \" \");\n"
                + "    }\n"
                + "}";
        }
        if ("Functions".equals(style)) {
            return shared
                + "    static void quickSort(int[] A, int low, int high) {\n"
                + "        if (low < high) {\n"
                + "            int loc = partition(A, low, high);\n"
                + "            quickSort(A, low, loc - 1);\n"
                + "            quickSort(A, loc + 1, high);\n"
                + "        }\n"
                + "    }\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        int n = sc.nextInt();\n"
                + "        int[] A = new int[n];\n"
                + "        for (int i = 0; i < n; i++) {\n"
                + "            A[i] = sc.nextInt();\n"
                + "        }\n"
                + "        quickSort(A, 0, n - 1);\n"
                + "        for (int x : A)\n"
                + "            System.out.print(x + \" \");\n"
                + "    }\n"
                + "}";
        }
        return shared
            + "    static void quickSort(int[] A, int low, int high) {\n"
            + "        if (low >= high)\n"
            + "            return;\n"
            + "        int loc = partition(A, low, high);\n"
            + "        quickSort(A, low, loc - 1);\n"
            + "        quickSort(A, loc + 1, high);\n"
            + "    }\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        int n = sc.nextInt();\n"
            + "        int[] A = new int[n];\n"
            + "        for (int i = 0; i < n; i++) {\n"
            + "            A[i] = sc.nextInt();\n"
            + "        }\n"
            + "        quickSort(A, 0, n - 1);\n"
            + "        for (int x : A)\n"
            + "            System.out.print(x + \" \");\n"
            + "    }\n"
            + "}";
    }

    private static String getJavaLinearQueue(String style) {
        if ("Functions".equals(style)) {
            return String.join("\n",
                "import java.util.*;",
                "",
                "public class LinearQueue {",
                "    static int[] insert(int[] queue, int front, int rear, int n, int item) {",
                "        if (rear == n) {",
                "            System.out.println(\"OVERFLOW\");",
                "            return new int[]{front, rear};",
                "        }",
                "",
                "        if (front == -1) {",
                "            front = 0;",
                "            rear = 0;",
                "        } else {",
                "            rear++;",
                "        }",
                "",
                "        queue[rear] = item;",
                "        return new int[]{front, rear};",
                "    }",
                "",
                "    static int[] delete(int[] queue, int front, int rear) {",
                "        if (front == -1) {",
                "            System.out.println(\"UNDERFLOW\");",
                "            return new int[]{front, rear};",
                "        }",
                "",
                "        System.out.println(\"Deleted: \" + queue[front]);",
                "",
                "        if (front == rear) {",
                "            front = -1;",
                "            rear = -1;",
                "        } else {",
                "            front++;",
                "        }",
                "",
                "        return new int[]{front, rear};",
                "    }",
                "",
                "    public static void main(String[] args) {",
                "        Scanner sc = new Scanner(System.in);",
                "        System.out.print(\"Enter queue size: \");",
                "        int n = sc.nextInt();",
                "        int[] queue = new int[n + 1];",
                "        int front = -1, rear = -1;",
                "        System.out.print(\"Enter number of items to insert: \");",
                "        int count = sc.nextInt();",
                "        for (int i = 1; i <= count; i++) {",
                "            System.out.print(\"Enter item \" + i + \": \");",
                "            int item = sc.nextInt();",
                "            int[] res = insert(queue, front, rear, n, item);",
                "            front = res[0];",
                "            rear = res[1];",
                "        }",
                "        if (front == -1) {",
                "            System.out.println(\"Queue is empty\");",
                "        } else {",
                "            System.out.print(\"Queue elements: \");",
                "            for (int i = front; i <= rear; i++) System.out.print(queue[i] + \" \");",
                "            System.out.println();",
                "        }",
                "        res = delete(queue, front, rear);",
                "        front = res[0];",
                "        rear = res[1];",
                "    }",
                "}");
        }
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "import java.util.*;",
                "",
                "public class LinearQueue {",
                "    public static void main(String[] args) {",
                "        System.out.println(\"Recursion not applicable for queue insertion and deletion\");",
                "    }",
                "}");
        }
        return String.join("\n",
            "import java.util.*;",
            "",
            "public class LinearQueue {",
            "    public static void main(String[] args) {",
            "        Scanner sc = new Scanner(System.in);",
            "        System.out.print(\"Enter queue size: \");",
            "        int n = sc.nextInt();",
            "        int[] queue = new int[n + 1];",
            "        int front = -1, rear = -1;",
            "",
            "        System.out.print(\"Enter number of items to insert: \");",
            "        int count = sc.nextInt();",
            "        for (int i = 1; i <= count; i++) {",
            "            System.out.print(\"Enter item \" + i + \": \");",
            "            int item = sc.nextInt();",
            "            if (rear == n) {",
            "                System.out.println(\"OVERFLOW\");",
            "            } else {",
            "                if (front == -1) {",
            "                    front = 0;",
            "                    rear = 0;",
            "                } else {",
            "                    rear++;",
            "                }",
            "                queue[rear] = item;",
            "                System.out.println(\"Item inserted\");",
            "            }",
            "        }",
            "        if (front == -1) {",
            "            System.out.println(\"Queue is empty\");",
            "        } else {",
            "            System.out.print(\"Queue elements: \");",
            "            for (int i = front; i <= rear; i++) System.out.print(queue[i] + \" \");",
            "            System.out.println();",
            "        }",
            "",
            "        if (front == -1) {",
            "            System.out.println(\"UNDERFLOW\");",
            "        } else {",
            "            int deleted = queue[front];",
            "            if (front == rear) {",
            "                front = -1;",
            "                rear = -1;",
            "            } else {",
            "                front++;",
            "            }",
            "            System.out.println(\"Deleted: \" + deleted);",
            "        }",
            "    }",
            "}");
    }

    private static String getJavaCircularQueue(String style) {
        if ("Functions".equals(style)) {
            return String.join("\n",
                "import java.util.*;",
                "",
                "public class CircularQueue {",
                "    static int[] insert(int[] queue, int front, int rear, int n, int item) {",
                "        if ((front == 0 && rear == n - 1) || (front == rear + 1)) {",
                "            System.out.println(\"OVERFLOW\");",
                "            return new int[]{front, rear};",
                "        }",
                "",
                "        if (front == -1) {",
                "            front = 0;",
                "            rear = 0;",
                "        } else if (rear == n - 1) {",
                "            rear = 0;",
                "        } else {",
                "            rear++;",
                "        }",
                "",
                "        queue[rear] = item;",
                "        return new int[]{front, rear};",
                "    }",
                "",
                "    static int[] delete(int[] queue, int front, int rear, int n) {",
                "        if (front == -1) {",
                "            System.out.println(\"UNDERFLOW\");",
                "            return new int[]{front, rear};",
                "        }",
                "",
                "        System.out.println(\"Deleted: \" + queue[front]);",
                "",
                "        if (front == rear) {",
                "            front = -1;",
                "            rear = -1;",
                "        } else if (front == n - 1) {",
                "            front = 0;",
                "        } else {",
                "            front++;",
                "        }",
                "",
                "        return new int[]{front, rear};",
                "    }",
                "",
                "    public static void main(String[] args) {",
                "        Scanner sc = new Scanner(System.in);",
                "        System.out.print(\"Enter queue size: \");",
                "        int n = sc.nextInt();",
                "        int[] queue = new int[n];",
                "        int front = -1, rear = -1;",
                "        System.out.print(\"Enter number of items to insert: \");",
                "        int count = sc.nextInt();",
                "        for (int i = 1; i <= count; i++) {",
                "            System.out.print(\"Enter item \" + i + \": \");",
                "            int item = sc.nextInt();",
                "            int[] res = insert(queue, front, rear, n, item);",
                "            front = res[0];",
                "            rear = res[1];",
                "        }",
                "        if (front == -1) {",
                "            System.out.println(\"Circular queue is empty\");",
                "        } else {",
                "            System.out.print(\"Circular queue elements: \");",
                "            int i = front;",
                "            while (true) {",
                "                System.out.print(queue[i] + \" \");",
                "                if (i == rear) break;",
                "                i = (i + 1) % n;",
                "            }",
                "            System.out.println();",
                "        }",
                "        res = delete(queue, front, rear, n);",
                "        front = res[0];",
                "        rear = res[1];",
                "    }",
                "}");
        }
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "import java.util.*;",
                "",
                "public class CircularQueue {",
                "    public static void main(String[] args) {",
                "        System.out.println(\"Recursion not applicable for circular queue operations\");",
                "    }",
                "}");
        }
        return String.join("\n",
            "import java.util.*;",
            "",
            "public class CircularQueue {",
            "    public static void main(String[] args) {",
            "        Scanner sc = new Scanner(System.in);",
            "        System.out.print(\"Enter queue size: \");",
            "        int n = sc.nextInt();",
            "        int[] queue = new int[n + 1];",
            "        int front = -1, rear = -1;",
            "        System.out.print(\"Enter number of items to insert: \");",
            "        int count = sc.nextInt();",
            "        for (int itemIndex = 1; itemIndex <= count; itemIndex++) {",
            "            System.out.print(\"Enter item \" + itemIndex + \": \");",
            "            int item = sc.nextInt();",
            "            if ((front == 0 && rear == n - 1) || (front == rear + 1)) {",
            "                System.out.println(\"OVERFLOW\");",
            "            } else {",
            "                if (front == -1) {",
            "                    front = 0;",
            "                    rear = 0;",
            "                } else if (rear == n - 1) {",
            "                    rear = 0;",
            "                } else {",
            "                    rear++;",
            "                }",
            "                queue[rear] = item;",
            "                System.out.println(\"Item inserted\");",
            "            }",
            "        }",
            "        if (front == -1) {",
            "            System.out.println(\"Circular queue is empty\");",
            "        } else {",
            "            System.out.print(\"Circular queue elements: \");",
            "            int i = front;",
            "            while (true) {",
            "                System.out.print(queue[i] + \" \");",
            "                if (i == rear) break;",
            "                i = (i + 1) % n;",
            "            }",
            "            System.out.println();",
            "        }",
            "",
            "        if (front == -1) {",
            "            System.out.println(\"UNDERFLOW\");",
            "        } else {",
            "            int deleted = queue[front];",
            "            if (front == rear) {",
            "                front = -1;",
            "                rear = -1;",
            "            } else if (front == n - 1) {",
            "                front = 0;",
            "            } else {",
            "                front++;",
            "            }",
            "            System.out.println(\"Deleted: \" + deleted);",
            "        }",
            "    }",
            "}");
    }

    private static String getJavaLinkedQueue(String style) {
        if ("Functions".equals(style)) {
            return String.join("\n",
                "import java.util.*;",
                "",
                "class Node {",
                "    int data;",
                "    Node next;",
                "",
                "    Node(int data) {",
                "        this.data = data;",
                "        this.next = null;",
                "    }",
                "}",
                "",
                "public class LinkedQueue {",
                "    static Node[] insert(Node front, Node rear, int item) {",
                "        Node newNode = new Node(item);",
                "        if (front == null) {",
                "            front = rear = newNode;",
                "        } else {",
                "            rear.next = newNode;",
                "            rear = newNode;",
                "        }",
                "        return new Node[]{front, rear};",
                "    }",
                "",
                "    static Node[] delete(Node front, Node rear) {",
                "        if (front == null) {",
                "            System.out.println(\"UNDERFLOW\");",
                "            return new Node[]{front, rear};",
                "        }",
                "        System.out.println(\"Deleted: \" + front.data);",
                "        front = front.next;",
                "        if (front == null) {",
                "            rear = null;",
                "        }",
                "        return new Node[]{front, rear};",
                "    }",
                "",
                "    public static void main(String[] args) {",
                "        Scanner sc = new Scanner(System.in);",
                "        Node front = null, rear = null;",
                "        System.out.print(\"Enter number of items to insert: \");",
                "        int count = sc.nextInt();",
                "        for (int i = 1; i <= count; i++) {",
                "            System.out.print(\"Enter item \" + i + \": \");",
                "            int item = sc.nextInt();",
                "            Node[] res = insert(front, rear, item);",
                "            front = res[0];",
                "            rear = res[1];",
                "        }",
                "        if (front == null) {",
                "            System.out.println(\"Linked queue is empty\");",
                "        } else {",
                "            System.out.print(\"Linked queue elements: \");",
                "            for (Node ptr = front; ptr != null; ptr = ptr.next) System.out.print(ptr.data + \" \");",
                "            System.out.println();",
                "        }",
                "        res = delete(front, rear);",
                "        front = res[0];",
                "        rear = res[1];",
                "    }",
                "}");
        }
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "import java.util.*;",
                "",
                "public class LinkedQueue {",
                "    public static void main(String[] args) {",
                "        System.out.println(\"Recursion not applicable for linked queue operations\");",
                "    }",
                "}");
        }
        return String.join("\n",
            "import java.util.*;",
            "",
            "class Node {",
            "    int data;",
            "    Node next;",
            "",
            "    Node(int data) {",
            "        this.data = data;",
            "        this.next = null;",
            "    }",
            "}",
            "",
            "public class LinkedQueue {",
            "    public static void main(String[] args) {",
            "        Scanner sc = new Scanner(System.in);",
            "        Node front = null, rear = null;",
            "        System.out.print(\"Enter number of items to insert: \");",
            "        int count = sc.nextInt();",
            "        for (int i = 1; i <= count; i++) {",
            "            System.out.print(\"Enter item \" + i + \": \");",
            "            int item = sc.nextInt();",
            "            Node newNode = new Node(item);",
            "",
            "            if (front == null) {",
            "                front = rear = newNode;",
            "            } else {",
            "                rear.next = newNode;",
            "                rear = newNode;",
            "            }",
            "",
            "            System.out.println(\"Item inserted\");",
            "        }",
            "        if (front == null) {",
            "            System.out.println(\"Linked queue is empty\");",
            "        } else {",
            "            System.out.print(\"Linked queue elements: \");",
            "            for (Node ptr = front; ptr != null; ptr = ptr.next) System.out.print(ptr.data + \" \");",
            "            System.out.println();",
            "        }",
            "",
            "        if (front == null) {",
            "            System.out.println(\"UNDERFLOW\");",
            "        } else {",
            "            int deleted = front.data;",
            "            front = front.next;",
            "            System.out.println(\"Deleted: \" + deleted);",
            "        }",
            "    }",
            "}");
    }

    private static String getJavaPriorityQueueArray(String style) {
        if ("Functions".equals(style)) {
            return String.join("\n",
                "import java.util.*;",
                "",
                "public class PriorityQueueArray {",
                "    static int insert(int[] data, int[] priority, int size, int item, int p) {",
                "        int i;",
                "        for (i = size - 1; i >= 0 && priority[i] > p; i--) {",
                "            data[i + 1] = data[i];",
                "            priority[i + 1] = priority[i];",
                "        }",
                "        data[i + 1] = item;",
                "        priority[i + 1] = p;",
                "        return size + 1;",
                "    }",
                "",
                "    static int delete(int[] data, int[] priority, int size) {",
                "        if (size == 0) {",
                "            System.out.println(\"UNDERFLOW\");",
                "            return size;",
                "        }",
                "        System.out.println(\"Deleted: \" + data[0]);",
                "        for (int i = 0; i < size - 1; i++) {",
                "            data[i] = data[i + 1];",
                "            priority[i] = priority[i + 1];",
                "        }",
                "        return size - 1;",
                "    }",
                "",
                "    public static void main(String[] args) {",
                "        Scanner sc = new Scanner(System.in);",
                "        System.out.print(\"Enter size of queue: \");",
                "        int n = sc.nextInt();",
                "        int[] data = new int[n];",
                "        int[] priority = new int[n];",
                "        int size = 0;",
                "        System.out.print(\"Enter item: \");",
                "        int item = sc.nextInt();",
                "        System.out.print(\"Enter priority: \");",
                "        int p = sc.nextInt();",
                "        size = insert(data, priority, size, item, p);",
                "        System.out.println(\"Item inserted\");",
                "        size = delete(data, priority, size);",
                "    }",
                "}");
        }
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "import java.util.*;",
                "",
                "public class PriorityQueueArray {",
                "    public static void main(String[] args) {",
                "        System.out.println(\"Recursion not applicable for priority queue (array)\");",
                "    }",
                "}");
        }
        return String.join("\n",
            "import java.util.*;",
            "",
            "public class PriorityQueueArray {",
            "    public static void main(String[] args) {",
            "        Scanner sc = new Scanner(System.in);",
            "        System.out.print(\"Enter size of queue: \");",
            "        int n = sc.nextInt();",
            "        int[] data = new int[n];",
            "        int[] priority = new int[n];",
            "        int size = 0;",
            "        System.out.print(\"Enter item: \");",
            "        int item = sc.nextInt();",
            "        System.out.print(\"Enter priority: \");",
            "        int p = sc.nextInt();",
            "        int i;",
            "        for (i = size - 1; i >= 0 && priority[i] > p; i--) {",
            "            data[i + 1] = data[i];",
            "            priority[i + 1] = priority[i];",
            "        }",
            "        data[i + 1] = item;",
            "        priority[i + 1] = p;",
            "        size++;",
            "        System.out.println(\"Item inserted\");",
            "        if (size == 0) {",
            "            System.out.println(\"UNDERFLOW\");",
            "        } else {",
            "            int deleted = data[0];",
            "            for (int j = 0; j < size - 1; j++) {",
            "                data[j] = data[j + 1];",
            "                priority[j] = priority[j + 1];",
            "            }",
            "            size--;",
            "            System.out.println(\"Deleted: \" + deleted);",
            "        }",
            "    }",
            "}");
    }

    private static String getJavaPriorityQueue2d(String style) {
        if ("Functions".equals(style)) {
            return String.join("\n",
                "import java.util.*;",
                "",
                "public class PriorityQueue2D {",
                "    static void insert(int[][] queue, int[] front, int[] rear, int n, int m, int item) {",
                "        if (rear[m] == n - 1) {",
                "            System.out.println(\"OVERFLOW\");",
                "            return;",
                "        }",
                "        if (front[m] == -1) {",
                "            front[m] = 0;",
                "            rear[m] = 0;",
                "        } else {",
                "            rear[m]++;",
                "        }",
                "        queue[m][rear[m]] = item;",
                "    }",
                "",
                "    static void delete(int[][] queue, int[] front, int[] rear, int p) {",
                "        int k = -1;",
                "        for (int i = 0; i < p; i++) {",
                "            if (front[i] != -1) {",
                "                k = i;",
                "                break;",
                "            }",
                "        }",
                "        if (k == -1) {",
                "            System.out.println(\"UNDERFLOW\");",
                "            return;",
                "        }",
                "        System.out.println(\"Deleted: \" + queue[k][front[k]]);",
                "        if (front[k] == rear[k]) {",
                "            front[k] = -1;",
                "            rear[k] = -1;",
                "        } else {",
                "            front[k]++;",
                "        }",
                "    }",
                "",
                "    public static void main(String[] args) {",
                "        Scanner sc = new Scanner(System.in);",
                "        System.out.print(\"Enter number of priority levels: \");",
                "        int p = sc.nextInt();",
                "        System.out.print(\"Enter size of each queue: \");",
                "        int n = sc.nextInt();",
                "        int[][] queue = new int[p][n];",
                "        int[] front = new int[p];",
                "        int[] rear = new int[p];",
                "        Arrays.fill(front, -1);",
                "        Arrays.fill(rear, -1);",
                "        System.out.print(\"Enter item: \");",
                "        int item = sc.nextInt();",
                "        System.out.print(\"Enter priority level: \");",
                "        int m = sc.nextInt();",
                "        insert(queue, front, rear, n, m, item);",
                "        System.out.println(\"Item inserted\");",
                "        delete(queue, front, rear, p);",
                "    }",
                "}");
        }
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "import java.util.*;",
                "",
                "public class PriorityQueue2D {",
                "    public static void main(String[] args) {",
                "        System.out.println(\"Recursion not applicable for priority queue (2D array)\");",
                "    }",
                "}");
        }
        return String.join("\n",
            "import java.util.*;",
            "",
            "public class PriorityQueue2D {",
            "    public static void main(String[] args) {",
            "        Scanner sc = new Scanner(System.in);",
            "        System.out.print(\"Enter number of priority levels: \");",
            "        int p = sc.nextInt();",
            "        System.out.print(\"Enter size of each queue: \");",
            "        int n = sc.nextInt();",
            "        int[][] queue = new int[p][n];",
            "        int[] front = new int[p];",
            "        int[] rear = new int[p];",
            "        Arrays.fill(front, -1);",
            "        Arrays.fill(rear, -1);",
            "        System.out.print(\"Enter item: \");",
            "        int item = sc.nextInt();",
            "        System.out.print(\"Enter priority level (0 to \" + (p - 1) + \"): \");",
            "        int m = sc.nextInt();",
            "        if (rear[m] == n - 1) {",
            "            System.out.println(\"OVERFLOW\");",
            "        } else {",
            "            if (front[m] == -1) {",
            "                front[m] = 0;",
            "                rear[m] = 0;",
            "            } else {",
            "                rear[m]++;",
            "            }",
            "            queue[m][rear[m]] = item;",
            "            System.out.println(\"Item inserted\");",
            "        }",
            "        int k = -1;",
            "        for (int i = 0; i < p; i++) {",
            "            if (front[i] != -1) {",
            "                k = i;",
            "                break;",
            "            }",
            "        }",
            "        if (k == -1) {",
            "            System.out.println(\"UNDERFLOW\");",
            "        } else {",
            "            int deleted = queue[k][front[k]];",
            "            if (front[k] == rear[k]) {",
            "                front[k] = -1;",
            "                rear[k] = -1;",
            "            } else {",
            "                front[k]++;",
            "            }",
            "            System.out.println(\"Deleted: \" + deleted);",
            "        }",
            "    }",
            "}");
    }

    private static String getJavaPreorderTraversal(String style) {
        String nodeBlock = "import java.util.*;\n\n"
            + "class Node {\n"
            + "    int data;\n"
            + "    Node left, right;\n"
            + "    Node(int data) {\n"
            + "        this.data = data;\n"
            + "        left = right = null;\n"
            + "    }\n"
            + "}\n\n";
        if ("Functions".equals(style)) {
            return nodeBlock
                + "public class Main {\n"
                + "    static void preorder(Node ptr) {\n"
                + "        if (ptr == null) return;\n"
                + "        System.out.print(ptr.data + \" \");\n"
                + "        preorder(ptr.left);\n"
                + "        preorder(ptr.right);\n"
                + "    }\n\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        System.out.print(\"Enter number of nodes: \");\n"
                + "        int n = sc.nextInt();\n"
                + "        Node[] nodes = new Node[n];\n"
                + "        for (int i = 0; i < n; i++) {\n"
                + "            System.out.print(\"Enter value of node \" + i + \": \");\n"
                + "            nodes[i] = new Node(sc.nextInt());\n"
                + "        }\n"
                + "        for (int i = 0; i < n; i++) {\n"
                + "            System.out.print(\"Enter left and right child index of node \" + i + \" (-1 if none): \");\n"
                + "            int l = sc.nextInt();\n"
                + "            int r = sc.nextInt();\n"
                + "            if (l != -1) nodes[i].left = nodes[l];\n"
                + "            if (r != -1) nodes[i].right = nodes[r];\n"
                + "        }\n"
                + "        preorder(nodes[0]);\n"
                + "    }\n"
                + "}";
        }
        if ("Recursion".equals(style)) {
            return nodeBlock
                + "public class Main {\n"
                + "    static void preorderRec(Node root) {\n"
                + "        if (root == null) return;\n"
                + "        System.out.print(root.data + \" \");\n"
                + "        preorderRec(root.left);\n"
                + "        preorderRec(root.right);\n"
                + "    }\n\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        System.out.print(\"Enter number of nodes: \");\n"
                + "        int n = sc.nextInt();\n"
                + "        Node[] nodes = new Node[n];\n"
                + "        for (int i = 0; i < n; i++) {\n"
                + "            System.out.print(\"Enter value of node \" + i + \": \");\n"
                + "            nodes[i] = new Node(sc.nextInt());\n"
                + "        }\n"
                + "        for (int i = 0; i < n; i++) {\n"
                + "            System.out.print(\"Enter left and right child index of node \" + i + \" (-1 if none): \");\n"
                + "            int l = sc.nextInt();\n"
                + "            int r = sc.nextInt();\n"
                + "            if (l != -1) nodes[i].left = nodes[l];\n"
                + "            if (r != -1) nodes[i].right = nodes[r];\n"
                + "        }\n"
                + "        preorderRec(nodes[0]);\n"
                + "    }\n"
                + "}";
        }
        return nodeBlock
            + "public class Main {\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        System.out.print(\"Enter number of nodes: \");\n"
            + "        int n = sc.nextInt();\n"
            + "        Node[] nodes = new Node[n];\n"
            + "        for (int i = 0; i < n; i++) {\n"
            + "            System.out.print(\"Enter value of node \" + i + \": \");\n"
            + "            nodes[i] = new Node(sc.nextInt());\n"
            + "        }\n"
            + "        for (int i = 0; i < n; i++) {\n"
            + "            System.out.print(\"Enter left and right child index of node \" + i + \" (-1 if none): \");\n"
            + "            int l = sc.nextInt();\n"
            + "            int r = sc.nextInt();\n"
            + "            if (l != -1) nodes[i].left = nodes[l];\n"
            + "            if (r != -1) nodes[i].right = nodes[r];\n"
            + "        }\n"
            + "        Stack<Node> stack = new Stack<>();\n"
            + "        Node ptr = nodes[0];\n"
            + "        stack.push(null);\n"
            + "        while (ptr != null) {\n"
            + "            System.out.print(ptr.data + \" \");\n"
            + "            if (ptr.right != null) {\n"
            + "                stack.push(ptr.right);\n"
            + "            }\n"
            + "            if (ptr.left != null) {\n"
            + "                ptr = ptr.left;\n"
            + "            } else {\n"
            + "                ptr = stack.pop();\n"
            + "            }\n"
            + "        }\n"
            + "    }\n"
            + "}";
    }

    private static String getJavaPostorderTraversal(String style) {
        String nodeBlock = "import java.util.*;\n\n"
            + "class Node {\n"
            + "    int data;\n"
            + "    Node left, right;\n"
            + "    Node(int data) {\n"
            + "        this.data = data;\n"
            + "        left = right = null;\n"
            + "    }\n"
            + "}\n\n";
        if ("Functions".equals(style)) {
            return nodeBlock
                + "public class Main {\n"
                + "    static void postorder(Node ptr) {\n"
                + "        if (ptr == null) return;\n"
                + "        postorder(ptr.left);\n"
                + "        postorder(ptr.right);\n"
                + "        System.out.print(ptr.data + \" \");\n"
                + "    }\n\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        System.out.print(\"Enter number of nodes: \");\n"
                + "        int n = sc.nextInt();\n"
                + "        Node[] nodes = new Node[n];\n"
                + "        for (int i = 0; i < n; i++) {\n"
                + "            System.out.print(\"Enter value of node \" + i + \": \");\n"
                + "            nodes[i] = new Node(sc.nextInt());\n"
                + "        }\n"
                + "        for (int i = 0; i < n; i++) {\n"
                + "            System.out.print(\"Enter left and right child index of node \" + i + \" (-1 if none): \");\n"
                + "            int l = sc.nextInt();\n"
                + "            int r = sc.nextInt();\n"
                + "            if (l != -1) nodes[i].left = nodes[l];\n"
                + "            if (r != -1) nodes[i].right = nodes[r];\n"
                + "        }\n"
                + "        postorder(nodes[0]);\n"
                + "    }\n"
                + "}";
        }
        if ("Recursion".equals(style)) {
            return nodeBlock
                + "public class Main {\n"
                + "    static void postorderRec(Node root) {\n"
                + "        if (root == null) return;\n"
                + "        postorderRec(root.left);\n"
                + "        postorderRec(root.right);\n"
                + "        System.out.print(root.data + \" \");\n"
                + "    }\n\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        System.out.print(\"Enter number of nodes: \");\n"
                + "        int n = sc.nextInt();\n"
                + "        Node[] nodes = new Node[n];\n"
                + "        for (int i = 0; i < n; i++) {\n"
                + "            System.out.print(\"Enter value of node \" + i + \": \");\n"
                + "            nodes[i] = new Node(sc.nextInt());\n"
                + "        }\n"
                + "        for (int i = 0; i < n; i++) {\n"
                + "            System.out.print(\"Enter left and right child index of node \" + i + \" (-1 if none): \");\n"
                + "            int l = sc.nextInt();\n"
                + "            int r = sc.nextInt();\n"
                + "            if (l != -1) nodes[i].left = nodes[l];\n"
                + "            if (r != -1) nodes[i].right = nodes[r];\n"
                + "        }\n"
                + "        postorderRec(nodes[0]);\n"
                + "    }\n"
                + "}";
        }
        return nodeBlock
            + "public class Main {\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        System.out.print(\"Enter number of nodes: \");\n"
            + "        int n = sc.nextInt();\n"
            + "        Node[] nodes = new Node[n];\n"
            + "        for (int i = 0; i < n; i++) {\n"
            + "            System.out.print(\"Enter value of node \" + i + \": \");\n"
            + "            nodes[i] = new Node(sc.nextInt());\n"
            + "        }\n"
            + "        for (int i = 0; i < n; i++) {\n"
            + "            System.out.print(\"Enter left and right child index of node \" + i + \" (-1 if none): \");\n"
            + "            int l = sc.nextInt();\n"
            + "            int r = sc.nextInt();\n"
            + "            if (l != -1) nodes[i].left = nodes[l];\n"
            + "            if (r != -1) nodes[i].right = nodes[r];\n"
            + "        }\n"
            + "        Stack<Node> stack = new Stack<>();\n"
            + "        Stack<Node> output = new Stack<>();\n"
            + "        Node ptr = nodes[0];\n"
            + "        stack.push(ptr);\n"
            + "        while (!stack.isEmpty()) {\n"
            + "            Node temp = stack.pop();\n"
            + "            output.push(temp);\n"
            + "            if (temp.left != null) stack.push(temp.left);\n"
            + "            if (temp.right != null) stack.push(temp.right);\n"
            + "        }\n"
            + "        while (!output.isEmpty()) {\n"
            + "            System.out.print(output.pop().data + \" \");\n"
            + "        }\n"
            + "    }\n"
            + "}";
    }

    private static String getJavaBstSearchParentTracking(String style) {
        String nodeBlock = "import java.util.*;\n\n"
            + "class Node {\n"
            + "    int data;\n"
            + "    Node left, right;\n"
            + "    Node(int data) {\n"
            + "        this.data = data;\n"
            + "        left = right = null;\n"
            + "    }\n"
            + "}\n\n";
        if ("Functions".equals(style)) {
            return nodeBlock
                + "public class Main {\n"
                + "    static Node loc = null, par = null;\n\n"
                + "    static void find(Node ptr, Node save, int item) {\n"
                + "        if (ptr == null) {\n"
                + "            loc = null;\n"
                + "            par = save;\n"
                + "            return;\n"
                + "        }\n"
                + "        if (item == ptr.data) {\n"
                + "            loc = ptr;\n"
                + "            par = save;\n"
                + "            return;\n"
                + "        }\n"
                + "        if (item < ptr.data)\n"
                + "            find(ptr.left, ptr, item);\n"
                + "        else\n"
                + "            find(ptr.right, ptr, item);\n"
                + "    }\n\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        System.out.print(\"Enter number of nodes: \");\n"
                + "        int n = sc.nextInt();\n"
                + "        Node[] nodes = new Node[n];\n"
                + "        System.out.println(\"Enter node values:\");\n"
                + "        for (int i = 0; i < n; i++) nodes[i] = new Node(sc.nextInt());\n"
                + "        for (int i = 0; i < n; i++) {\n"
                + "            System.out.print(\"Enter left and right child index of node \" + i + \" (-1 if none): \");\n"
                + "            int l = sc.nextInt();\n"
                + "            int r = sc.nextInt();\n"
                + "            if (l != -1) nodes[i].left = nodes[l];\n"
                + "            if (r != -1) nodes[i].right = nodes[r];\n"
                + "        }\n"
                + "        System.out.print(\"Enter ITEM to search: \");\n"
                + "        int item = sc.nextInt();\n"
                + "        find(nodes[0], null, item);\n"
                + "        if (loc != null)\n"
                + "            System.out.println(\"Found. Parent: \" + (par == null ? \"NULL\" : par.data));\n"
                + "        else\n"
                + "            System.out.println(\"Not Found. Parent: \" + (par == null ? \"NULL\" : par.data));\n"
                + "    }\n"
                + "}";
        }
        if ("Recursion".equals(style)) {
            return nodeBlock
                + "public class Main {\n"
                + "    static Node loc = null, par = null;\n\n"
                + "    static void findRec(Node root, Node parent, int item) {\n"
                + "        if (root == null) {\n"
                + "            loc = null;\n"
                + "            par = parent;\n"
                + "            return;\n"
                + "        }\n"
                + "        if (item == root.data) {\n"
                + "            loc = root;\n"
                + "            par = parent;\n"
                + "            return;\n"
                + "        }\n"
                + "        if (item < root.data)\n"
                + "            findRec(root.left, root, item);\n"
                + "        else\n"
                + "            findRec(root.right, root, item);\n"
                + "    }\n\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        System.out.print(\"Enter number of nodes: \");\n"
                + "        int n = sc.nextInt();\n"
                + "        Node[] nodes = new Node[n];\n"
                + "        System.out.println(\"Enter node values:\");\n"
                + "        for (int i = 0; i < n; i++) nodes[i] = new Node(sc.nextInt());\n"
                + "        for (int i = 0; i < n; i++) {\n"
                + "            System.out.print(\"Enter left and right child index of node \" + i + \" (-1 if none): \");\n"
                + "            int l = sc.nextInt();\n"
                + "            int r = sc.nextInt();\n"
                + "            if (l != -1) nodes[i].left = nodes[l];\n"
                + "            if (r != -1) nodes[i].right = nodes[r];\n"
                + "        }\n"
                + "        System.out.print(\"Enter ITEM to search: \");\n"
                + "        int item = sc.nextInt();\n"
                + "        findRec(nodes[0], null, item);\n"
                + "        if (loc != null)\n"
                + "            System.out.println(\"Found. Parent: \" + (par == null ? \"NULL\" : par.data));\n"
                + "        else\n"
                + "            System.out.println(\"Not Found. Parent: \" + (par == null ? \"NULL\" : par.data));\n"
                + "    }\n"
                + "}";
        }
        return nodeBlock
            + "public class Main {\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        System.out.print(\"Enter number of nodes: \");\n"
            + "        int n = sc.nextInt();\n"
            + "        Node[] nodes = new Node[n];\n"
            + "        for (int i = 0; i < n; i++) {\n"
            + "            System.out.print(\"Enter value of node \" + i + \": \");\n"
            + "            nodes[i] = new Node(sc.nextInt());\n"
            + "        }\n"
            + "        for (int i = 0; i < n; i++) {\n"
            + "            System.out.print(\"Enter left and right child index of node \" + i + \" (-1 if none): \");\n"
            + "            int l = sc.nextInt();\n"
            + "            int r = sc.nextInt();\n"
            + "            if (l != -1) nodes[i].left = nodes[l];\n"
            + "            if (r != -1) nodes[i].right = nodes[r];\n"
            + "        }\n"
            + "        System.out.print(\"Enter ITEM to search: \");\n"
            + "        int item = sc.nextInt();\n"
            + "        Node root = nodes[0];\n"
            + "        Node ptr = root, save = null;\n"
            + "        Node loc = null, par = null;\n"
            + "        if (root == null) {\n"
            + "            loc = null;\n"
            + "            par = null;\n"
            + "        } else if (item == root.data) {\n"
            + "            loc = root;\n"
            + "            par = null;\n"
            + "        } else {\n"
            + "            if (item < root.data) {\n"
            + "                ptr = root.left;\n"
            + "                save = root;\n"
            + "            } else {\n"
            + "                ptr = root.right;\n"
            + "                save = root;\n"
            + "            }\n"
            + "            while (ptr != null) {\n"
            + "                if (item == ptr.data) {\n"
            + "                    loc = ptr;\n"
            + "                    par = save;\n"
            + "                    break;\n"
            + "                } else if (item < ptr.data) {\n"
            + "                    save = ptr;\n"
            + "                    ptr = ptr.left;\n"
            + "                } else {\n"
            + "                    save = ptr;\n"
            + "                    ptr = ptr.right;\n"
            + "                }\n"
            + "            }\n"
            + "            if (ptr == null) {\n"
            + "                loc = null;\n"
            + "                par = save;\n"
            + "            }\n"
            + "        }\n"
            + "        if (loc != null)\n"
            + "            System.out.println(\"Found. Parent: \" + (par == null ? \"NULL\" : par.data));\n"
            + "        else\n"
            + "            System.out.println(\"Not Found. Parent: \" + (par == null ? \"NULL\" : par.data));\n"
            + "    }\n"
            + "}";
    }

    private static String getJavaBstInsertion(String style) {
        String nodeBlock = "import java.util.*;\n\n"
            + "class Node {\n"
            + "    int data;\n"
            + "    Node left, right;\n"
            + "    Node(int data) {\n"
            + "        this.data = data;\n"
            + "        left = right = null;\n"
            + "    }\n"
            + "}\n\n";
        if ("Functions".equals(style)) {
            return nodeBlock
                + "public class Main {\n"
                + "    static Node insertFunc(Node root, int item) {\n"
                + "        if (root == null) return new Node(item);\n"
                + "        if (item < root.data)\n"
                + "            root.left = insertFunc(root.left, item);\n"
                + "        else if (item > root.data)\n"
                + "            root.right = insertFunc(root.right, item);\n"
                + "        return root;\n"
                + "    }\n\n"
                + "    static void inorder(Node root) {\n"
                + "        if (root == null) return;\n"
                + "        inorder(root.left);\n"
                + "        System.out.print(root.data + \" \");\n"
                + "        inorder(root.right);\n"
                + "    }\n\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        System.out.print(\"Enter number of existing BST nodes: \");\n"
                + "        int n = sc.nextInt();\n"
                + "        Node root = null;\n"
                + "        System.out.println(\"Enter BST node values:\");\n"
                + "        for (int i = 0; i < n; i++) {\n"
                + "            int val = sc.nextInt();\n"
                + "            root = insertFunc(root, val);\n"
                + "        }\n"
                + "        System.out.print(\"Current inorder traversal: \");\n"
                + "        inorder(root);\n"
                + "        System.out.println();\n"
                + "        System.out.print(\"Enter item to insert: \");\n"
                + "        int item = sc.nextInt();\n"
                + "        root = insertFunc(root, item);\n"
                + "        System.out.println(\"Insertion Done\");\n"
                + "        System.out.print(\"BST after insertion (inorder): \");\n"
                + "        inorder(root);\n"
                + "        System.out.println();\n"
                + "    }\n"
                + "}";
        }
        if ("Recursion".equals(style)) {
            return nodeBlock
                + "public class Main {\n"
                + "    static Node insertRec(Node root, int item) {\n"
                + "        if (root == null) {\n"
                + "            return new Node(item);\n"
                + "        }\n"
                + "        if (item < root.data)\n"
                + "            root.left = insertRec(root.left, item);\n"
                + "        else if (item > root.data)\n"
                + "            root.right = insertRec(root.right, item);\n"
                + "        return root;\n"
                + "    }\n\n"
                + "    static void inorder(Node root) {\n"
                + "        if (root == null) return;\n"
                + "        inorder(root.left);\n"
                + "        System.out.print(root.data + \" \");\n"
                + "        inorder(root.right);\n"
                + "    }\n\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        System.out.print(\"Enter number of existing BST nodes: \");\n"
                + "        int n = sc.nextInt();\n"
                + "        Node root = null;\n"
                + "        System.out.println(\"Enter BST node values:\");\n"
                + "        for (int i = 0; i < n; i++) {\n"
                + "            int val = sc.nextInt();\n"
                + "            root = insertRec(root, val);\n"
                + "        }\n"
                + "        System.out.print(\"Current inorder traversal: \");\n"
                + "        inorder(root);\n"
                + "        System.out.println();\n"
                + "        System.out.print(\"Enter item to insert: \");\n"
                + "        int item = sc.nextInt();\n"
                + "        root = insertRec(root, item);\n"
                + "        System.out.println(\"Insertion Done\");\n"
                + "        System.out.print(\"BST after insertion (inorder): \");\n"
                + "        inorder(root);\n"
                + "        System.out.println();\n"
                + "    }\n"
                + "}";
        }
        return nodeBlock
            + "public class Main {\n"
            + "    static void inorder(Node root) {\n"
            + "        if (root == null) return;\n"
            + "        inorder(root.left);\n"
            + "        System.out.print(root.data + \" \");\n"
            + "        inorder(root.right);\n"
            + "    }\n\n"
            + "    static Node insert(Node root, int item) {\n"
            + "        Node parent = null, ptr = root;\n"
            + "        while (ptr != null) {\n"
            + "            parent = ptr;\n"
            + "            if (item < ptr.data)\n"
            + "                ptr = ptr.left;\n"
            + "            else if (item > ptr.data)\n"
            + "                ptr = ptr.right;\n"
            + "            else\n"
            + "                return root;\n"
            + "        }\n"
            + "        Node newNode = new Node(item);\n"
            + "        if (parent == null)\n"
            + "            return newNode;\n"
            + "        else if (item < parent.data)\n"
            + "            parent.left = newNode;\n"
            + "        else\n"
            + "            parent.right = newNode;\n"
            + "        return root;\n"
            + "    }\n\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        System.out.print(\"Enter number of existing BST nodes: \");\n"
            + "        int n = sc.nextInt();\n"
            + "        Node root = null;\n"
            + "        System.out.println(\"Enter BST node values:\");\n"
            + "        for (int i = 0; i < n; i++) {\n"
            + "            int val = sc.nextInt();\n"
            + "            root = insert(root, val);\n"
            + "        }\n"
            + "        System.out.print(\"Current inorder traversal: \");\n"
            + "        inorder(root);\n"
            + "        System.out.println();\n"
            + "        System.out.print(\"Enter item to insert: \");\n"
            + "        int item = sc.nextInt();\n"
            + "        root = insert(root, item);\n"
            + "        System.out.println(\"Insertion Done\");\n"
            + "        System.out.print(\"BST after insertion (inorder): \");\n"
            + "        inorder(root);\n"
            + "        System.out.println();\n"
            + "    }\n"
            + "}";
    }

    private static String getJavaBstDeletionCaseA(String style) {
        String base = "import java.util.*;\n\n"
            + "class Node {\n"
            + "    int data;\n"
            + "    Node left, right;\n"
            + "    Node(int data) { this.data = data; left = right = null; }\n"
            + "}\n\n"
            + "class Util {\n"
            + "    static Node insert(Node root, int val) {\n"
            + "        if (root == null) return new Node(val);\n"
            + "        if (val < root.data) root.left = insert(root.left, val);\n"
            + "        else if (val > root.data) root.right = insert(root.right, val);\n"
            + "        return root;\n"
            + "    }\n"
            + "}\n\n";
        if ("Functions".equals(style)) {
            return base
                + "public class Main {\n"
                + "    static Node deleteFunc(Node root, int item) {\n"
                + "        if (root == null) return null;\n"
                + "        if (item < root.data) root.left = deleteFunc(root.left, item);\n"
                + "        else if (item > root.data) root.right = deleteFunc(root.right, item);\n"
                + "        else {\n"
                + "            if (root.left == null) return root.right;\n"
                + "            else if (root.right == null) return root.left;\n"
                + "        }\n"
                + "        return root;\n"
                + "    }\n"
                + "    static void inorder(Node root) {\n"
                + "        if (root == null) return;\n"
                + "        inorder(root.left);\n"
                + "        System.out.print(root.data + \" \");\n"
                + "        inorder(root.right);\n"
                + "    }\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        System.out.print(\"Enter number of BST nodes: \");\n"
                + "        int n = sc.nextInt(); Node root = null;\n"
                + "        System.out.println(\"Enter BST node values:\");\n"
                + "        for (int i = 0; i < n; i++) root = Util.insert(root, sc.nextInt());\n"
                + "        System.out.print(\"BST before deletion (inorder): \");\n"
                + "        inorder(root);\n"
                + "        System.out.println();\n"
                + "        System.out.print(\"Enter item to delete: \");\n"
                + "        root = deleteFunc(root, sc.nextInt());\n"
                + "        System.out.println(\"Deletion Done\");\n"
                + "        System.out.print(\"BST after deletion (inorder): \");\n"
                + "        inorder(root);\n"
                + "        System.out.println();\n"
                + "    }\n"
                + "}";
        }
        if ("Recursion".equals(style)) {
            return base
                + "public class Main {\n"
                + "    static Node deleteRec(Node root, int item) {\n"
                + "        if (root == null) return null;\n"
                + "        if (item < root.data) root.left = deleteRec(root.left, item);\n"
                + "        else if (item > root.data) root.right = deleteRec(root.right, item);\n"
                + "        else {\n"
                + "            if (root.left == null && root.right == null) return null;\n"
                + "            else if (root.left != null) return root.left;\n"
                + "            else return root.right;\n"
                + "        }\n"
                + "        return root;\n"
                + "    }\n"
                + "    static void inorder(Node root) {\n"
                + "        if (root == null) return;\n"
                + "        inorder(root.left);\n"
                + "        System.out.print(root.data + \" \");\n"
                + "        inorder(root.right);\n"
                + "    }\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        System.out.print(\"Enter number of BST nodes: \");\n"
                + "        int n = sc.nextInt(); Node root = null;\n"
                + "        System.out.println(\"Enter BST node values:\");\n"
                + "        for (int i = 0; i < n; i++) root = Util.insert(root, sc.nextInt());\n"
                + "        System.out.print(\"BST before deletion (inorder): \");\n"
                + "        inorder(root);\n"
                + "        System.out.println();\n"
                + "        System.out.print(\"Enter item to delete: \");\n"
                + "        root = deleteRec(root, sc.nextInt());\n"
                + "        System.out.println(\"Deletion Done\");\n"
                + "        System.out.print(\"BST after deletion (inorder): \");\n"
                + "        inorder(root);\n"
                + "        System.out.println();\n"
                + "    }\n"
                + "}";
        }
        return base
            + "public class Main {\n"
            + "    static Node deleteCaseA(Node root, int item) {\n"
            + "        Node ptr = root, par = null;\n"
            + "        while (ptr != null && ptr.data != item) {\n"
            + "            par = ptr;\n"
            + "            if (item < ptr.data) ptr = ptr.left; else ptr = ptr.right;\n"
            + "        }\n"
            + "        if (ptr == null) return root;\n"
            + "        Node child;\n"
            + "        if (ptr.left == null && ptr.right == null) child = null;\n"
            + "        else if (ptr.left != null) child = ptr.left;\n"
            + "        else child = ptr.right;\n"
            + "        if (par != null) {\n"
            + "            if (par.left == ptr) par.left = child; else par.right = child;\n"
            + "        } else {\n"
            + "            root = child;\n"
            + "        }\n"
            + "        return root;\n"
            + "    }\n"
            + "    static void inorder(Node root) {\n"
            + "        if (root == null) return;\n"
            + "        inorder(root.left);\n"
            + "        System.out.print(root.data + \" \");\n"
            + "        inorder(root.right);\n"
            + "    }\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        System.out.print(\"Enter number of BST nodes: \");\n"
            + "        int n = sc.nextInt(); Node root = null;\n"
            + "        System.out.println(\"Enter BST node values:\");\n"
            + "        for (int i = 0; i < n; i++) root = Util.insert(root, sc.nextInt());\n"
            + "        System.out.print(\"BST before deletion (inorder): \");\n"
            + "        inorder(root);\n"
            + "        System.out.println();\n"
            + "        System.out.print(\"Enter item to delete: \");\n"
            + "        root = deleteCaseA(root, sc.nextInt());\n"
            + "        System.out.println(\"Deletion Done\");\n"
            + "        System.out.print(\"BST after deletion (inorder): \");\n"
            + "        inorder(root);\n"
            + "        System.out.println();\n"
            + "    }\n"
            + "}";
    }

    private static String getJavaBstDeletionCaseB(String style) {
        String base = "import java.util.*;\n\n"
            + "class Node {\n"
            + "    int data;\n"
            + "    Node left, right;\n"
            + "    Node(int data) { this.data = data; left = right = null; }\n"
            + "}\n\n"
            + "class Util {\n"
            + "    static Node insert(Node root, int val) {\n"
            + "        if (root == null) return new Node(val);\n"
            + "        if (val < root.data) root.left = insert(root.left, val);\n"
            + "        else if (val > root.data) root.right = insert(root.right, val);\n"
            + "        return root;\n"
            + "    }\n"
            + "}\n\n";
        if ("Functions".equals(style) || "Recursion".equals(style)) {
            String methodName = "Functions".equals(style) ? "deleteFunc" : "deleteRec";
            return base
                + "public class Main {\n"
                + "    static Node " + methodName + "(Node root, int item) {\n"
                + "        if (root == null) return null;\n"
                + "        if (item < root.data) root.left = " + methodName + "(root.left, item);\n"
                + "        else if (item > root.data) root.right = " + methodName + "(root.right, item);\n"
                + "        else if (root.left != null && root.right != null) {\n"
                + "            Node suc = root.right;\n"
                + "            while (suc.left != null) suc = suc.left;\n"
                + "            root.data = suc.data;\n"
                + "            root.right = " + methodName + "(root.right, suc.data);\n"
                + "        }\n"
                + "        return root;\n"
                + "    }\n"
                + "    static void inorder(Node root) {\n"
                + "        if (root == null) return;\n"
                + "        inorder(root.left);\n"
                + "        System.out.print(root.data + \" \");\n"
                + "        inorder(root.right);\n"
                + "    }\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        System.out.print(\"Enter number of BST nodes: \");\n"
                + "        int n = sc.nextInt(); Node root = null;\n"
                + "        System.out.println(\"Enter BST node values:\");\n"
                + "        for (int i = 0; i < n; i++) root = Util.insert(root, sc.nextInt());\n"
                + "        System.out.print(\"BST before deletion (inorder): \");\n"
                + "        inorder(root);\n"
                + "        System.out.println();\n"
                + "        System.out.print(\"Enter item to delete: \");\n"
                + "        root = " + methodName + "(root, sc.nextInt());\n"
                + "        System.out.println(\"Deletion Done\");\n"
                + "        System.out.print(\"BST after deletion (inorder): \");\n"
                + "        inorder(root);\n"
                + "        System.out.println();\n"
                + "    }\n"
                + "}";
        }
        return base
            + "public class Main {\n"
            + "    static Node deleteCaseB(Node root, int item) {\n"
            + "        Node ptr = root;\n"
            + "        while (ptr != null && ptr.data != item) {\n"
            + "            if (item < ptr.data) ptr = ptr.left; else ptr = ptr.right;\n"
            + "        }\n"
            + "        if (ptr == null) return root;\n"
            + "        if (ptr.left != null && ptr.right != null) {\n"
            + "            Node sucParent = ptr;\n"
            + "            Node suc = ptr.right;\n"
            + "            while (suc.left != null) { sucParent = suc; suc = suc.left; }\n"
            + "            ptr.data = suc.data;\n"
            + "            if (sucParent.left == suc) sucParent.left = suc.right; else sucParent.right = suc.right;\n"
            + "        }\n"
            + "        return root;\n"
            + "    }\n"
            + "    static void inorder(Node root) {\n"
            + "        if (root == null) return;\n"
            + "        inorder(root.left);\n"
            + "        System.out.print(root.data + \" \");\n"
            + "        inorder(root.right);\n"
            + "    }\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        System.out.print(\"Enter number of BST nodes: \");\n"
            + "        int n = sc.nextInt(); Node root = null;\n"
            + "        System.out.println(\"Enter BST node values:\");\n"
            + "        for (int i = 0; i < n; i++) root = Util.insert(root, sc.nextInt());\n"
            + "        System.out.print(\"BST before deletion (inorder): \");\n"
            + "        inorder(root);\n"
            + "        System.out.println();\n"
            + "        System.out.print(\"Enter item to delete: \");\n"
            + "        root = deleteCaseB(root, sc.nextInt());\n"
            + "        System.out.println(\"Deletion Done\");\n"
            + "        System.out.print(\"BST after deletion (inorder): \");\n"
            + "        inorder(root);\n"
            + "        System.out.println();\n"
            + "    }\n"
            + "}";
    }

    private static String getJavaBstDeletionDel(String style) {
        String base = "import java.util.*;\n\n"
            + "class Node {\n"
            + "    int data;\n"
            + "    Node left, right;\n"
            + "    Node(int data) { this.data = data; left = right = null; }\n"
            + "}\n\n"
            + "class Util {\n"
            + "    static Node insert(Node root, int val) {\n"
            + "        if (root == null) return new Node(val);\n"
            + "        if (val < root.data) root.left = insert(root.left, val);\n"
            + "        else if (val > root.data) root.right = insert(root.right, val);\n"
            + "        return root;\n"
            + "    }\n"
            + "}\n\n";
        String methodName = "Iterative".equals(style) ? "delete" : ("Functions".equals(style) ? "deleteFunc" : "deleteRec");
        return base
            + "public class Main {\n"
            + "    static Node " + methodName + "(Node root, int item) {\n"
            + "        if (root == null) { System.out.println(\"ITEM not in tree\"); return null; }\n"
            + "        if (item < root.data) root.left = " + methodName + "(root.left, item);\n"
            + "        else if (item > root.data) root.right = " + methodName + "(root.right, item);\n"
            + "        else {\n"
            + "            if (root.left != null && root.right != null) {\n"
            + "                Node suc = root.right;\n"
            + "                while (suc.left != null) suc = suc.left;\n"
            + "                root.data = suc.data;\n"
            + "                root.right = " + methodName + "(root.right, suc.data);\n"
            + "            } else if (root.left != null) {\n"
            + "                return root.left;\n"
            + "            } else {\n"
            + "                return root.right;\n"
            + "            }\n"
            + "        }\n"
            + "        return root;\n"
            + "    }\n"
            + "    static void inorder(Node root) {\n"
            + "        if (root == null) return;\n"
            + "        inorder(root.left);\n"
            + "        System.out.print(root.data + \" \");\n"
            + "        inorder(root.right);\n"
            + "    }\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        System.out.print(\"Enter number of BST nodes: \");\n"
            + "        int n = sc.nextInt(); Node root = null;\n"
            + "        System.out.println(\"Enter BST node values:\");\n"
            + "        for (int i = 0; i < n; i++) root = Util.insert(root, sc.nextInt());\n"
            + "        System.out.print(\"BST before deletion (inorder): \");\n"
            + "        inorder(root);\n"
            + "        System.out.println();\n"
            + "        System.out.print(\"Enter item to delete: \");\n"
            + "        root = " + methodName + "(root, sc.nextInt());\n"
            + "        System.out.println(\"Deletion Done\");\n"
            + "        System.out.print(\"BST after deletion (inorder): \");\n"
            + "        inorder(root);\n"
            + "        System.out.println();\n"
            + "    }\n"
            + "}";
    }

    private static String getJavaMaxHeapInsertion(String style) {
        String body = "        int n = sc.nextInt();\n"
            + "        int[] tree = new int[100];\n"
            + "        for (int i = 1; i <= n; i++) tree[i] = sc.nextInt();\n"
            + "        int item = sc.nextInt();\n";
        if ("Functions".equals(style)) {
            return "import java.util.*;\npublic class Main {\n"
                + "    static int insertHeap(int[] tree, int n, int item) {\n"
                + "        n = n + 1; int ptr = n;\n"
                + "        while (ptr > 1) { int par = ptr / 2; if (item <= tree[par]) { tree[ptr] = item; return n; } tree[ptr] = tree[par]; ptr = par; }\n"
                + "        tree[ptr] = item; return n;\n"
                + "    }\n"
                + "    public static void main(String[] args) { Scanner sc = new Scanner(System.in);\n" + body
                + "        n = insertHeap(tree, n, item);\n"
                + "        for (int i = 1; i <= n; i++) System.out.print(tree[i] + \" \");\n"
                + "    }\n}";
        }
        if ("Recursion".equals(style)) {
            return "import java.util.*;\npublic class Main {\n"
                + "    static void insertRec(int[] tree, int ptr, int item) {\n"
                + "        if (ptr == 1) { tree[ptr] = item; return; }\n"
                + "        int par = ptr / 2; if (item <= tree[par]) { tree[ptr] = item; return; }\n"
                + "        tree[ptr] = tree[par]; insertRec(tree, par, item);\n"
                + "    }\n"
                + "    public static void main(String[] args) { Scanner sc = new Scanner(System.in);\n" + body
                + "        n = n + 1; insertRec(tree, n, item);\n"
                + "        for (int i = 1; i <= n; i++) System.out.print(tree[i] + \" \");\n"
                + "    }\n}";
        }
        return "import java.util.*;\npublic class Main {\n"
            + "    public static void main(String[] args) { Scanner sc = new Scanner(System.in);\n" + body
            + "        n = n + 1; int ptr = n;\n"
            + "        while (ptr > 1) { int par = ptr / 2; if (item <= tree[par]) { tree[ptr] = item; break; } tree[ptr] = tree[par]; ptr = par; }\n"
            + "        if (ptr == 1) tree[ptr] = item;\n"
            + "        for (int i = 1; i <= n; i++) System.out.print(tree[i] + \" \");\n"
            + "    }\n}";
    }

    private static String getJavaMaxHeapDeletion(String style) {
        String body = "        int n = sc.nextInt();\n"
            + "        int[] tree = new int[100];\n"
            + "        for (int i = 1; i <= n; i++) tree[i] = sc.nextInt();\n";
        if ("Functions".equals(style)) {
            return "import java.util.*;\npublic class Main {\n"
                + "    static int deleteHeap(int[] tree, int n) {\n"
                + "        int item = tree[1]; int last = tree[n]; n--; int ptr = 1;\n"
                + "        while (2 * ptr <= n) { int left = 2 * ptr; int right = left + 1; int child = (right <= n && tree[right] > tree[left]) ? right : left; if (last >= tree[child]) break; tree[ptr] = tree[child]; ptr = child; }\n"
                + "        tree[ptr] = last; return item;\n"
                + "    }\n"
                + "    public static void main(String[] args) { Scanner sc = new Scanner(System.in);\n" + body
                + "        int deleted = deleteHeap(tree, n);\n"
                + "        System.out.println(\"Deleted element: \" + deleted);\n"
                + "    }\n}";
        }
        if ("Recursion".equals(style)) {
            return "import java.util.*;\npublic class Main {\n"
                + "    static void deleteRec(int[] tree, int ptr, int n, int last) {\n"
                + "        int left = 2 * ptr, right = left + 1; if (left > n) { tree[ptr] = last; return; }\n"
                + "        int child = (right <= n && tree[right] > tree[left]) ? right : left; if (last >= tree[child]) { tree[ptr] = last; return; }\n"
                + "        tree[ptr] = tree[child]; deleteRec(tree, child, n, last);\n"
                + "    }\n"
                + "    public static void main(String[] args) { Scanner sc = new Scanner(System.in);\n" + body
                + "        int item = tree[1]; int last = tree[n]; n--; deleteRec(tree, 1, n, last);\n"
                + "        System.out.println(\"Deleted element: \" + item);\n"
                + "        for (int i = 1; i <= n; i++) System.out.print(tree[i] + \" \");\n"
                + "    }\n}";
        }
        return "import java.util.*;\npublic class Main {\n"
            + "    public static void main(String[] args) { Scanner sc = new Scanner(System.in);\n" + body
            + "        int item = tree[1]; int last = tree[n]; n = n - 1; int ptr = 1;\n"
            + "        while (2 * ptr <= n) { int left = 2 * ptr; int right = left + 1; int child = (right <= n && tree[right] > tree[left]) ? right : left; if (last >= tree[child]) break; tree[ptr] = tree[child]; ptr = child; }\n"
            + "        tree[ptr] = last; System.out.println(\"Deleted element: \" + item);\n"
            + "        for (int i = 1; i <= n; i++) System.out.print(tree[i] + \" \");\n"
            + "    }\n}";
    }

    private static String getJavaHeapSort(String style) {
        String helpers = "    static void insertHeap(int[] A, int n, int item) { int ptr = n + 1; while (ptr > 1) { int par = ptr / 2; if (item <= A[par]) { A[ptr] = item; return; } A[ptr] = A[par]; ptr = par; } A[ptr] = item; }\n"
            + "    static int deleteHeap(int[] A, int n) { int item = A[1]; int last = A[n]; n--; int ptr = 1; while (2 * ptr <= n) { int left = 2 * ptr; int right = left + 1; int child = (right <= n && A[right] > A[left]) ? right : left; if (last >= A[child]) break; A[ptr] = A[child]; ptr = child; } A[ptr] = last; return item; }\n";
        if ("Functions".equals(style)) {
            return "import java.util.*;\npublic class Main {\n"
                + "    static void buildHeap(int[] A, int n) { for (int j = 2; j <= n; j++) insertHeap(A, j - 1, A[j]); }\n"
                + "    static void sortHeap(int[] A, int n) { int size = n; while (size > 1) { int item = deleteHeap(A, size); A[size] = item; size--; } }\n"
                + helpers
                + "    public static void main(String[] args) { Scanner sc = new Scanner(System.in); int n = sc.nextInt(); int[] A = new int[200]; for (int i = 1; i <= n; i++) A[i] = sc.nextInt(); buildHeap(A, n); sortHeap(A, n); for (int i = 1; i <= n; i++) System.out.print(A[i] + \" \"); }\n}";
        }
        if ("Recursion".equals(style)) {
            return "import java.util.*;\npublic class Main {\n"
                + "    static void deleteMaxRec(int[] A, int n) { if (n <= 1) return; int item = deleteHeap(A, n); A[n] = item; deleteMaxRec(A, n - 1); }\n"
                + "    static void buildHeap(int[] A, int n) { for (int j = 2; j <= n; j++) insertHeap(A, j - 1, A[j]); }\n"
                + helpers
                + "    public static void main(String[] args) { Scanner sc = new Scanner(System.in); int n = sc.nextInt(); int[] A = new int[200]; for (int i = 1; i <= n; i++) A[i] = sc.nextInt(); buildHeap(A, n); deleteMaxRec(A, n); for (int i = 1; i <= n; i++) System.out.print(A[i] + \" \"); }\n}";
        }
        return "import java.util.*;\npublic class Main {\n"
            + helpers
            + "    public static void main(String[] args) { Scanner sc = new Scanner(System.in); int n = sc.nextInt(); int[] A = new int[200]; for (int i = 1; i <= n; i++) A[i] = sc.nextInt(); for (int j = 2; j <= n; j++) insertHeap(A, j - 1, A[j]); int size = n; while (size > 1) { int item = deleteHeap(A, size); A[size] = item; size--; } for (int i = 1; i <= n; i++) System.out.print(A[i] + \" \"); }\n}";
    }

    private static String getJavaSearchInLinkedList(String style) {
        if ("Functions".equals(style)) {
            return "import java.util.Scanner;\n"
                + "class Node {\n"
                + "    int data;\n"
                + "    Node next;\n"
                + "    Node(int data) {\n"
                + "        this.data = data;\n"
                + "    }\n"
                + "}\n"
                + "class Main {\n"
                + "    static Node search(Node ptr, int item) {\n"
                + "        while(ptr != null) {\n"
                + "            if(ptr.data == item)\n"
                + "                return ptr;\n"
                + "            ptr = ptr.next;\n"
                + "        }\n"
                + "        return null;\n"
                + "    }\n"
                + "    static void display(Node head) {\n"
                + "        System.out.print(\"Linked list: \");\n"
                + "        for(Node ptr = head; ptr != null; ptr = ptr.next)\n"
                + "            System.out.print(ptr.data + \" \");\n"
                + "        System.out.println();\n"
                + "    }\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        System.out.print(\"Enter number of nodes: \");\n"
                + "        int n = sc.nextInt();\n"
                + "        Node head = null, temp = null;\n"
                + "        System.out.println(\"Enter node values:\");\n"
                + "        for(int i = 0; i < n; i++) {\n"
                + "            Node newNode = new Node(sc.nextInt());\n"
                + "            if(head == null)\n"
                + "                head = temp = newNode;\n"
                + "            else {\n"
                + "                temp.next = newNode;\n"
                + "                temp = newNode;\n"
                + "            }\n"
                + "        }\n"
                + "        display(head);\n"
                + "        System.out.print(\"Enter item to search: \");\n"
                + "        int item = sc.nextInt();\n"
                + "        Node loc = search(head, item);\n"
                + "        if(loc != null)\n"
                + "            System.out.println(\"Found\");\n"
                + "        else\n"
                + "            System.out.println(\"Not Found\");\n"
                + "    }\n"
                + "}";
        }
        if ("Recursion".equals(style)) {
            return "import java.util.Scanner;\n"
                + "class Node {\n"
                + "    int data;\n"
                + "    Node next;\n"
                + "    Node(int data) {\n"
                + "        this.data = data;\n"
                + "    }\n"
                + "}\n"
                + "class Main {\n"
                + "    static Node search(Node ptr, int item) {\n"
                + "        if(ptr == null)\n"
                + "            return null;\n"
                + "        if(ptr.data == item)\n"
                + "            return ptr;\n"
                + "        return search(ptr.next, item);\n"
                + "    }\n"
                + "    static void display(Node head) {\n"
                + "        System.out.print(\"Linked list: \");\n"
                + "        for(Node ptr = head; ptr != null; ptr = ptr.next)\n"
                + "            System.out.print(ptr.data + \" \");\n"
                + "        System.out.println();\n"
                + "    }\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        System.out.print(\"Enter number of nodes: \");\n"
                + "        int n = sc.nextInt();\n"
                + "        Node head = null, temp = null;\n"
                + "        System.out.println(\"Enter node values:\");\n"
                + "        for(int i = 0; i < n; i++) {\n"
                + "            Node newNode = new Node(sc.nextInt());\n"
                + "            if(head == null)\n"
                + "                head = temp = newNode;\n"
                + "            else {\n"
                + "                temp.next = newNode;\n"
                + "                temp = newNode;\n"
                + "            }\n"
                + "        }\n"
                + "        display(head);\n"
                + "        System.out.print(\"Enter item to search: \");\n"
                + "        int item = sc.nextInt();\n"
                + "        Node loc = search(head, item);\n"
                + "        if(loc != null)\n"
                + "            System.out.println(\"Found\");\n"
                + "        else\n"
                + "            System.out.println(\"Not Found\");\n"
                + "    }\n"
                + "}";
        }
        return "import java.util.Scanner;\n"
            + "class Node {\n"
            + "    int data;\n"
            + "    Node next;\n"
            + "    Node(int data) {\n"
            + "        this.data = data;\n"
            + "    }\n"
            + "}\n"
            + "class Main {\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        System.out.print(\"Enter number of nodes: \");\n"
            + "        int n = sc.nextInt();\n"
            + "        Node head = null, temp = null;\n"
            + "        System.out.println(\"Enter node values:\");\n"
            + "        for(int i = 0; i < n; i++) {\n"
            + "            Node newNode = new Node(sc.nextInt());\n"
            + "            if(head == null)\n"
            + "                head = temp = newNode;\n"
            + "            else {\n"
            + "                temp.next = newNode;\n"
            + "                temp = newNode;\n"
            + "            }\n"
            + "        }\n"
            + "        System.out.print(\"Linked list: \");\n"
            + "        for(Node current = head; current != null; current = current.next)\n"
            + "            System.out.print(current.data + \" \");\n"
            + "        System.out.println();\n"
            + "        System.out.print(\"Enter item to search: \");\n"
            + "        int item = sc.nextInt();\n"
            + "        Node ptr = head;\n"
            + "        Node loc = null;\n"
            + "        while(ptr != null) {\n"
            + "            if(ptr.data == item) {\n"
            + "                loc = ptr;\n"
            + "                break;\n"
            + "            }\n"
            + "            ptr = ptr.next;\n"
            + "        }\n"
            + "        if(loc != null)\n"
            + "            System.out.println(\"Found\");\n"
            + "        else\n"
            + "            System.out.println(\"Not Found\");\n"
            + "    }\n"
            + "}";
    }

    private static String getJavaInsertAtBeginningLinkedList(String style) {
        if ("Recursion".equals(style)) {
            return "// Recursion is not applicable here because insertion at beginning is a single-step pointer update.";
        }
        if ("Functions".equals(style)) {
            return "import java.util.Scanner;\n"
                + "class Main {\n"
                + "    static int MAX = 100;\n"
                + "    static int[] INFO = new int[MAX];\n"
                + "    static int[] LINK = new int[MAX];\n"
                + "    static int START = -1;\n"
                + "    static int AVAIL;\n"
                + "    static void init() {\n"
                + "        for(int i = 0; i < MAX - 1; i++)\n"
                + "            LINK[i] = i + 1;\n"
                + "        LINK[MAX - 1] = -1;\n"
                + "        AVAIL = 0;\n"
                + "    }\n"
                + "    static void append(int item) {\n"
                + "        if(AVAIL == -1) return;\n"
                + "        int NEW = AVAIL;\n"
                + "        AVAIL = LINK[AVAIL];\n"
                + "        INFO[NEW] = item;\n"
                + "        LINK[NEW] = -1;\n"
                + "        if(START == -1) {\n"
                + "            START = NEW;\n"
                + "        } else {\n"
                + "            int ptr = START;\n"
                + "            while(LINK[ptr] != -1) ptr = LINK[ptr];\n"
                + "            LINK[ptr] = NEW;\n"
                + "        }\n"
                + "    }\n"
                + "    static void insFirst(int item) {\n"
                + "        if(AVAIL == -1) {\n"
                + "            System.out.println(\"OVERFLOW\");\n"
                + "            return;\n"
                + "        }\n"
                + "        int NEW = AVAIL;\n"
                + "        AVAIL = LINK[AVAIL];\n"
                + "        INFO[NEW] = item;\n"
                + "        LINK[NEW] = START;\n"
                + "        START = NEW;\n"
                + "    }\n"
                + "    static void display() {\n"
                + "        System.out.print(\"Linked list: \");\n"
                + "        int ptr = START;\n"
                + "        while(ptr != -1) {\n"
                + "            System.out.print(INFO[ptr] + \" \");\n"
                + "            ptr = LINK[ptr];\n"
                + "        }\n"
                + "        System.out.println();\n"
                + "    }\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        init();\n"
                + "        System.out.print(\"Enter number of existing nodes: \");\n"
                + "        int n = sc.nextInt();\n"
                + "        System.out.println(\"Enter node values:\");\n"
                + "        for(int i = 0; i < n; i++) append(sc.nextInt());\n"
                + "        display();\n"
                + "        System.out.print(\"Enter item to insert at beginning: \");\n"
                + "        int item = sc.nextInt();\n"
                + "        insFirst(item);\n"
                + "        System.out.println(\"Inserted successfully\");\n"
                + "        display();\n"
                + "    }\n"
                + "}";
        }
        return "import java.util.Scanner;\n"
            + "class Main {\n"
            + "    static int MAX = 100;\n"
            + "    static int[] INFO = new int[MAX];\n"
            + "    static int[] LINK = new int[MAX];\n"
            + "    static int START = -1;\n"
            + "    static int AVAIL;\n"
            + "    static void init() {\n"
            + "        for(int i = 0; i < MAX - 1; i++)\n"
            + "            LINK[i] = i + 1;\n"
            + "        LINK[MAX - 1] = -1;\n"
            + "        AVAIL = 0;\n"
            + "    }\n"
            + "    static void append(int item) {\n"
            + "        if(AVAIL == -1) return;\n"
            + "        int NEW = AVAIL;\n"
            + "        AVAIL = LINK[AVAIL];\n"
            + "        INFO[NEW] = item;\n"
            + "        LINK[NEW] = -1;\n"
            + "        if(START == -1) START = NEW;\n"
            + "        else {\n"
            + "            int ptr = START;\n"
            + "            while(LINK[ptr] != -1) ptr = LINK[ptr];\n"
            + "            LINK[ptr] = NEW;\n"
            + "        }\n"
            + "    }\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        init();\n"
            + "        System.out.print(\"Enter number of existing nodes: \");\n"
            + "        int n = sc.nextInt();\n"
            + "        System.out.println(\"Enter node values:\");\n"
            + "        for(int i = 0; i < n; i++) append(sc.nextInt());\n"
            + "        System.out.print(\"Current linked list: \");\n"
            + "        for(int ptr = START; ptr != -1; ptr = LINK[ptr]) System.out.print(INFO[ptr] + \" \");\n"
            + "        System.out.println();\n"
            + "        System.out.print(\"Enter item to insert at beginning: \");\n"
            + "        int item = sc.nextInt();\n"
            + "        if(AVAIL == -1) {\n"
            + "            System.out.println(\"OVERFLOW\");\n"
            + "            return;\n"
            + "        }\n"
            + "        int NEW = AVAIL;\n"
            + "        AVAIL = LINK[AVAIL];\n"
            + "        INFO[NEW] = item;\n"
            + "        LINK[NEW] = START;\n"
            + "        START = NEW;\n"
            + "        System.out.println(\"Inserted successfully\");\n"
            + "        System.out.print(\"Linked list after insertion: \");\n"
            + "        for(int ptr = START; ptr != -1; ptr = LINK[ptr]) System.out.print(INFO[ptr] + \" \");\n"
            + "        System.out.println();\n"
            + "    }\n"
            + "}";
    }

    private static String getJavaInsertAfterGivenNodeLinkedList(String style) {
        if ("Recursion".equals(style)) {
            return "// Recursion is not applicable here because insertion after a given node is a direct pointer operation.";
        }
        if ("Functions".equals(style)) {
            return "import java.util.Scanner;\n"
                + "class Main {\n"
                + "    static int MAX = 100;\n"
                + "    static int[] INFO = new int[MAX];\n"
                + "    static int[] LINK = new int[MAX];\n"
                + "    static int START = -1;\n"
                + "    static int AVAIL;\n"
                + "    static void init() {\n"
                + "        for(int i = 0; i < MAX - 1; i++)\n"
                + "            LINK[i] = i + 1;\n"
                + "        LINK[MAX - 1] = -1;\n"
                + "        AVAIL = 0;\n"
                + "    }\n"
                + "    static void append(int item) {\n"
                + "        if(AVAIL == -1) return;\n"
                + "        int NEW = AVAIL;\n"
                + "        AVAIL = LINK[AVAIL];\n"
                + "        INFO[NEW] = item;\n"
                + "        LINK[NEW] = -1;\n"
                + "        if(START == -1) START = NEW;\n"
                + "        else {\n"
                + "            int ptr = START;\n"
                + "            while(LINK[ptr] != -1) ptr = LINK[ptr];\n"
                + "            LINK[ptr] = NEW;\n"
                + "        }\n"
                + "    }\n"
                + "    static void insLoc(int loc, int item) {\n"
                + "        if(AVAIL == -1) {\n"
                + "            System.out.println(\"OVERFLOW\");\n"
                + "            return;\n"
                + "        }\n"
                + "        int NEW = AVAIL;\n"
                + "        AVAIL = LINK[AVAIL];\n"
                + "        INFO[NEW] = item;\n"
                + "        if(loc == -1) {\n"
                + "            LINK[NEW] = START;\n"
                + "            START = NEW;\n"
                + "        } else {\n"
                + "            LINK[NEW] = LINK[loc];\n"
                + "            LINK[loc] = NEW;\n"
                + "        }\n"
                + "    }\n"
                + "    static void display() {\n"
                + "        System.out.print(\"Linked list: \");\n"
                + "        for(int ptr = START; ptr != -1; ptr = LINK[ptr]) System.out.print(INFO[ptr] + \" \");\n"
                + "        System.out.println();\n"
                + "    }\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        init();\n"
                + "        System.out.print(\"Enter number of existing nodes: \");\n"
                + "        int n = sc.nextInt();\n"
                + "        System.out.println(\"Enter node values:\");\n"
                + "        for(int i = 0; i < n; i++) append(sc.nextInt());\n"
                + "        display();\n"
                + "        System.out.print(\"Enter item to insert: \");\n"
                + "        int item = sc.nextInt();\n"
                + "        System.out.print(\"Enter location index after which to insert (-1 for beginning): \");\n"
                + "        int loc = sc.nextInt();\n"
                + "        insLoc(loc, item);\n"
                + "        System.out.println(\"Inserted successfully\");\n"
                + "        display();\n"
                + "    }\n"
                + "}";
        }
        return "import java.util.Scanner;\n"
            + "class Main {\n"
            + "    static int MAX = 100;\n"
            + "    static int[] INFO = new int[MAX];\n"
            + "    static int[] LINK = new int[MAX];\n"
            + "    static int START = -1;\n"
            + "    static int AVAIL;\n"
            + "    static void init() {\n"
            + "        for(int i = 0; i < MAX - 1; i++)\n"
            + "            LINK[i] = i + 1;\n"
            + "        LINK[MAX - 1] = -1;\n"
            + "        AVAIL = 0;\n"
            + "    }\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        init();\n"
            + "        int item = sc.nextInt();\n"
            + "        int loc = sc.nextInt();\n"
            + "        if(AVAIL == -1) {\n"
            + "            System.out.println(\"OVERFLOW\");\n"
            + "            return;\n"
            + "        }\n"
            + "        int NEW = AVAIL;\n"
            + "        AVAIL = LINK[AVAIL];\n"
            + "        INFO[NEW] = item;\n"
            + "        if(loc == -1) {\n"
            + "            LINK[NEW] = START;\n"
            + "            START = NEW;\n"
            + "        } else {\n"
            + "            LINK[NEW] = LINK[loc];\n"
            + "            LINK[loc] = NEW;\n"
            + "        }\n"
            + "        System.out.println(\"Inserted successfully\");\n"
            + "    }\n"
            + "}";
    }

    private static String getJavaInsertIntoSortedLinkedList(String style) {
        if ("Recursion".equals(style)) {
            return "class Main {\n"
                + "    static int[] INFO = new int[100];\n"
                + "    static int[] LINK = new int[100];\n"
                + "    static int findARec(int ptr, int item, int start) {\n"
                + "        if(ptr == -1)\n"
                + "            return start;\n"
                + "        if(item < INFO[ptr])\n"
                + "            return start;\n"
                + "        return findARec(LINK[ptr], item, ptr);\n"
                + "    }\n"
                + "}";
        }
        return "import java.util.Scanner;\n"
            + "class Main {\n"
            + "    static int MAX = 100;\n"
            + "    static int[] INFO = new int[MAX];\n"
            + "    static int[] LINK = new int[MAX];\n"
            + "    static int START = -1;\n"
            + "    static int AVAIL;\n"
            + "    static void init() {\n"
            + "        for(int i = 0; i < MAX - 1; i++)\n"
            + "            LINK[i] = i + 1;\n"
            + "        LINK[MAX - 1] = -1;\n"
            + "        AVAIL = 0;\n"
            + "    }\n"
            + "    static void append(int item) {\n"
            + "        if(AVAIL == -1) return;\n"
            + "        int NEW = AVAIL;\n"
            + "        AVAIL = LINK[AVAIL];\n"
            + "        INFO[NEW] = item;\n"
            + "        LINK[NEW] = -1;\n"
            + "        if(START == -1) START = NEW;\n"
            + "        else {\n"
            + "            int ptr = START;\n"
            + "            while(LINK[ptr] != -1) ptr = LINK[ptr];\n"
            + "            LINK[ptr] = NEW;\n"
            + "        }\n"
            + "    }\n"
            + "    static int findA(int item) {\n"
            + "        if(START == -1) return -1;\n"
            + "        if(item < INFO[START]) return -1;\n"
            + "        int save = START;\n"
            + "        int ptr = LINK[START];\n"
            + "        while(ptr != -1) {\n"
            + "            if(item < INFO[ptr])\n"
            + "                return save;\n"
            + "            save = ptr;\n"
            + "            ptr = LINK[ptr];\n"
            + "        }\n"
            + "        return save;\n"
            + "    }\n"
            + "    static void insLoc(int loc, int item) {\n"
            + "        if(AVAIL == -1) return;\n"
            + "        int NEW = AVAIL;\n"
            + "        AVAIL = LINK[AVAIL];\n"
            + "        INFO[NEW] = item;\n"
            + "        if(loc == -1) {\n"
            + "            LINK[NEW] = START;\n"
            + "            START = NEW;\n"
            + "        } else {\n"
            + "            LINK[NEW] = LINK[loc];\n"
            + "            LINK[loc] = NEW;\n"
            + "        }\n"
            + "    }\n"
            + "    static void display() {\n"
            + "        System.out.print(\"Sorted linked list: \");\n"
            + "        for(int ptr = START; ptr != -1; ptr = LINK[ptr]) System.out.print(INFO[ptr] + \" \");\n"
            + "        System.out.println();\n"
            + "    }\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        init();\n"
            + "        System.out.print(\"Enter number of existing sorted nodes: \");\n"
            + "        int n = sc.nextInt();\n"
            + "        System.out.println(\"Enter sorted node values:\");\n"
            + "        for(int i = 0; i < n; i++) append(sc.nextInt());\n"
            + "        display();\n"
            + "        System.out.print(\"Enter item to insert: \");\n"
            + "        int item = sc.nextInt();\n"
            + "        int loc = findA(item);\n"
            + "        insLoc(loc, item);\n"
            + "        System.out.println(\"Inserted successfully\");\n"
            + "        display();\n"
            + "    }\n"
            + "}";
    }

    private static String getJavaDeleteNodeGivenLocationLinkedList(String style) {
        if ("Recursion".equals(style)) {
            return "// Recursion is not applicable here because deletion by location is a direct pointer update.";
        }
        if ("Functions".equals(style)) {
            return "import java.util.Scanner;\n"
                + "class Main {\n"
                + "    static int MAX = 100;\n"
                + "    static int[] INFO = new int[MAX];\n"
                + "    static int[] LINK = new int[MAX];\n"
                + "    static int START = -1;\n"
                + "    static int AVAIL;\n"
                + "    static void init() {\n"
                + "        for(int i = 0; i < MAX - 1; i++) LINK[i] = i + 1;\n"
                + "        LINK[MAX - 1] = -1;\n"
                + "        AVAIL = 0;\n"
                + "    }\n"
                + "    static void append(int item) {\n"
                + "        if(AVAIL == -1) return;\n"
                + "        int NEW = AVAIL;\n"
                + "        AVAIL = LINK[AVAIL];\n"
                + "        INFO[NEW] = item;\n"
                + "        LINK[NEW] = -1;\n"
                + "        if(START == -1) START = NEW;\n"
                + "        else {\n"
                + "            int ptr = START;\n"
                + "            while(LINK[ptr] != -1) ptr = LINK[ptr];\n"
                + "            LINK[ptr] = NEW;\n"
                + "        }\n"
                + "    }\n"
                + "    static void del(int loc, int locp) {\n"
                + "        if(locp == -1) START = LINK[START];\n"
                + "        else LINK[locp] = LINK[loc];\n"
                + "        LINK[loc] = AVAIL;\n"
                + "        AVAIL = loc;\n"
                + "    }\n"
                + "    static void display() {\n"
                + "        System.out.print(\"Linked list: \");\n"
                + "        for(int ptr = START; ptr != -1; ptr = LINK[ptr]) System.out.print(INFO[ptr] + \" \");\n"
                + "        System.out.println();\n"
                + "    }\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        init();\n"
                + "        System.out.print(\"Enter number of nodes: \");\n"
                + "        int n = sc.nextInt();\n"
                + "        System.out.println(\"Enter node values:\");\n"
                + "        for(int i = 0; i < n; i++) append(sc.nextInt());\n"
                + "        display();\n"
                + "        System.out.print(\"Enter location index to delete: \");\n"
                + "        int loc = sc.nextInt();\n"
                + "        System.out.print(\"Enter predecessor location index (-1 if deleting first node): \");\n"
                + "        int locp = sc.nextInt();\n"
                + "        del(loc, locp);\n"
                + "        System.out.println(\"Deleted successfully\");\n"
                + "        display();\n"
                + "    }\n"
                + "}";
        }
        return "import java.util.Scanner;\n"
            + "class Main {\n"
            + "    static int MAX = 100;\n"
            + "    static int[] INFO = new int[MAX];\n"
            + "    static int[] LINK = new int[MAX];\n"
            + "    static int START = -1;\n"
            + "    static int AVAIL;\n"
            + "    static void init() {\n"
            + "        for(int i = 0; i < MAX - 1; i++) LINK[i] = i + 1;\n"
            + "        LINK[MAX - 1] = -1;\n"
            + "        AVAIL = 0;\n"
            + "    }\n"
            + "    static void append(int item) {\n"
            + "        if(AVAIL == -1) return;\n"
            + "        int NEW = AVAIL;\n"
            + "        AVAIL = LINK[AVAIL];\n"
            + "        INFO[NEW] = item;\n"
            + "        LINK[NEW] = -1;\n"
            + "        if(START == -1) START = NEW;\n"
            + "        else {\n"
            + "            int ptr = START;\n"
            + "            while(LINK[ptr] != -1) ptr = LINK[ptr];\n"
            + "            LINK[ptr] = NEW;\n"
            + "        }\n"
            + "    }\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        init();\n"
            + "        System.out.print(\"Enter number of nodes: \");\n"
            + "        int n = sc.nextInt();\n"
            + "        System.out.println(\"Enter node values:\");\n"
            + "        for(int i = 0; i < n; i++) append(sc.nextInt());\n"
            + "        System.out.print(\"Current linked list: \");\n"
            + "        for(int ptr = START; ptr != -1; ptr = LINK[ptr]) System.out.print(INFO[ptr] + \" \");\n"
            + "        System.out.println();\n"
            + "        System.out.print(\"Enter location index to delete: \");\n"
            + "        int loc = sc.nextInt();\n"
            + "        System.out.print(\"Enter predecessor location index (-1 if deleting first node): \");\n"
            + "        int locp = sc.nextInt();\n"
            + "        if(locp == -1) {\n"
            + "            START = LINK[START];\n"
            + "        } else {\n"
            + "            LINK[locp] = LINK[loc];\n"
            + "        }\n"
            + "        LINK[loc] = AVAIL;\n"
            + "        AVAIL = loc;\n"
            + "        System.out.println(\"Deleted successfully\");\n"
            + "        System.out.print(\"Linked list after deletion: \");\n"
            + "        for(int ptr = START; ptr != -1; ptr = LINK[ptr]) System.out.print(INFO[ptr] + \" \");\n"
            + "        System.out.println();\n"
            + "    }\n"
            + "}";
    }

    private static String getJavaDeleteNodeByItemLinkedList(String style) {
        if ("Recursion".equals(style)) {
            return "// Recursion is not suitable here because predecessor tracking is required.";
        }
        return "import java.util.Scanner;\n"
            + "class Main {\n"
            + "    static int MAX = 100;\n"
            + "    static int[] INFO = new int[MAX];\n"
            + "    static int[] LINK = new int[MAX];\n"
            + "    static int START = -1;\n"
            + "    static int AVAIL;\n"
            + "    static void init() {\n"
            + "        for(int i = 0; i < MAX - 1; i++) LINK[i] = i + 1;\n"
            + "        LINK[MAX - 1] = -1;\n"
            + "        AVAIL = 0;\n"
            + "    }\n"
            + "    static void append(int item) {\n"
            + "        if(AVAIL == -1) return;\n"
            + "        int NEW = AVAIL;\n"
            + "        AVAIL = LINK[AVAIL];\n"
            + "        INFO[NEW] = item;\n"
            + "        LINK[NEW] = -1;\n"
            + "        if(START == -1) START = NEW;\n"
            + "        else {\n"
            + "            int ptr = START;\n"
            + "            while(LINK[ptr] != -1) ptr = LINK[ptr];\n"
            + "            LINK[ptr] = NEW;\n"
            + "        }\n"
            + "    }\n"
            + "    static int[] findb(int item) {\n"
            + "        int[] res = new int[]{-1, -1};\n"
            + "        if(START == -1) return res;\n"
            + "        if(INFO[START] == item) {\n"
            + "            res[0] = START;\n"
            + "            return res;\n"
            + "        }\n"
            + "        int save = START;\n"
            + "        int ptr = LINK[START];\n"
            + "        while(ptr != -1) {\n"
            + "            if(INFO[ptr] == item) {\n"
            + "                res[0] = ptr;\n"
            + "                res[1] = save;\n"
            + "                return res;\n"
            + "            }\n"
            + "            save = ptr;\n"
            + "            ptr = LINK[ptr];\n"
            + "        }\n"
            + "        return res;\n"
            + "    }\n"
            + "    static void deleteItem(int item) {\n"
            + "        int[] res = findb(item);\n"
            + "        int loc = res[0];\n"
            + "        int locp = res[1];\n"
            + "        if(loc == -1) {\n"
            + "            System.out.println(\"ITEM not in list\");\n"
            + "            return;\n"
            + "        }\n"
            + "        if(locp == -1) START = LINK[START];\n"
            + "        else LINK[locp] = LINK[loc];\n"
            + "        LINK[loc] = AVAIL;\n"
            + "        AVAIL = loc;\n"
            + "    }\n"
            + "    public static void main(String[] args) {\n"
            + "        init();\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        System.out.print(\"Enter number of nodes: \");\n"
            + "        int n = sc.nextInt();\n"
            + "        System.out.println(\"Enter node values:\");\n"
            + "        for(int i = 0; i < n; i++) append(sc.nextInt());\n"
            + "        System.out.print(\"Current linked list: \");\n"
            + "        for(int ptr = START; ptr != -1; ptr = LINK[ptr]) System.out.print(INFO[ptr] + \" \");\n"
            + "        System.out.println();\n"
            + "        System.out.print(\"Enter item to delete: \");\n"
            + "        int item = sc.nextInt();\n"
            + "        deleteItem(item);\n"
            + "        System.out.println(\"Deletion completed\");\n"
            + "        System.out.print(\"Linked list after deletion: \");\n"
            + "        for(int ptr = START; ptr != -1; ptr = LINK[ptr]) System.out.print(INFO[ptr] + \" \");\n"
            + "        System.out.println();\n"
            + "    }\n"
            + "}";
    }

    private static String getJavaTraverseCircularHeaderList(String style) {
        if ("Recursion".equals(style)) {
            return "import java.util.*;\n"
                + "class Node {\n"
                + "    int data;\n"
                + "    Node next;\n"
                + "    Node(int data) {\n"
                + "        this.data = data;\n"
                + "        this.next = null;\n"
                + "    }\n"
                + "}\n"
                + "public class CircularHeaderList {\n"
                + "    static void traverse(Node ptr, Node header) {\n"
                + "        if(ptr == header) {\n"
                + "            return;\n"
                + "        }\n"
                + "        System.out.print(ptr.data + \" \");\n"
                + "        traverse(ptr.next, header);\n"
                + "    }\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        int n = sc.nextInt();\n"
                + "        Node header = new Node(-1);\n"
                + "        header.next = header;\n"
                + "        Node last = header;\n"
                + "        for(int i = 0; i < n; i++) {\n"
                + "            int val = sc.nextInt();\n"
                + "            Node temp = new Node(val);\n"
                + "            last.next = temp;\n"
                + "            temp.next = header;\n"
                + "            last = temp;\n"
                + "        }\n"
                + "        traverse(header.next, header);\n"
                + "    }\n"
                + "}";
        }
        if ("Functions".equals(style)) {
            return "import java.util.*;\n"
                + "class Node {\n"
                + "    int data;\n"
                + "    Node next;\n"
                + "    Node(int data) {\n"
                + "        this.data = data;\n"
                + "        this.next = null;\n"
                + "    }\n"
                + "}\n"
                + "public class CircularHeaderList {\n"
                + "    static void traverse(Node header) {\n"
                + "        Node ptr = header.next;\n"
                + "        while(ptr != header) {\n"
                + "            System.out.print(ptr.data + \" \");\n"
                + "            ptr = ptr.next;\n"
                + "        }\n"
                + "    }\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        int n = sc.nextInt();\n"
                + "        Node header = new Node(-1);\n"
                + "        header.next = header;\n"
                + "        Node last = header;\n"
                + "        for(int i = 0; i < n; i++) {\n"
                + "            int val = sc.nextInt();\n"
                + "            Node temp = new Node(val);\n"
                + "            last.next = temp;\n"
                + "            temp.next = header;\n"
                + "            last = temp;\n"
                + "        }\n"
                + "        traverse(header);\n"
                + "    }\n"
                + "}";
        }
        return "import java.util.*;\n"
            + "class Node {\n"
            + "    int data;\n"
            + "    Node next;\n"
            + "    Node(int data) {\n"
            + "        this.data = data;\n"
            + "        this.next = null;\n"
            + "    }\n"
            + "}\n"
            + "public class CircularHeaderList {\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        int n = sc.nextInt();\n"
            + "        Node header = new Node(-1);\n"
            + "        header.next = header;\n"
            + "        Node last = header;\n"
            + "        for(int i = 0; i < n; i++) {\n"
            + "            int val = sc.nextInt();\n"
            + "            Node temp = new Node(val);\n"
            + "            last.next = temp;\n"
            + "            temp.next = header;\n"
            + "            last = temp;\n"
            + "        }\n"
            + "        Node ptr = header.next;\n"
            + "        while(ptr != header) {\n"
            + "            System.out.print(ptr.data + \" \");\n"
            + "            ptr = ptr.next;\n"
            + "        }\n"
            + "    }\n"
            + "}";
    }

    private static String getJavaSearchCircularHeaderList(String style) {
        if ("Recursion".equals(style)) {
            return "import java.util.*;\n"
                + "class Node {\n"
                + "    int data;\n"
                + "    Node next;\n"
                + "    Node(int data) { this.data = data; this.next = null; }\n"
                + "}\n"
                + "public class CircularSearch {\n"
                + "    static Node search(Node ptr, Node header, int item) {\n"
                + "        if(ptr == header)\n"
                + "            return null;\n"
                + "        if(ptr.data == item)\n"
                + "            return ptr;\n"
                + "        return search(ptr.next, header, item);\n"
                + "    }\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        int n = sc.nextInt();\n"
                + "        Node header = new Node(-1);\n"
                + "        header.next = header;\n"
                + "        Node last = header;\n"
                + "        for(int i = 0; i < n; i++) {\n"
                + "            int val = sc.nextInt();\n"
                + "            Node temp = new Node(val);\n"
                + "            last.next = temp;\n"
                + "            temp.next = header;\n"
                + "            last = temp;\n"
                + "        }\n"
                + "        int item = sc.nextInt();\n"
                + "        Node result = search(header.next, header, item);\n"
                + "        if(result != null)\n"
                + "            System.out.println(\"Element found\");\n"
                + "        else\n"
                + "            System.out.println(\"Element not found\");\n"
                + "    }\n"
                + "}";
        }
        if ("Functions".equals(style)) {
            return "import java.util.*;\n"
                + "class Node {\n"
                + "    int data;\n"
                + "    Node next;\n"
                + "    Node(int data) { this.data = data; this.next = null; }\n"
                + "}\n"
                + "public class CircularSearch {\n"
                + "    static Node search(Node header, int item) {\n"
                + "        Node ptr = header.next;\n"
                + "        while(ptr != header && ptr.data != item) {\n"
                + "            ptr = ptr.next;\n"
                + "        }\n"
                + "        if(ptr != header)\n"
                + "            return ptr;\n"
                + "        return null;\n"
                + "    }\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        int n = sc.nextInt();\n"
                + "        Node header = new Node(-1);\n"
                + "        header.next = header;\n"
                + "        Node last = header;\n"
                + "        for(int i = 0; i < n; i++) {\n"
                + "            int val = sc.nextInt();\n"
                + "            Node temp = new Node(val);\n"
                + "            last.next = temp;\n"
                + "            temp.next = header;\n"
                + "            last = temp;\n"
                + "        }\n"
                + "        int item = sc.nextInt();\n"
                + "        Node result = search(header, item);\n"
                + "        if(result != null)\n"
                + "            System.out.println(\"Element found\");\n"
                + "        else\n"
                + "            System.out.println(\"Element not found\");\n"
                + "    }\n"
                + "}";
        }
        return "import java.util.*;\n"
            + "class Node {\n"
            + "    int data;\n"
            + "    Node next;\n"
            + "    Node(int data) { this.data = data; this.next = null; }\n"
            + "}\n"
            + "public class CircularSearch {\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        int n = sc.nextInt();\n"
            + "        Node header = new Node(-1);\n"
            + "        header.next = header;\n"
            + "        Node last = header;\n"
            + "        for(int i = 0; i < n; i++) {\n"
            + "            int val = sc.nextInt();\n"
            + "            Node temp = new Node(val);\n"
            + "            last.next = temp;\n"
            + "            temp.next = header;\n"
            + "            last = temp;\n"
            + "        }\n"
            + "        int item = sc.nextInt();\n"
            + "        Node ptr = header.next;\n"
            + "        while(ptr != header && ptr.data != item) {\n"
            + "            ptr = ptr.next;\n"
            + "        }\n"
            + "        if(ptr != header)\n"
            + "            System.out.println(\"Element found\");\n"
            + "        else\n"
            + "            System.out.println(\"Element not found\");\n"
            + "    }\n"
            + "}";
    }

    private static String getJavaDeleteCircularHeaderList(String style) {
        if ("Recursion".equals(style)) {
            return "import java.util.*;\n"
                + "class Node {\n"
                + "    int data;\n"
                + "    Node next;\n"
                + "    Node(int data) { this.data = data; this.next = null; }\n"
                + "}\n"
                + "public class DeleteCircularHL {\n"
                + "    static Node[] find(Node ptr, Node header, Node prev, int item) {\n"
                + "        if(ptr == header)\n"
                + "            return new Node[]{null, prev};\n"
                + "        if(ptr.data == item)\n"
                + "            return new Node[]{ptr, prev};\n"
                + "        return find(ptr.next, header, ptr, item);\n"
                + "    }\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        int n = sc.nextInt();\n"
                + "        Node header = new Node(-1);\n"
                + "        header.next = header;\n"
                + "        Node last = header;\n"
                + "        for(int i = 0; i < n; i++) {\n"
                + "            int val = sc.nextInt();\n"
                + "            Node temp = new Node(val);\n"
                + "            last.next = temp;\n"
                + "            temp.next = header;\n"
                + "            last = temp;\n"
                + "        }\n"
                + "        int item = sc.nextInt();\n"
                + "        Node[] res = find(header.next, header, header, item);\n"
                + "        Node loc = res[0];\n"
                + "        Node locp = res[1];\n"
                + "        if(loc == null) {\n"
                + "            System.out.println(\"Item not found\");\n"
                + "        } else {\n"
                + "            locp.next = loc.next;\n"
                + "            System.out.println(\"Item deleted\");\n"
                + "        }\n"
                + "    }\n"
                + "}";
        }
        if ("Functions".equals(style)) {
            return "import java.util.*;\n"
                + "class Node {\n"
                + "    int data;\n"
                + "    Node next;\n"
                + "    Node(int data) { this.data = data; this.next = null; }\n"
                + "}\n"
                + "public class DeleteCircularHL {\n"
                + "    static Node[] find(Node header, int item) {\n"
                + "        Node ptr = header.next;\n"
                + "        Node save = header;\n"
                + "        while(ptr != header && ptr.data != item) {\n"
                + "            save = ptr;\n"
                + "            ptr = ptr.next;\n"
                + "        }\n"
                + "        if(ptr == header)\n"
                + "            return new Node[]{null, save};\n"
                + "        return new Node[]{ptr, save};\n"
                + "    }\n"
                + "    static void delete(Node header, int item) {\n"
                + "        Node[] res = find(header, item);\n"
                + "        Node loc = res[0];\n"
                + "        Node locp = res[1];\n"
                + "        if(loc == null) {\n"
                + "            System.out.println(\"Item not found\");\n"
                + "            return;\n"
                + "        }\n"
                + "        locp.next = loc.next;\n"
                + "        System.out.println(\"Item deleted\");\n"
                + "    }\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        int n = sc.nextInt();\n"
                + "        Node header = new Node(-1);\n"
                + "        header.next = header;\n"
                + "        Node last = header;\n"
                + "        for(int i = 0; i < n; i++) {\n"
                + "            int val = sc.nextInt();\n"
                + "            Node temp = new Node(val);\n"
                + "            last.next = temp;\n"
                + "            temp.next = header;\n"
                + "            last = temp;\n"
                + "        }\n"
                + "        int item = sc.nextInt();\n"
                + "        delete(header, item);\n"
                + "    }\n"
                + "}";
        }
        return "import java.util.*;\n"
            + "class Node {\n"
            + "    int data;\n"
            + "    Node next;\n"
            + "    Node(int data) { this.data = data; this.next = null; }\n"
            + "}\n"
            + "public class DeleteCircularHL {\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        int n = sc.nextInt();\n"
            + "        Node header = new Node(-1);\n"
            + "        header.next = header;\n"
            + "        Node last = header;\n"
            + "        for(int i = 0; i < n; i++) {\n"
            + "            int val = sc.nextInt();\n"
            + "            Node temp = new Node(val);\n"
            + "            last.next = temp;\n"
            + "            temp.next = header;\n"
            + "            last = temp;\n"
            + "        }\n"
            + "        int item = sc.nextInt();\n"
            + "        Node ptr = header.next;\n"
            + "        Node save = header;\n"
            + "        while(ptr != header && ptr.data != item) {\n"
            + "            save = ptr;\n"
            + "            ptr = ptr.next;\n"
            + "        }\n"
            + "        if(ptr == header) {\n"
            + "            System.out.println(\"Item not found\");\n"
            + "        } else {\n"
            + "            save.next = ptr.next;\n"
            + "            System.out.println(\"Item deleted\");\n"
            + "        }\n"
            + "    }\n"
            + "}";
    }

    private static String getCCountNodesLinkedList(String style) {
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "struct node { int data; struct node* next; };",
                "int count(struct node* ptr) {",
                "    if(ptr == NULL) return 0;",
                "    return 1 + count(ptr->next);",
                "}");
        }
        if ("Functions".equals(style)) {
            return String.join("\n",
                "struct node { int data; struct node* next; };",
                "int count(struct node* ptr) {",
                "    int num = 0;",
                "    while(ptr != NULL) {",
                "        num++;",
                "        ptr = ptr->next;",
                "    }",
                "    return num;",
                "}");
        }
        return String.join("\n",
            "struct node { int data; struct node* next; };",
            "int main() {",
            "    int num = 0;",
            "    struct node* ptr = NULL;",
            "    while(ptr != NULL) {",
            "        num++;",
            "        ptr = ptr->next;",
            "    }",
            "}");
    }

    private static String getCSearchInLinkedList(String style) {
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "struct node { int data; struct node* next; };",
                "struct node* search(struct node* ptr, int item) {",
                "    if(ptr == NULL) return NULL;",
                "    if(ptr->data == item) return ptr;",
                "    return search(ptr->next, item);",
                "}");
        }
        if ("Functions".equals(style)) {
            return String.join("\n",
                "struct node { int data; struct node* next; };",
                "struct node* search(struct node* ptr, int item) {",
                "    while(ptr != NULL) {",
                "        if(ptr->data == item) return ptr;",
                "        ptr = ptr->next;",
                "    }",
                "    return NULL;",
                "}");
        }
        return String.join("\n",
            "struct node { int data; struct node* next; };",
            "int main() {",
            "    struct node *ptr = NULL, *loc = NULL;",
            "    int item = 0;",
            "    while(ptr != NULL) {",
            "        if(ptr->data == item) { loc = ptr; break; }",
            "        ptr = ptr->next;",
            "    }",
            "}");
    }

    private static String getCInsertAtBeginningLinkedList(String style) {
        if ("Recursion".equals(style)) {
            return "/* Recursion is not applicable here because insertion at beginning is a single-step pointer update. */";
        }
        return String.join("\n",
            "int INFO[100], LINK[100];",
            "int START = -1, AVAIL = 0;",
            "void insFirst(int item) {",
            "    if(AVAIL == -1) return;",
            "    int NEW = AVAIL;",
            "    AVAIL = LINK[AVAIL];",
            "    INFO[NEW] = item;",
            "    LINK[NEW] = START;",
            "    START = NEW;",
            "}");
    }

    private static String getCInsertAfterGivenNodeLinkedList(String style) {
        if ("Recursion".equals(style)) {
            return "/* Recursion is not applicable here because insertion after a given node is a direct pointer operation. */";
        }
        return String.join("\n",
            "int INFO[100], LINK[100];",
            "int START = -1, AVAIL = 0;",
            "void insLoc(int loc, int item) {",
            "    if(AVAIL == -1) return;",
            "    int NEW = AVAIL;",
            "    AVAIL = LINK[AVAIL];",
            "    INFO[NEW] = item;",
            "    if(loc == -1) { LINK[NEW] = START; START = NEW; }",
            "    else { LINK[NEW] = LINK[loc]; LINK[loc] = NEW; }",
            "}");
    }

    private static String getCInsertIntoSortedLinkedList(String style) {
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "int INFO[100], LINK[100];",
                "int findARec(int ptr, int item, int previous) {",
                "    if(ptr == -1) return previous;",
                "    if(item < INFO[ptr]) return previous;",
                "    return findARec(LINK[ptr], item, ptr);",
                "}");
        }
        return String.join("\n",
            "int INFO[100], LINK[100];",
            "int START = -1;",
            "int findA(int item) {",
            "    if(START == -1) return -1;",
            "    if(item < INFO[START]) return -1;",
            "    int save = START;",
            "    int ptr = LINK[START];",
            "    while(ptr != -1) {",
            "        if(item < INFO[ptr]) return save;",
            "        save = ptr;",
            "        ptr = LINK[ptr];",
            "    }",
            "    return save;",
            "}");
    }

    private static String getCDeleteNodeGivenLocationLinkedList(String style) {
        if ("Recursion".equals(style)) {
            return "/* Recursion is not applicable here because deletion by location is a direct pointer update. */";
        }
        return String.join("\n",
            "int LINK[100];",
            "int START = -1, AVAIL = 0;",
            "void del(int loc, int locp) {",
            "    if(locp == -1) START = LINK[START];",
            "    else LINK[locp] = LINK[loc];",
            "    LINK[loc] = AVAIL;",
            "    AVAIL = loc;",
            "}");
    }

    private static String getCDeleteNodeByItemLinkedList(String style) {
        if ("Recursion".equals(style)) {
            return "/* Recursion is not suitable here because predecessor tracking is required. */";
        }
        return String.join("\n",
            "int INFO[100], LINK[100];",
            "int START = -1;",
            "void findb(int item, int *loc, int *locp) {",
            "    if(START == -1) { *loc = -1; *locp = -1; return; }",
            "    if(INFO[START] == item) { *loc = START; *locp = -1; return; }",
            "    int save = START;",
            "    int ptr = LINK[START];",
            "    while(ptr != -1) {",
            "        if(INFO[ptr] == item) { *loc = ptr; *locp = save; return; }",
            "        save = ptr;",
            "        ptr = LINK[ptr];",
            "    }",
            "    *loc = -1; *locp = -1;",
            "}");
    }

    private static String getCTraverseCircularHeaderList(String style) {
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "void traverse(struct Node* ptr, struct Node* header) {",
                "    if(ptr == header) return;",
                "    printf(\"%d \", ptr->data);",
                "    traverse(ptr->next, header);",
                "}");
        }
        if ("Functions".equals(style)) {
            return String.join("\n",
                "void traverse(struct Node* header) {",
                "    struct Node* ptr = header->next;",
                "    while(ptr != header) {",
                "        printf(\"%d \", ptr->data);",
                "        ptr = ptr->next;",
                "    }",
                "}");
        }
        return String.join("\n",
            "struct Node* ptr = header->next;",
            "while(ptr != header) {",
            "    printf(\"%d \", ptr->data);",
            "    ptr = ptr->next;",
            "}");
    }

    private static String getCSearchCircularHeaderList(String style) {
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "struct Node* search(struct Node* ptr, struct Node* header, int item) {",
                "    if(ptr == header) return NULL;",
                "    if(ptr->data == item) return ptr;",
                "    return search(ptr->next, header, item);",
                "}");
        }
        if ("Functions".equals(style)) {
            return String.join("\n",
                "struct Node* search(struct Node* header, int item) {",
                "    struct Node* ptr = header->next;",
                "    while(ptr != header && ptr->data != item) {",
                "        ptr = ptr->next;",
                "    }",
                "    if(ptr != header) return ptr;",
                "    return NULL;",
                "}");
        }
        return String.join("\n",
            "struct Node* ptr = header->next;",
            "while(ptr != header && ptr->data != item) {",
            "    ptr = ptr->next;",
            "}",
            "if(ptr != header) printf(\"Element found\\n\");",
            "else printf(\"Element not found\\n\");");
    }

    private static String getCDeleteCircularHeaderList(String style) {
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "struct Node* find(struct Node* ptr, struct Node* header, struct Node* prev, int item, struct Node** locp) {",
                "    if(ptr == header) { *locp = prev; return NULL; }",
                "    if(ptr->data == item) { *locp = prev; return ptr; }",
                "    return find(ptr->next, header, ptr, item, locp);",
                "}");
        }
        if ("Functions".equals(style)) {
            return String.join("\n",
                "void deleteNode(struct Node* header, int item) {",
                "    struct Node* ptr = header->next;",
                "    struct Node* save = header;",
                "    while(ptr != header && ptr->data != item) {",
                "        save = ptr;",
                "        ptr = ptr->next;",
                "    }",
                "    if(ptr == header) return;",
                "    save->next = ptr->next;",
                "}");
        }
        return String.join("\n",
            "struct Node* ptr = header->next;",
            "struct Node* save = header;",
            "while(ptr != header && ptr->data != item) {",
            "    save = ptr;",
            "    ptr = ptr->next;",
            "}",
            "if(ptr == header) printf(\"Item not found\\n\");",
            "else save->next = ptr->next;");
    }

    private static String getPythonCountNodesLinkedList(String style) {
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "def count_nodes(ptr):",
                "    if ptr is None:",
                "        return 0",
                "    return 1 + count_nodes(ptr.next)");
        }
        if ("Functions".equals(style)) {
            return String.join("\n",
                "def count_nodes(ptr):",
                "    num = 0",
                "    while ptr:",
                "        num += 1",
                "        ptr = ptr.next",
                "    return num");
        }
        return String.join("\n",
            "count = 0",
            "ptr = head",
            "while ptr:",
            "    count += 1",
            "    ptr = ptr.next");
    }

    private static String getPythonSearchInLinkedList(String style) {
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "def search(ptr, item):",
                "    if ptr is None:",
                "        return None",
                "    if ptr.data == item:",
                "        return ptr",
                "    return search(ptr.next, item)");
        }
        if ("Functions".equals(style)) {
            return String.join("\n",
                "def search(ptr, item):",
                "    while ptr:",
                "        if ptr.data == item:",
                "            return ptr",
                "        ptr = ptr.next",
                "    return None");
        }
        return String.join("\n",
            "ptr = head",
            "loc = None",
            "while ptr:",
            "    if ptr.data == item:",
            "        loc = ptr",
            "        break",
            "    ptr = ptr.next");
    }

    private static String getPythonInsertAtBeginningLinkedList(String style) {
        if ("Recursion".equals(style)) {
            return "# Recursion is not applicable here because insertion at beginning is a single-step pointer update.";
        }
        return String.join("\n",
            "INFO = [0] * 100",
            "LINK = list(range(1, 100)) + [-1]",
            "START = -1",
            "AVAIL = 0",
            "def ins_first(item):",
            "    global START, AVAIL",
            "    if AVAIL == -1:",
            "        return",
            "    new = AVAIL",
            "    AVAIL = LINK[AVAIL]",
            "    INFO[new] = item",
            "    LINK[new] = START",
            "    START = new");
    }

    private static String getPythonInsertAfterGivenNodeLinkedList(String style) {
        if ("Recursion".equals(style)) {
            return "# Recursion is not applicable here because insertion after a given node is a direct pointer operation.";
        }
        return String.join("\n",
            "INFO = [0] * 100",
            "LINK = list(range(1, 100)) + [-1]",
            "START = -1",
            "AVAIL = 0",
            "def ins_loc(loc, item):",
            "    global START, AVAIL",
            "    if AVAIL == -1:",
            "        return",
            "    new = AVAIL",
            "    AVAIL = LINK[AVAIL]",
            "    INFO[new] = item",
            "    if loc == -1:",
            "        LINK[new] = START",
            "        START = new",
            "    else:",
            "        LINK[new] = LINK[loc]",
            "        LINK[loc] = new");
    }

    private static String getPythonInsertIntoSortedLinkedList(String style) {
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "def findARec(ptr, item, previous):",
                "    if ptr == -1:",
                "        return previous",
                "    if item < INFO[ptr]:",
                "        return previous",
                "    return findARec(LINK[ptr], item, ptr)");
        }
        return String.join("\n",
            "def findA(item):",
            "    global START",
            "    if START == -1:",
            "        return -1",
            "    if item < INFO[START]:",
            "        return -1",
            "    save = START",
            "    ptr = LINK[START]",
            "    while ptr != -1:",
            "        if item < INFO[ptr]:",
            "            return save",
            "        save = ptr",
            "        ptr = LINK[ptr]",
            "    return save");
    }

    private static String getPythonDeleteNodeGivenLocationLinkedList(String style) {
        if ("Recursion".equals(style)) {
            return "# Recursion is not applicable here because deletion by location is a direct pointer update.";
        }
        return String.join("\n",
            "def delete(loc, locp):",
            "    global START, AVAIL",
            "    if locp == -1:",
            "        START = LINK[START]",
            "    else:",
            "        LINK[locp] = LINK[loc]",
            "    LINK[loc] = AVAIL",
            "    AVAIL = loc");
    }

    private static String getPythonDeleteNodeByItemLinkedList(String style) {
        if ("Recursion".equals(style)) {
            return "# Recursion is not suitable here because predecessor tracking is required.";
        }
        return String.join("\n",
            "def findb(item):",
            "    global START",
            "    if START == -1:",
            "        return (-1, -1)",
            "    if INFO[START] == item:",
            "        return (START, -1)",
            "    save = START",
            "    ptr = LINK[START]",
            "    while ptr != -1:",
            "        if INFO[ptr] == item:",
            "            return (ptr, save)",
            "        save = ptr",
            "        ptr = LINK[ptr]",
            "    return (-1, -1)");
    }

    private static String getPythonTraverseCircularHeaderList(String style) {
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "def traverse(ptr, header):",
                "    if ptr == header:",
                "        return",
                "    print(ptr.data, end=\" \")",
                "    traverse(ptr.next, header)");
        }
        if ("Functions".equals(style)) {
            return String.join("\n",
                "def traverse(header):",
                "    ptr = header.next",
                "    while ptr != header:",
                "        print(ptr.data, end=\" \")",
                "        ptr = ptr.next");
        }
        return String.join("\n",
            "ptr = header.next",
            "while ptr != header:",
            "    print(ptr.data, end=\" \")",
            "    ptr = ptr.next");
    }

    private static String getPythonSearchCircularHeaderList(String style) {
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "def search(ptr, header, item):",
                "    if ptr == header:",
                "        return None",
                "    if ptr.data == item:",
                "        return ptr",
                "    return search(ptr.next, header, item)");
        }
        if ("Functions".equals(style)) {
            return String.join("\n",
                "def search(header, item):",
                "    ptr = header.next",
                "    while ptr != header and ptr.data != item:",
                "        ptr = ptr.next",
                "    if ptr != header:",
                "        return ptr",
                "    return None");
        }
        return String.join("\n",
            "ptr = header.next",
            "while ptr != header and ptr.data != item:",
            "    ptr = ptr.next",
            "print(\"Element found\" if ptr != header else \"Element not found\")");
    }

    private static String getPythonDeleteCircularHeaderList(String style) {
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "def find(ptr, header, prev, item):",
                "    if ptr == header:",
                "        return None, prev",
                "    if ptr.data == item:",
                "        return ptr, prev",
                "    return find(ptr.next, header, ptr, item)");
        }
        if ("Functions".equals(style)) {
            return String.join("\n",
                "def delete_node(header, item):",
                "    ptr = header.next",
                "    save = header",
                "    while ptr != header and ptr.data != item:",
                "        save = ptr",
                "        ptr = ptr.next",
                "    if ptr == header:",
                "        print(\"Item not found\")",
                "    else:",
                "        save.next = ptr.next",
                "        print(\"Item deleted\")");
        }
        return String.join("\n",
            "ptr = header.next",
            "save = header",
            "while ptr != header and ptr.data != item:",
            "    save = ptr",
            "    ptr = ptr.next",
            "if ptr == header:",
            "    print(\"Item not found\")",
            "else:",
            "    save.next = ptr.next",
            "    print(\"Item deleted\")");
    }

    private static String getCStackBasics(String style) {
        if ("Recursion".equals(style)) {
            return "/* Recursion is not applicable for array stack PUSH and POP in this book style. */";
        }
        if ("Functions".equals(style)) {
            return String.join("\n",
                "#include <stdio.h>",
                "#define MAX 10",
                "",
                "int STACK[MAX];",
                "int TOP = -1;",
                "",
                "void push(int item) {",
                "    if(TOP == MAX - 1) {",
                "        printf(\"Overflow\\n\");",
                "        return;",
                "    }",
                "    TOP = TOP + 1;",
                "    STACK[TOP] = item;",
                "}",
                "",
                "int pop() {",
                "    int item;",
                "    if(TOP == -1) {",
                "        printf(\"Underflow\\n\");",
                "        return -1;",
                "    }",
                "    item = STACK[TOP];",
                "    TOP = TOP - 1;",
                "    return item;",
                "}",
                "",
                "int main() {",
                "    int choice, item;",
                "    do {",
                "        printf(\"1. PUSH\\n2. POP\\n3. EXIT\\n\");",
                "        printf(\"Enter choice: \");",
                "        scanf(\"%d\", &choice);",
                "",
                "        if(choice == 1) {",
                "            printf(\"Enter item: \");",
                "            scanf(\"%d\", &item);",
                "            push(item);",
                "        } else if(choice == 2) {",
                "            item = pop();",
                "            if(item != -1) {",
                "                printf(\"Deleted item: %d\\n\", item);",
                "            }",
                "        }",
                "    } while(choice != 3);",
                "    return 0;",
                "}");
        }
        return String.join("\n",
            "#include <stdio.h>",
            "#define MAX 10",
            "",
            "int main() {",
            "    int STACK[MAX];",
            "    int TOP = -1;",
            "    int choice, item;",
            "",
            "    do {",
            "        printf(\"1. PUSH\\n2. POP\\n3. EXIT\\n\");",
            "        printf(\"Enter choice: \");",
            "        scanf(\"%d\", &choice);",
            "",
            "        if(choice == 1) {",
            "            if(TOP == MAX - 1) {",
            "                printf(\"Overflow\\n\");",
            "            } else {",
            "                printf(\"Enter item: \");",
            "                scanf(\"%d\", &item);",
            "                TOP = TOP + 1;",
            "                STACK[TOP] = item;",
            "            }",
            "        } else if(choice == 2) {",
            "            if(TOP == -1) {",
            "                printf(\"Underflow\\n\");",
            "            } else {",
            "                item = STACK[TOP];",
            "                TOP = TOP - 1;",
            "                printf(\"Deleted item: %d\\n\", item);",
            "            }",
            "        }",
            "    } while(choice != 3);",
            "    return 0;",
            "}");
    }

    private static String getCLinearQueue(String style) {
        if ("Functions".equals(style)) {
            return String.join("\n",
                "#include <stdio.h>",
                "",
                "void insert(int queue[], int *front, int *rear, int n, int item) {",
                "    if (*rear == n) {",
                "        printf(\"OVERFLOW\\n\");",
                "        return;",
                "    }",
                "    if (*front == -1) {",
                "        *front = 0;",
                "        *rear = 0;",
                "    } else {",
                "        (*rear)++;",
                "    }",
                "    queue[*rear] = item;",
                "}",
                "",
                "void delete(int queue[], int *front, int *rear) {",
                "    if (*front == -1) {",
                "        printf(\"UNDERFLOW\\n\");",
                "        return;",
                "    }",
                "    printf(\"Deleted: %d\\n\", queue[*front]);",
                "    if (*front == *rear) {",
                "        *front = -1;",
                "        *rear = -1;",
                "    } else {",
                "        (*front)++;",
                "    }",
                "}",
                "",
                "int main() {",
                "    int n, front = -1, rear = -1, item;",
                "    printf(\"Enter queue size: \");",
                "    scanf(\"%d\", &n);",
                "    int queue[n + 1];",
                "    printf(\"Enter item to insert: \");",
                "    scanf(\"%d\", &item);",
                "    insert(queue, &front, &rear, n, item);",
                "    delete(queue, &front, &rear);",
                "    return 0;",
                "}");
        }
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "#include <stdio.h>",
                "",
                "int main() {",
                "    printf(\"Recursion not applicable for queue insertion and deletion\\n\");",
                "    return 0;",
                "}");
        }
        return String.join("\n",
            "#include <stdio.h>",
            "",
            "int main() {",
            "    int n, front = -1, rear = -1, item;",
            "    printf(\"Enter queue size: \");",
            "    scanf(\"%d\", &n);",
            "    int queue[n + 1];",
            "    printf(\"Enter item to insert: \");",
            "    scanf(\"%d\", &item);",
            "    if (rear == n) {",
            "        printf(\"OVERFLOW\\n\");",
            "    } else {",
            "        if (front == -1) {",
            "            front = 0;",
            "            rear = 0;",
            "        } else {",
            "            rear++;",
            "        }",
            "        queue[rear] = item;",
            "        printf(\"Item inserted\\n\");",
            "    }",
            "    if (front == -1) {",
            "        printf(\"UNDERFLOW\\n\");",
            "    } else {",
            "        int deleted = queue[front];",
            "        if (front == rear) {",
            "            front = -1;",
            "            rear = -1;",
            "        } else {",
            "            front++;",
            "        }",
            "        printf(\"Deleted: %d\\n\", deleted);",
            "    }",
            "    return 0;",
            "}");
    }

    private static String getCCircularQueue(String style) {
        if ("Functions".equals(style)) {
            return String.join("\n",
                "#include <stdio.h>",
                "",
                "void insert(int queue[], int *front, int *rear, int n, int item) {",
                "    if ((*front == 0 && *rear == n - 1) || (*front == *rear + 1)) {",
                "        printf(\"OVERFLOW\\n\");",
                "        return;",
                "    }",
                "    if (*front == -1) {",
                "        *front = 0;",
                "        *rear = 0;",
                "    } else if (*rear == n - 1) {",
                "        *rear = 0;",
                "    } else {",
                "        (*rear)++;",
                "    }",
                "    queue[*rear] = item;",
                "}",
                "",
                "void delete(int queue[], int *front, int *rear, int n) {",
                "    if (*front == -1) {",
                "        printf(\"UNDERFLOW\\n\");",
                "        return;",
                "    }",
                "    printf(\"Deleted: %d\\n\", queue[*front]);",
                "    if (*front == *rear) {",
                "        *front = -1;",
                "        *rear = -1;",
                "    } else if (*front == n - 1) {",
                "        *front = 0;",
                "    } else {",
                "        (*front)++;",
                "    }",
                "}",
                "",
                "int main() {",
                "    int n, front = -1, rear = -1, item;",
                "    printf(\"Enter queue size: \");",
                "    scanf(\"%d\", &n);",
                "    int queue[n];",
                "    printf(\"Enter item to insert: \");",
                "    scanf(\"%d\", &item);",
                "    insert(queue, &front, &rear, n, item);",
                "    delete(queue, &front, &rear, n);",
                "    return 0;",
                "}");
        }
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "#include <stdio.h>",
                "",
                "int main() {",
                "    printf(\"Recursion not applicable for circular queue operations\\n\");",
                "    return 0;",
                "}");
        }
        return String.join("\n",
            "#include <stdio.h>",
            "",
            "int main() {",
            "    int n, front = -1, rear = -1, item;",
            "    printf(\"Enter queue size: \");",
            "    scanf(\"%d\", &n);",
            "    int queue[n];",
            "    printf(\"Enter item to insert: \");",
            "    scanf(\"%d\", &item);",
            "    if ((front == 0 && rear == n - 1) || (front == rear + 1)) {",
            "        printf(\"OVERFLOW\\n\");",
            "    } else {",
            "        if (front == -1) {",
            "            front = 0;",
            "            rear = 0;",
            "        } else if (rear == n - 1) {",
            "            rear = 0;",
            "        } else {",
            "            rear++;",
            "        }",
            "        queue[rear] = item;",
            "        printf(\"Item inserted\\n\");",
            "    }",
            "    if (front == -1) {",
            "        printf(\"UNDERFLOW\\n\");",
            "    } else {",
            "        int deleted = queue[front];",
            "        if (front == rear) {",
            "            front = -1;",
            "            rear = -1;",
            "        } else if (front == n - 1) {",
            "            front = 0;",
            "        } else {",
            "            front++;",
            "        }",
            "        printf(\"Deleted: %d\\n\", deleted);",
            "    }",
            "    return 0;",
            "}");
    }

    private static String getCLinkedQueue(String style) {
        if ("Functions".equals(style)) {
            return String.join("\n",
                "#include <stdio.h>",
                "#include <stdlib.h>",
                "",
                "struct Node {",
                "    int data;",
                "    struct Node* next;",
                "};",
                "",
                "void insert(struct Node** front, struct Node** rear, int item) {",
                "    struct Node* newNode = (struct Node*)malloc(sizeof(struct Node));",
                "    newNode->data = item;",
                "    newNode->next = NULL;",
                "    if (*front == NULL) {",
                "        *front = *rear = newNode;",
                "    } else {",
                "        (*rear)->next = newNode;",
                "        *rear = newNode;",
                "    }",
                "}",
                "",
                "void delete(struct Node** front, struct Node** rear) {",
                "    if (*front == NULL) {",
                "        printf(\"UNDERFLOW\\n\");",
                "        return;",
                "    }",
                "    printf(\"Deleted: %d\\n\", (*front)->data);",
                "    struct Node* temp = *front;",
                "    *front = (*front)->next;",
                "    free(temp);",
                "    if (*front == NULL)",
                "        *rear = NULL;",
                "}",
                "",
                "int main() {",
                "    struct Node *front = NULL, *rear = NULL;",
                "    int item;",
                "    printf(\"Enter item to insert: \");",
                "    scanf(\"%d\", &item);",
                "    insert(&front, &rear, item);",
                "    printf(\"Item inserted\\n\");",
                "    delete(&front, &rear);",
                "    return 0;",
                "}");
        }
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "#include <stdio.h>",
                "",
                "int main() {",
                "    printf(\"Recursion not applicable for linked queue operations\\n\");",
                "    return 0;",
                "}");
        }
        return String.join("\n",
            "#include <stdio.h>",
            "#include <stdlib.h>",
            "",
            "struct Node {",
            "    int data;",
            "    struct Node* next;",
            "};",
            "",
            "int main() {",
            "    struct Node *front = NULL, *rear = NULL;",
            "    int item;",
            "    printf(\"Enter item to insert: \");",
            "    scanf(\"%d\", &item);",
            "    struct Node* newNode = (struct Node*)malloc(sizeof(struct Node));",
            "    newNode->data = item;",
            "    newNode->next = NULL;",
            "    if (front == NULL) {",
            "        front = rear = newNode;",
            "    } else {",
            "        rear->next = newNode;",
            "        rear = newNode;",
            "    }",
            "    printf(\"Item inserted\\n\");",
            "    if (front == NULL) {",
            "        printf(\"UNDERFLOW\\n\");",
            "    } else {",
            "        int deleted = front->data;",
            "        struct Node* temp = front;",
            "        front = front->next;",
            "        free(temp);",
            "        printf(\"Deleted: %d\\n\", deleted);",
            "    }",
            "    return 0;",
            "}");
    }

    private static String getCPriorityQueueArray(String style) {
        if ("Functions".equals(style)) {
            return String.join("\n",
                "#include <stdio.h>",
                "",
                "int insert(int data[], int priority[], int size, int item, int p) {",
                "    int i;",
                "    for (i = size - 1; i >= 0 && priority[i] > p; i--) {",
                "        data[i + 1] = data[i];",
                "        priority[i + 1] = priority[i];",
                "    }",
                "    data[i + 1] = item;",
                "    priority[i + 1] = p;",
                "    return size + 1;",
                "}",
                "",
                "int delete(int data[], int priority[], int size) {",
                "    if (size == 0) {",
                "        printf(\"UNDERFLOW\\n\");",
                "        return size;",
                "    }",
                "    printf(\"Deleted: %d\\n\", data[0]);",
                "    for (int i = 0; i < size - 1; i++) {",
                "        data[i] = data[i + 1];",
                "        priority[i] = priority[i + 1];",
                "    }",
                "    return size - 1;",
                "}",
                "",
                "int main() {",
                "    int n, size = 0, item, p;",
                "    printf(\"Enter size of queue: \");",
                "    scanf(\"%d\", &n);",
                "    int data[n], priority[n];",
                "    printf(\"Enter item: \");",
                "    scanf(\"%d\", &item);",
                "    printf(\"Enter priority: \");",
                "    scanf(\"%d\", &p);",
                "    size = insert(data, priority, size, item, p);",
                "    printf(\"Item inserted\\n\");",
                "    size = delete(data, priority, size);",
                "    return 0;",
                "}");
        }
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "#include <stdio.h>",
                "",
                "int main() {",
                "    printf(\"Recursion not applicable for priority queue (array)\\n\");",
                "    return 0;",
                "}");
        }
        return String.join("\n",
            "#include <stdio.h>",
            "",
            "int main() {",
            "    int n, size = 0, item, p;",
            "    printf(\"Enter size of queue: \");",
            "    scanf(\"%d\", &n);",
            "    int data[n], priority[n];",
            "    printf(\"Enter item: \");",
            "    scanf(\"%d\", &item);",
            "    printf(\"Enter priority: \");",
            "    scanf(\"%d\", &p);",
            "    int i;",
            "    for (i = size - 1; i >= 0 && priority[i] > p; i--) {",
            "        data[i + 1] = data[i];",
            "        priority[i + 1] = priority[i];",
            "    }",
            "    data[i + 1] = item;",
            "    priority[i + 1] = p;",
            "    size++;",
            "    printf(\"Item inserted\\n\");",
            "    if (size == 0) {",
            "        printf(\"UNDERFLOW\\n\");",
            "    } else {",
            "        int deleted = data[0];",
            "        for (int j = 0; j < size - 1; j++) {",
            "            data[j] = data[j + 1];",
            "            priority[j] = priority[j + 1];",
            "        }",
            "        size--;",
            "        printf(\"Deleted: %d\\n\", deleted);",
            "    }",
            "    return 0;",
            "}");
    }

    private static String getCPriorityQueue2d(String style) {
        if ("Functions".equals(style)) {
            return String.join("\n",
                "#include <stdio.h>",
                "",
                "void insert(int queue[][100], int front[], int rear[], int n, int m, int item) {",
                "    if (rear[m] == n - 1) {",
                "        printf(\"OVERFLOW\\n\");",
                "        return;",
                "    }",
                "    if (front[m] == -1) {",
                "        front[m] = 0;",
                "        rear[m] = 0;",
                "    } else {",
                "        rear[m]++;",
                "    }",
                "    queue[m][rear[m]] = item;",
                "}",
                "",
                "void delete(int queue[][100], int front[], int rear[], int p) {",
                "    int k = -1;",
                "    for (int i = 0; i < p; i++) {",
                "        if (front[i] != -1) {",
                "            k = i;",
                "            break;",
                "        }",
                "    }",
                "    if (k == -1) {",
                "        printf(\"UNDERFLOW\\n\");",
                "        return;",
                "    }",
                "    printf(\"Deleted: %d\\n\", queue[k][front[k]]);",
                "    if (front[k] == rear[k]) {",
                "        front[k] = -1;",
                "        rear[k] = -1;",
                "    } else {",
                "        front[k]++;",
                "    }",
                "}",
                "",
                "int main() {",
                "    int p, n;",
                "    printf(\"Enter number of priority levels: \");",
                "    scanf(\"%d\", &p);",
                "    printf(\"Enter size of each queue: \");",
                "    scanf(\"%d\", &n);",
                "    int queue[100][100];",
                "    int front[100], rear[100];",
                "    for (int i = 0; i < p; i++) {",
                "        front[i] = -1;",
                "        rear[i] = -1;",
                "    }",
                "    int item, m;",
                "    printf(\"Enter item: \");",
                "    scanf(\"%d\", &item);",
                "    printf(\"Enter priority level: \");",
                "    scanf(\"%d\", &m);",
                "    insert(queue, front, rear, n, m, item);",
                "    printf(\"Item inserted\\n\");",
                "    delete(queue, front, rear, p);",
                "    return 0;",
                "}");
        }
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "#include <stdio.h>",
                "",
                "int main() {",
                "    printf(\"Recursion not applicable for priority queue (2D array)\\n\");",
                "    return 0;",
                "}");
        }
        return String.join("\n",
            "#include <stdio.h>",
            "",
            "int main() {",
            "    int p, n;",
            "    printf(\"Enter number of priority levels: \");",
            "    scanf(\"%d\", &p);",
            "    printf(\"Enter size of each queue: \");",
            "    scanf(\"%d\", &n);",
            "    int queue[p][n];",
            "    int front[p], rear[p];",
            "    for (int i = 0; i < p; i++) {",
            "        front[i] = -1;",
            "        rear[i] = -1;",
            "    }",
            "    int item, m;",
            "    printf(\"Enter item: \");",
            "    scanf(\"%d\", &item);",
            "    printf(\"Enter priority level: \");",
            "    scanf(\"%d\", &m);",
            "    if (rear[m] == n - 1) {",
            "        printf(\"OVERFLOW\\n\");",
            "    } else {",
            "        if (front[m] == -1) {",
            "            front[m] = 0;",
            "            rear[m] = 0;",
            "        } else {",
            "            rear[m]++;",
            "        }",
            "        queue[m][rear[m]] = item;",
            "        printf(\"Item inserted\\n\");",
            "    }",
            "    int k = -1;",
            "    for (int i = 0; i < p; i++) {",
            "        if (front[i] != -1) {",
            "            k = i;",
            "            break;",
            "        }",
            "    }",
            "    if (k == -1) {",
            "        printf(\"UNDERFLOW\\n\");",
            "    } else {",
            "        int deleted = queue[k][front[k]];",
            "        if (front[k] == rear[k]) {",
            "            front[k] = -1;",
            "            rear[k] = -1;",
            "        } else {",
            "            front[k]++;",
            "        }",
            "        printf(\"Deleted: %d\\n\", deleted);",
            "    }",
            "    return 0;",
            "}");
    }

    private static String getCPreorderTraversal(String style) {
        String header = "#include <stdio.h>\n#include <stdlib.h>\n\n"
            + "struct Node {\n"
            + "    int data;\n"
            + "    struct Node *left, *right;\n"
            + "};\n\n"
            + "struct Node* create(int data) {\n"
            + "    struct Node* n = (struct Node*)malloc(sizeof(struct Node));\n"
            + "    n->data = data;\n"
            + "    n->left = n->right = NULL;\n"
            + "    return n;\n"
            + "}\n\n";
        if ("Functions".equals(style)) {
            return header
                + "void preorder(struct Node* ptr) {\n"
                + "    if (ptr == NULL) return;\n"
                + "    printf(\"%d \", ptr->data);\n"
                + "    preorder(ptr->left);\n"
                + "    preorder(ptr->right);\n"
                + "}\n\n"
                + "int main() {\n"
                + "    int n, i;\n"
                + "    printf(\"Enter number of nodes: \");\n"
                + "    scanf(\"%d\", &n);\n"
                + "    struct Node* nodes[100];\n"
                + "    for (i = 0; i < n; i++) {\n"
                + "        int val;\n"
                + "        printf(\"Enter value of node %d: \", i);\n"
                + "        scanf(\"%d\", &val);\n"
                + "        nodes[i] = create(val);\n"
                + "    }\n"
                + "    for (i = 0; i < n; i++) {\n"
                + "        int l, r;\n"
                + "        printf(\"Enter left and right child index of node %d (-1 if none): \", i);\n"
                + "        scanf(\"%d %d\", &l, &r);\n"
                + "        if (l != -1) nodes[i]->left = nodes[l];\n"
                + "        if (r != -1) nodes[i]->right = nodes[r];\n"
                + "    }\n"
                + "    preorder(nodes[0]);\n"
                + "    return 0;\n"
                + "}";
        }
        if ("Recursion".equals(style)) {
            return header
                + "void preorderRec(struct Node* root) {\n"
                + "    if (root == NULL) return;\n"
                + "    printf(\"%d \", root->data);\n"
                + "    preorderRec(root->left);\n"
                + "    preorderRec(root->right);\n"
                + "}\n\n"
                + "int main() {\n"
                + "    int n, i;\n"
                + "    printf(\"Enter number of nodes: \");\n"
                + "    scanf(\"%d\", &n);\n"
                + "    struct Node* nodes[100];\n"
                + "    for (i = 0; i < n; i++) {\n"
                + "        int val;\n"
                + "        printf(\"Enter value of node %d: \", i);\n"
                + "        scanf(\"%d\", &val);\n"
                + "        nodes[i] = create(val);\n"
                + "    }\n"
                + "    for (i = 0; i < n; i++) {\n"
                + "        int l, r;\n"
                + "        printf(\"Enter left and right child index of node %d (-1 if none): \", i);\n"
                + "        scanf(\"%d %d\", &l, &r);\n"
                + "        if (l != -1) nodes[i]->left = nodes[l];\n"
                + "        if (r != -1) nodes[i]->right = nodes[r];\n"
                + "    }\n"
                + "    preorderRec(nodes[0]);\n"
                + "    return 0;\n"
                + "}";
        }
        return header
            + "int main() {\n"
            + "    int n, i;\n"
            + "    printf(\"Enter number of nodes: \");\n"
            + "    scanf(\"%d\", &n);\n"
            + "    struct Node* nodes[100];\n"
            + "    for (i = 0; i < n; i++) {\n"
            + "        int val;\n"
            + "        printf(\"Enter value of node %d: \", i);\n"
            + "        scanf(\"%d\", &val);\n"
            + "        nodes[i] = create(val);\n"
            + "    }\n"
            + "    for (i = 0; i < n; i++) {\n"
            + "        int l, r;\n"
            + "        printf(\"Enter left and right child index of node %d (-1 if none): \", i);\n"
            + "        scanf(\"%d %d\", &l, &r);\n"
            + "        if (l != -1) nodes[i]->left = nodes[l];\n"
            + "        if (r != -1) nodes[i]->right = nodes[r];\n"
            + "    }\n"
            + "    struct Node* stack[100];\n"
            + "    int top = 0;\n"
            + "    struct Node* ptr = nodes[0];\n"
            + "    stack[top++] = NULL;\n"
            + "    while (ptr != NULL) {\n"
            + "        printf(\"%d \", ptr->data);\n"
            + "        if (ptr->right != NULL) {\n"
            + "            stack[top++] = ptr->right;\n"
            + "        }\n"
            + "        if (ptr->left != NULL) {\n"
            + "            ptr = ptr->left;\n"
            + "        } else {\n"
            + "            ptr = stack[--top];\n"
            + "        }\n"
            + "    }\n"
            + "    return 0;\n"
            + "}";
    }

    private static String getCPostorderTraversal(String style) {
        String header = "#include <stdio.h>\n#include <stdlib.h>\n\n"
            + "struct Node {\n"
            + "    int data;\n"
            + "    struct Node *left, *right;\n"
            + "};\n\n"
            + "struct Node* create(int data) {\n"
            + "    struct Node* n = (struct Node*)malloc(sizeof(struct Node));\n"
            + "    n->data = data;\n"
            + "    n->left = n->right = NULL;\n"
            + "    return n;\n"
            + "}\n\n";
        if ("Functions".equals(style)) {
            return header
                + "void postorder(struct Node* ptr) {\n"
                + "    if (ptr == NULL) return;\n"
                + "    postorder(ptr->left);\n"
                + "    postorder(ptr->right);\n"
                + "    printf(\"%d \", ptr->data);\n"
                + "}\n\n"
                + "int main() {\n"
                + "    int n, i;\n"
                + "    printf(\"Enter number of nodes: \");\n"
                + "    scanf(\"%d\", &n);\n"
                + "    struct Node* nodes[100];\n"
                + "    for (i = 0; i < n; i++) {\n"
                + "        int val;\n"
                + "        printf(\"Enter value of node %d: \", i);\n"
                + "        scanf(\"%d\", &val);\n"
                + "        nodes[i] = create(val);\n"
                + "    }\n"
                + "    for (i = 0; i < n; i++) {\n"
                + "        int l, r;\n"
                + "        printf(\"Enter left and right child index of node %d (-1 if none): \", i);\n"
                + "        scanf(\"%d %d\", &l, &r);\n"
                + "        if (l != -1) nodes[i]->left = nodes[l];\n"
                + "        if (r != -1) nodes[i]->right = nodes[r];\n"
                + "    }\n"
                + "    postorder(nodes[0]);\n"
                + "    return 0;\n"
                + "}";
        }
        if ("Recursion".equals(style)) {
            return header
                + "void postorderRec(struct Node* root) {\n"
                + "    if (root == NULL) return;\n"
                + "    postorderRec(root->left);\n"
                + "    postorderRec(root->right);\n"
                + "    printf(\"%d \", root->data);\n"
                + "}\n\n"
                + "int main() {\n"
                + "    int n, i;\n"
                + "    printf(\"Enter number of nodes: \");\n"
                + "    scanf(\"%d\", &n);\n"
                + "    struct Node* nodes[100];\n"
                + "    for (i = 0; i < n; i++) {\n"
                + "        int val;\n"
                + "        printf(\"Enter value of node %d: \", i);\n"
                + "        scanf(\"%d\", &val);\n"
                + "        nodes[i] = create(val);\n"
                + "    }\n"
                + "    for (i = 0; i < n; i++) {\n"
                + "        int l, r;\n"
                + "        printf(\"Enter left and right child index of node %d (-1 if none): \", i);\n"
                + "        scanf(\"%d %d\", &l, &r);\n"
                + "        if (l != -1) nodes[i]->left = nodes[l];\n"
                + "        if (r != -1) nodes[i]->right = nodes[r];\n"
                + "    }\n"
                + "    postorderRec(nodes[0]);\n"
                + "    return 0;\n"
                + "}";
        }
        return header
            + "int main() {\n"
            + "    int n, i;\n"
            + "    printf(\"Enter number of nodes: \");\n"
            + "    scanf(\"%d\", &n);\n"
            + "    struct Node* nodes[100];\n"
            + "    for (i = 0; i < n; i++) {\n"
            + "        int val;\n"
            + "        printf(\"Enter value of node %d: \", i);\n"
            + "        scanf(\"%d\", &val);\n"
            + "        nodes[i] = create(val);\n"
            + "    }\n"
            + "    for (i = 0; i < n; i++) {\n"
            + "        int l, r;\n"
            + "        printf(\"Enter left and right child index of node %d (-1 if none): \", i);\n"
            + "        scanf(\"%d %d\", &l, &r);\n"
            + "        if (l != -1) nodes[i]->left = nodes[l];\n"
            + "        if (r != -1) nodes[i]->right = nodes[r];\n"
            + "    }\n"
            + "    struct Node* stack[100];\n"
            + "    struct Node* output[100];\n"
            + "    int top1 = -1, top2 = -1;\n"
            + "    stack[++top1] = nodes[0];\n"
            + "    while (top1 != -1) {\n"
            + "        struct Node* temp = stack[top1--];\n"
            + "        output[++top2] = temp;\n"
            + "        if (temp->left != NULL) stack[++top1] = temp->left;\n"
            + "        if (temp->right != NULL) stack[++top1] = temp->right;\n"
            + "    }\n"
            + "    while (top2 != -1) {\n"
            + "        printf(\"%d \", output[top2--]->data);\n"
            + "    }\n"
            + "    return 0;\n"
            + "}";
    }

    private static String getCBstSearchParentTracking(String style) {
        String header = "#include <stdio.h>\n#include <stdlib.h>\n\n"
            + "struct Node {\n"
            + "    int data;\n"
            + "    struct Node *left, *right;\n"
            + "};\n\n"
            + "struct Node* create(int data) {\n"
            + "    struct Node* n = (struct Node*)malloc(sizeof(struct Node));\n"
            + "    n->data = data;\n"
            + "    n->left = n->right = NULL;\n"
            + "    return n;\n"
            + "}\n\n";
        if ("Functions".equals(style)) {
            return header
                + "void find(struct Node* ptr, struct Node* save, int item, struct Node** loc, struct Node** par) {\n"
                + "    if (ptr == NULL) {\n"
                + "        *loc = NULL;\n"
                + "        *par = save;\n"
                + "        return;\n"
                + "    }\n"
                + "    if (item == ptr->data) {\n"
                + "        *loc = ptr;\n"
                + "        *par = save;\n"
                + "        return;\n"
                + "    }\n"
                + "    if (item < ptr->data)\n"
                + "        find(ptr->left, ptr, item, loc, par);\n"
                + "    else\n"
                + "        find(ptr->right, ptr, item, loc, par);\n"
                + "}\n\n"
                + "int main() {\n"
                + "    int n, i, item;\n"
                + "    scanf(\"%d\", &n);\n"
                + "    struct Node* nodes[100];\n"
                + "    for (i = 0; i < n; i++) {\n"
                + "        int val;\n"
                + "        scanf(\"%d\", &val);\n"
                + "        nodes[i] = create(val);\n"
                + "    }\n"
                + "    for (i = 0; i < n; i++) {\n"
                + "        int l, r;\n"
                + "        scanf(\"%d %d\", &l, &r);\n"
                + "        if (l != -1) nodes[i]->left = nodes[l];\n"
                + "        if (r != -1) nodes[i]->right = nodes[r];\n"
                + "    }\n"
                + "    scanf(\"%d\", &item);\n"
                + "    struct Node *loc = NULL, *par = NULL;\n"
                + "    find(nodes[0], NULL, item, &loc, &par);\n"
                + "    if (loc != NULL)\n"
                + "        printf(\"Found. Parent: %d\\n\", par ? par->data : -1);\n"
                + "    else\n"
                + "        printf(\"Not Found. Parent: %d\\n\", par ? par->data : -1);\n"
                + "    return 0;\n"
                + "}";
        }
        if ("Recursion".equals(style)) {
            return header
                + "void findRec(struct Node* root, struct Node* parent, int item, struct Node** loc, struct Node** par) {\n"
                + "    if (root == NULL) {\n"
                + "        *loc = NULL;\n"
                + "        *par = parent;\n"
                + "        return;\n"
                + "    }\n"
                + "    if (item == root->data) {\n"
                + "        *loc = root;\n"
                + "        *par = parent;\n"
                + "        return;\n"
                + "    }\n"
                + "    if (item < root->data)\n"
                + "        findRec(root->left, root, item, loc, par);\n"
                + "    else\n"
                + "        findRec(root->right, root, item, loc, par);\n"
                + "}\n\n"
                + "int main() {\n"
                + "    int n, i, item;\n"
                + "    scanf(\"%d\", &n);\n"
                + "    struct Node* nodes[100];\n"
                + "    for (i = 0; i < n; i++) {\n"
                + "        int val;\n"
                + "        scanf(\"%d\", &val);\n"
                + "        nodes[i] = create(val);\n"
                + "    }\n"
                + "    for (i = 0; i < n; i++) {\n"
                + "        int l, r;\n"
                + "        scanf(\"%d %d\", &l, &r);\n"
                + "        if (l != -1) nodes[i]->left = nodes[l];\n"
                + "        if (r != -1) nodes[i]->right = nodes[r];\n"
                + "    }\n"
                + "    scanf(\"%d\", &item);\n"
                + "    struct Node *loc = NULL, *par = NULL;\n"
                + "    findRec(nodes[0], NULL, item, &loc, &par);\n"
                + "    if (loc != NULL)\n"
                + "        printf(\"Found. Parent: %d\\n\", par ? par->data : -1);\n"
                + "    else\n"
                + "        printf(\"Not Found. Parent: %d\\n\", par ? par->data : -1);\n"
                + "    return 0;\n"
                + "}";
        }
        return header
            + "int main() {\n"
            + "    int n, i, item;\n"
            + "    scanf(\"%d\", &n);\n"
            + "    struct Node* nodes[100];\n"
            + "    for (i = 0; i < n; i++) {\n"
            + "        int val;\n"
            + "        scanf(\"%d\", &val);\n"
            + "        nodes[i] = create(val);\n"
            + "    }\n"
            + "    for (i = 0; i < n; i++) {\n"
            + "        int l, r;\n"
            + "        scanf(\"%d %d\", &l, &r);\n"
            + "        if (l != -1) nodes[i]->left = nodes[l];\n"
            + "        if (r != -1) nodes[i]->right = nodes[r];\n"
            + "    }\n"
            + "    scanf(\"%d\", &item);\n"
            + "    struct Node *root = nodes[0], *ptr = root, *save = NULL;\n"
            + "    struct Node *loc = NULL, *par = NULL;\n"
            + "    if (root == NULL) {\n"
            + "        loc = NULL;\n"
            + "        par = NULL;\n"
            + "    } else if (item == root->data) {\n"
            + "        loc = root;\n"
            + "        par = NULL;\n"
            + "    } else {\n"
            + "        if (item < root->data) {\n"
            + "            ptr = root->left;\n"
            + "            save = root;\n"
            + "        } else {\n"
            + "            ptr = root->right;\n"
            + "            save = root;\n"
            + "        }\n"
            + "        while (ptr != NULL) {\n"
            + "            if (item == ptr->data) {\n"
            + "                loc = ptr;\n"
            + "                par = save;\n"
            + "                break;\n"
            + "            } else if (item < ptr->data) {\n"
            + "                save = ptr;\n"
            + "                ptr = ptr->left;\n"
            + "            } else {\n"
            + "                save = ptr;\n"
            + "                ptr = ptr->right;\n"
            + "            }\n"
            + "        }\n"
            + "        if (ptr == NULL) {\n"
            + "            loc = NULL;\n"
            + "            par = save;\n"
            + "        }\n"
            + "    }\n"
            + "    if (loc != NULL)\n"
            + "        printf(\"Found. Parent: %d\\n\", par ? par->data : -1);\n"
            + "    else\n"
            + "        printf(\"Not Found. Parent: %d\\n\", par ? par->data : -1);\n"
            + "    return 0;\n"
            + "}";
    }

    private static String getCBstInsertion(String style) {
        String header = "#include <stdio.h>\n#include <stdlib.h>\n\n"
            + "struct Node {\n"
            + "    int data;\n"
            + "    struct Node *left, *right;\n"
            + "};\n\n"
            + "struct Node* create(int data) {\n"
            + "    struct Node* n = (struct Node*)malloc(sizeof(struct Node));\n"
            + "    n->data = data;\n"
            + "    n->left = n->right = NULL;\n"
            + "    return n;\n"
            + "}\n\n";
        if ("Functions".equals(style)) {
            return header
                + "struct Node* insertFunc(struct Node* root, int item) {\n"
                + "    if (root == NULL)\n"
                + "        return create(item);\n"
                + "    if (item < root->data)\n"
                + "        root->left = insertFunc(root->left, item);\n"
                + "    else if (item > root->data)\n"
                + "        root->right = insertFunc(root->right, item);\n"
                + "    return root;\n"
                + "}\n\n"
                + "int main() {\n"
                + "    int n, val, item;\n"
                + "    scanf(\"%d\", &n);\n"
                + "    struct Node* root = NULL;\n"
                + "    for (int i = 0; i < n; i++) {\n"
                + "        scanf(\"%d\", &val);\n"
                + "        root = insertFunc(root, val);\n"
                + "    }\n"
                + "    scanf(\"%d\", &item);\n"
                + "    root = insertFunc(root, item);\n"
                + "    printf(\"Insertion Done\\n\");\n"
                + "    return 0;\n"
                + "}";
        }
        if ("Recursion".equals(style)) {
            return header
                + "struct Node* insertRec(struct Node* root, int item) {\n"
                + "    if (root == NULL)\n"
                + "        return create(item);\n"
                + "    if (item < root->data)\n"
                + "        root->left = insertRec(root->left, item);\n"
                + "    else if (item > root->data)\n"
                + "        root->right = insertRec(root->right, item);\n"
                + "    return root;\n"
                + "}\n\n"
                + "int main() {\n"
                + "    int n, val, item;\n"
                + "    scanf(\"%d\", &n);\n"
                + "    struct Node* root = NULL;\n"
                + "    for (int i = 0; i < n; i++) {\n"
                + "        scanf(\"%d\", &val);\n"
                + "        root = insertRec(root, val);\n"
                + "    }\n"
                + "    scanf(\"%d\", &item);\n"
                + "    root = insertRec(root, item);\n"
                + "    printf(\"Insertion Done\\n\");\n"
                + "    return 0;\n"
                + "}";
        }
        return header
            + "struct Node* insert(struct Node* root, int item) {\n"
            + "    struct Node *parent = NULL, *ptr = root;\n"
            + "    while (ptr != NULL) {\n"
            + "        parent = ptr;\n"
            + "        if (item < ptr->data)\n"
            + "            ptr = ptr->left;\n"
            + "        else if (item > ptr->data)\n"
            + "            ptr = ptr->right;\n"
            + "        else\n"
            + "            return root;\n"
            + "    }\n"
            + "    struct Node* newNode = create(item);\n"
            + "    if (parent == NULL)\n"
            + "        return newNode;\n"
            + "    else if (item < parent->data)\n"
            + "        parent->left = newNode;\n"
            + "    else\n"
            + "        parent->right = newNode;\n"
            + "    return root;\n"
            + "}\n\n"
            + "int main() {\n"
            + "    int n, val, item;\n"
            + "    scanf(\"%d\", &n);\n"
            + "    struct Node* root = NULL;\n"
            + "    for (int i = 0; i < n; i++) {\n"
            + "        scanf(\"%d\", &val);\n"
            + "        root = insert(root, val);\n"
            + "    }\n"
            + "    scanf(\"%d\", &item);\n"
            + "    root = insert(root, item);\n"
            + "    printf(\"Insertion Done\\n\");\n"
            + "    return 0;\n"
            + "}";
    }

    private static String getCBstDeletionCaseA(String style) {
        String base = "#include <stdio.h>\n#include <stdlib.h>\n\n"
            + "struct Node { int data; struct Node *left, *right; };\n"
            + "struct Node* create(int data) { struct Node* n = (struct Node*)malloc(sizeof(struct Node)); n->data = data; n->left = n->right = NULL; return n; }\n"
            + "struct Node* insert(struct Node* root, int val) { if (root == NULL) return create(val); if (val < root->data) root->left = insert(root->left, val); else if (val > root->data) root->right = insert(root->right, val); return root; }\n";
        if ("Functions".equals(style)) {
            return base + "struct Node* deleteFunc(struct Node* root, int item) { if (root == NULL) return NULL; if (item < root->data) root->left = deleteFunc(root->left, item); else if (item > root->data) root->right = deleteFunc(root->right, item); else { if (root->left == NULL) return root->right; else if (root->right == NULL) return root->left; } return root; }\nint main() { int n, val, item; scanf(\"%d\", &n); struct Node* root = NULL; for (int i = 0; i < n; i++) { scanf(\"%d\", &val); root = insert(root, val); } scanf(\"%d\", &item); root = deleteFunc(root, item); printf(\"Deletion Done\\n\"); return 0; }";
        }
        if ("Recursion".equals(style)) {
            return base + "struct Node* deleteRec(struct Node* root, int item) { if (root == NULL) return NULL; if (item < root->data) root->left = deleteRec(root->left, item); else if (item > root->data) root->right = deleteRec(root->right, item); else { if (root->left == NULL && root->right == NULL) return NULL; else if (root->left != NULL) return root->left; else return root->right; } return root; }\nint main() { int n, val, item; scanf(\"%d\", &n); struct Node* root = NULL; for (int i = 0; i < n; i++) { scanf(\"%d\", &val); root = insert(root, val); } scanf(\"%d\", &item); root = deleteRec(root, item); printf(\"Deletion Done\\n\"); return 0; }";
        }
        return base + "struct Node* deleteCaseA(struct Node* root, int item) { struct Node *ptr = root, *par = NULL; while (ptr != NULL && ptr->data != item) { par = ptr; if (item < ptr->data) ptr = ptr->left; else ptr = ptr->right; } if (ptr == NULL) return root; struct Node* child; if (ptr->left == NULL && ptr->right == NULL) child = NULL; else if (ptr->left != NULL) child = ptr->left; else child = ptr->right; if (par != NULL) { if (par->left == ptr) par->left = child; else par->right = child; } else { root = child; } return root; }\nint main() { int n, val, item; scanf(\"%d\", &n); struct Node* root = NULL; for (int i = 0; i < n; i++) { scanf(\"%d\", &val); root = insert(root, val); } scanf(\"%d\", &item); root = deleteCaseA(root, item); printf(\"Deletion Done\\n\"); return 0; }";
    }

    private static String getCBstDeletionCaseB(String style) {
        String base = "#include <stdio.h>\n#include <stdlib.h>\n\n"
            + "struct Node { int data; struct Node *left, *right; };\n"
            + "struct Node* create(int data) { struct Node* n = (struct Node*)malloc(sizeof(struct Node)); n->data = data; n->left = n->right = NULL; return n; }\n"
            + "struct Node* insert(struct Node* root, int val) { if (root == NULL) return create(val); if (val < root->data) root->left = insert(root->left, val); else if (val > root->data) root->right = insert(root->right, val); return root; }\n";
        if ("Functions".equals(style) || "Recursion".equals(style)) {
            String name = "Functions".equals(style) ? "deleteFunc" : "deleteRec";
            return base + "struct Node* " + name + "(struct Node* root, int item) { if (root == NULL) return NULL; if (item < root->data) root->left = " + name + "(root->left, item); else if (item > root->data) root->right = " + name + "(root->right, item); else if (root->left != NULL && root->right != NULL) { struct Node* suc = root->right; while (suc->left != NULL) suc = suc->left; root->data = suc->data; root->right = " + name + "(root->right, suc->data); } return root; }\nint main() { int n, val, item; scanf(\"%d\", &n); struct Node* root = NULL; for (int i = 0; i < n; i++) { scanf(\"%d\", &val); root = insert(root, val); } scanf(\"%d\", &item); root = " + name + "(root, item); printf(\"Deletion Done\\n\"); return 0; }";
        }
        return base + "struct Node* deleteCaseB(struct Node* root, int item) { struct Node *ptr = root; while (ptr != NULL && ptr->data != item) { if (item < ptr->data) ptr = ptr->left; else ptr = ptr->right; } if (ptr == NULL) return root; if (ptr->left != NULL && ptr->right != NULL) { struct Node *sucParent = ptr; struct Node *suc = ptr->right; while (suc->left != NULL) { sucParent = suc; suc = suc->left; } ptr->data = suc->data; if (sucParent->left == suc) sucParent->left = suc->right; else sucParent->right = suc->right; } return root; }\nint main() { int n, val, item; scanf(\"%d\", &n); struct Node* root = NULL; for (int i = 0; i < n; i++) { scanf(\"%d\", &val); root = insert(root, val); } scanf(\"%d\", &item); root = deleteCaseB(root, item); printf(\"Deletion Done\\n\"); return 0; }";
    }

    private static String getCBstDeletionDel(String style) {
        String base = "#include <stdio.h>\n#include <stdlib.h>\n\n"
            + "struct Node { int data; struct Node *left, *right; };\n"
            + "struct Node* create(int data) { struct Node* n = (struct Node*)malloc(sizeof(struct Node)); n->data = data; n->left = n->right = NULL; return n; }\n"
            + "struct Node* insert(struct Node* root, int val) { if (root == NULL) return create(val); if (val < root->data) root->left = insert(root->left, val); else if (val > root->data) root->right = insert(root->right, val); return root; }\n";
        String name = "Iterative".equals(style) ? "deleteNode" : ("Functions".equals(style) ? "deleteFunc" : "deleteRec");
        return base + "struct Node* " + name + "(struct Node* root, int item) { if (root == NULL) { printf(\"ITEM not in tree\\n\"); return NULL; } if (item < root->data) root->left = " + name + "(root->left, item); else if (item > root->data) root->right = " + name + "(root->right, item); else { if (root->left != NULL && root->right != NULL) { struct Node* suc = root->right; while (suc->left != NULL) suc = suc->left; root->data = suc->data; root->right = " + name + "(root->right, suc->data); } else if (root->left != NULL) return root->left; else return root->right; } return root; }\nint main() { int n, val, item; scanf(\"%d\", &n); struct Node* root = NULL; for (int i = 0; i < n; i++) { scanf(\"%d\", &val); root = insert(root, val); } scanf(\"%d\", &item); root = " + name + "(root, item); printf(\"Deletion Done\\n\"); return 0; }";
    }

    private static String getCMaxHeapInsertion(String style) {
        String base = "#include <stdio.h>\n";
        if ("Functions".equals(style)) {
            return base + "int insertHeap(int tree[], int n, int item) { n = n + 1; int ptr = n; while (ptr > 1) { int par = ptr / 2; if (item <= tree[par]) { tree[ptr] = item; return n; } tree[ptr] = tree[par]; ptr = par; } tree[ptr] = item; return n; }\nint main() { int n, tree[100], item; scanf(\"%d\", &n); for (int i = 1; i <= n; i++) scanf(\"%d\", &tree[i]); scanf(\"%d\", &item); n = insertHeap(tree, n, item); for (int i = 1; i <= n; i++) printf(\"%d \", tree[i]); return 0; }";
        }
        if ("Recursion".equals(style)) {
            return base + "void insertRec(int tree[], int ptr, int item) { if (ptr == 1) { tree[ptr] = item; return; } int par = ptr / 2; if (item <= tree[par]) { tree[ptr] = item; return; } tree[ptr] = tree[par]; insertRec(tree, par, item); }\nint main() { int n, tree[100], item; scanf(\"%d\", &n); for (int i = 1; i <= n; i++) scanf(\"%d\", &tree[i]); scanf(\"%d\", &item); n = n + 1; insertRec(tree, n, item); for (int i = 1; i <= n; i++) printf(\"%d \", tree[i]); return 0; }";
        }
        return base + "int main() { int n, tree[100], item; scanf(\"%d\", &n); for (int i = 1; i <= n; i++) scanf(\"%d\", &tree[i]); scanf(\"%d\", &item); n = n + 1; int ptr = n; while (ptr > 1) { int par = ptr / 2; if (item <= tree[par]) { tree[ptr] = item; break; } tree[ptr] = tree[par]; ptr = par; } if (ptr == 1) tree[ptr] = item; for (int i = 1; i <= n; i++) printf(\"%d \", tree[i]); return 0; }";
    }

    private static String getCMaxHeapDeletion(String style) {
        String base = "#include <stdio.h>\n";
        if ("Functions".equals(style)) {
            return base + "int deleteHeap(int tree[], int n) { int item = tree[1]; int last = tree[n]; n--; int ptr = 1; while (2 * ptr <= n) { int left = 2 * ptr; int right = left + 1; int child = (right <= n && tree[right] > tree[left]) ? right : left; if (last >= tree[child]) break; tree[ptr] = tree[child]; ptr = child; } tree[ptr] = last; return item; }\nint main() { int n, tree[100]; scanf(\"%d\", &n); for (int i = 1; i <= n; i++) scanf(\"%d\", &tree[i]); int deleted = deleteHeap(tree, n); printf(\"Deleted element: %d\\n\", deleted); return 0; }";
        }
        if ("Recursion".equals(style)) {
            return base + "void deleteRec(int tree[], int ptr, int n, int last) { int left = 2 * ptr; int right = left + 1; if (left > n) { tree[ptr] = last; return; } int child = (right <= n && tree[right] > tree[left]) ? right : left; if (last >= tree[child]) { tree[ptr] = last; return; } tree[ptr] = tree[child]; deleteRec(tree, child, n, last); }\nint main() { int n, tree[100]; scanf(\"%d\", &n); for (int i = 1; i <= n; i++) scanf(\"%d\", &tree[i]); int item = tree[1]; int last = tree[n]; n--; deleteRec(tree, 1, n, last); printf(\"Deleted element: %d\\n\", item); for (int i = 1; i <= n; i++) printf(\"%d \", tree[i]); return 0; }";
        }
        return base + "int main() { int n, tree[100]; scanf(\"%d\", &n); for (int i = 1; i <= n; i++) scanf(\"%d\", &tree[i]); int item = tree[1]; int last = tree[n]; n--; int ptr = 1; while (2 * ptr <= n) { int left = 2 * ptr; int right = left + 1; int child = (right <= n && tree[right] > tree[left]) ? right : left; if (last >= tree[child]) break; tree[ptr] = tree[child]; ptr = child; } tree[ptr] = last; printf(\"Deleted element: %d\\n\", item); for (int i = 1; i <= n; i++) printf(\"%d \", tree[i]); return 0; }";
    }

    private static String getCHeapSort(String style) {
        String helpers = "void insertHeap(int A[], int n, int item) { int ptr = n + 1; while (ptr > 1) { int par = ptr / 2; if (item <= A[par]) { A[ptr] = item; return; } A[ptr] = A[par]; ptr = par; } A[ptr] = item; }\nint deleteHeap(int A[], int n) { int item = A[1]; int last = A[n]; n--; int ptr = 1; while (2 * ptr <= n) { int left = 2 * ptr; int right = left + 1; int child = (right <= n && A[right] > A[left]) ? right : left; if (last >= A[child]) break; A[ptr] = A[child]; ptr = child; } A[ptr] = last; return item; }\n";
        if ("Functions".equals(style)) {
            return "#include <stdio.h>\n" + helpers + "void buildHeap(int A[], int n) { for (int j = 2; j <= n; j++) insertHeap(A, j - 1, A[j]); }\nvoid sortHeap(int A[], int n) { int size = n; while (size > 1) { int item = deleteHeap(A, size); A[size] = item; size--; } }\nint main() { int n, A[200]; scanf(\"%d\", &n); for (int i = 1; i <= n; i++) scanf(\"%d\", &A[i]); buildHeap(A, n); sortHeap(A, n); for (int i = 1; i <= n; i++) printf(\"%d \", A[i]); return 0; }";
        }
        if ("Recursion".equals(style)) {
            return "#include <stdio.h>\n" + helpers + "void deleteMaxRec(int A[], int n) { if (n <= 1) return; int item = deleteHeap(A, n); A[n] = item; deleteMaxRec(A, n - 1); }\nint main() { int n, A[200]; scanf(\"%d\", &n); for (int i = 1; i <= n; i++) scanf(\"%d\", &A[i]); for (int j = 2; j <= n; j++) insertHeap(A, j - 1, A[j]); deleteMaxRec(A, n); for (int i = 1; i <= n; i++) printf(\"%d \", A[i]); return 0; }";
        }
        return "#include <stdio.h>\n" + helpers + "int main() { int n, A[200]; scanf(\"%d\", &n); for (int i = 1; i <= n; i++) scanf(\"%d\", &A[i]); for (int j = 2; j <= n; j++) insertHeap(A, j - 1, A[j]); int size = n; while (size > 1) { int item = deleteHeap(A, size); A[size] = item; size--; } for (int i = 1; i <= n; i++) printf(\"%d \", A[i]); return 0; }";
    }

    private static String getCLinkedStackPush(String style) {
        if ("Recursion".equals(style)) {
            return "/* Recursion is not applicable for linked stack PUSH in this book style. */";
        }
        if ("Functions".equals(style)) {
            return String.join("\n",
                "#include <stdio.h>",
                "#include <stdlib.h>",
                "",
                "struct node {",
                "    int data;",
                "    struct node* next;",
                "};",
                "",
                "void push(struct node** top, int item) {",
                "    struct node* newNode = (struct node*)malloc(sizeof(struct node));",
                "    if(newNode == NULL) {",
                "        printf(\"Overflow\\n\");",
                "        return;",
                "    }",
                "    newNode->data = item;",
                "    newNode->next = *top;",
                "    *top = newNode;",
                "}",
                "",
                "void display(struct node* top) {",
                "    while(top != NULL) {",
                "        printf(\"%d \", top->data);",
                "        top = top->next;",
                "    }",
                "    printf(\"\\n\");",
                "}",
                "",
                "int main() {",
                "    int n, i, item;",
                "    struct node* top = NULL;",
                "    printf(\"Enter number of items: \");",
                "    scanf(\"%d\", &n);",
                "    for(i = 0; i < n; i++) {",
                "        printf(\"Enter item: \");",
                "        scanf(\"%d\", &item);",
                "        push(&top, item);",
                "    }",
                "    printf(\"Stack after PUSH operations:\\n\");",
                "    display(top);",
                "    return 0;",
                "}");
        }
        return String.join("\n",
            "#include <stdio.h>",
            "#include <stdlib.h>",
            "",
            "struct node {",
            "    int data;",
            "    struct node* next;",
            "};",
            "",
            "int main() {",
            "    int n, i, item;",
            "    struct node* top = NULL;",
            "    struct node* newNode;",
            "    struct node* ptr;",
            "",
            "    printf(\"Enter number of items: \");",
            "    scanf(\"%d\", &n);",
            "",
            "    for(i = 0; i < n; i++) {",
            "        newNode = (struct node*)malloc(sizeof(struct node));",
            "        if(newNode == NULL) {",
            "            printf(\"Overflow\\n\");",
            "            return 0;",
            "        }",
            "        printf(\"Enter item: \");",
            "        scanf(\"%d\", &item);",
            "        newNode->data = item;",
            "        newNode->next = top;",
            "        top = newNode;",
            "    }",
            "",
            "    printf(\"Stack after PUSH operations:\\n\");",
            "    ptr = top;",
            "    while(ptr != NULL) {",
            "        printf(\"%d \", ptr->data);",
            "        ptr = ptr->next;",
            "    }",
            "    printf(\"\\n\");",
            "    return 0;",
            "}");
    }

    private static String getCLinkedStackPop(String style) {
        if ("Recursion".equals(style)) {
            return "/* Recursion is not applicable for linked stack POP in this book style. */";
        }
        if ("Functions".equals(style)) {
            return String.join("\n",
                "#include <stdio.h>",
                "#include <stdlib.h>",
                "",
                "struct node {",
                "    int data;",
                "    struct node* next;",
                "};",
                "",
                "void push(struct node** top, int item) {",
                "    struct node* newNode = (struct node*)malloc(sizeof(struct node));",
                "    newNode->data = item;",
                "    newNode->next = *top;",
                "    *top = newNode;",
                "}",
                "",
                "int pop(struct node** top) {",
                "    if(*top == NULL) {",
                "        printf(\"Underflow\\n\");",
                "        return -1;",
                "    }",
                "    struct node* temp = *top;",
                "    int item = temp->data;",
                "    *top = temp->next;",
                "    free(temp);",
                "    return item;",
                "}",
                "",
                "void display(struct node* top) {",
                "    while(top != NULL) {",
                "        printf(\"%d \", top->data);",
                "        top = top->next;",
                "    }",
                "    printf(\"\\n\");",
                "}",
                "",
                "int main() {",
                "    struct node* top = NULL;",
                "    int deleted;",
                "    push(&top, 10);",
                "    push(&top, 20);",
                "    push(&top, 30);",
                "    deleted = pop(&top);",
                "    if(deleted != -1) {",
                "        printf(\"Deleted item: %d\\n\", deleted);",
                "    }",
                "    printf(\"Stack after POP:\\n\");",
                "    display(top);",
                "    return 0;",
                "}");
        }
        return String.join("\n",
            "#include <stdio.h>",
            "#include <stdlib.h>",
            "",
            "struct node {",
            "    int data;",
            "    struct node* next;",
            "};",
            "",
            "int main() {",
            "    struct node *top = NULL, *newNode, *ptr;",
            "    int item;",
            "",
            "    newNode = (struct node*)malloc(sizeof(struct node));",
            "    newNode->data = 10;",
            "    newNode->next = top;",
            "    top = newNode;",
            "",
            "    newNode = (struct node*)malloc(sizeof(struct node));",
            "    newNode->data = 20;",
            "    newNode->next = top;",
            "    top = newNode;",
            "",
            "    newNode = (struct node*)malloc(sizeof(struct node));",
            "    newNode->data = 30;",
            "    newNode->next = top;",
            "    top = newNode;",
            "",
            "    if(top == NULL) {",
            "        printf(\"Underflow\\n\");",
            "    } else {",
            "        ptr = top;",
            "        item = ptr->data;",
            "        top = ptr->next;",
            "        free(ptr);",
            "        printf(\"Deleted item: %d\\n\", item);",
            "    }",
            "",
            "    printf(\"Stack after POP:\\n\");",
            "    ptr = top;",
            "    while(ptr != NULL) {",
            "        printf(\"%d \", ptr->data);",
            "        ptr = ptr->next;",
            "    }",
            "    printf(\"\\n\");",
            "    return 0;",
            "}");
    }

    private static String getCQuicksort(String style) {
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "#include <stdio.h>",
                "",
                "int partition(int arr[], int low, int high) {",
                "    int pivot = arr[low];",
                "    int left = low + 1;",
                "    int right = high;",
                "",
                "    while(left <= right) {",
                "        while(left <= high && arr[left] <= pivot) {",
                "            left++;",
                "        }",
                "        while(arr[right] > pivot) {",
                "            right--;",
                "        }",
                "        if(left < right) {",
                "            int temp = arr[left];",
                "            arr[left] = arr[right];",
                "            arr[right] = temp;",
                "        }",
                "    }",
                "",
                "    arr[low] = arr[right];",
                "    arr[right] = pivot;",
                "    return right;",
                "}",
                "",
                "void quicksort(int arr[], int low, int high) {",
                "    if(low < high) {",
                "        int loc = partition(arr, low, high);",
                "        quicksort(arr, low, loc - 1);",
                "        quicksort(arr, loc + 1, high);",
                "    }",
                "}",
                "",
                "int main() {",
                "    int n, i;",
                "    printf(\"Enter number of elements: \");",
                "    scanf(\"%d\", &n);",
                "    int arr[n];",
                "    printf(\"Enter elements:\\n\");",
                "    for(i = 0; i < n; i++) {",
                "        scanf(\"%d\", &arr[i]);",
                "    }",
                "    quicksort(arr, 0, n - 1);",
                "    printf(\"Sorted array:\\n\");",
                "    for(i = 0; i < n; i++) {",
                "        printf(\"%d \", arr[i]);",
                "    }",
                "    return 0;",
                "}");
        }
        if ("Functions".equals(style)) {
            return String.join("\n",
                "#include <stdio.h>",
                "",
                "int partition(int arr[], int low, int high) {",
                "    int pivot = arr[low];",
                "    int left = low + 1;",
                "    int right = high;",
                "    while(left <= right) {",
                "        while(left <= high && arr[left] <= pivot) {",
                "            left++;",
                "        }",
                "        while(arr[right] > pivot) {",
                "            right--;",
                "        }",
                "        if(left < right) {",
                "            int temp = arr[left];",
                "            arr[left] = arr[right];",
                "            arr[right] = temp;",
                "        }",
                "    }",
                "    arr[low] = arr[right];",
                "    arr[right] = pivot;",
                "    return right;",
                "}",
                "",
                "void quicksort(int arr[], int low, int high) {",
                "    int loc;",
                "    if(low < high) {",
                "        loc = partition(arr, low, high);",
                "        quicksort(arr, low, loc - 1);",
                "        quicksort(arr, loc + 1, high);",
                "    }",
                "}",
                "",
                "int main() {",
                "    int n, i;",
                "    printf(\"Enter number of elements: \");",
                "    scanf(\"%d\", &n);",
                "    int arr[n];",
                "    printf(\"Enter elements:\\n\");",
                "    for(i = 0; i < n; i++) {",
                "        scanf(\"%d\", &arr[i]);",
                "    }",
                "    quicksort(arr, 0, n - 1);",
                "    printf(\"Sorted array:\\n\");",
                "    for(i = 0; i < n; i++) {",
                "        printf(\"%d \", arr[i]);",
                "    }",
                "    return 0;",
                "}");
        }
        return String.join("\n",
            "#include <stdio.h>",
            "",
            "int partition(int arr[], int low, int high) {",
            "    int pivot = arr[low];",
            "    int left = low + 1;",
            "    int right = high;",
            "",
            "    while(left <= right) {",
            "        while(left <= high && arr[left] <= pivot) {",
            "            left++;",
            "        }",
            "        while(arr[right] > pivot) {",
            "            right--;",
            "        }",
            "        if(left < right) {",
            "            int temp = arr[left];",
            "            arr[left] = arr[right];",
            "            arr[right] = temp;",
            "        }",
            "    }",
            "",
            "    arr[low] = arr[right];",
            "    arr[right] = pivot;",
            "    return right;",
            "}",
            "",
            "int main() {",
            "    int n, i;",
            "    printf(\"Enter number of elements: \");",
            "    scanf(\"%d\", &n);",
            "    int arr[n];",
            "    int lower[n];",
            "    int upper[n];",
            "    int top = -1;",
            "",
            "    printf(\"Enter elements:\\n\");",
            "    for(i = 0; i < n; i++) {",
            "        scanf(\"%d\", &arr[i]);",
            "    }",
            "",
            "    if(n > 1) {",
            "        top = top + 1;",
            "        lower[top] = 0;",
            "        upper[top] = n - 1;",
            "    }",
            "",
            "    while(top != -1) {",
            "        int beg = lower[top];",
            "        int end = upper[top];",
            "        int loc;",
            "        top = top - 1;",
            "        loc = partition(arr, beg, end);",
            "",
            "        if(beg < loc - 1) {",
            "            top = top + 1;",
            "            lower[top] = beg;",
            "            upper[top] = loc - 1;",
            "        }",
            "        if(loc + 1 < end) {",
            "            top = top + 1;",
            "            lower[top] = loc + 1;",
            "            upper[top] = end;",
            "        }",
            "    }",
            "",
            "    printf(\"Sorted array:\\n\");",
            "    for(i = 0; i < n; i++) {",
            "        printf(\"%d \", arr[i]);",
            "    }",
            "    return 0;",
            "}");
    }

    private static String getPythonStackBasics(String style) {
        if ("Recursion".equals(style)) {
            return "# Recursion is not applicable for array stack PUSH and POP in this book style.";
        }
        if ("Functions".equals(style)) {
            return String.join("\n",
                "MAX = 10",
                "stack = []",
                "",
                "def push(item):",
                "    if len(stack) == MAX:",
                "        print(\"Overflow\")",
                "        return",
                "    stack.append(item)",
                "",
                "def pop_item():",
                "    if len(stack) == 0:",
                "        print(\"Underflow\")",
                "        return None",
                "    return stack.pop()",
                "",
                "while True:",
                "    print(\"1. PUSH\")",
                "    print(\"2. POP\")",
                "    print(\"3. EXIT\")",
                "    choice = int(input(\"Enter choice: \"))",
                "",
                "    if choice == 1:",
                "        item = int(input(\"Enter item: \"))",
                "        push(item)",
                "    elif choice == 2:",
                "        deleted = pop_item()",
                "        if deleted is not None:",
                "            print(\"Deleted item:\", deleted)",
                "    else:",
                "        break");
        }
        return String.join("\n",
            "MAX = 10",
            "stack = []",
            "",
            "while True:",
            "    print(\"1. PUSH\")",
            "    print(\"2. POP\")",
            "    print(\"3. EXIT\")",
            "    choice = int(input(\"Enter choice: \"))",
            "",
            "    if choice == 1:",
            "        if len(stack) == MAX:",
            "            print(\"Overflow\")",
            "        else:",
            "            item = int(input(\"Enter item: \"))",
            "            stack.append(item)",
            "    elif choice == 2:",
            "        if len(stack) == 0:",
            "            print(\"Underflow\")",
            "        else:",
            "            deleted = stack.pop()",
            "            print(\"Deleted item:\", deleted)",
            "    else:",
            "        break");
    }

    private static String getPythonLinkedStackPush(String style) {
        if ("Recursion".equals(style)) {
            return "# Recursion is not applicable for linked stack PUSH in this book style.";
        }
        if ("Functions".equals(style)) {
            return String.join("\n",
                "class Node:",
                "    def __init__(self, data):",
                "        self.data = data",
                "        self.next = None",
                "",
                "def push(top, item):",
                "    new_node = Node(item)",
                "    new_node.next = top",
                "    return new_node",
                "",
                "def display(top):",
                "    ptr = top",
                "    while ptr is not None:",
                "        print(ptr.data, end=\" \")",
                "        ptr = ptr.next",
                "    print()",
                "",
                "top = None",
                "n = int(input(\"Enter number of items: \"))",
                "for _ in range(n):",
                "    item = int(input(\"Enter item: \"))",
                "    top = push(top, item)",
                "",
                "print(\"Stack after PUSH operations:\")",
                "display(top)");
        }
        return String.join("\n",
            "class Node:",
            "    def __init__(self, data):",
            "        self.data = data",
            "        self.next = None",
            "",
            "top = None",
            "n = int(input(\"Enter number of items: \"))",
            "for _ in range(n):",
            "    item = int(input(\"Enter item: \"))",
            "    new_node = Node(item)",
            "    new_node.next = top",
            "    top = new_node",
            "",
            "print(\"Stack after PUSH operations:\")",
            "ptr = top",
            "while ptr is not None:",
            "    print(ptr.data, end=\" \")",
            "    ptr = ptr.next",
            "print()");
    }

    private static String getPythonLinkedStackPop(String style) {
        if ("Recursion".equals(style)) {
            return "# Recursion is not applicable for linked stack POP in this book style.";
        }
        if ("Functions".equals(style)) {
            return String.join("\n",
                "class Node:",
                "    def __init__(self, data):",
                "        self.data = data",
                "        self.next = None",
                "",
                "def push(top, item):",
                "    new_node = Node(item)",
                "    new_node.next = top",
                "    return new_node",
                "",
                "def pop_item(top):",
                "    if top is None:",
                "        print(\"Underflow\")",
                "        return top, None",
                "    item = top.data",
                "    top = top.next",
                "    return top, item",
                "",
                "top = None",
                "top = push(top, 10)",
                "top = push(top, 20)",
                "top = push(top, 30)",
                "top, deleted = pop_item(top)",
                "if deleted is not None:",
                "    print(\"Deleted item:\", deleted)",
                "print(\"Stack after POP:\")",
                "ptr = top",
                "while ptr is not None:",
                "    print(ptr.data, end=\" \")",
                "    ptr = ptr.next",
                "print()");
        }
        return String.join("\n",
            "class Node:",
            "    def __init__(self, data):",
            "        self.data = data",
            "        self.next = None",
            "",
            "top = None",
            "new_node = Node(10)",
            "new_node.next = top",
            "top = new_node",
            "new_node = Node(20)",
            "new_node.next = top",
            "top = new_node",
            "new_node = Node(30)",
            "new_node.next = top",
            "top = new_node",
            "",
            "if top is None:",
            "    print(\"Underflow\")",
            "else:",
            "    deleted = top.data",
            "    top = top.next",
            "    print(\"Deleted item:\", deleted)",
            "",
            "print(\"Stack after POP:\")",
            "ptr = top",
            "while ptr is not None:",
            "    print(ptr.data, end=\" \")",
            "    ptr = ptr.next",
            "print()");
    }

    private static String getPythonQuicksort(String style) {
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "def partition(arr, low, high):",
                "    pivot = arr[low]",
                "    left = low + 1",
                "    right = high",
                "",
                "    while left <= right:",
                "        while left <= high and arr[left] <= pivot:",
                "            left += 1",
                "        while arr[right] > pivot:",
                "            right -= 1",
                "        if left < right:",
                "            arr[left], arr[right] = arr[right], arr[left]",
                "",
                "    arr[low], arr[right] = arr[right], arr[low]",
                "    return right",
                "",
                "def quicksort(arr, low, high):",
                "    if low < high:",
                "        loc = partition(arr, low, high)",
                "        quicksort(arr, low, loc - 1)",
                "        quicksort(arr, loc + 1, high)",
                "",
                "n = int(input(\"Enter number of elements: \"))",
                "arr = list(map(int, input(\"Enter elements: \").split()))",
                "quicksort(arr, 0, n - 1)",
                "print(\"Sorted array:\")",
                "print(*arr)");
        }
        if ("Functions".equals(style)) {
            return String.join("\n",
                "def partition(arr, low, high):",
                "    pivot = arr[low]",
                "    left = low + 1",
                "    right = high",
                "    while left <= right:",
                "        while left <= high and arr[left] <= pivot:",
                "            left += 1",
                "        while arr[right] > pivot:",
                "            right -= 1",
                "        if left < right:",
                "            arr[left], arr[right] = arr[right], arr[left]",
                "    arr[low], arr[right] = arr[right], arr[low]",
                "    return right",
                "",
                "def quicksort(arr, low, high):",
                "    if low < high:",
                "        loc = partition(arr, low, high)",
                "        quicksort(arr, low, loc - 1)",
                "        quicksort(arr, loc + 1, high)",
                "",
                "n = int(input(\"Enter number of elements: \"))",
                "arr = list(map(int, input(\"Enter elements: \").split()))",
                "quicksort(arr, 0, n - 1)",
                "print(\"Sorted array:\")",
                "print(*arr)");
        }
        return String.join("\n",
            "def partition(arr, low, high):",
            "    pivot = arr[low]",
            "    left = low + 1",
            "    right = high",
            "",
            "    while left <= right:",
            "        while left <= high and arr[left] <= pivot:",
            "            left += 1",
            "        while arr[right] > pivot:",
            "            right -= 1",
            "        if left < right:",
            "            arr[left], arr[right] = arr[right], arr[left]",
            "",
            "    arr[low], arr[right] = arr[right], arr[low]",
            "    return right",
            "",
            "n = int(input(\"Enter number of elements: \"))",
            "arr = list(map(int, input(\"Enter elements: \").split()))",
            "lower = []",
            "upper = []",
            "",
            "if n > 1:",
            "    lower.append(0)",
            "    upper.append(n - 1)",
            "",
            "while lower:",
            "    beg = lower.pop()",
            "    end = upper.pop()",
            "    loc = partition(arr, beg, end)",
            "",
            "    if beg < loc - 1:",
            "        lower.append(beg)",
            "        upper.append(loc - 1)",
            "    if loc + 1 < end:",
            "        lower.append(loc + 1)",
            "        upper.append(end)",
            "",
            "print(\"Sorted array:\")",
            "print(*arr)");
    }

    private static String getPythonLinearQueue(String style) {
        if ("Functions".equals(style)) {
            return String.join("\n",
                "def insert(queue, front, rear, n, item):",
                "    if rear == n:",
                "        print(\"OVERFLOW\")",
                "        return front, rear",
                "",
                "    if front == -1:",
                "        front = 0",
                "        rear = 0",
                "    else:",
                "        rear += 1",
                "",
                "    queue[rear] = item",
                "    return front, rear",
                "",
                "def delete(queue, front, rear):",
                "    if front == -1:",
                "        print(\"UNDERFLOW\")",
                "        return front, rear",
                "",
                "    print(\"Deleted:\", queue[front])",
                "    if front == rear:",
                "        return -1, -1",
                "    else:",
                "        return front + 1, rear",
                "",
                "n = int(input(\"Enter queue size: \"))",
                "queue = [0] * (n + 1)",
                "front = -1",
                "rear = -1",
                "item = int(input(\"Enter item to insert: \"))",
                "front, rear = insert(queue, front, rear, n, item)",
                "front, rear = delete(queue, front, rear)");
        }
        if ("Recursion".equals(style)) {
            return "print(\"Recursion not applicable for queue insertion and deletion\")";
        }
        return String.join("\n",
            "n = int(input(\"Enter queue size: \"))",
            "queue = [0] * (n + 1)",
            "front = -1",
            "rear = -1",
            "",
            "item = int(input(\"Enter item to insert: \"))",
            "",
            "if rear == n:",
            "    print(\"OVERFLOW\")",
            "else:",
            "    if front == -1:",
            "        front = 0",
            "        rear = 0",
            "    else:",
            "        rear += 1",
            "    queue[rear] = item",
            "    print(\"Item inserted\")",
            "",
            "if front == -1:",
            "    print(\"UNDERFLOW\")",
            "else:",
            "    deleted = queue[front]",
            "    if front == rear:",
            "        front = -1",
            "        rear = -1",
            "    else:",
            "        front += 1",
            "    print(\"Deleted:\", deleted)");
    }

    private static String getPythonCircularQueue(String style) {
        if ("Functions".equals(style)) {
            return String.join("\n",
                "def insert(queue, front, rear, n, item):",
                "    if (front == 0 and rear == n - 1) or (front == rear + 1):",
                "        print(\"OVERFLOW\")",
                "        return front, rear",
                "",
                "    if front == -1:",
                "        front = 0",
                "        rear = 0",
                "    elif rear == n - 1:",
                "        rear = 0",
                "    else:",
                "        rear += 1",
                "",
                "    queue[rear] = item",
                "    return front, rear",
                "",
                "def delete(queue, front, rear, n):",
                "    if front == -1:",
                "        print(\"UNDERFLOW\")",
                "        return front, rear",
                "",
                "    print(\"Deleted:\", queue[front])",
                "    if front == rear:",
                "        return -1, -1",
                "    elif front == n - 1:",
                "        return 0, rear",
                "    else:",
                "        return front + 1, rear",
                "",
                "n = int(input(\"Enter queue size: \"))",
                "queue = [0] * n",
                "front = -1",
                "rear = -1",
                "item = int(input(\"Enter item to insert: \"))",
                "front, rear = insert(queue, front, rear, n, item)",
                "front, rear = delete(queue, front, rear, n)");
        }
        if ("Recursion".equals(style)) {
            return "print(\"Recursion not applicable for circular queue operations\")";
        }
        return String.join("\n",
            "n = int(input(\"Enter queue size: \"))",
            "queue = [0] * n",
            "front = -1",
            "rear = -1",
            "",
            "item = int(input(\"Enter item to insert: \"))",
            "",
            "if (front == 0 and rear == n - 1) or (front == rear + 1):",
            "    print(\"OVERFLOW\")",
            "else:",
            "    if front == -1:",
            "        front = 0",
            "        rear = 0",
            "    elif rear == n - 1:",
            "        rear = 0",
            "    else:",
            "        rear += 1",
            "    queue[rear] = item",
            "    print(\"Item inserted\")",
            "",
            "if front == -1:",
            "    print(\"UNDERFLOW\")",
            "else:",
            "    deleted = queue[front]",
            "    if front == rear:",
            "        front = -1",
            "        rear = -1",
            "    elif front == n - 1:",
            "        front = 0",
            "    else:",
            "        front += 1",
            "    print(\"Deleted:\", deleted)");
    }

    private static String getPythonLinkedQueue(String style) {
        if ("Functions".equals(style)) {
            return String.join("\n",
                "class Node:",
                "    def __init__(self, data):",
                "        self.data = data",
                "        self.next = None",
                "",
                "def insert(front, rear, item):",
                "    new_node = Node(item)",
                "    if front is None:",
                "        return new_node, new_node",
                "    else:",
                "        rear.next = new_node",
                "        return front, new_node",
                "",
                "def delete(front, rear):",
                "    if front is None:",
                "        print(\"UNDERFLOW\")",
                "        return front, rear",
                "",
                "    print(\"Deleted:\", front.data)",
                "    front = front.next",
                "    if front is None:",
                "        rear = None",
                "    return front, rear",
                "",
                "front = None",
                "rear = None",
                "item = int(input(\"Enter item to insert: \"))",
                "front, rear = insert(front, rear, item)",
                "print(\"Item inserted\")",
                "front, rear = delete(front, rear)");
        }
        if ("Recursion".equals(style)) {
            return "print(\"Recursion not applicable for linked queue operations\")";
        }
        return String.join("\n",
            "class Node:",
            "    def __init__(self, data):",
            "        self.data = data",
            "        self.next = None",
            "",
            "front = None",
            "rear = None",
            "",
            "item = int(input(\"Enter item to insert: \"))",
            "new_node = Node(item)",
            "",
            "if front is None:",
            "    front = rear = new_node",
            "else:",
            "    rear.next = new_node",
            "    rear = new_node",
            "",
            "print(\"Item inserted\")",
            "",
            "if front is None:",
            "    print(\"UNDERFLOW\")",
            "else:",
            "    deleted = front.data",
            "    front = front.next",
            "    print(\"Deleted:\", deleted)");
    }

    private static String getPythonPriorityQueueArray(String style) {
        if ("Functions".equals(style)) {
            return String.join("\n",
                "def insert(data, priority, size, item, p):",
                "    i = size - 1",
                "    while i >= 0 and priority[i] > p:",
                "        data[i + 1] = data[i]",
                "        priority[i + 1] = priority[i]",
                "        i -= 1",
                "    data[i + 1] = item",
                "    priority[i + 1] = p",
                "    return size + 1",
                "",
                "def delete(data, priority, size):",
                "    if size == 0:",
                "        print(\"UNDERFLOW\")",
                "        return size",
                "",
                "    print(\"Deleted:\", data[0])",
                "    for i in range(size - 1):",
                "        data[i] = data[i + 1]",
                "        priority[i] = priority[i + 1]",
                "    return size - 1",
                "",
                "n = int(input(\"Enter size of queue: \"))",
                "data = [0] * n",
                "priority = [0] * n",
                "size = 0",
                "item = int(input(\"Enter item: \"))",
                "p = int(input(\"Enter priority: \"))",
                "size = insert(data, priority, size, item, p)",
                "print(\"Item inserted\")",
                "size = delete(data, priority, size)");
        }
        if ("Recursion".equals(style)) {
            return "print(\"Recursion not applicable for priority queue (array)\")";
        }
        return String.join("\n",
            "n = int(input(\"Enter size of queue: \"))",
            "data = [0] * n",
            "priority = [0] * n",
            "size = 0",
            "",
            "item = int(input(\"Enter item: \"))",
            "p = int(input(\"Enter priority: \"))",
            "",
            "i = size - 1",
            "while i >= 0 and priority[i] > p:",
            "    data[i + 1] = data[i]",
            "    priority[i + 1] = priority[i]",
            "    i -= 1",
            "",
            "data[i + 1] = item",
            "priority[i + 1] = p",
            "size += 1",
            "print(\"Item inserted\")",
            "",
            "if size == 0:",
            "    print(\"UNDERFLOW\")",
            "else:",
            "    deleted = data[0]",
            "    for j in range(size - 1):",
            "        data[j] = data[j + 1]",
            "        priority[j] = priority[j + 1]",
            "    size -= 1",
            "    print(\"Deleted:\", deleted)");
    }

    private static String getPythonPriorityQueue2d(String style) {
        if ("Functions".equals(style)) {
            return String.join("\n",
                "def insert(queue, front, rear, n, m, item):",
                "    if rear[m] == n - 1:",
                "        print(\"OVERFLOW\")",
                "        return front, rear",
                "",
                "    if front[m] == -1:",
                "        front[m] = 0",
                "        rear[m] = 0",
                "    else:",
                "        rear[m] += 1",
                "",
                "    queue[m][rear[m]] = item",
                "    return front, rear",
                "",
                "def delete(queue, front, rear, p):",
                "    k = -1",
                "    for i in range(p):",
                "        if front[i] != -1:",
                "            k = i",
                "            break",
                "",
                "    if k == -1:",
                "        print(\"UNDERFLOW\")",
                "        return front, rear",
                "",
                "    print(\"Deleted:\", queue[k][front[k]])",
                "    if front[k] == rear[k]:",
                "        front[k] = -1",
                "        rear[k] = -1",
                "    else:",
                "        front[k] += 1",
                "    return front, rear",
                "",
                "p = int(input(\"Enter number of priority levels: \"))",
                "n = int(input(\"Enter size of each queue: \"))",
                "queue = [[0] * n for _ in range(p)]",
                "front = [-1] * p",
                "rear = [-1] * p",
                "item = int(input(\"Enter item: \"))",
                "m = int(input(\"Enter priority level: \"))",
                "front, rear = insert(queue, front, rear, n, m, item)",
                "print(\"Item inserted\")",
                "front, rear = delete(queue, front, rear, p)");
        }
        if ("Recursion".equals(style)) {
            return "print(\"Recursion not applicable for priority queue (2D array)\")";
        }
        return String.join("\n",
            "p = int(input(\"Enter number of priority levels: \"))",
            "n = int(input(\"Enter size of each queue: \"))",
            "",
            "queue = [[0]*n for _ in range(p)]",
            "front = [-1]*p",
            "rear = [-1]*p",
            "",
            "item = int(input(\"Enter item: \"))",
            "m = int(input(\"Enter priority level: \"))",
            "",
            "if rear[m] == n - 1:",
            "    print(\"OVERFLOW\")",
            "else:",
            "    if front[m] == -1:",
            "        front[m] = 0",
            "        rear[m] = 0",
            "    else:",
            "        rear[m] += 1",
            "    queue[m][rear[m]] = item",
            "    print(\"Item inserted\")",
            "",
            "k = -1",
            "for i in range(p):",
            "    if front[i] != -1:",
            "        k = i",
            "        break",
            "",
            "if k == -1:",
            "    print(\"UNDERFLOW\")",
            "else:",
            "    deleted = queue[k][front[k]]",
            "    if front[k] == rear[k]:",
            "        front[k] = -1",
            "        rear[k] = -1",
            "    else:",
            "        front[k] += 1",
            "    print(\"Deleted:\", deleted)");
    }

    private static String getPythonPreorderTraversal(String style) {
        String setup = "class Node:\n"
            + "    def __init__(self, data):\n"
            + "        self.data = data\n"
            + "        self.left = None\n"
            + "        self.right = None\n\n";
        if ("Functions".equals(style)) {
            return setup
                + "def preorder(ptr):\n"
                + "    if ptr is None:\n"
                + "        return\n"
                + "    print(ptr.data, end=\" \")\n"
                + "    preorder(ptr.left)\n"
                + "    preorder(ptr.right)\n\n"
                + "n = int(input(\"Enter number of nodes: \"))\n"
                + "nodes = []\n"
                + "for i in range(n):\n"
                + "    val = int(input(f\"Enter value of node {i}: \"))\n"
                + "    nodes.append(Node(val))\n"
                + "for i in range(n):\n"
                + "    l, r = map(int, input(f\"Enter left and right child index of node {i} (-1 if none): \").split())\n"
                + "    if l != -1:\n"
                + "        nodes[i].left = nodes[l]\n"
                + "    if r != -1:\n"
                + "        nodes[i].right = nodes[r]\n"
                + "preorder(nodes[0])";
        }
        if ("Recursion".equals(style)) {
            return setup
                + "def preorderRec(root):\n"
                + "    if root is None:\n"
                + "        return\n"
                + "    print(root.data, end=\" \")\n"
                + "    preorderRec(root.left)\n"
                + "    preorderRec(root.right)\n\n"
                + "n = int(input(\"Enter number of nodes: \"))\n"
                + "nodes = []\n"
                + "for i in range(n):\n"
                + "    val = int(input(f\"Enter value of node {i}: \"))\n"
                + "    nodes.append(Node(val))\n"
                + "for i in range(n):\n"
                + "    l, r = map(int, input(f\"Enter left and right child index of node {i} (-1 if none): \").split())\n"
                + "    if l != -1:\n"
                + "        nodes[i].left = nodes[l]\n"
                + "    if r != -1:\n"
                + "        nodes[i].right = nodes[r]\n"
                + "preorderRec(nodes[0])";
        }
        return setup
            + "n = int(input(\"Enter number of nodes: \"))\n"
            + "nodes = []\n"
            + "for i in range(n):\n"
            + "    val = int(input(f\"Enter value of node {i}: \"))\n"
            + "    nodes.append(Node(val))\n"
            + "for i in range(n):\n"
            + "    l, r = map(int, input(f\"Enter left and right child index of node {i} (-1 if none): \").split())\n"
            + "    if l != -1:\n"
            + "        nodes[i].left = nodes[l]\n"
            + "    if r != -1:\n"
            + "        nodes[i].right = nodes[r]\n"
            + "stack = []\n"
            + "ptr = nodes[0]\n"
            + "stack.append(None)\n"
            + "while ptr is not None:\n"
            + "    print(ptr.data, end=\" \")\n"
            + "    if ptr.right is not None:\n"
            + "        stack.append(ptr.right)\n"
            + "    if ptr.left is not None:\n"
            + "        ptr = ptr.left\n"
            + "    else:\n"
            + "        ptr = stack.pop()";
    }

    private static String getPythonPostorderTraversal(String style) {
        String setup = "class Node:\n"
            + "    def __init__(self, data):\n"
            + "        self.data = data\n"
            + "        self.left = None\n"
            + "        self.right = None\n\n";
        if ("Functions".equals(style)) {
            return setup
                + "def postorder(ptr):\n"
                + "    if ptr is None:\n"
                + "        return\n"
                + "    postorder(ptr.left)\n"
                + "    postorder(ptr.right)\n"
                + "    print(ptr.data, end=\" \")\n\n"
                + "n = int(input(\"Enter number of nodes: \"))\n"
                + "nodes = []\n"
                + "for i in range(n):\n"
                + "    val = int(input(f\"Enter value of node {i}: \"))\n"
                + "    nodes.append(Node(val))\n"
                + "for i in range(n):\n"
                + "    l, r = map(int, input(f\"Enter left and right child index of node {i} (-1 if none): \").split())\n"
                + "    if l != -1:\n"
                + "        nodes[i].left = nodes[l]\n"
                + "    if r != -1:\n"
                + "        nodes[i].right = nodes[r]\n"
                + "postorder(nodes[0])";
        }
        if ("Recursion".equals(style)) {
            return setup
                + "def postorderRec(root):\n"
                + "    if root is None:\n"
                + "        return\n"
                + "    postorderRec(root.left)\n"
                + "    postorderRec(root.right)\n"
                + "    print(root.data, end=\" \")\n\n"
                + "n = int(input(\"Enter number of nodes: \"))\n"
                + "nodes = []\n"
                + "for i in range(n):\n"
                + "    val = int(input(f\"Enter value of node {i}: \"))\n"
                + "    nodes.append(Node(val))\n"
                + "for i in range(n):\n"
                + "    l, r = map(int, input(f\"Enter left and right child index of node {i} (-1 if none): \").split())\n"
                + "    if l != -1:\n"
                + "        nodes[i].left = nodes[l]\n"
                + "    if r != -1:\n"
                + "        nodes[i].right = nodes[r]\n"
                + "postorderRec(nodes[0])";
        }
        return setup
            + "n = int(input(\"Enter number of nodes: \"))\n"
            + "nodes = []\n"
            + "for i in range(n):\n"
            + "    val = int(input(f\"Enter value of node {i}: \"))\n"
            + "    nodes.append(Node(val))\n"
            + "for i in range(n):\n"
            + "    l, r = map(int, input(f\"Enter left and right child index of node {i} (-1 if none): \").split())\n"
            + "    if l != -1:\n"
            + "        nodes[i].left = nodes[l]\n"
            + "    if r != -1:\n"
            + "        nodes[i].right = nodes[r]\n"
            + "stack = []\n"
            + "output = []\n"
            + "stack.append(nodes[0])\n"
            + "while stack:\n"
            + "    temp = stack.pop()\n"
            + "    output.append(temp)\n"
            + "    if temp.left:\n"
            + "        stack.append(temp.left)\n"
            + "    if temp.right:\n"
            + "        stack.append(temp.right)\n"
            + "while output:\n"
            + "    print(output.pop().data, end=\" \")";
    }

    private static String getPythonBstSearchParentTracking(String style) {
        String setup = "class Node:\n"
            + "    def __init__(self, data):\n"
            + "        self.data = data\n"
            + "        self.left = None\n"
            + "        self.right = None\n\n";
        if ("Functions".equals(style)) {
            return setup
                + "loc = None\n"
                + "par = None\n\n"
                + "def find(ptr, save, item):\n"
                + "    global loc, par\n"
                + "    if ptr is None:\n"
                + "        loc = None\n"
                + "        par = save\n"
                + "        return\n"
                + "    if item == ptr.data:\n"
                + "        loc = ptr\n"
                + "        par = save\n"
                + "        return\n"
                + "    if item < ptr.data:\n"
                + "        find(ptr.left, ptr, item)\n"
                + "    else:\n"
                + "        find(ptr.right, ptr, item)\n\n"
                + "n = int(input())\n"
                + "nodes = [Node(int(input())) for _ in range(n)]\n"
                + "for i in range(n):\n"
                + "    l, r = map(int, input().split())\n"
                + "    if l != -1:\n"
                + "        nodes[i].left = nodes[l]\n"
                + "    if r != -1:\n"
                + "        nodes[i].right = nodes[r]\n"
                + "item = int(input())\n"
                + "find(nodes[0], None, item)\n"
                + "if loc:\n"
                + "    print(\"Found. Parent:\", par.data if par else \"NULL\")\n"
                + "else:\n"
                + "    print(\"Not Found. Parent:\", par.data if par else \"NULL\")";
        }
        if ("Recursion".equals(style)) {
            return setup
                + "loc = None\n"
                + "par = None\n\n"
                + "def findRec(root, parent, item):\n"
                + "    global loc, par\n"
                + "    if root is None:\n"
                + "        loc = None\n"
                + "        par = parent\n"
                + "        return\n"
                + "    if item == root.data:\n"
                + "        loc = root\n"
                + "        par = parent\n"
                + "        return\n"
                + "    if item < root.data:\n"
                + "        findRec(root.left, root, item)\n"
                + "    else:\n"
                + "        findRec(root.right, root, item)\n\n"
                + "n = int(input())\n"
                + "nodes = [Node(int(input())) for _ in range(n)]\n"
                + "for i in range(n):\n"
                + "    l, r = map(int, input().split())\n"
                + "    if l != -1:\n"
                + "        nodes[i].left = nodes[l]\n"
                + "    if r != -1:\n"
                + "        nodes[i].right = nodes[r]\n"
                + "item = int(input())\n"
                + "findRec(nodes[0], None, item)\n"
                + "if loc:\n"
                + "    print(\"Found. Parent:\", par.data if par else \"NULL\")\n"
                + "else:\n"
                + "    print(\"Not Found. Parent:\", par.data if par else \"NULL\")";
        }
        return setup
            + "n = int(input())\n"
            + "nodes = [Node(int(input())) for _ in range(n)]\n"
            + "for i in range(n):\n"
            + "    l, r = map(int, input().split())\n"
            + "    if l != -1:\n"
            + "        nodes[i].left = nodes[l]\n"
            + "    if r != -1:\n"
            + "        nodes[i].right = nodes[r]\n"
            + "item = int(input())\n"
            + "root = nodes[0]\n"
            + "ptr = root\n"
            + "save = None\n"
            + "loc = None\n"
            + "par = None\n"
            + "if root is None:\n"
            + "    loc = None\n"
            + "    par = None\n"
            + "elif item == root.data:\n"
            + "    loc = root\n"
            + "    par = None\n"
            + "else:\n"
            + "    if item < root.data:\n"
            + "        ptr = root.left\n"
            + "        save = root\n"
            + "    else:\n"
            + "        ptr = root.right\n"
            + "        save = root\n"
            + "    while ptr is not None:\n"
            + "        if item == ptr.data:\n"
            + "            loc = ptr\n"
            + "            par = save\n"
            + "            break\n"
            + "        elif item < ptr.data:\n"
            + "            save = ptr\n"
            + "            ptr = ptr.left\n"
            + "        else:\n"
            + "            save = ptr\n"
            + "            ptr = ptr.right\n"
            + "    if ptr is None:\n"
            + "        loc = None\n"
            + "        par = save\n"
            + "if loc:\n"
            + "    print(\"Found. Parent:\", par.data if par else \"NULL\")\n"
            + "else:\n"
            + "    print(\"Not Found. Parent:\", par.data if par else \"NULL\")";
    }

    private static String getPythonBstInsertion(String style) {
        String setup = "class Node:\n"
            + "    def __init__(self, data):\n"
            + "        self.data = data\n"
            + "        self.left = None\n"
            + "        self.right = None\n\n";
        if ("Functions".equals(style)) {
            return setup
                + "def insertFunc(root, item):\n"
                + "    if root is None:\n"
                + "        return Node(item)\n"
                + "    if item < root.data:\n"
                + "        root.left = insertFunc(root.left, item)\n"
                + "    elif item > root.data:\n"
                + "        root.right = insertFunc(root.right, item)\n"
                + "    return root\n\n"
                + "n = int(input())\n"
                + "root = None\n"
                + "for _ in range(n):\n"
                + "    val = int(input())\n"
                + "    root = insertFunc(root, val)\n"
                + "item = int(input())\n"
                + "root = insertFunc(root, item)\n"
                + "print(\"Insertion Done\")";
        }
        if ("Recursion".equals(style)) {
            return setup
                + "def insertRec(root, item):\n"
                + "    if root is None:\n"
                + "        return Node(item)\n"
                + "    if item < root.data:\n"
                + "        root.left = insertRec(root.left, item)\n"
                + "    elif item > root.data:\n"
                + "        root.right = insertRec(root.right, item)\n"
                + "    return root\n\n"
                + "n = int(input())\n"
                + "root = None\n"
                + "for _ in range(n):\n"
                + "    val = int(input())\n"
                + "    root = insertRec(root, val)\n"
                + "item = int(input())\n"
                + "root = insertRec(root, item)\n"
                + "print(\"Insertion Done\")";
        }
        return setup
            + "def insert(root, item):\n"
            + "    parent = None\n"
            + "    ptr = root\n"
            + "    while ptr:\n"
            + "        parent = ptr\n"
            + "        if item < ptr.data:\n"
            + "            ptr = ptr.left\n"
            + "        elif item > ptr.data:\n"
            + "            ptr = ptr.right\n"
            + "        else:\n"
            + "            return root\n"
            + "    newNode = Node(item)\n"
            + "    if parent is None:\n"
            + "        return newNode\n"
            + "    elif item < parent.data:\n"
            + "        parent.left = newNode\n"
            + "    else:\n"
            + "        parent.right = newNode\n"
            + "    return root\n\n"
            + "n = int(input())\n"
            + "root = None\n"
            + "for _ in range(n):\n"
            + "    val = int(input())\n"
            + "    root = insert(root, val)\n"
            + "item = int(input())\n"
            + "root = insert(root, item)\n"
            + "print(\"Insertion Done\")";
    }

    private static String getPythonBstDeletionCaseA(String style) {
        String base = "class Node:\n"
            + "    def __init__(self, data):\n"
            + "        self.data = data\n"
            + "        self.left = None\n"
            + "        self.right = None\n\n"
            + "def insert(root, val):\n"
            + "    if root is None:\n"
            + "        return Node(val)\n"
            + "    if val < root.data:\n"
            + "        root.left = insert(root.left, val)\n"
            + "    elif val > root.data:\n"
            + "        root.right = insert(root.right, val)\n"
            + "    return root\n\n";
        if ("Functions".equals(style)) {
            return base
                + "def deleteFunc(root, item):\n"
                + "    if root is None:\n"
                + "        return None\n"
                + "    if item < root.data:\n"
                + "        root.left = deleteFunc(root.left, item)\n"
                + "    elif item > root.data:\n"
                + "        root.right = deleteFunc(root.right, item)\n"
                + "    else:\n"
                + "        if root.left is None:\n"
                + "            return root.right\n"
                + "        elif root.right is None:\n"
                + "            return root.left\n"
                + "    return root\n\n"
                + "n = int(input())\nroot = None\nfor _ in range(n):\n    root = insert(root, int(input()))\nroot = deleteFunc(root, int(input()))\nprint(\"Deletion Done\")";
        }
        if ("Recursion".equals(style)) {
            return base
                + "def deleteRec(root, item):\n"
                + "    if root is None:\n"
                + "        return None\n"
                + "    if item < root.data:\n"
                + "        root.left = deleteRec(root.left, item)\n"
                + "    elif item > root.data:\n"
                + "        root.right = deleteRec(root.right, item)\n"
                + "    else:\n"
                + "        if root.left is None and root.right is None:\n"
                + "            return None\n"
                + "        elif root.left is not None:\n"
                + "            return root.left\n"
                + "        else:\n"
                + "            return root.right\n"
                + "    return root\n\n"
                + "n = int(input())\nroot = None\nfor _ in range(n):\n    root = insert(root, int(input()))\nroot = deleteRec(root, int(input()))\nprint(\"Deletion Done\")";
        }
        return base
            + "def deleteCaseA(root, item):\n"
            + "    ptr = root\n    par = None\n"
            + "    while ptr and ptr.data != item:\n"
            + "        par = ptr\n"
            + "        if item < ptr.data:\n"
            + "            ptr = ptr.left\n"
            + "        else:\n"
            + "            ptr = ptr.right\n"
            + "    if ptr is None:\n"
            + "        return root\n"
            + "    if ptr.left is None and ptr.right is None:\n"
            + "        child = None\n"
            + "    elif ptr.left:\n"
            + "        child = ptr.left\n"
            + "    else:\n"
            + "        child = ptr.right\n"
            + "    if par:\n"
            + "        if par.left == ptr:\n"
            + "            par.left = child\n"
            + "        else:\n"
            + "            par.right = child\n"
            + "    else:\n"
            + "        root = child\n"
            + "    return root\n\n"
            + "n = int(input())\nroot = None\nfor _ in range(n):\n    root = insert(root, int(input()))\nroot = deleteCaseA(root, int(input()))\nprint(\"Deletion Done\")";
    }

    private static String getPythonBstDeletionCaseB(String style) {
        String base = "class Node:\n"
            + "    def __init__(self, data):\n"
            + "        self.data = data\n"
            + "        self.left = None\n"
            + "        self.right = None\n\n"
            + "def insert(root, val):\n"
            + "    if root is None:\n"
            + "        return Node(val)\n"
            + "    if val < root.data:\n"
            + "        root.left = insert(root.left, val)\n"
            + "    elif val > root.data:\n"
            + "        root.right = insert(root.right, val)\n"
            + "    return root\n\n";
        if ("Functions".equals(style) || "Recursion".equals(style)) {
            String name = "Functions".equals(style) ? "deleteFunc" : "deleteRec";
            return base
                + "def " + name + "(root, item):\n"
                + "    if root is None:\n"
                + "        return None\n"
                + "    if item < root.data:\n"
                + "        root.left = " + name + "(root.left, item)\n"
                + "    elif item > root.data:\n"
                + "        root.right = " + name + "(root.right, item)\n"
                + "    else:\n"
                + "        if root.left and root.right:\n"
                + "            suc = root.right\n"
                + "            while suc.left:\n"
                + "                suc = suc.left\n"
                + "            root.data = suc.data\n"
                + "            root.right = " + name + "(root.right, suc.data)\n"
                + "    return root\n\n"
                + "n = int(input())\nroot = None\nfor _ in range(n):\n    root = insert(root, int(input()))\nroot = " + name + "(root, int(input()))\nprint(\"Deletion Done\")";
        }
        return base
            + "def deleteCaseB(root, item):\n"
            + "    ptr = root\n"
            + "    while ptr and ptr.data != item:\n"
            + "        if item < ptr.data:\n"
            + "            ptr = ptr.left\n"
            + "        else:\n"
            + "            ptr = ptr.right\n"
            + "    if ptr is None:\n"
            + "        return root\n"
            + "    if ptr.left and ptr.right:\n"
            + "        suc_parent = ptr\n"
            + "        suc = ptr.right\n"
            + "        while suc.left:\n"
            + "            suc_parent = suc\n"
            + "            suc = suc.left\n"
            + "        ptr.data = suc.data\n"
            + "        if suc_parent.left == suc:\n"
            + "            suc_parent.left = suc.right\n"
            + "        else:\n"
            + "            suc_parent.right = suc.right\n"
            + "    return root\n\n"
            + "n = int(input())\nroot = None\nfor _ in range(n):\n    root = insert(root, int(input()))\nroot = deleteCaseB(root, int(input()))\nprint(\"Deletion Done\")";
    }

    private static String getPythonBstDeletionDel(String style) {
        String base = "class Node:\n"
            + "    def __init__(self, data):\n"
            + "        self.data = data\n"
            + "        self.left = None\n"
            + "        self.right = None\n\n"
            + "def insert(root, val):\n"
            + "    if root is None:\n"
            + "        return Node(val)\n"
            + "    if val < root.data:\n"
            + "        root.left = insert(root.left, val)\n"
            + "    elif val > root.data:\n"
            + "        root.right = insert(root.right, val)\n"
            + "    return root\n\n";
        String name = "Iterative".equals(style) ? "deleteNode" : ("Functions".equals(style) ? "deleteFunc" : "deleteRec");
        return base
            + "def " + name + "(root, item):\n"
            + "    if root is None:\n"
            + "        print(\"ITEM not in tree\")\n"
            + "        return None\n"
            + "    if item < root.data:\n"
            + "        root.left = " + name + "(root.left, item)\n"
            + "    elif item > root.data:\n"
            + "        root.right = " + name + "(root.right, item)\n"
            + "    else:\n"
            + "        if root.left and root.right:\n"
            + "            suc = root.right\n"
            + "            while suc.left:\n"
            + "                suc = suc.left\n"
            + "            root.data = suc.data\n"
            + "            root.right = " + name + "(root.right, suc.data)\n"
            + "        elif root.left:\n"
            + "            return root.left\n"
            + "        else:\n"
            + "            return root.right\n"
            + "    return root\n\n"
            + "n = int(input())\nroot = None\nfor _ in range(n):\n    root = insert(root, int(input()))\nroot = " + name + "(root, int(input()))\nprint(\"Deletion Done\")";
    }

    private static String getPythonMaxHeapInsertion(String style) {
        if ("Functions".equals(style)) {
            return "def insertHeap(tree, n, item):\n    n += 1\n    ptr = n\n    while ptr > 1:\n        par = ptr // 2\n        if item <= tree[par]:\n            tree[ptr] = item\n            return n\n        tree[ptr] = tree[par]\n        ptr = par\n    tree[ptr] = item\n    return n\n\nn = int(input())\ntree = [0]*100\nfor i in range(1, n+1):\n    tree[i] = int(input())\nitem = int(input())\nn = insertHeap(tree, n, item)\nfor i in range(1, n+1):\n    print(tree[i], end=\" \")";
        }
        if ("Recursion".equals(style)) {
            return "def insertRec(tree, ptr, item):\n    if ptr == 1:\n        tree[ptr] = item\n        return\n    par = ptr // 2\n    if item <= tree[par]:\n        tree[ptr] = item\n        return\n    tree[ptr] = tree[par]\n    insertRec(tree, par, item)\n\nn = int(input())\ntree = [0]*100\nfor i in range(1, n+1):\n    tree[i] = int(input())\nitem = int(input())\nn += 1\ninsertRec(tree, n, item)\nfor i in range(1, n+1):\n    print(tree[i], end=\" \")";
        }
        return "n = int(input())\ntree = [0]*100\nfor i in range(1, n+1):\n    tree[i] = int(input())\nitem = int(input())\nn += 1\nptr = n\nwhile ptr > 1:\n    par = ptr // 2\n    if item <= tree[par]:\n        tree[ptr] = item\n        break\n    tree[ptr] = tree[par]\n    ptr = par\nif ptr == 1:\n    tree[ptr] = item\nfor i in range(1, n+1):\n    print(tree[i], end=\" \")";
    }

    private static String getPythonMaxHeapDeletion(String style) {
        if ("Functions".equals(style)) {
            return "def deleteHeap(tree, n):\n    item = tree[1]\n    last = tree[n]\n    n -= 1\n    ptr = 1\n    while 2 * ptr <= n:\n        left = 2 * ptr\n        right = left + 1\n        child = right if (right <= n and tree[right] > tree[left]) else left\n        if last >= tree[child]:\n            break\n        tree[ptr] = tree[child]\n        ptr = child\n    tree[ptr] = last\n    return item\n\nn = int(input())\ntree = [0]*100\nfor i in range(1, n+1):\n    tree[i] = int(input())\ndeleted = deleteHeap(tree, n)\nprint(\"Deleted element:\", deleted)";
        }
        if ("Recursion".equals(style)) {
            return "def deleteRec(tree, ptr, n, last):\n    left = 2 * ptr\n    right = left + 1\n    if left > n:\n        tree[ptr] = last\n        return\n    child = right if (right <= n and tree[right] > tree[left]) else left\n    if last >= tree[child]:\n        tree[ptr] = last\n        return\n    tree[ptr] = tree[child]\n    deleteRec(tree, child, n, last)\n\nn = int(input())\ntree = [0]*100\nfor i in range(1, n+1):\n    tree[i] = int(input())\nitem = tree[1]\nlast = tree[n]\nn -= 1\ndeleteRec(tree, 1, n, last)\nprint(\"Deleted element:\", item)\nfor i in range(1, n+1):\n    print(tree[i], end=\" \")";
        }
        return "n = int(input())\ntree = [0]*100\nfor i in range(1, n+1):\n    tree[i] = int(input())\nitem = tree[1]\nlast = tree[n]\nn -= 1\nptr = 1\nwhile 2 * ptr <= n:\n    left = 2 * ptr\n    right = left + 1\n    child = right if (right <= n and tree[right] > tree[left]) else left\n    if last >= tree[child]:\n        break\n    tree[ptr] = tree[child]\n    ptr = child\ntree[ptr] = last\nprint(\"Deleted element:\", item)\nfor i in range(1, n+1):\n    print(tree[i], end=\" \")";
    }

    private static String getPythonHeapSort(String style) {
        String helpers = "def insertHeap(A, n, item):\n    ptr = n + 1\n    while ptr > 1:\n        par = ptr // 2\n        if item <= A[par]:\n            A[ptr] = item\n            return\n        A[ptr] = A[par]\n        ptr = par\n    A[ptr] = item\n\ndef deleteHeap(A, n):\n    item = A[1]\n    last = A[n]\n    n -= 1\n    ptr = 1\n    while 2 * ptr <= n:\n        left = 2 * ptr\n        right = left + 1\n        child = right if (right <= n and A[right] > A[left]) else left\n        if last >= A[child]:\n            break\n        A[ptr] = A[child]\n        ptr = child\n    A[ptr] = last\n    return item\n\n";
        if ("Functions".equals(style)) {
            return helpers + "def buildHeap(A, n):\n    for j in range(2, n + 1):\n        insertHeap(A, j - 1, A[j])\n\ndef sortHeap(A, n):\n    size = n\n    while size > 1:\n        item = deleteHeap(A, size)\n        A[size] = item\n        size -= 1\n\nn = int(input())\nA = [0]*200\nfor i in range(1, n+1):\n    A[i] = int(input())\nbuildHeap(A, n)\nsortHeap(A, n)\nfor i in range(1, n+1):\n    print(A[i], end=\" \")";
        }
        if ("Recursion".equals(style)) {
            return helpers + "def deleteMaxRec(A, n):\n    if n <= 1:\n        return\n    item = deleteHeap(A, n)\n    A[n] = item\n    deleteMaxRec(A, n - 1)\n\nn = int(input())\nA = [0]*200\nfor i in range(1, n+1):\n    A[i] = int(input())\nfor j in range(2, n+1):\n    insertHeap(A, j - 1, A[j])\ndeleteMaxRec(A, n)\nfor i in range(1, n+1):\n    print(A[i], end=\" \")";
        }
        return helpers + "n = int(input())\nA = [0]*200\nfor i in range(1, n+1):\n    A[i] = int(input())\nfor j in range(2, n+1):\n    insertHeap(A, j - 1, A[j])\nsize = n\nwhile size > 1:\n    item = deleteHeap(A, size)\n    A[size] = item\n    size -= 1\nfor i in range(1, n+1):\n    print(A[i], end=\" \")";
    }

    private static String getJavaWarshallAlgorithm(String style) {
        if ("Functions".equals(style)) {
            return String.join("\n",
                "import java.util.*;",
                "public class Main {",
                "    static void initMatrix(int[][] A, int[][] P, int m) {",
                "        for (int i = 1; i <= m; i++)",
                "            for (int j = 1; j <= m; j++)",
                "                P[i][j] = (A[i][j] == 0) ? 0 : 1;",
                "    }",
                "    static void updateMatrix(int[][] P, int m) {",
                "        for (int k = 1; k <= m; k++)",
                "            for (int i = 1; i <= m; i++)",
                "                for (int j = 1; j <= m; j++)",
                "                    P[i][j] = P[i][j] | (P[i][k] & P[k][j]);",
                "    }",
                "    public static void main(String[] args) {",
                "        Scanner sc = new Scanner(System.in);",
                "        int m = sc.nextInt();",
                "        int[][] A = new int[m + 1][m + 1];",
                "        int[][] P = new int[m + 1][m + 1];",
                "        for (int i = 1; i <= m; i++)",
                "            for (int j = 1; j <= m; j++)",
                "                A[i][j] = sc.nextInt();",
                "        initMatrix(A, P, m);",
                "        updateMatrix(P, m);",
                "        for (int i = 1; i <= m; i++) {",
                "            for (int j = 1; j <= m; j++) System.out.print(P[i][j] + \" \");",
                "            System.out.println();",
                "        }",
                "    }",
                "}");
        }
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "import java.util.*;",
                "public class Main {",
                "    static void warshallRec(int[][] P, int k, int m) {",
                "        if (k > m) return;",
                "        for (int i = 1; i <= m; i++)",
                "            for (int j = 1; j <= m; j++)",
                "                P[i][j] = P[i][j] | (P[i][k] & P[k][j]);",
                "        warshallRec(P, k + 1, m);",
                "    }",
                "    public static void main(String[] args) {",
                "        Scanner sc = new Scanner(System.in);",
                "        int m = sc.nextInt();",
                "        int[][] A = new int[m + 1][m + 1];",
                "        int[][] P = new int[m + 1][m + 1];",
                "        for (int i = 1; i <= m; i++)",
                "            for (int j = 1; j <= m; j++)",
                "                A[i][j] = sc.nextInt();",
                "        for (int i = 1; i <= m; i++)",
                "            for (int j = 1; j <= m; j++)",
                "                P[i][j] = (A[i][j] == 0) ? 0 : 1;",
                "        warshallRec(P, 1, m);",
                "        for (int i = 1; i <= m; i++) {",
                "            for (int j = 1; j <= m; j++) System.out.print(P[i][j] + \" \");",
                "            System.out.println();",
                "        }",
                "    }",
                "}");
        }
        return String.join("\n",
            "import java.util.*;",
            "public class Main {",
            "    public static void main(String[] args) {",
            "        Scanner sc = new Scanner(System.in);",
            "        int m = sc.nextInt();",
            "        int[][] A = new int[m + 1][m + 1];",
            "        int[][] P = new int[m + 1][m + 1];",
            "        for (int i = 1; i <= m; i++)",
            "            for (int j = 1; j <= m; j++)",
            "                A[i][j] = sc.nextInt();",
            "        for (int i = 1; i <= m; i++)",
            "            for (int j = 1; j <= m; j++)",
            "                P[i][j] = (A[i][j] == 0) ? 0 : 1;",
            "        for (int k = 1; k <= m; k++)",
            "            for (int i = 1; i <= m; i++)",
            "                for (int j = 1; j <= m; j++)",
            "                    P[i][j] = P[i][j] | (P[i][k] & P[k][j]);",
            "        for (int i = 1; i <= m; i++) {",
            "            for (int j = 1; j <= m; j++) System.out.print(P[i][j] + \" \");",
            "            System.out.println();",
            "        }",
            "    }",
            "}");
    }

    private static String getJavaFloydAlgorithm(String style) {
        if ("Functions".equals(style)) {
            return String.join("\n",
                "import java.util.*;",
                "public class Main {",
                "    static final int INF = 999999;",
                "    static void initMatrix(int[][] W, int[][] Q, int m) {",
                "        for (int i = 1; i <= m; i++)",
                "            for (int j = 1; j <= m; j++)",
                "                Q[i][j] = (W[i][j] == 0) ? INF : W[i][j];",
                "    }",
                "    static void updateMatrix(int[][] Q, int m) {",
                "        for (int k = 1; k <= m; k++)",
                "            for (int i = 1; i <= m; i++)",
                "                for (int j = 1; j <= m; j++)",
                "                    Q[i][j] = Math.min(Q[i][j], Q[i][k] + Q[k][j]);",
                "    }",
                "    public static void main(String[] args) {",
                "        Scanner sc = new Scanner(System.in);",
                "        int m = sc.nextInt();",
                "        int[][] W = new int[m + 1][m + 1];",
                "        int[][] Q = new int[m + 1][m + 1];",
                "        for (int i = 1; i <= m; i++)",
                "            for (int j = 1; j <= m; j++)",
                "                W[i][j] = sc.nextInt();",
                "        initMatrix(W, Q, m);",
                "        updateMatrix(Q, m);",
                "        for (int i = 1; i <= m; i++) {",
                "            for (int j = 1; j <= m; j++) System.out.print(Q[i][j] + \" \");",
                "            System.out.println();",
                "        }",
                "    }",
                "}");
        }
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "import java.util.*;",
                "public class Main {",
                "    static final int INF = 999999;",
                "    static void floydRec(int[][] Q, int k, int m) {",
                "        if (k > m) return;",
                "        for (int i = 1; i <= m; i++)",
                "            for (int j = 1; j <= m; j++)",
                "                Q[i][j] = Math.min(Q[i][j], Q[i][k] + Q[k][j]);",
                "        floydRec(Q, k + 1, m);",
                "    }",
                "    public static void main(String[] args) {",
                "        Scanner sc = new Scanner(System.in);",
                "        int m = sc.nextInt();",
                "        int[][] W = new int[m + 1][m + 1];",
                "        int[][] Q = new int[m + 1][m + 1];",
                "        for (int i = 1; i <= m; i++)",
                "            for (int j = 1; j <= m; j++)",
                "                W[i][j] = sc.nextInt();",
                "        for (int i = 1; i <= m; i++)",
                "            for (int j = 1; j <= m; j++)",
                "                Q[i][j] = (W[i][j] == 0) ? INF : W[i][j];",
                "        floydRec(Q, 1, m);",
                "        for (int i = 1; i <= m; i++) {",
                "            for (int j = 1; j <= m; j++) System.out.print(Q[i][j] + \" \");",
                "            System.out.println();",
                "        }",
                "    }",
                "}");
        }
        return String.join("\n",
            "import java.util.*;",
            "public class Main {",
            "    static final int INF = 999999;",
            "    public static void main(String[] args) {",
            "        Scanner sc = new Scanner(System.in);",
            "        int m = sc.nextInt();",
            "        int[][] W = new int[m + 1][m + 1];",
            "        int[][] Q = new int[m + 1][m + 1];",
            "        for (int i = 1; i <= m; i++)",
            "            for (int j = 1; j <= m; j++)",
            "                W[i][j] = sc.nextInt();",
            "        for (int i = 1; i <= m; i++)",
            "            for (int j = 1; j <= m; j++)",
            "                Q[i][j] = (W[i][j] == 0) ? INF : W[i][j];",
            "        for (int k = 1; k <= m; k++)",
            "            for (int i = 1; i <= m; i++)",
            "                for (int j = 1; j <= m; j++)",
            "                    Q[i][j] = Math.min(Q[i][j], Q[i][k] + Q[k][j]);",
            "        for (int i = 1; i <= m; i++) {",
            "            for (int j = 1; j <= m; j++) System.out.print(Q[i][j] + \" \");",
            "            System.out.println();",
            "        }",
            "    }",
            "}");
    }

    private static String getJavaFindEdgeGraph(String style) { return String.join("\n", "import java.util.*;", "public class Main {", "    public static void main(String[] args) {", "        Scanner sc = new Scanner(System.in);", "        System.out.print(\"Enter number of vertices: \");", "        int n = sc.nextInt();", "        int[][] graph = new int[n][n];", "        System.out.println(\"Enter adjacency matrix:\");", "        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) graph[i][j] = sc.nextInt();", "        System.out.print(\"Enter source vertex: \");", "        int a = sc.nextInt();", "        System.out.print(\"Enter destination vertex: \");", "        int b = sc.nextInt();", "        if (a >= 0 && a < n && b >= 0 && b < n && graph[a][b] == 1) System.out.println(\"Edge Found\"); else System.out.println(\"Edge Not Found\");", "    }", "}"); }
    private static String getJavaInsertNodeGraph(String style) { return String.join("\n", "import java.util.*;", "public class Main {", "    static boolean exists(int[] node, int n, int item) { for (int i = 0; i < n; i++) if (node[i] == item) return true; return false; }", "    public static void main(String[] args) {", "        Scanner sc = new Scanner(System.in);", "        System.out.print(\"Enter number of existing nodes: \");", "        int n = sc.nextInt();", "        int[] node = new int[100];", "        System.out.println(\"Enter node values:\");", "        for (int i = 0; i < n; i++) node[i] = sc.nextInt();", "        System.out.print(\"Enter node value to insert: \");", "        int item = sc.nextInt();", "        if (exists(node, n, item)) System.out.println(\"Node Exists\"); else { node[n++] = item; System.out.println(\"Node Inserted\"); System.out.print(\"Nodes after insertion: \"); for (int i = 0; i < n; i++) System.out.print(node[i] + \" \"); }", "    }", "}"); }
    private static String getJavaInsertEdgeGraph(String style) { return String.join("\n", "import java.util.*;", "public class Main {", "    public static void main(String[] args) {", "        Scanner sc = new Scanner(System.in);", "        System.out.print(\"Enter number of vertices: \");", "        int n = sc.nextInt();", "        int[][] graph = new int[n][n];", "        System.out.println(\"Enter adjacency matrix:\");", "        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) graph[i][j] = sc.nextInt();", "        System.out.print(\"Enter source vertex: \");", "        int a = sc.nextInt();", "        System.out.print(\"Enter destination vertex: \");", "        int b = sc.nextInt();", "        if (a < 0 || a >= n || b < 0 || b >= n) System.out.println(\"Invalid Edge\"); else { graph[a][b] = 1; System.out.println(\"Edge Inserted\"); }", "    }", "}"); }
    private static String getJavaDeleteEdgeGraph(String style) { return String.join("\n", "import java.util.*;", "public class Main {", "    public static void main(String[] args) {", "        Scanner sc = new Scanner(System.in);", "        System.out.print(\"Enter number of vertices: \");", "        int n = sc.nextInt();", "        int[][] graph = new int[n][n];", "        System.out.println(\"Enter adjacency matrix:\");", "        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) graph[i][j] = sc.nextInt();", "        System.out.print(\"Enter source vertex: \");", "        int a = sc.nextInt();", "        System.out.print(\"Enter destination vertex: \");", "        int b = sc.nextInt();", "        if (a < 0 || a >= n || b < 0 || b >= n || graph[a][b] == 0) System.out.println(\"Edge Not Found\"); else { graph[a][b] = 0; System.out.println(\"Edge Deleted\"); }", "    }", "}"); }
    private static String getJavaDeleteVertexGraph(String style) { return String.join("\n", "import java.util.*;", "public class Main {", "    static void clearRec(int[][] graph, int target, int index, int n) { if (index == n) return; graph[target][index] = 0; graph[index][target] = 0; clearRec(graph, target, index + 1, n); }", "    public static void main(String[] args) {", "        Scanner sc = new Scanner(System.in);", "        System.out.print(\"Enter number of vertices: \");", "        int n = sc.nextInt();", "        int[][] graph = new int[n][n];", "        System.out.println(\"Enter adjacency matrix:\");", "        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) graph[i][j] = sc.nextInt();", "        System.out.print(\"Enter vertex index to delete: \");", "        int target = sc.nextInt();", "        if (target < 0 || target >= n) System.out.println(\"Node Not Found\"); else if (\"Recursion\".equals(\"" + "\")) System.out.println(\"Node Deleted\"); else { clearRec(graph, target, 0, n); System.out.println(\"Node Deleted\"); }", "    }", "}"); }
    private static String getJavaBreadthFirstSearch(String style) { return String.join("\n", "import java.util.*;", "public class Main {", "    public static void main(String[] args) {", "        Scanner sc = new Scanner(System.in);", "        System.out.print(\"Enter number of vertices: \");", "        int n = sc.nextInt();", "        int[][] graph = new int[n][n];", "        System.out.println(\"Enter adjacency matrix:\");", "        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) graph[i][j] = sc.nextInt();", "        System.out.print(\"Enter start vertex: \");", "        int start = sc.nextInt();", "        int[] status = new int[n];", "        Arrays.fill(status, 1);", "        Queue<Integer> q = new LinkedList<>();", "        q.add(start); status[start] = 2;", "        System.out.print(\"BFS traversal: \");", "        while (!q.isEmpty()) { int node = q.poll(); System.out.print(node + \" \"); status[node] = 3; for (int i = 0; i < n; i++) if (graph[node][i] == 1 && status[i] == 1) { q.add(i); status[i] = 2; } }", "    }", "}"); }
    private static String getJavaDepthFirstSearch(String style) { return String.join("\n", "import java.util.*;", "public class Main {", "    static void dfsRec(int[][] graph, int node, int[] status) { if (status[node] != 1) return; System.out.print(node + \" \"); status[node] = 3; for (int i = 0; i < graph.length; i++) if (graph[node][i] == 1 && status[i] == 1) dfsRec(graph, i, status); }", "    public static void main(String[] args) {", "        Scanner sc = new Scanner(System.in);", "        System.out.print(\"Enter number of vertices: \");", "        int n = sc.nextInt();", "        int[][] graph = new int[n][n];", "        System.out.println(\"Enter adjacency matrix:\");", "        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) graph[i][j] = sc.nextInt();", "        System.out.print(\"Enter start vertex: \");", "        int start = sc.nextInt();", "        int[] status = new int[n];", "        Arrays.fill(status, 1);", "        System.out.print(\"DFS traversal: \");", "        if (\"Recursion\".equals(\"" + "\")) dfsRec(graph, start, status); else { Stack<Integer> stack = new Stack<>(); stack.push(start); status[start] = 2; while (!stack.isEmpty()) { int node = stack.pop(); System.out.print(node + \" \"); status[node] = 3; for (int i = 0; i < n; i++) if (graph[node][i] == 1 && status[i] == 1) { stack.push(i); status[i] = 2; } } }", "    }", "}"); }
    private static String getJavaTopologicalSort(String style) { return String.join("\n", "import java.util.*;", "public class Main {", "    static void topoDFS(int[][] graph, int node, boolean[] visited, Stack<Integer> stack) { visited[node] = true; for (int i = 0; i < graph.length; i++) if (graph[node][i] == 1 && !visited[i]) topoDFS(graph, i, visited, stack); stack.push(node); }", "    public static void main(String[] args) {", "        Scanner sc = new Scanner(System.in);", "        System.out.print(\"Enter number of vertices: \");", "        int n = sc.nextInt();", "        int[][] graph = new int[n][n];", "        System.out.println(\"Enter adjacency matrix:\");", "        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) graph[i][j] = sc.nextInt();", "        System.out.print(\"Topological order: \");", "        if (\"Recursion\".equals(\"" + "\")) { boolean[] visited = new boolean[n]; Stack<Integer> stack = new Stack<>(); for (int i = 0; i < n; i++) if (!visited[i]) topoDFS(graph, i, visited, stack); while (!stack.isEmpty()) System.out.print(stack.pop() + \" \"); } else { int[] indeg = new int[n]; for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) if (graph[i][j] == 1) indeg[j]++; Queue<Integer> q = new LinkedList<>(); for (int i = 0; i < n; i++) if (indeg[i] == 0) q.add(i); while (!q.isEmpty()) { int node = q.poll(); System.out.print(node + \" \"); for (int j = 0; j < n; j++) if (graph[node][j] == 1) { indeg[j]--; if (indeg[j] == 0) q.add(j); } } }", "    }", "}"); }
    private static String getCWarshallAlgorithm(String style) { return String.join("\n", "#include <stdio.h>", "int main() {", "    int m, A[101][101], P[101][101];", "    scanf(\"%d\", &m);", "    for (int i = 1; i <= m; i++) for (int j = 1; j <= m; j++) scanf(\"%d\", &A[i][j]);", "    for (int i = 1; i <= m; i++) for (int j = 1; j <= m; j++) P[i][j] = (A[i][j] == 0) ? 0 : 1;", "    for (int k = 1; k <= m; k++) for (int i = 1; i <= m; i++) for (int j = 1; j <= m; j++) P[i][j] = P[i][j] || (P[i][k] && P[k][j]);", "    for (int i = 1; i <= m; i++) { for (int j = 1; j <= m; j++) printf(\"%d \", P[i][j]); printf(\"\\n\"); }", "    return 0;", "}"); }
    private static String getCFloydAlgorithm(String style) { return String.join("\n", "#include <stdio.h>", "#define INF 999999", "int main() {", "    int m, W[101][101], Q[101][101];", "    scanf(\"%d\", &m);", "    for (int i = 1; i <= m; i++) for (int j = 1; j <= m; j++) scanf(\"%d\", &W[i][j]);", "    for (int i = 1; i <= m; i++) for (int j = 1; j <= m; j++) Q[i][j] = (W[i][j] == 0) ? INF : W[i][j];", "    for (int k = 1; k <= m; k++) for (int i = 1; i <= m; i++) for (int j = 1; j <= m; j++) Q[i][j] = (Q[i][j] < Q[i][k] + Q[k][j]) ? Q[i][j] : (Q[i][k] + Q[k][j]);", "    for (int i = 1; i <= m; i++) { for (int j = 1; j <= m; j++) printf(\"%d \", Q[i][j]); printf(\"\\n\"); }", "    return 0;", "}"); }
    private static String getCFindEdgeGraph(String style) { return String.join("\n", "#include <stdio.h>", "int main() {", "    int n, a, b, graph[100][100];", "    scanf(\"%d\", &n);", "    for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) scanf(\"%d\", &graph[i][j]);", "    scanf(\"%d %d\", &a, &b);", "    if (a >= 0 && a < n && b >= 0 && b < n && graph[a][b] == 1) printf(\"Edge Found\\n\"); else printf(\"Edge Not Found\\n\");", "    return 0;", "}"); }
    private static String getCInsertNodeGraph(String style) { return String.join("\n", "#include <stdio.h>", "int main() {", "    int n, item, node[100];", "    scanf(\"%d\", &n);", "    for (int i = 0; i < n; i++) scanf(\"%d\", &node[i]);", "    scanf(\"%d\", &item);", "    for (int i = 0; i < n; i++) if (node[i] == item) { printf(\"Node Exists\\n\"); return 0; }", "    node[n++] = item; printf(\"Node Inserted\\n\"); for (int i = 0; i < n; i++) printf(\"%d \", node[i]);", "    return 0;", "}"); }
    private static String getCInsertEdgeGraph(String style) { return String.join("\n", "#include <stdio.h>", "int main() {", "    int n, a, b, graph[100][100];", "    scanf(\"%d\", &n);", "    for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) scanf(\"%d\", &graph[i][j]);", "    scanf(\"%d %d\", &a, &b);", "    if (a < 0 || a >= n || b < 0 || b >= n) printf(\"Invalid Edge\\n\"); else { graph[a][b] = 1; printf(\"Edge Inserted\\n\"); }", "    return 0;", "}"); }
    private static String getCDeleteEdgeGraph(String style) { return String.join("\n", "#include <stdio.h>", "int main() {", "    int n, a, b, graph[100][100];", "    scanf(\"%d\", &n);", "    for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) scanf(\"%d\", &graph[i][j]);", "    scanf(\"%d %d\", &a, &b);", "    if (a < 0 || a >= n || b < 0 || b >= n || graph[a][b] == 0) printf(\"Edge Not Found\\n\"); else { graph[a][b] = 0; printf(\"Edge Deleted\\n\"); }", "    return 0;", "}"); }
    private static String getCDeleteVertexGraph(String style) { return String.join("\n", "#include <stdio.h>", "int main() {", "    int n, target, graph[100][100];", "    scanf(\"%d\", &n);", "    for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) scanf(\"%d\", &graph[i][j]);", "    scanf(\"%d\", &target);", "    if (target < 0 || target >= n) printf(\"Node Not Found\\n\"); else { for (int i = 0; i < n; i++) { graph[target][i] = 0; graph[i][target] = 0; } printf(\"Node Deleted\\n\"); }", "    return 0;", "}"); }
    private static String getCBreadthFirstSearch(String style) { return String.join("\n", "#include <stdio.h>", "int queue[100], front = 0, rear = -1;", "void enqueue(int x) { queue[++rear] = x; }", "int dequeue() { return queue[front++]; }", "int isEmpty() { return front > rear; }", "int main() {", "    int n, start, graph[100][100], status[100];", "    scanf(\"%d\", &n);", "    for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) scanf(\"%d\", &graph[i][j]);", "    scanf(\"%d\", &start);", "    for (int i = 0; i < n; i++) status[i] = 1;", "    enqueue(start); status[start] = 2;", "    while (!isEmpty()) { int node = dequeue(); printf(\"%d \", node); status[node] = 3; for (int i = 0; i < n; i++) if (graph[node][i] == 1 && status[i] == 1) { enqueue(i); status[i] = 2; } }", "    return 0;", "}"); }
    private static String getCDepthFirstSearch(String style) { return String.join("\n", "#include <stdio.h>", "int stackArr[100], top = -1;", "void push(int x) { stackArr[++top] = x; }", "int pop() { return stackArr[top--]; }", "int isEmpty() { return top == -1; }", "int main() {", "    int n, start, graph[100][100], status[100];", "    scanf(\"%d\", &n);", "    for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) scanf(\"%d\", &graph[i][j]);", "    scanf(\"%d\", &start);", "    for (int i = 0; i < n; i++) status[i] = 1;", "    push(start); status[start] = 2;", "    while (!isEmpty()) { int node = pop(); printf(\"%d \", node); status[node] = 3; for (int i = 0; i < n; i++) if (graph[node][i] == 1 && status[i] == 1) { push(i); status[i] = 2; } }", "    return 0;", "}"); }
    private static String getCTopologicalSort(String style) { return String.join("\n", "#include <stdio.h>", "int queue[100], front = 0, rear = -1;", "void enqueue(int x) { queue[++rear] = x; }", "int dequeue() { return queue[front++]; }", "int isEmpty() { return front > rear; }", "int main() {", "    int n, graph[100][100], indeg[100] = {0};", "    scanf(\"%d\", &n);", "    for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) { scanf(\"%d\", &graph[i][j]); if (graph[i][j] == 1) indeg[j]++; }", "    for (int i = 0; i < n; i++) if (indeg[i] == 0) enqueue(i);", "    while (!isEmpty()) { int node = dequeue(); printf(\"%d \", node); for (int j = 0; j < n; j++) if (graph[node][j] == 1) { indeg[j]--; if (indeg[j] == 0) enqueue(j); } }", "    return 0;", "}"); }
    private static String getPythonWarshallAlgorithm(String style) { return String.join("\n", "m = int(input())", "A = [list(map(int, input().split())) for _ in range(m)]", "P = [[0 if A[i][j] == 0 else 1 for j in range(m)] for i in range(m)]", "for k in range(m):", "    for i in range(m):", "        for j in range(m):", "            P[i][j] = P[i][j] or (P[i][k] and P[k][j])", "for row in P:", "    print(*row)"); }
    private static String getPythonFloydAlgorithm(String style) { return String.join("\n", "INF = 999999", "m = int(input())", "W = [list(map(int, input().split())) for _ in range(m)]", "Q = [[INF if W[i][j] == 0 else W[i][j] for j in range(m)] for i in range(m)]", "for k in range(m):", "    for i in range(m):", "        for j in range(m):", "            Q[i][j] = min(Q[i][j], Q[i][k] + Q[k][j])", "for row in Q:", "    print(*row)"); }
    private static String getPythonFindEdgeGraph(String style) { return String.join("\n", "n = int(input())", "graph = [list(map(int, input().split())) for _ in range(n)]", "a, b = map(int, input().split())", "print(\"Edge Found\" if 0 <= a < n and 0 <= b < n and graph[a][b] == 1 else \"Edge Not Found\")"); }
    private static String getPythonInsertNodeGraph(String style) { return String.join("\n", "n = int(input())", "node = list(map(int, input().split()))", "item = int(input())", "if item in node:", "    print(\"Node Exists\")", "else:", "    node.append(item)", "    print(\"Node Inserted\")", "    print(*node)"); }
    private static String getPythonInsertEdgeGraph(String style) { return String.join("\n", "n = int(input())", "graph = [list(map(int, input().split())) for _ in range(n)]", "a, b = map(int, input().split())", "if a < 0 or a >= n or b < 0 or b >= n:", "    print(\"Invalid Edge\")", "else:", "    graph[a][b] = 1", "    print(\"Edge Inserted\")"); }
    private static String getPythonDeleteEdgeGraph(String style) { return String.join("\n", "n = int(input())", "graph = [list(map(int, input().split())) for _ in range(n)]", "a, b = map(int, input().split())", "if a < 0 or a >= n or b < 0 or b >= n or graph[a][b] == 0:", "    print(\"Edge Not Found\")", "else:", "    graph[a][b] = 0", "    print(\"Edge Deleted\")"); }
    private static String getPythonDeleteVertexGraph(String style) { return String.join("\n", "n = int(input())", "graph = [list(map(int, input().split())) for _ in range(n)]", "target = int(input())", "if target < 0 or target >= n:", "    print(\"Node Not Found\")", "else:", "    for i in range(n):", "        graph[target][i] = 0", "        graph[i][target] = 0", "    print(\"Node Deleted\")"); }
    private static String getPythonBreadthFirstSearch(String style) { return String.join("\n", "from collections import deque", "n = int(input())", "graph = [list(map(int, input().split())) for _ in range(n)]", "start = int(input())", "status = [1] * n", "q = deque([start])", "status[start] = 2", "while q:", "    node = q.popleft()", "    print(node, end=' ')", "    status[node] = 3", "    for i in range(n):", "        if graph[node][i] == 1 and status[i] == 1:", "            q.append(i)", "            status[i] = 2"); }
    private static String getPythonDepthFirstSearch(String style) { return String.join("\n", "n = int(input())", "graph = [list(map(int, input().split())) for _ in range(n)]", "start = int(input())", "status = [1] * n", "stack = [start]", "status[start] = 2", "while stack:", "    node = stack.pop()", "    print(node, end=' ')", "    status[node] = 3", "    for i in range(n):", "        if graph[node][i] == 1 and status[i] == 1:", "            stack.append(i)", "            status[i] = 2"); }
    private static String getPythonTopologicalSort(String style) { return String.join("\n", "from collections import deque", "n = int(input())", "graph = [list(map(int, input().split())) for _ in range(n)]", "indeg = [0] * n", "for i in range(n):", "    for j in range(n):", "        if graph[i][j] == 1:", "            indeg[j] += 1", "q = deque(i for i in range(n) if indeg[i] == 0)", "while q:", "    node = q.popleft()", "    print(node, end=' ')", "    for j in range(n):", "        if graph[node][j] == 1:", "            indeg[j] -= 1", "            if indeg[j] == 0:", "                q.append(j)"); }

    private static String getJavaInsertionSortPdf(String style) {
        if ("Functions".equals(style)) {
            return String.join("\n",
                "import java.util.*;",
                "public class Main {",
                "    static void insertElement(int[] A, int k) {",
                "        int temp = A[k];",
                "        int ptr = k - 1;",
                "        while (temp < A[ptr]) {",
                "            A[ptr + 1] = A[ptr];",
                "            ptr--;",
                "        }",
                "        A[ptr + 1] = temp;",
                "    }",
                "",
                "    static void insertionSort(int[] A, int n) {",
                "        A[0] = Integer.MIN_VALUE;",
                "        for (int k = 2; k <= n; k++) insertElement(A, k);",
                "    }",
                "",
                "    static void printArray(int[] A, int n) {",
                "        for (int i = 1; i <= n; i++) System.out.print(A[i] + \" \");",
                "        System.out.println();",
                "    }",
                "",
                "    public static void main(String[] args) {",
                "        Scanner sc = new Scanner(System.in);",
                "        System.out.print(\"Enter number of elements: \");",
                "        int n = sc.nextInt();",
                "        int[] A = new int[n + 1];",
                "        System.out.println(\"Enter elements:\");",
                "        for (int i = 1; i <= n; i++) A[i] = sc.nextInt();",
                "        insertionSort(A, n);",
                "        System.out.print(\"Sorted array: \");",
                "        printArray(A, n);",
                "    }",
                "}");
        }
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "import java.util.*;",
                "public class Main {",
                "    static void insertionRec(int[] A, int n) {",
                "        if (n <= 1) return;",
                "        insertionRec(A, n - 1);",
                "        int temp = A[n];",
                "        int ptr = n - 1;",
                "        while (ptr >= 1 && temp < A[ptr]) {",
                "            A[ptr + 1] = A[ptr];",
                "            ptr--;",
                "        }",
                "        A[ptr + 1] = temp;",
                "    }",
                "",
                "    static void printArray(int[] A, int n) {",
                "        for (int i = 1; i <= n; i++) System.out.print(A[i] + \" \");",
                "        System.out.println();",
                "    }",
                "",
                "    public static void main(String[] args) {",
                "        Scanner sc = new Scanner(System.in);",
                "        System.out.print(\"Enter number of elements: \");",
                "        int n = sc.nextInt();",
                "        int[] A = new int[n + 1];",
                "        A[0] = Integer.MIN_VALUE;",
                "        System.out.println(\"Enter elements:\");",
                "        for (int i = 1; i <= n; i++) A[i] = sc.nextInt();",
                "        insertionRec(A, n);",
                "        System.out.print(\"Sorted array: \");",
                "        printArray(A, n);",
                "    }",
                "}");
        }
        return String.join("\n",
            "import java.util.*;",
            "public class Main {",
            "    static void printArray(int[] A, int n) {",
            "        for (int i = 1; i <= n; i++) System.out.print(A[i] + \" \");",
            "        System.out.println();",
            "    }",
            "",
            "    public static void main(String[] args) {",
            "        Scanner sc = new Scanner(System.in);",
            "        System.out.print(\"Enter number of elements: \");",
            "        int n = sc.nextInt();",
            "        int[] A = new int[n + 1];",
            "        A[0] = Integer.MIN_VALUE;",
            "        System.out.println(\"Enter elements:\");",
            "        for (int i = 1; i <= n; i++) A[i] = sc.nextInt();",
            "        for (int k = 2; k <= n; k++) {",
            "            int temp = A[k];",
            "            int ptr = k - 1;",
            "            while (temp < A[ptr]) {",
            "                A[ptr + 1] = A[ptr];",
            "                ptr--;",
            "            }",
            "            A[ptr + 1] = temp;",
            "        }",
            "        System.out.print(\"Sorted array: \");",
            "        printArray(A, n);",
            "    }",
            "}");
    }

    private static String getJavaSelectionSortPdf(String style) {
        if ("Functions".equals(style)) {
            return String.join("\n",
                "import java.util.*;",
                "public class Main {",
                "    static int findMin(int[] A, int k, int n) {",
                "        int min = A[k], loc = k;",
                "        for (int j = k + 1; j <= n; j++) if (min > A[j]) { min = A[j]; loc = j; }",
                "        return loc;",
                "    }",
                "",
                "    static void swap(int[] A, int i, int j) {",
                "        int temp = A[i];",
                "        A[i] = A[j];",
                "        A[j] = temp;",
                "    }",
                "",
                "    static void selectionSort(int[] A, int n) {",
                "        for (int k = 1; k <= n - 1; k++) {",
                "            int loc = findMin(A, k, n);",
                "            swap(A, k, loc);",
                "        }",
                "    }",
                "",
                "    static void printArray(int[] A, int n) {",
                "        for (int i = 1; i <= n; i++) System.out.print(A[i] + \" \");",
                "        System.out.println();",
                "    }",
                "",
                "    public static void main(String[] args) {",
                "        Scanner sc = new Scanner(System.in);",
                "        System.out.print(\"Enter number of elements: \");",
                "        int n = sc.nextInt();",
                "        int[] A = new int[n + 1];",
                "        System.out.println(\"Enter elements:\");",
                "        for (int i = 1; i <= n; i++) A[i] = sc.nextInt();",
                "        selectionSort(A, n);",
                "        System.out.print(\"Sorted array: \");",
                "        printArray(A, n);",
                "    }",
                "}");
        }
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "import java.util.*;",
                "public class Main {",
                "    static void selectionRec(int[] A, int k, int n) {",
                "        if (k == n) return;",
                "        int loc = k;",
                "        for (int j = k + 1; j <= n; j++) if (A[j] < A[loc]) loc = j;",
                "        int temp = A[k];",
                "        A[k] = A[loc];",
                "        A[loc] = temp;",
                "        selectionRec(A, k + 1, n);",
                "    }",
                "",
                "    static void printArray(int[] A, int n) {",
                "        for (int i = 1; i <= n; i++) System.out.print(A[i] + \" \");",
                "        System.out.println();",
                "    }",
                "",
                "    public static void main(String[] args) {",
                "        Scanner sc = new Scanner(System.in);",
                "        System.out.print(\"Enter number of elements: \");",
                "        int n = sc.nextInt();",
                "        int[] A = new int[n + 1];",
                "        System.out.println(\"Enter elements:\");",
                "        for (int i = 1; i <= n; i++) A[i] = sc.nextInt();",
                "        selectionRec(A, 1, n);",
                "        System.out.print(\"Sorted array: \");",
                "        printArray(A, n);",
                "    }",
                "}");
        }
        return String.join("\n",
            "import java.util.*;",
            "public class Main {",
            "    static void printArray(int[] A, int n) {",
            "        for (int i = 1; i <= n; i++) System.out.print(A[i] + \" \");",
            "        System.out.println();",
            "    }",
            "",
            "    public static void main(String[] args) {",
            "        Scanner sc = new Scanner(System.in);",
            "        System.out.print(\"Enter number of elements: \");",
            "        int n = sc.nextInt();",
            "        int[] A = new int[n + 1];",
            "        System.out.println(\"Enter elements:\");",
            "        for (int i = 1; i <= n; i++) A[i] = sc.nextInt();",
            "        for (int k = 1; k <= n - 1; k++) {",
            "            int min = A[k];",
            "            int loc = k;",
            "            for (int j = k + 1; j <= n; j++) if (min > A[j]) { min = A[j]; loc = j; }",
            "            int temp = A[k];",
            "            A[k] = A[loc];",
            "            A[loc] = temp;",
            "        }",
            "        System.out.print(\"Sorted array: \");",
            "        printArray(A, n);",
            "    }",
            "}");
    }
    private static String getJavaMergingPdf(String style) { if ("Functions".equals(style)) return String.join("\n", "import java.util.*;", "public class Main {", "    static void merge(int[] A, int R, int[] B, int S, int[] C) { int NA = 1, NB = 1, PTR = 1; while (NA <= R && NB <= S) { if (A[NA] < B[NB]) C[PTR++] = A[NA++]; else C[PTR++] = B[NB++]; } if (NA > R) for (int k = 0; k <= S - NB; k++) C[PTR + k] = B[NB + k]; else for (int k = 0; k <= R - NA; k++) C[PTR + k] = A[NA + k]; }", "    public static void main(String[] args) { Scanner sc = new Scanner(System.in); int R = sc.nextInt(); int[] A = new int[R + 1]; for (int i = 1; i <= R; i++) A[i] = sc.nextInt(); int S = sc.nextInt(); int[] B = new int[S + 1]; for (int i = 1; i <= S; i++) B[i] = sc.nextInt(); int[] C = new int[R + S + 1]; merge(A, R, B, S, C); for (int i = 1; i <= R + S; i++) System.out.print(C[i] + \" \"); }", "}"); if ("Recursion".equals(style)) return String.join("\n", "import java.util.*;", "public class Main {", "    static void mergeRec(int[] A, int[] B, int[] C, int NA, int NB, int PTR, int R, int S) { if (NA > R) { for (int k = 0; k <= S - NB; k++) C[PTR + k] = B[NB + k]; return; } if (NB > S) { for (int k = 0; k <= R - NA; k++) C[PTR + k] = A[NA + k]; return; } if (A[NA] < B[NB]) { C[PTR] = A[NA]; mergeRec(A, B, C, NA + 1, NB, PTR + 1, R, S); } else { C[PTR] = B[NB]; mergeRec(A, B, C, NA, NB + 1, PTR + 1, R, S); } }", "    public static void main(String[] args) { Scanner sc = new Scanner(System.in); int R = sc.nextInt(); int[] A = new int[R + 1]; for (int i = 1; i <= R; i++) A[i] = sc.nextInt(); int S = sc.nextInt(); int[] B = new int[S + 1]; for (int i = 1; i <= S; i++) B[i] = sc.nextInt(); int[] C = new int[R + S + 1]; mergeRec(A, B, C, 1, 1, 1, R, S); for (int i = 1; i <= R + S; i++) System.out.print(C[i] + \" \"); }", "}"); return String.join("\n", "import java.util.*;", "public class Main {", "    public static void main(String[] args) { Scanner sc = new Scanner(System.in); int R = sc.nextInt(); int[] A = new int[R + 1]; for (int i = 1; i <= R; i++) A[i] = sc.nextInt(); int S = sc.nextInt(); int[] B = new int[S + 1]; for (int i = 1; i <= S; i++) B[i] = sc.nextInt(); int[] C = new int[R + S + 1]; int NA = 1, NB = 1, PTR = 1; while (NA <= R && NB <= S) { if (A[NA] < B[NB]) C[PTR++] = A[NA++]; else C[PTR++] = B[NB++]; } if (NA > R) for (int k = 0; k <= S - NB; k++) C[PTR + k] = B[NB + k]; else for (int k = 0; k <= R - NA; k++) C[PTR + k] = A[NA + k]; for (int i = 1; i <= R + S; i++) System.out.print(C[i] + \" \"); }", "}"); }
    private static String getJavaMergeSortPdf(String style) { if ("Functions".equals(style) || "Iterative".equals(style)) return String.join("\n", "import java.util.*;", "public class Main {", "    static void merge(int[] A, int l1, int s1, int[] B, int l2, int s2, int[] C, int sc) { int i = s1, j = s2, k = sc; while (i < s1 + l1 && j < s2 + l2) { if (A[i] < B[j]) C[k++] = A[i++]; else C[k++] = B[j++]; } while (i < s1 + l1) C[k++] = A[i++]; while (j < s2 + l2) C[k++] = B[j++]; }", "    static void mergePass(int[] A, int n, int L, int[] B) { int q = n / (2 * L), s = 2 * L * q; for (int i = 1; i <= s; i += 2 * L) merge(A, L, i, A, L, i + L, B, i); if (s + L < n) merge(A, L, s + 1, A, n - s - L, s + L + 1, B, s + 1); else for (int i = s + 1; i <= n; i++) B[i] = A[i]; }", "    static void mergeSort(int[] A, int n) { int[] B = new int[n + 1]; int L = 1; while (L < n) { mergePass(A, n, L, B); mergePass(B, n, 2 * L, A); L = 4 * L; } }", "    public static void main(String[] args) { Scanner sc = new Scanner(System.in); int n = sc.nextInt(); int[] A = new int[n + 1]; for (int i = 1; i <= n; i++) A[i] = sc.nextInt(); mergeSort(A, n); for (int i = 1; i <= n; i++) System.out.print(A[i] + \" \"); }", "}"); return String.join("\n", "import java.util.*;", "public class Main {", "    static void merge(int[] A, int low, int mid, int high) { int[] temp = new int[high + 1]; int i = low, j = mid + 1, k = low; while (i <= mid && j <= high) { if (A[i] < A[j]) temp[k++] = A[i++]; else temp[k++] = A[j++]; } while (i <= mid) temp[k++] = A[i++]; while (j <= high) temp[k++] = A[j++]; for (int x = low; x <= high; x++) A[x] = temp[x]; }", "    static void mergeSortRec(int[] A, int low, int high) { if (low >= high) return; int mid = (low + high) / 2; mergeSortRec(A, low, mid); mergeSortRec(A, mid + 1, high); merge(A, low, mid, high); }", "    public static void main(String[] args) { Scanner sc = new Scanner(System.in); int n = sc.nextInt(); int[] A = new int[n]; for (int i = 0; i < n; i++) A[i] = sc.nextInt(); mergeSortRec(A, 0, n - 1); for (int i = 0; i < n; i++) System.out.print(A[i] + \" \"); }", "}"); }
    private static String getJavaRadixSortPdf(String style) { if ("Functions".equals(style) || "Iterative".equals(style)) return String.join("\n", "import java.util.*;", "public class Main {", "    static void distribute(int[] A, int n, int divisor, Queue<Integer>[] Q) { for (int i = 0; i < n; i++) { int digit = (A[i] / divisor) % 10; Q[digit].add(A[i]); } }", "    static void collect(int[] A, Queue<Integer>[] Q) { int index = 0; for (int i = 0; i < 10; i++) while (!Q[i].isEmpty()) A[index++] = Q[i].remove(); }", "    static void radixSort(int[] A, int n, int d) { int divisor = 1; for (int k = 1; k <= d; k++) { Queue<Integer>[] Q = new LinkedList[10]; for (int i = 0; i < 10; i++) Q[i] = new LinkedList<>(); distribute(A, n, divisor, Q); collect(A, Q); divisor *= 10; } }", "    public static void main(String[] args) { Scanner sc = new Scanner(System.in); int n = sc.nextInt(); int[] A = new int[n]; for (int i = 0; i < n; i++) A[i] = sc.nextInt(); int d = sc.nextInt(); radixSort(A, n, d); for (int i = 0; i < n; i++) System.out.print(A[i] + \" \"); }", "}"); return String.join("\n", "import java.util.*;", "public class Main {", "    static void radixRec(int[] A, int n, int d, int divisor) { if (d == 0) return; Queue<Integer>[] Q = new LinkedList[10]; for (int i = 0; i < 10; i++) Q[i] = new LinkedList<>(); for (int i = 0; i < n; i++) { int digit = (A[i] / divisor) % 10; Q[digit].add(A[i]); } int index = 0; for (int i = 0; i < 10; i++) while (!Q[i].isEmpty()) A[index++] = Q[i].remove(); radixRec(A, n, d - 1, divisor * 10); }", "    public static void main(String[] args) { Scanner sc = new Scanner(System.in); int n = sc.nextInt(); int[] A = new int[n]; for (int i = 0; i < n; i++) A[i] = sc.nextInt(); int d = sc.nextInt(); radixRec(A, n, d, 1); for (int i = 0; i < n; i++) System.out.print(A[i] + \" \"); }", "}"); }
    private static String getCInsertionSortPdf(String style) { return String.join("\n", "#include <stdio.h>", "int main() {", "    int n; scanf(\"%d\", &n); int A[100]; A[0] = -99999; for (int i = 1; i <= n; i++) scanf(\"%d\", &A[i]);", "    for (int k = 2; k <= n; k++) { int temp = A[k]; int ptr = k - 1; while (temp < A[ptr]) { A[ptr + 1] = A[ptr]; ptr--; } A[ptr + 1] = temp; }", "    for (int i = 1; i <= n; i++) printf(\"%d \", A[i]);", "    return 0;", "}"); }
    private static String getCSelectionSortPdf(String style) { return String.join("\n", "#include <stdio.h>", "int main() {", "    int n; scanf(\"%d\", &n); int A[100]; for (int i = 1; i <= n; i++) scanf(\"%d\", &A[i]);", "    for (int k = 1; k <= n - 1; k++) { int min = A[k], loc = k; for (int j = k + 1; j <= n; j++) if (min > A[j]) { min = A[j]; loc = j; } int temp = A[k]; A[k] = A[loc]; A[loc] = temp; }", "    for (int i = 1; i <= n; i++) printf(\"%d \", A[i]);", "    return 0;", "}"); }
    private static String getCMergingPdf(String style) { return String.join("\n", "#include <stdio.h>", "int main() {", "    int R, S; scanf(\"%d\", &R); int A[100]; for (int i = 1; i <= R; i++) scanf(\"%d\", &A[i]); scanf(\"%d\", &S); int B[100]; for (int i = 1; i <= S; i++) scanf(\"%d\", &B[i]); int C[200]; int NA = 1, NB = 1, PTR = 1; while (NA <= R && NB <= S) { if (A[NA] < B[NB]) C[PTR++] = A[NA++]; else C[PTR++] = B[NB++]; } if (NA > R) for (int k = 0; k <= S - NB; k++) C[PTR + k] = B[NB + k]; else for (int k = 0; k <= R - NA; k++) C[PTR + k] = A[NA + k]; for (int i = 1; i <= R + S; i++) printf(\"%d \", C[i]); return 0; }"); }
    private static String getCMergeSortPdf(String style) { return String.join("\n", "#include <stdio.h>", "void merge(int A[], int l1, int s1, int B[], int l2, int s2, int C[], int sc) { int i = s1, j = s2, k = sc; while (i < s1 + l1 && j < s2 + l2) { if (A[i] < B[j]) C[k++] = A[i++]; else C[k++] = B[j++]; } while (i < s1 + l1) C[k++] = A[i++]; while (j < s2 + l2) C[k++] = B[j++]; }", "void mergePass(int A[], int n, int L, int B[]) { int q = n / (2 * L), s = 2 * L * q; for (int i = 1; i <= s; i += 2 * L) merge(A, L, i, A, L, i + L, B, i); if (s + L < n) merge(A, L, s + 1, A, n - s - L, s + L + 1, B, s + 1); else for (int i = s + 1; i <= n; i++) B[i] = A[i]; }", "int main() { int n; scanf(\"%d\", &n); int A[100], B[100]; for (int i = 1; i <= n; i++) scanf(\"%d\", &A[i]); int L = 1; while (L < n) { mergePass(A, n, L, B); mergePass(B, n, 2 * L, A); L = 4 * L; } for (int i = 1; i <= n; i++) printf(\"%d \", A[i]); return 0; }"); }
    private static String getCRadixSortPdf(String style) { return String.join("\n", "#include <stdio.h>", "int main() {", "    int n, d; scanf(\"%d\", &n); int A[100]; for (int i = 0; i < n; i++) scanf(\"%d\", &A[i]); scanf(\"%d\", &d); int divisor = 1;", "    for (int k = 1; k <= d; k++) { int Q[10][100], front[10], rear[10]; for (int i = 0; i < 10; i++) front[i] = rear[i] = -1; for (int i = 0; i < n; i++) { int digit = (A[i] / divisor) % 10; if (front[digit] == -1) front[digit] = 0; Q[digit][++rear[digit]] = A[i]; } int index = 0; for (int i = 0; i < 10; i++) if (front[i] != -1) for (int j = front[i]; j <= rear[i]; j++) A[index++] = Q[i][j]; divisor *= 10; }", "    for (int i = 0; i < n; i++) printf(\"%d \", A[i]);", "    return 0;", "}"); }
    private static String getPythonInsertionSortPdf(String style) { return String.join("\n", "n = int(input())", "A = [0] * (n + 1)", "A[0] = -99999", "for i in range(1, n + 1):", "    A[i] = int(input())", "for k in range(2, n + 1):", "    temp = A[k]", "    ptr = k - 1", "    while temp < A[ptr]:", "        A[ptr + 1] = A[ptr]", "        ptr -= 1", "    A[ptr + 1] = temp", "for i in range(1, n + 1):", "    print(A[i], end=' ')"); }
    private static String getPythonSelectionSortPdf(String style) { return String.join("\n", "n = int(input())", "A = [0] * (n + 1)", "for i in range(1, n + 1):", "    A[i] = int(input())", "for k in range(1, n):", "    min_val = A[k]", "    loc = k", "    for j in range(k + 1, n + 1):", "        if min_val > A[j]:", "            min_val = A[j]", "            loc = j", "    A[k], A[loc] = A[loc], A[k]", "for i in range(1, n + 1):", "    print(A[i], end=' ')"); }
    private static String getPythonMergingPdf(String style) { return String.join("\n", "R = int(input())", "A = [0] * (R + 1)", "for i in range(1, R + 1):", "    A[i] = int(input())", "S = int(input())", "B = [0] * (S + 1)", "for i in range(1, S + 1):", "    B[i] = int(input())", "C = [0] * (R + S + 1)", "NA, NB, PTR = 1, 1, 1", "while NA <= R and NB <= S:", "    if A[NA] < B[NB]:", "        C[PTR] = A[NA]", "        NA += 1", "    else:", "        C[PTR] = B[NB]", "        NB += 1", "    PTR += 1", "if NA > R:", "    for k in range(0, S - NB + 1):", "        C[PTR + k] = B[NB + k]", "else:", "    for k in range(0, R - NA + 1):", "        C[PTR + k] = A[NA + k]", "for i in range(1, R + S + 1):", "    print(C[i], end=' ')"); }
    private static String getPythonMergeSortPdf(String style) { return String.join("\n", "def merge(A, l1, s1, B, l2, s2, C, sc):", "    i, j, k = s1, s2, sc", "    while i < s1 + l1 and j < s2 + l2:", "        if A[i] < B[j]:", "            C[k] = A[i]", "            i += 1", "        else:", "            C[k] = B[j]", "            j += 1", "        k += 1", "    while i < s1 + l1:", "        C[k] = A[i]", "        i += 1", "        k += 1", "    while j < s2 + l2:", "        C[k] = B[j]", "        j += 1", "        k += 1", "def mergePass(A, n, L, B):", "    q = n // (2 * L)", "    s = 2 * L * q", "    i = 1", "    while i <= s:", "        merge(A, L, i, A, L, i + L, B, i)", "        i += 2 * L", "    if s + L < n:", "        merge(A, L, s + 1, A, n - s - L, s + L + 1, B, s + 1)", "    else:", "        for x in range(s + 1, n + 1):", "            B[x] = A[x]", "n = int(input())", "A = [0] * (n + 1)", "B = [0] * (n + 1)", "for i in range(1, n + 1):", "    A[i] = int(input())", "L = 1", "while L < n:", "    mergePass(A, n, L, B)", "    mergePass(B, n, 2 * L, A)", "    L = 4 * L", "for i in range(1, n + 1):", "    print(A[i], end=' ')"); }
    private static String getPythonRadixSortPdf(String style) { return String.join("\n", "n = int(input())", "A = [int(input()) for _ in range(n)]", "d = int(input())", "divisor = 1", "for k in range(1, d + 1):", "    Q = [[] for _ in range(10)]", "    for i in range(n):", "        digit = (A[i] // divisor) % 10", "        Q[digit].append(A[i])", "    index = 0", "    for i in range(10):", "        for num in Q[i]:", "            A[index] = num", "            index += 1", "    divisor *= 10", "for i in A:", "    print(i, end=' ')"); }

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
