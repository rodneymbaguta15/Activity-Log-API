package com.activity.activity_logger.dto;

import com.activity.activity_logger.model.EventType;
import jakarta.validation.constraints.*;
import lombok.Data;


@Data
public class ActivityLogRequest {

    @NotBlank(message = "userId is required")
    @Size(max = 100, message = "userId must not exceed 100 characters")
    private String userId;

    @NotNull(message = "eventType is required")
    private EventType eventType;

    @Size(max = 100, message = "resourceType must not exceed 100 characters")
    private String resourceType;

    @Size(max = 100, message = "resourceId must not exceed 100 characters")
    private String resourceId;

    @Size(max = 1000, message = "description must not exceed 1000 characters")
    private String description;

    // Accepts both IPv4 and IPv6
    @Pattern(
            regexp = "^([0-9]{1,3}\\.){3}[0-9]{1,3}$|^[0-9a-fA-F:]+$",
            message = "ipAddress must be a valid IPv4 or IPv6 address"
    )
    @Size(max = 45, message = "ipAddress must not exceed 45 characters")
    private String ipAddress;

    // Expects a valid JSON string; stored as-is — no JSONB driver required
    private String metadata;
}
