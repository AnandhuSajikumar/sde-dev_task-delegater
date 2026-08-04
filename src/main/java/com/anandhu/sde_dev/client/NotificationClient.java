package com.anandhu.sde_dev.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
public class NotificationClient {
    private final RestClient restClient;

    public NotificationClient(@Value("${notification-service.base-url}") String baseUrl, RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.baseUrl(baseUrl).build();
    }

    public void syncSubscriber(Long engineerId, String email) {
        restClient.put()
                .uri("/subscribers/{engineerId}", engineerId)
                .body(new SyncSubscriberRequest(email, null, null))
                .retrieve()
                .toBodilessEntity();
    }

    public void triggerTaskAssignedNotification(Long engineerId, Long taskId, String taskTitle) {
        restClient.post()
                .uri("/notifications")
                .body(new NotificationRequest(
                        String.valueOf(engineerId),
                        "TASK_ASSIGNED",
                        "EMAIL",
                        Map.of("taskId", taskId, "taskTitle", taskTitle),
                        "TASK_ASSIGNED-" + taskId + "-" + engineerId
                ))
                .retrieve()
                .toBodilessEntity();
    }

    private record SyncSubscriberRequest(String email, String phone, String pushToken) {}
    private record NotificationRequest(String recipientId, String templateId, String channel, Map<String, Object> payload, String idempotencyKey) {}
}
