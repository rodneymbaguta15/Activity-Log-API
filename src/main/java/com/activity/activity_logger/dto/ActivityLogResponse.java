package com.activity.activity_logger.dto;

import com.activity.activity_logger.model.ActivityLog;
import com.activity.activity_logger.model.EventType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Builder
public class ActivityLogResponse {

    private Long id;
    private String userId;
    private EventType eventType;
    private String resourceType;
    private String resourceId;
    private String description;
    private String ipAddress;
    private String metadata;
    private LocalDateTime createdAt;

    public static ActivityLogResponse from(ActivityLog log) {
        return ActivityLogResponse.builder()
                .id(log.getId())
                .userId(log.getUserId())
                .eventType(log.getEventType())
                .resourceType(log.getResourceType())
                .resourceId(log.getResourceId())
                .description(log.getDescription())
                .ipAddress(log.getIpAddress())
                .metadata(log.getMetadata())
                .createdAt(log.getCreatedAt())
                .build();
    }
}
