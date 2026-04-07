package com.example.taskmanager;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record Task(
    @Null
    Long id,
    @NotNull
    Long creatorId,
    Long assignedUserId,
    TaskStatus status,
    @Null
    LocalDate createDate,
    @NotNull
    @FutureOrPresent
    LocalDate deadLineTime,
    @Null
    LocalDateTime doneDateTime,
    @NotNull
    TaskPriority priority
){}
