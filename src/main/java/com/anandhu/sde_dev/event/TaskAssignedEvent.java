package com.anandhu.sde_dev.event;

public record TaskAssignedEvent(Long engineerId, Long taskId, String taskTitle, String email) {}
