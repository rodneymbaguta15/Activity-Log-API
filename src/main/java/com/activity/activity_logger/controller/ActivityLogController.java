package com.activity.activity_logger.controller;


import com.activity.activity_logger.dto.ActivityLogRequest;
import com.activity.activity_logger.dto.ActivityLogResponse;
import com.activity.activity_logger.model.EventType;
import com.activity.activity_logger.service.ActivityLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/logs")
@RequiredArgsConstructor
public class ActivityLogController {

    private final ActivityLogService service;

    // POST /api/v1/logs
    @PostMapping
    public ResponseEntity<ActivityLogResponse> create(@Valid @RequestBody ActivityLogRequest request) {
        ActivityLogResponse response = service.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET /api/v1/logs?userId=&eventType=&from=&to=&page=0&size=20&sort=createdAt,desc
    @GetMapping
    public ResponseEntity<Page<ActivityLogResponse>> findAll(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) EventType eventType,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(service.findAll(userId, eventType, from, to, pageable));
    }

    // GET /api/v1/logs/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ActivityLogResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    // GET /api/v1/logs/users/{userId}?page=0&size=20
    @GetMapping("/users/{userId}")
    public ResponseEntity<Page<ActivityLogResponse>> findByUserId(
            @PathVariable String userId,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(service.findByUserId(userId, pageable));
    }

    // DELETE /api/v1/logs/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
