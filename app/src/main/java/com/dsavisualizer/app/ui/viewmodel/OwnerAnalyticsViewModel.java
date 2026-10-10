package com.dsavisualizer.app.ui.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dsavisualizer.app.data.ApiClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OwnerAnalyticsViewModel extends ViewModel {

    public static class TopicItem {
        public final String techniqueId;
        public final int uses;

        public TopicItem(String techniqueId, int uses) {
            this.techniqueId = techniqueId;
            this.uses = uses;
        }
    }

    public static class IssueItem {
        public final String techniqueId;
        public final String type;
        public final int count;

        public IssueItem(String techniqueId, String type, int count) {
            this.techniqueId = techniqueId;
            this.type = type;
            this.count = count;
        }
    }

    public static class AuditItem {
        public final String action;
        public final String actorName;
        public final String targetName;
        public final String createdAt;

        public AuditItem(String action, String actorName, String targetName, String createdAt) {
            this.action = action;
            this.actorName = actorName;
            this.targetName = targetName;
            this.createdAt = createdAt;
        }
    }

    public static class MistakeTopicItem {
        public final String techniqueId;
        public final int count;

        public MistakeTopicItem(String techniqueId, int count) {
            this.techniqueId = techniqueId;
            this.count = count;
        }
    }

    public static class AnalyticsUiState {
        public final int totalUsers;
        public final int activeTeachers;
        public final int totalClasses;
        public final int activeClasses;
        public final int totalResources;
        public final int totalFeedback;
        public final int totalPracticeSessions;
        public final int averageQuizScore;
        public final List<TopicItem> topics;
        public final List<IssueItem> issues;
        public final List<MistakeTopicItem> topMistakes;
        public final List<AuditItem> auditLogs;

        public AnalyticsUiState(
            int totalUsers,
            int activeTeachers,
            int totalClasses,
            int activeClasses,
            int totalResources,
            int totalFeedback,
            int totalPracticeSessions,
            int averageQuizScore,
            List<TopicItem> topics,
            List<IssueItem> issues,
            List<MistakeTopicItem> topMistakes,
            List<AuditItem> auditLogs
        ) {
            this.totalUsers = totalUsers;
            this.activeTeachers = activeTeachers;
            this.totalClasses = totalClasses;
            this.activeClasses = activeClasses;
            this.totalResources = totalResources;
            this.totalFeedback = totalFeedback;
            this.totalPracticeSessions = totalPracticeSessions;
            this.averageQuizScore = averageQuizScore;
            this.topics = topics;
            this.issues = issues;
            this.topMistakes = topMistakes;
            this.auditLogs = auditLogs;
        }
    }

    private final MutableLiveData<AnalyticsUiState> uiState = new MutableLiveData<>(null);
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>(null);

    public LiveData<AnalyticsUiState> getUiState() {
        return uiState;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void loadAnalytics(String token, boolean forceRefresh) {
        if (!forceRefresh && uiState.getValue() != null) {
            return;
        }

        loading.setValue(true);
        errorMessage.setValue(null);

        new Thread(() -> {
            try {
                JSONObject analyticsResponse = ApiClient.getInstance().execute("GET", "/api/admin/analytics", null, token);
                JSONObject auditResponse = ApiClient.getInstance().execute("GET", "/api/admin/audit-logs", null, token);

                JSONObject analytics = analyticsResponse.optJSONObject("data") == null
                    ? null
                    : analyticsResponse.optJSONObject("data").optJSONObject("analytics");
                JSONArray mostUsedTopics = analytics == null ? null : analytics.optJSONArray("mostUsedTopics");
                JSONArray mostReportedIssues = analytics == null ? null : analytics.optJSONArray("mostReportedContentIssues");
                JSONArray topMistakeTopics = analytics == null ? null : analytics.optJSONArray("topMistakeTopics");
                JSONObject summary = analytics == null ? null : analytics.optJSONObject("summary");
                JSONArray auditLogs = auditResponse.optJSONObject("data") == null
                    ? null
                    : auditResponse.optJSONObject("data").optJSONArray("auditLogs");

                List<TopicItem> topicItems = new ArrayList<>();
                if (mostUsedTopics != null) {
                    for (int i = 0; i < mostUsedTopics.length(); i++) {
                        JSONObject item = mostUsedTopics.optJSONObject(i);
                        if (item != null) {
                            topicItems.add(new TopicItem(item.optString("techniqueId", ""), item.optInt("uses", 0)));
                        }
                    }
                }

                List<IssueItem> issueItems = new ArrayList<>();
                if (mostReportedIssues != null) {
                    for (int i = 0; i < mostReportedIssues.length(); i++) {
                        JSONObject item = mostReportedIssues.optJSONObject(i);
                        if (item != null) {
                            issueItems.add(new IssueItem(item.optString("techniqueId", ""), item.optString("type", "general"), item.optInt("count", 0)));
                        }
                    }
                }

                List<MistakeTopicItem> mistakeItems = new ArrayList<>();
                if (topMistakeTopics != null) {
                    for (int i = 0; i < topMistakeTopics.length(); i++) {
                        JSONObject item = topMistakeTopics.optJSONObject(i);
                        if (item != null) {
                            mistakeItems.add(new MistakeTopicItem(item.optString("techniqueId", ""), item.optInt("count", 0)));
                        }
                    }
                }

                List<AuditItem> auditItems = new ArrayList<>();
                if (auditLogs != null) {
                    for (int i = 0; i < auditLogs.length() && i < 8; i++) {
                        JSONObject item = auditLogs.optJSONObject(i);
                        if (item != null) {
                            auditItems.add(new AuditItem(
                                item.optString("action", ""),
                                item.optString("actorName", ""),
                                item.optString("targetName", ""),
                                item.optString("createdAt", "")
                            ));
                        }
                    }
                }

                AnalyticsUiState state = new AnalyticsUiState(
                    summary == null ? 0 : summary.optInt("totalUsers", 0),
                    summary == null ? 0 : summary.optInt("activeTeachers", 0),
                    summary == null ? 0 : summary.optInt("totalClasses", 0),
                    summary == null ? 0 : summary.optInt("activeClasses", 0),
                    summary == null ? 0 : summary.optInt("totalResources", 0),
                    summary == null ? 0 : summary.optInt("totalFeedback", 0),
                    summary == null ? 0 : summary.optInt("totalPracticeSessions", 0),
                    summary == null ? 0 : summary.optInt("averageQuizScore", 0),
                    topicItems,
                    issueItems,
                    mistakeItems,
                    auditItems
                );

                uiState.postValue(state);
                loading.postValue(false);
            } catch (Exception error) {
                loading.postValue(false);
                errorMessage.postValue(error.getMessage() == null ? "Failed to load analytics" : error.getMessage());
            }
        }).start();
    }
}
