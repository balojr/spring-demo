package com.example.martin.service.notification;

import com.example.martin.config.Constants;
import com.example.martin.service.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class NotificationServiceHttpClient {

    private final Logger log = LoggerFactory.getLogger(ItemService.class);
    private final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String sendEmail(String sourceAddress, String email, String subject, String content, boolean isHtml) {

        log.info(
                "Request to send email. sourceAddress : {}, destinationAddress : {}, subject : {}, content : {}, isHtml : {} ",
                sourceAddress,
                email,
                subject,
                content,
                isHtml
        );

        String referenceId = UUID.randomUUID().toString();

        Map<String, String> messageMap = new HashMap<>();
        messageMap.put("sourceAddress", sourceAddress);
        messageMap.put("destinationAddress", email);
        messageMap.put("messageType", "EMAIL");
        messageMap.put("subject", subject);
        messageMap.put("message", content);
        messageMap.put("isHtml", String.valueOf(isHtml));

        try {
            String jsonRequest = objectMapper.writeValueAsString(messageMap);

            RequestBody requestBody = RequestBody.create(jsonRequest, MediaType.parse("application/json"));

            Request request = new Request.Builder().url(Constants.NOTIFICATION_URL).post(requestBody).build();
            try (Response ignored = okHttpClient.newCall(request).execute()) {
                return referenceId;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void sendSMS(String sourceAddress, String msisdn, String content) {
        log.info("Request to send sms. sourceAddress : {}, msisdn : {}", sourceAddress, msisdn);

        Map<String, String> messageMap = new HashMap<>();
        messageMap.put("destinationAddress", msisdn);
        messageMap.put("messageType", "SMS");
        messageMap.put("message", content);
        messageMap.put("sourceAddress", sourceAddress);

        try {
            String jsonRequest = objectMapper.writeValueAsString(messageMap); //JSON.stringly() - object

            log.info(jsonRequest);

            RequestBody requestBody = RequestBody.create(jsonRequest,
                    MediaType.parse("application/json"));

            Request request = new Request.Builder().url(Constants.NOTIFICATION_URL).post(requestBody).build();
            try (Response ignored = okHttpClient.newCall(request).execute()) {
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
