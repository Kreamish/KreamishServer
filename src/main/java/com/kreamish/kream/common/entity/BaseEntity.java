package com.kreamish.kream.common.entity;

import jakarta.persistence.Column;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

public abstract class BaseEntity {

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @Column(name = "create_id", nullable = false, updatable = false)
    private String createId;
    @Column(name = "update_id")
    private String updateId;
}
