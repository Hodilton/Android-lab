package com.example.lab_6.models;

public class AppSettings {
    private String username;
    private boolean networkPermission;
    private boolean notificationsEnabled;

    public AppSettings() {}

    public AppSettings(String username,
                       boolean networkPermission,
                       boolean notificationsEnabled) {
        this.username = username;
        this.networkPermission = networkPermission;
        this.notificationsEnabled = notificationsEnabled;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public boolean getNetworkPermission() { return networkPermission; }
    public void setNetworkPermission(boolean networkPermission) { this.networkPermission = networkPermission; }

    public boolean getNotificationsEnabled() { return notificationsEnabled; }
    public void setNotificationsEnabled(boolean notificationsEnabled) { this.notificationsEnabled = notificationsEnabled; }
}