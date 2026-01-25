package com.example.placement.model.entity;

import com.example.placement.model.entity.enums.AdminRole;
import lombok.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@CompoundIndex(name = "admin_unique_fields", def = "{'email':1, 'username':1, 'mobile':1}", unique = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "admins")
public class Admin {

    @Id
    private String id;

    /* ===== BASIC INFO ===== */
    private String name;
    private String email;
    private String username;
    private String mobile;

    /* ===== AUTH ===== */
    private String password;
    private AdminRole role;
    private Boolean isActive;

    /* ===== AUDIT ===== */
    @Builder.Default
    private LocalDateTime lastLoginAt = LocalDateTime.now();
    @CreatedDate
    @Builder.Default
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdAt = LocalDateTime.now();
    @LastModifiedDate
    @Builder.Default
    @Setter(AccessLevel.NONE)
    private LocalDateTime updatedAt = LocalDateTime.now();
}