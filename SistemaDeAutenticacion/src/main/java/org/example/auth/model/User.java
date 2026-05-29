package org.example.auth.model;

public class User {

    private String username;
    private String passwordHash;
    private UserStatus status;
    private int failedAttempts;

    public User(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.status = UserStatus.ACTIVE;
        this.failedAttempts = 0;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public UserStatus getStatus() {
        return status;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void incrementAttempts() {
        this.failedAttempts++;
        if (this.failedAttempts >= 3) {
            this.status = UserStatus.BLOCKED;
        }
    }

    public void resetAttempts() {
        this.failedAttempts = 0;
    }

    public void unblock(String newPasswordHash) {
        this.passwordHash = newPasswordHash;
        this.status = UserStatus.ACTIVE;
        this.failedAttempts = 0;
    }


}
