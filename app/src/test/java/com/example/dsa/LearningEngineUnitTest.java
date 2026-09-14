package com.example.dsa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.dsavisualizer.app.learning.ActionType;
import com.dsavisualizer.app.learning.AnswerFeedback;
import com.dsavisualizer.app.learning.DecisionContext;
import com.dsavisualizer.app.learning.DecisionType;
import com.dsavisualizer.app.learning.DifficultyLevel;
import com.dsavisualizer.app.learning.ExplanationEngine;
import com.dsavisualizer.app.learning.LearningQuestion;
import com.dsavisualizer.app.learning.OperationType;
import com.dsavisualizer.app.learning.QuestionGenerator;
import com.dsavisualizer.app.learning.QuestionOption;
import com.dsavisualizer.app.learning.StepState;
import com.dsavisualizer.app.learning.StructureType;

import org.junit.Test;

import java.util.Arrays;

public class LearningEngineUnitTest {

    private StepState createSearchStep() {
        return new StepState.Builder()
            .setAlgorithmId("binary_search")
            .setAlgorithmTitle("Binary Search")
            .setStructureType(StructureType.SEARCH)
            .setOperationType(OperationType.COMPARE)
            .setStepNumber(2)
            .setDecisionContext(new DecisionContext(
                DecisionType.NEXT_STEP,
                "The target is bigger than mid. What should happen next?",
                "Move right because the target is greater than the middle value.",
                Arrays.asList(ActionType.MOVE_RIGHT, ActionType.MOVE_LEFT, ActionType.FOUND, ActionType.COMPLETE)
            ))
            .addPointer("low", 0)
            .addPointer("mid", 2)
            .addPointer("high", 5)
            .putMetadata("relation", "greater")
            .setSummary("Mid is smaller than the target.")
            .setHint("Compare the target with the middle value.")
            .setCorrectAction(ActionType.MOVE_RIGHT)
            .build();
    }

    @Test
    public void guidedQuestions_useHintAndTwoOptions() {
        QuestionGenerator generator = new QuestionGenerator();
        StepState step = createSearchStep();

        LearningQuestion question = generator.generate(step, DifficultyLevel.GUIDED);

        assertEquals("Guided: The target is bigger than mid. What should happen next?", question.prompt);
        assertEquals(2, question.options.size());
        boolean foundCorrect = false;
        for (QuestionOption option : question.options) {
            if (option.actionType == ActionType.MOVE_RIGHT && option.correct) {
                foundCorrect = true;
                break;
            }
        }
        assertTrue(foundCorrect);
        assertFalse(question.hint.isEmpty());
    }

    @Test
    public void intermediateQuestions_useThreeOptionsAndMinimalHint() {
        QuestionGenerator generator = new QuestionGenerator();
        StepState step = createSearchStep();

        LearningQuestion question = generator.generate(step, DifficultyLevel.INTERMEDIATE);

        assertEquals("Level 2: The target is bigger than mid. What should happen next?", question.prompt);
        assertEquals(3, question.options.size());
        assertTrue(question.hint.startsWith("Active:"));
    }

    @Test
    public void expertQuestions_useAllOptionsAndNoHint() {
        QuestionGenerator generator = new QuestionGenerator();
        StepState step = createSearchStep();

        LearningQuestion question = generator.generate(step, DifficultyLevel.EXPERT);

        assertEquals("Expert quiz: predict the exact next operation.", question.prompt);
        assertEquals(4, question.options.size());
        assertEquals("", question.hint);
    }

    @Test
    public void explanationEngine_marksCorrectAndWrongAnswers() {
        ExplanationEngine explanationEngine = new ExplanationEngine();
        StepState step = createSearchStep();

        AnswerFeedback correct = explanationEngine.explain(step, ActionType.MOVE_RIGHT);
        AnswerFeedback wrong = explanationEngine.explain(step, ActionType.MOVE_LEFT);

        assertTrue(correct.correct);
        assertEquals("Correct", correct.title);
        assertFalse(correct.explanation.isEmpty());

        assertFalse(wrong.correct);
        assertEquals("Wrong Answer", wrong.title);
        assertTrue(wrong.explanation.contains("move right"));
    }
}
