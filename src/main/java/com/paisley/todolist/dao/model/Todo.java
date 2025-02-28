package com.paisley.todolist.dao.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "todos")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    // 使用 FLAG 表示完成狀態（'Y' = 完成, 'N' = 未完成）
    @Column(nullable = false, length = 1)
    private String completed;

    // 建立日期，預設為當前時間
    @Column(nullable = false, updatable = false)
    private LocalDateTime createDate = LocalDateTime.now();

    // 更新日期，應該在每次更新時修改
    @Column(nullable = false)
    private LocalDateTime updateDate = LocalDateTime.now();

    // 讓 updateDate 在每次更新時自動更新
    @PreUpdate
    public void setUpdateTimestamp() {
        this.updateDate = LocalDateTime.now();
    }
}