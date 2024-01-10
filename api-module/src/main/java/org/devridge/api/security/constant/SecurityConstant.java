package org.devridge.api.security.constant;

public interface SecurityConstant {

    String USER_ROLE = "USER";

    String[] ALL_PERMIT_PATHS = new String[]{
            "/api/users",
            "/api/login",
            "/api/users/emails/availability",
            "/api/login/**",
            "/api/social-login",
            "/api/social-login/**",
            "/api/emails/send-verification-email",
            "/api/emails/verify-code",
    };

    String[] USER_ROLE_PERMIT_PATHS = new String[]{
            "/api/**",
    };

}
