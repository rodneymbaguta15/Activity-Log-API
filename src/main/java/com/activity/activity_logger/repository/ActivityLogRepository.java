package com.activity.activity_logger.repository;

import com.activity.activity_logger.model.ActivityLog;
import com.activity.activity_logger.model.EventType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {

    // All logs for a specific user (paginated)
    Page<ActivityLog> findByUserId(String userId, Pageable pageable);

    // Flexible filtered query — all params are optional
    @Query("""
        SELECT a FROM ActivityLog a
        WHERE (:userId    IS NULL OR a.userId    = :userId)
          AND (:eventType IS NULL OR a.eventType = :eventType)
          AND (:from      IS NULL OR a.createdAt >= :from)
          AND (:to        IS NULL OR a.createdAt <= :to)
        ORDER BY a.createdAt DESC
        """)
    Page<ActivityLog> findByFilters(
            @Param("userId")    String userId,
            @Param("eventType") EventType eventType,
            @Param("from") LocalDateTime from,
            @Param("to")        LocalDateTime to,
            Pageable pageable
    );
}
