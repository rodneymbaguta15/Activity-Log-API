package com.activity.activity_logger.service;

import com.activity.activity_logger.dto.ActivityLogRequest;
import com.activity.activity_logger.dto.ActivityLogResponse;
import com.activity.activity_logger.exception.ResourceNotFoundException;
import com.activity.activity_logger.model.ActivityLog;
import com.activity.activity_logger.model.EventType;
import com.activity.activity_logger.repository.ActivityLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityLogService {

    private final ActivityLogRepository repository;

    @Transactional
    public ActivityLogResponse save(ActivityLogRequest request) {
        ActivityLog activityLog = ActivityLog.builder()
                .userId(request.getUserId())
                .eventType(request.getEventType())
                .resourceType(request.getResourceType())
                .resourceId(request.getResourceId())
                .description(request.getDescription())
                .ipAddress(request.getIpAddress())
                .metadata(request.getMetadata())
                .build();

        ActivityLog saved = repository.save(activityLog);
        log.info("Saved activity log [id={}] for user [{}]", saved.getId(), saved.getUserId());
        return ActivityLogResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public Page<ActivityLogResponse> findAll(
            String userId,
            EventType eventType,
            LocalDateTime from,
            LocalDateTime to,
            Pageable pageable
    ) {
        return repository
                .findByFilters(userId, eventType, from, to, pageable)
                .map(ActivityLogResponse::from);
    }

    @Transactional(readOnly = true)
    public ActivityLogResponse findById(Long id) {
        return repository.findById(id)
                .map(ActivityLogResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException("Activity log not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public Page<ActivityLogResponse> findByUserId(String userId, Pageable pageable) {
        return repository.findByUserId(userId, pageable).map(ActivityLogResponse::from);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Activity log not found with id: " + id);
        }
        repository.deleteById(id);
        log.info("Deleted activity log [id={}]", id);
    }
}
