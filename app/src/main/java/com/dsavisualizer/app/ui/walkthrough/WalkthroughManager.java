package com.dsavisualizer.app.ui.walkthrough;

public final class WalkthroughManager {

    public static final String STAGE_HOME_STUDENT = "home_student";
    public static final String STAGE_HOME_TEACHER = "home_teacher";
    public static final String STAGE_HOME_OWNER = "home_owner";
    public static final String STAGE_DETAIL = "detail";
    public static final String STAGE_CLASSROOM_STUDENT = "classroom_student";
    public static final String STAGE_CLASSROOM_TEACHER = "classroom_teacher";
    public static final String STAGE_COMPLETE = "complete";

    public static final String TIP_LANDSCAPE = "landscape";
    public static final String TIP_PRACTICE = "practice";
    public static final String TIP_CLASSROOM = "classroom";

    private WalkthroughManager() {
    }

    public static String defaultStageForRole(String role) {
        if ("owner".equalsIgnoreCase(role)) {
            return STAGE_HOME_OWNER;
        }
        if ("teacher".equalsIgnoreCase(role)) {
            return STAGE_HOME_TEACHER;
        }
        return STAGE_HOME_STUDENT;
    }

    public static String classroomStageForRole(String role) {
        if ("owner".equalsIgnoreCase(role) || "teacher".equalsIgnoreCase(role)) {
            return STAGE_CLASSROOM_TEACHER;
        }
        return STAGE_CLASSROOM_STUDENT;
    }

    public static boolean isHomeStage(String stage) {
        return STAGE_HOME_STUDENT.equals(stage)
            || STAGE_HOME_TEACHER.equals(stage)
            || STAGE_HOME_OWNER.equals(stage);
    }
}
