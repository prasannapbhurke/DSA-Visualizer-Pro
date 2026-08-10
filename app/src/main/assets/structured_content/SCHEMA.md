# Structured Content Schema

This folder stores JSON-backed learning content for `DSA Visualizer Pro`.

## Catalog

File: `catalog.json`

Required top-level fields:

```json
{
  "version": 1,
  "categories": [],
  "techniques": [],
  "contentFiles": []
}
```

### Category item

Required:
- `id`
- `title`

Optional:
- `subtitle`
- `accentHex`

### Technique item

Required:
- `id`
- `categoryId`
- `title`

Optional:
- `subtitle`
- `difficulty`

## Content file shapes

Two supported shapes:

### Single entry file

```json
{
  "id": "bubble_sort",
  "title": "Bubble Sort"
}
```

### Grouped content file

```json
{
  "contents": [
    {
      "id": "stack_basics",
      "title": "Stack Operations"
    }
  ]
}
```

## Content entry

Required:
- `id`
- `title`
- `definition`
- `complexity`

Recommended:
- `realLifeExample`
- `sampleInput`
- `sampleTarget`
- `visualizationType`
- `algorithm`
- `code`

### Complexity

```json
{
  "best": "O(1)",
  "average": "O(n)",
  "worst": "O(n)",
  "space": "O(1)"
}
```

### Metadata

Optional but recommended for Phase 3+:

```json
{
  "metadata": {
    "contentVersion": 2,
    "formatVersion": 2,
    "sourceTitle": "Lipschutz Data Structures",
    "sourceChapter": "Sorting",
    "sourceReference": "Bubble Sort procedure",
    "updatedAt": "2026-04-15",
    "overrideOf": ""
  }
}
```

### Visualization type

Use one of:
- `searching`
- `sorting`
- `structure`
- `tree`
- `graph`

This helps keep structured content aligned with the visualizer family used by the app.

### Algorithm block

```json
{
  "algorithm": {
    "Iterative": "...",
    "Functions": "...",
    "Recursion": "..."
  }
}
```

### Code block

Supported forms:

Legacy flat form:

```json
{
  "code": {
    "Iterative": "...",
    "Functions": "...",
    "Recursion": "..."
  }
}
```

Preferred language-based form:

```json
{
  "code": {
    "Java": {
      "Iterative": "...",
      "Functions": "...",
      "Recursion": "..."
    },
    "C": {
      "Iterative": "..."
    },
    "Python": {
      "Iterative": "..."
    }
  }
}
```

### Commented code block

```json
{
  "commentedCode": {
    "C": {
      "Iterative": "..."
    }
  }
}
```

## Validation rules

Current validator checks:
- category has `id` and `title`
- technique has `id`, `categoryId`, and `title`
- content entry has `id`, `title`, `definition`, and `complexity`
- metadata fields are required for migrated content
- `visualizationType` must exist or be inferable from the technique id
- algorithm block must contain `Iterative`, `Functions`, and `Recursion`
- code block must provide `Java`, `C`, and `Python` with all 3 styles
- merged content is checked again after repository fallback is applied

## Authoring guidance

- Use stable ids like `bubble_sort`, `binary_search`, `bst_insertion`
- Prefer grouped files for large families like `stack_queue.json`
- Use a `defaults` block in grouped files when many entries share the same source metadata
- Add `metadata` for newly migrated or updated entries
- Keep algorithms and code in beginner-friendly readable form
