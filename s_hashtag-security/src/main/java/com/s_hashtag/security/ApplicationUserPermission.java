package com.s_hashtag.security;

public enum ApplicationUserPermission {

    USER("user"),
    MEMBER_READ("member:read"),
    MEMBER_WRITE("member:write"),
    BATCH_READ("batch:read"),
    BATCH_WRITE("batch:write");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
