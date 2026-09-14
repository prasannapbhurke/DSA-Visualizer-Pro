package com.dsavisualizer.app;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.dsavisualizer.app.data.ApiClient;
import com.dsavisualizer.app.data.AppPreferences;
import com.dsavisualizer.app.ui.SafeInsetHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.materialswitch.MaterialSwitch;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ControlCentreActivity extends AppCompatActivity {

    private AppPreferences appPreferences;
    private ProgressBar loadingIndicator;

    private TextView roleSubtitleText;
    private MaterialCardView announcementCard;
    private TextView announcementText;

    private TextView kpi1Value;
    private TextView kpi1Label;
    private TextView kpi2Value;
    private TextView kpi2Label;
    private TextView kpi3Value;
    private TextView kpi3Label;
    private TextView kpi4Value;
    private TextView kpi4Label;

    private MaterialButton actionClassroom;
    private MaterialButton actionFeedback;
    private MaterialButton actionUsers;
    private MaterialButton actionAnalytics;

    private TextView liveStatusText;
    private MaterialButton liveBroadcastButton;

    private View settingsContainer;
    private MaterialSwitch switchAvl;
    private MaterialSwitch switchHuffman;
    private MaterialSwitch switchTrie;
    private MaterialSwitch switchDijkstra;
    private MaterialButton setAnnouncementButton;

    private TextView feedText;

    private boolean isOwner = false;
    private boolean isBroadcasting = false;
    private String activeBroadcastClassId = "";
    private final List<ClassroomItem> teacherClasses = new ArrayList<>();

    private static class ClassroomItem {
        final String id;
        final String name;

        ClassroomItem(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_centre);
        SafeInsetHelper.applyTopAndBottomInsets(findViewById(R.id.control_centre_root), 20, 20, 20, 20);

        appPreferences = new AppPreferences(this);
        String role = appPreferences.getAuthRole();
        isOwner = "owner".equalsIgnoreCase(role);
        boolean isTeacher = "teacher".equalsIgnoreCase(role);

        if (!isOwner && !isTeacher) {
            Toast.makeText(this, R.string.control_centre_unauthorized, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initViews();
        setupListeners();
        loadControlCentreData();
    }

    private void initViews() {
        findViewById(R.id.control_centre_back_button).setOnClickListener(v -> finish());
        findViewById(R.id.control_centre_refresh_button).setOnClickListener(v -> loadControlCentreData());

        roleSubtitleText = findViewById(R.id.control_centre_role_subtitle);
        loadingIndicator = findViewById(R.id.control_centre_loading);

        announcementCard = findViewById(R.id.control_centre_announcement_card);
        announcementText = findViewById(R.id.control_centre_announcement_text);

        kpi1Value = findViewById(R.id.kpi_1_value);
        kpi1Label = findViewById(R.id.kpi_1_label);
        kpi2Value = findViewById(R.id.kpi_2_value);
        kpi2Label = findViewById(R.id.kpi_2_label);
        kpi3Value = findViewById(R.id.kpi_3_value);
        kpi3Label = findViewById(R.id.kpi_3_label);
        kpi4Value = findViewById(R.id.kpi_4_value);
        kpi4Label = findViewById(R.id.kpi_4_label);

        actionClassroom = findViewById(R.id.control_action_classroom);
        actionFeedback = findViewById(R.id.control_action_feedback);
        actionUsers = findViewById(R.id.control_action_users);
        actionAnalytics = findViewById(R.id.control_action_analytics);

        liveStatusText = findViewById(R.id.control_centre_live_status);
        liveBroadcastButton = findViewById(R.id.control_centre_live_button);

        settingsContainer = findViewById(R.id.control_centre_settings_container);
        switchAvl = findViewById(R.id.switch_flag_avl);
        switchHuffman = findViewById(R.id.switch_flag_huffman);
        switchTrie = findViewById(R.id.switch_flag_trie);
        switchDijkstra = findViewById(R.id.switch_flag_dijkstra);
        setAnnouncementButton = findViewById(R.id.control_centre_set_announcement_button);

        feedText = findViewById(R.id.control_centre_feed_text);

        // Adjust role visibility
        roleSubtitleText.setText(isOwner ? R.string.control_centre_owner_subtitle : R.string.control_centre_teacher_subtitle);
        actionUsers.setVisibility(isOwner ? View.VISIBLE : View.GONE);
        actionAnalytics.setVisibility(isOwner ? View.VISIBLE : View.GONE);
        settingsContainer.setVisibility(isOwner ? View.VISIBLE : View.GONE);

        if (isOwner) {
            kpi1Label.setText(R.string.control_centre_kpi_users);
            kpi2Label.setText(R.string.control_centre_kpi_classes);
            kpi3Label.setText(R.string.control_centre_kpi_sessions);
            kpi4Label.setText(R.string.control_centre_kpi_feedback);
        } else {
            kpi1Label.setText(R.string.control_centre_kpi_classes);
            kpi2Label.setText(R.string.control_centre_kpi_students);
            kpi3Label.setText(R.string.control_centre_kpi_sessions);
            kpi4Label.setText(R.string.control_centre_kpi_feedback);
        }
    }

    private void setupListeners() {
        actionClassroom.setOnClickListener(v -> startActivity(new Intent(this, ClassroomActivity.class)));
        actionFeedback.setOnClickListener(v -> {
            if (isOwner) {
                startActivity(new Intent(this, OwnerFeedbackActivity.class));
            } else {
                startActivity(new Intent(this, TeacherFeedbackActivity.class));
            }
        });
        actionUsers.setOnClickListener(v -> startActivity(new Intent(this, OwnerUsersActivity.class)));
        actionAnalytics.setOnClickListener(v -> startActivity(new Intent(this, OwnerAnalyticsActivity.class)));

        liveBroadcastButton.setOnClickListener(v -> toggleLiveBroadcast());

        if (isOwner) {
            View.OnClickListener switchListener = v -> syncFeatureSettings();
            switchAvl.setOnClickListener(switchListener);
            switchHuffman.setOnClickListener(switchListener);
            switchTrie.setOnClickListener(switchListener);
            switchDijkstra.setOnClickListener(switchListener);
            setAnnouncementButton.setOnClickListener(v -> promptAnnouncementDialog());
        }
    }

    private void loadControlCentreData() {
        String token = appPreferences.getAuthToken();
        if (token.trim().isEmpty()) {
            Toast.makeText(this, R.string.owner_users_session_expired, Toast.LENGTH_SHORT).show();
            return;
        }

        loadingIndicator.setVisibility(View.VISIBLE);
        ApiClient.getInstance().get("/api/control-center/overview", token, new ApiClient.Callback() {
            @Override
            public void onSuccess(@NonNull JSONObject response) {
                loadingIndicator.setVisibility(View.GONE);
                bindOverviewResponse(response);
            }

            @Override
            public void onError(@NonNull Exception error) {
                loadingIndicator.setVisibility(View.GONE);
                Toast.makeText(ControlCentreActivity.this, R.string.control_centre_load_error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void bindOverviewResponse(JSONObject response) {
        JSONObject data = response.optJSONObject("data");
        if (data == null) return;
        JSONObject overview = data.optJSONObject("overview");
        if (overview == null) return;

        JSONObject stats = overview.optJSONObject("stats");
        if (stats != null) {
            if (isOwner) {
                kpi1Value.setText(String.valueOf(stats.optInt("totalUsers", 0)));
                kpi2Value.setText(String.valueOf(stats.optInt("totalClasses", 0)));
                kpi3Value.setText(String.valueOf(stats.optInt("totalSessions", 0)));
                kpi4Value.setText(String.valueOf(stats.optInt("unreviewedFeedback", 0)));
            } else {
                kpi1Value.setText(String.valueOf(stats.optInt("totalClasses", 0)));
                kpi2Value.setText(String.valueOf(stats.optInt("totalStudents", 0)));
                kpi3Value.setText(String.valueOf(stats.optInt("totalResources", 0)));
                kpi4Value.setText(String.valueOf(stats.optInt("pendingFeedback", 0)));
            }
        }

        // Cache classrooms for teacher broadcast
        teacherClasses.clear();
        JSONArray classesArray = overview.optJSONArray("classes");
        if (classesArray != null) {
            for (int i = 0; i < classesArray.length(); i++) {
                JSONObject c = classesArray.optJSONObject(i);
                if (c != null) {
                    teacherClasses.add(new ClassroomItem(c.optString("id"), c.optString("name")));
                }
            }
        }

        // Feature flags / System settings (Owner view)
        JSONObject settings = overview.optJSONObject("settings");
        if (settings != null) {
            String announcement = settings.optString("announcement", "").trim();
            if (!announcement.isEmpty()) {
                announcementCard.setVisibility(View.VISIBLE);
                announcementText.setText(announcement);
            } else {
                announcementCard.setVisibility(View.GONE);
            }

            JSONObject flags = settings.optJSONObject("experimentalVisualizers");
            if (flags != null) {
                switchAvl.setChecked(flags.optBoolean("avlTree", true));
                switchHuffman.setChecked(flags.optBoolean("huffmanCoding", true));
                switchTrie.setChecked(flags.optBoolean("trie", true));
                switchDijkstra.setChecked(flags.optBoolean("dijkstra", true));
            }
        }

        // Activity / Feed List
        StringBuilder feedBuilder = new StringBuilder();
        if (isOwner) {
            JSONArray topAlgos = overview.optJSONArray("topAlgorithms");
            if (topAlgos != null && topAlgos.length() > 0) {
                feedBuilder.append("🔥 Top Practiced Algorithms Platform-wide:\n\n");
                for (int i = 0; i < topAlgos.length(); i++) {
                    JSONObject item = topAlgos.optJSONObject(i);
                    if (item != null) {
                        feedBuilder.append("• ")
                            .append(item.optString("id").replace("_", " ").toUpperCase())
                            .append(": ")
                            .append(item.optInt("count"))
                            .append(" runs\n");
                    }
                }
            } else {
                feedBuilder.append("No algorithm practice data recorded yet.");
            }
        } else {
            JSONArray studentActivity = overview.optJSONArray("recentStudentActivity");
            if (studentActivity != null && studentActivity.length() > 0) {
                feedBuilder.append("⚡ Recent Student Submissions in Your Classes:\n\n");
                for (int i = 0; i < studentActivity.length(); i++) {
                    JSONObject item = studentActivity.optJSONObject(i);
                    if (item != null) {
                        feedBuilder.append("• ")
                            .append(item.optString("studentName", "Student"))
                            .append(" practiced ")
                            .append(item.optString("techniqueId").replace("_", " ").toUpperCase())
                            .append(" (Score: ")
                            .append(item.optInt("score"))
                            .append(")\n");
                    }
                }
            } else {
                feedBuilder.append("No student practice activity recorded in your classes yet.");
            }
        }
        feedText.setText(feedBuilder.toString());
    }

    private void syncFeatureSettings() {
        try {
            JSONObject body = new JSONObject();
            JSONObject settings = new JSONObject();
            settings.put("announcement", announcementText.getText().toString());
            settings.put("maintenanceMode", false);
            settings.put("registrationEnabled", true);

            JSONObject flags = new JSONObject();
            flags.put("avlTree", switchAvl.isChecked());
            flags.put("huffmanCoding", switchHuffman.isChecked());
            flags.put("trie", switchTrie.isChecked());
            flags.put("dijkstra", switchDijkstra.isChecked());
            settings.put("experimentalVisualizers", flags);

            body.put("settings", settings);

            ApiClient.getInstance().put("/api/control-center/settings", body, appPreferences.getAuthToken(), new ApiClient.Callback() {
                @Override
                public void onSuccess(@NonNull JSONObject response) {
                    Toast.makeText(ControlCentreActivity.this, "Feature flags synchronized", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(@NonNull Exception error) {
                    Toast.makeText(ControlCentreActivity.this, "Failed to update flags", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "Could not serialize settings", Toast.LENGTH_SHORT).show();
        }
    }

    private void promptAnnouncementDialog() {
        EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        input.setHint("Enter system announcement message...");
        input.setText(announcementText.getText());

        new AlertDialog.Builder(this)
            .setTitle("Broadcast System Notice")
            .setView(input)
            .setPositiveButton("Publish", (dialog, which) -> {
                String notice = input.getText().toString().trim();
                announcementText.setText(notice);
                announcementCard.setVisibility(notice.isEmpty() ? View.GONE : View.VISIBLE);
                syncFeatureSettings();
            })
            .setNegativeButton("Cancel", null)
            .show();
    }

    private void toggleLiveBroadcast() {
        if (isBroadcasting) {
            isBroadcasting = false;
            activeBroadcastClassId = "";
            liveStatusText.setText(R.string.control_centre_live_inactive);
            liveBroadcastButton.setText(R.string.control_centre_live_start);
            Toast.makeText(this, "Live presentation ended", Toast.LENGTH_SHORT).show();
            return;
        }

        if (teacherClasses.isEmpty()) {
            Toast.makeText(this, "Create a class in Classroom Hub first before broadcasting", Toast.LENGTH_LONG).show();
            return;
        }

        String[] classNames = new String[teacherClasses.size()];
        for (int i = 0; i < teacherClasses.size(); i++) {
            classNames[i] = teacherClasses.get(i).name;
        }

        new AlertDialog.Builder(this)
            .setTitle("Select Classroom for Live Session")
            .setItems(classNames, (dialog, which) -> {
                ClassroomItem selectedClass = teacherClasses.get(which);
                startLiveBroadcastForClass(selectedClass);
            })
            .setNegativeButton("Cancel", null)
            .show();
    }

    private void startLiveBroadcastForClass(ClassroomItem classroom) {
        try {
            JSONObject body = new JSONObject();
            JSONObject session = new JSONObject();
            session.put("classId", classroom.id);
            session.put("techniqueId", "avl_tree");
            session.put("currentStep", 0);
            session.put("playbackSpeed", 1.0);
            session.put("isPaused", false);
            session.put("statusMessage", "Live session initiated by instructor");
            body.put("session", session);

            ApiClient.getInstance().post("/api/control-center/live-session", body, appPreferences.getAuthToken(), new ApiClient.Callback() {
                @Override
                public void onSuccess(@NonNull JSONObject response) {
                    isBroadcasting = true;
                    activeBroadcastClassId = classroom.id;
                    liveStatusText.setText(getString(R.string.control_centre_live_active, classroom.name, "AVL Tree"));
                    liveBroadcastButton.setText(R.string.control_centre_live_stop);
                    Toast.makeText(ControlCentreActivity.this, "Live session broadcasting to " + classroom.name, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(@NonNull Exception error) {
                    Toast.makeText(ControlCentreActivity.this, "Could not start live broadcast", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "Could not prepare broadcast payload", Toast.LENGTH_SHORT).show();
        }
    }
}
