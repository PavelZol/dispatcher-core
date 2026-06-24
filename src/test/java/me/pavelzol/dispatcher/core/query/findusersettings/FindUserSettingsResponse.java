package me.pavelzol.dispatcher.core.query.findusersettings;

public final class FindUserSettingsResponse {
    private long userId;
    private boolean notificationsEnabled;
    private String theme;

    public long getUserId() {
        return userId;
    }

    void setUserId(long userId) {
        this.userId = userId;
    }

    public boolean isNotificationsEnabled() {
        return notificationsEnabled;
    }

    void setNotificationsEnabled(boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }

    public String getTheme() {
        return theme;
    }

    void setTheme(String theme) {
        this.theme = theme;
    }
}
