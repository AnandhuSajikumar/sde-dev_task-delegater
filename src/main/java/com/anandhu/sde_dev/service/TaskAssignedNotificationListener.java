package com.anandhu.sde_dev.service;

import com.anandhu.sde_dev.client.NotificationClient;
import com.anandhu.sde_dev.event.TaskAssignedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class TaskAssignedNotificationListener {

    private static final Logger log = LoggerFactory.getLogger(TaskAssignedNotificationListener.class);
    private final NotificationClient notificationClient;

    public TaskAssignedNotificationListener(NotificationClient notificationClient) {
        this.notificationClient = notificationClient;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleTaskAssignedEvent(TaskAssignedEvent event) {
        try {
            notificationClient.syncSubscriber(event.engineerId(), event.email());
            notificationClient.triggerTaskAssignedNotification(event.engineerId(), event.taskId(), event.taskTitle());
        } catch (Exception e) {
            log.warn("Failed to send notification for task {} assigned to engineer {}", event.taskId(), event.engineerId(), e);
        }
    }
}
