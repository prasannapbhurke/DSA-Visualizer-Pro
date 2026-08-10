package com.example.dsa;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LandscapeStabilityTest {

    private void runRotationTestForTechnique(String techniqueId) {
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            android.util.Log.e("TestCrash", "FATAL CRASH IN TEST", throwable);
        });
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = new Intent(context, TechniqueDetailActivity.class);
        intent.putExtra(TechniqueDetailActivity.EXTRA_TECHNIQUE_ID, techniqueId);

        try (ActivityScenario<TechniqueDetailActivity> scenario = ActivityScenario.launch(intent)) {
            // Wait for activity to load
            Thread.sleep(500);

            // Select the Visualize tab
            Espresso.onView(ViewMatchers.withId(R.id.detail_flow_visualize))
                    .perform(ViewActions.click());
            Thread.sleep(300);

            // Rotate to landscape
            scenario.onActivity(activity -> {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            });
            Thread.sleep(1000);

            // Rotate back to portrait
            scenario.onActivity(activity -> {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            });
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLinearSearchRotation() {
        runRotationTestForTechnique("linear_search");
    }

    @Test
    public void testBinarySearchRotation() {
        runRotationTestForTechnique("binary_search");
    }

    @Test
    public void testBubbleSortRotation() {
        runRotationTestForTechnique("bubble_sort");
    }

    @Test
    public void testQuickSortRotation() {
        runRotationTestForTechnique("quicksort");
    }

    @Test
    public void testRadixSortRotation() {
        runRotationTestForTechnique("radix_sort");
    }

    @Test
    public void testHeapSortRotation() {
        runRotationTestForTechnique("heapsort");
    }

    @Test
    public void testStackRotation() {
        runRotationTestForTechnique("stack_basics");
    }

    @Test
    public void testQueueRotation() {
        runRotationTestForTechnique("queue_basics");
    }

    @Test
    public void testDequeRotation() {
        runRotationTestForTechnique("deque");
    }

    @Test
    public void testTreeTraversalRotation() {
        runRotationTestForTechnique("tree_traversal");
    }

    @Test
    public void testGraphTraversalRotation() {
        runRotationTestForTechnique("graph_traversal");
    }

    @Test
    public void testDijkstraRotation() {
        runRotationTestForTechnique("dijkstra_algorithm");
    }
}
