# Structured Content Import Checklist

Use this checklist whenever a new algorithm or topic is added to structured content.

## Before adding

- Choose a stable id like `bubble_sort`, `binary_search`, `bst_insertion`
- Confirm the correct category id in `catalog.json`
- Decide whether the topic belongs in an existing grouped file or a new content file

## Required content fields

- `id`
- `title`
- `definition`
- `complexity`
- `visualizationType`
- `algorithm.Iterative`
- `algorithm.Functions`
- `algorithm.Recursion`

## Recommended learning fields

- `realLifeExample`
- `sampleInput`
- `sampleTarget`
- `metadata.sourceTitle`
- `metadata.sourceChapter`
- `metadata.sourceReference`
- `metadata.updatedAt`

## Code completeness

- Add `Java` code for:
  - `Iterative`
  - `Functions`
  - `Recursion`
- Add `C` code for:
  - `Iterative`
  - `Functions`
  - `Recursion`
- Add `Python` code for:
  - `Iterative`
  - `Functions`
  - `Recursion`

## Commented code

- Add detailed commented code when available
- Keep `Code` tab learner-friendly
- Make sure `Code Clean` still works without comments

## Source metadata

- Fill `metadata.contentVersion`
- Fill `metadata.formatVersion`
- Fill `metadata.sourceTitle`
- Fill `metadata.sourceChapter`
- Fill `metadata.sourceReference`
- Fill `metadata.updatedAt`

## Visualization mapping

- Choose the correct visualization family:
  - `searching`
  - `sorting`
  - `structure`
  - `tree`
  - `graph`
- Put it in `visualizationType`
- For grouped files, use `defaults.visualizationType` only when every entry in that file shares the same family

## Final checks

- Add or update the technique entry in `catalog.json`
- Add or update the `contentFiles` entry in `catalog.json`
- Run `./gradlew.bat assembleDebug`
- Check the detail screen:
  - source line visible
  - version line visible
  - algorithm tab loads
  - code tab loads
  - visualization still opens
