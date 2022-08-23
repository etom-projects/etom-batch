package com.kurly.delivery.batch.batch.domain.user.enumerable;

public enum ERole {
    ROLE_USER,
    ROLE_ADMIN;

    public static ERole parse(String string) {
        switch (string) {
            case "ROLE_USER":
                return ROLE_USER;
            case "ROLE_ADMIN":
                return ROLE_ADMIN;
            default:
                return null;
        }
    }
}
