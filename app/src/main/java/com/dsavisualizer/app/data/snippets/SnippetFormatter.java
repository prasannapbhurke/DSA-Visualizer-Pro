package com.dsavisualizer.app.data.snippets;

public final class SnippetFormatter {

    private SnippetFormatter() {
    }

    public static String normalizeStyle(String style) {
        if ("Functions".equalsIgnoreCase(style) || "Function".equalsIgnoreCase(style)) {
            return "Functions";
        }
        if ("Recursion".equalsIgnoreCase(style)) {
            return "Recursion";
        }
        return "Iterative";
    }

    public static String formatCodeSnippet(String language, String code) {
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

}
