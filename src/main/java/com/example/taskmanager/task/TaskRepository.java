package com.example.taskmanager.task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    @Query(value = "select t from TaskEntity t where t.status = 'IN_PROGRESS' and t.assignedUserId = :assignedUserId")
    List<TaskEntity> findByAssignedUserIdAndStatus_INPPROGRESS(@Param("assignedUserId") Long assignedUserId);
}
