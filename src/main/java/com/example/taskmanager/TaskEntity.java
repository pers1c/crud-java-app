package com.example.taskmanager;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "tasks")
@Entity
public class TaskEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "assigned_user_id")
    private Long assignedUserId;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TaskStatus status;
    @Column(name  ="create_Date")
    private LocalDate createDate;
    @Column(name = "deadLine_date")
    private LocalDate deadLineDate;
    @Column(name = "done_Date_Time")
    private LocalDateTime doneDateTime;
    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private TaskPriority priority;

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getAssignedUserId() {
        return assignedUserId;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public LocalDate getDeadLineDate() {
        return deadLineDate;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setAssignedUserId(Long assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public void setDeadLineDate(LocalDate deadLineDate) {
        this.deadLineDate = deadLineDate;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public LocalDateTime getDoneDateTime() {
        return doneDateTime;
    }
    public void setDoneDateTime(LocalDateTime doneDateTime) {
        this.doneDateTime = doneDateTime;
    }
    public TaskEntity() {
    }

    public TaskEntity(Long id, Long userId, Long assignedUserId, TaskStatus status, LocalDate createDate, LocalDate deadLineDate, LocalDateTime doneDateTime, TaskPriority priority) {
        this.id = id;
        this.userId = userId;
        this.assignedUserId = assignedUserId;
        this.status = status;
        this.createDate = createDate;
        this.deadLineDate = deadLineDate;
        this.doneDateTime = doneDateTime;
        this.priority = priority;
    }
}
