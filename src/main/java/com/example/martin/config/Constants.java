package com.example.martin.config;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

    public static final String SYSTEM = "system";
    public static final String DEFAULT_LANGUAGE = "en";

    public static final String API_ENDPOINT                 = "http://51.15.243.105:4200";

    public static final String NOTIFICATION_URL = "http://51.15.233.87:15432/message/queue";
    public static final String DEFAULT_EMAIL_SOURCE_ADDRESS = "alerts@meliora.tech";
    public static final String DEFAULT_SMS_SOURCE_ADDRESS = "SMSAfrica";

    public static final String DEFAULT_USER_VERIFICATION_EMAIL_TEMPLATE= "Dear $userName.  Please verify your email: $email using this OTP: $token . Click here to verify : http://51.15.141.206:9090/api/registration/confirm?token=$token " +
            "Thank you. $home.";

    private Constants() {}
}
