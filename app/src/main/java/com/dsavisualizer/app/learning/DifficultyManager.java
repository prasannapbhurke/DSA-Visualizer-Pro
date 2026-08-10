package com.dsavisualizer.app.learning;

public class DifficultyManager {

    public DifficultyConfig resolve(DifficultyLevel level) {
        if (level == DifficultyLevel.EXPERT) {
            return new DifficultyConfig(false, false, 450L);
        }
        if (level == DifficultyLevel.INTERMEDIATE) {
            return new DifficultyConfig(false, true, 750L);
        }
        return new DifficultyConfig(true, true, 1100L);
    }
}
