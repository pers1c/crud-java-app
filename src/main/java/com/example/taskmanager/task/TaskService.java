package com.example.taskmanager.task;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task getTaskById(Long id){
        TaskEntity taskEntity = taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No found task by id=" + id));
        return toTask(taskEntity);
    }

    public List<Task> findAllTask() {
        List<TaskEntity> allEntity = taskRepository.findAll();
        return allEntity.stream().map(this::toTask).toList();
    }
    @Transactional
    public void deleteTask(Long id) {
        var taskEntity = taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found task by id=" + id));
        if (taskEntity.getStatus() != TaskStatus.DONE){
            throw new IllegalArgumentException("Cannot deleted task with status=DONE");
        }
        taskRepository.deleteById(id);

    }
    @Transactional
    public Task createTask(@NonNull Task taskToCreate) {
        if (taskToCreate.status() != null){
            throw new IllegalArgumentException("Status should be empty");
        }
        var entityToSave = new TaskEntity(
                null,
                taskToCreate.creatorId(),
                taskToCreate.assignedUserId(),
                TaskStatus.CREATED,
                LocalDate.now(),
                taskToCreate.deadLineTime(),
                null,
                taskToCreate.priority()
        );
        var savedEntity = taskRepository.save(entityToSave);
        return toTask(savedEntity);
    }
    @Transactional
    public Task updateTask(Long id, Task tasktoUpdate) {
        var taskEntity = taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found task by id=" + id));
        if (taskEntity.getStatus() == TaskStatus.DONE && !tasktoUpdate.status().equals(TaskStatus.IN_PROGRESS)){
            throw new IllegalArgumentException("Cannot updated task with status = " + tasktoUpdate.status());
        }
        var updateTaskEntity = new TaskEntity(
                id,
                tasktoUpdate.creatorId(),
                tasktoUpdate.assignedUserId(),
                taskEntity.getStatus(),
                taskEntity.getCreateDate(),
                tasktoUpdate.deadLineTime(),
                tasktoUpdate.doneDateTime(),
                tasktoUpdate.priority()
        );
        taskRepository.save(updateTaskEntity);
        return toTask(updateTaskEntity);
    }
    private Task toTask(TaskEntity taskEntity){
        return new Task(
                taskEntity.getId(),
                taskEntity.getUserId(),
                taskEntity.getAssignedUserId(),
                taskEntity.getStatus(),
                taskEntity.getCreateDate(),
                taskEntity.getDeadLineDate(),
                taskEntity.getDoneDateTime(),
                taskEntity.getPriority()
        );
    }

    public Task startTask(Long id) {
        var taskEntity = taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found task by id=" + id));
        if (taskEntity.getStatus() != TaskStatus.CREATED){
            throw new IllegalArgumentException("Task status should be 'CREATE'");
        }
        if (taskRepository.findByAssignedUserIdAndStatus_INPPROGRESS(taskEntity.getAssignedUserId()).size() > 4){
            throw new IllegalArgumentException("User have 5 or more tasks");
        }
        taskEntity.setStatus(TaskStatus.IN_PROGRESS);
        taskRepository.save(taskEntity);
        return toTask(taskEntity);
    }

    public Task compliteTask(Long id) {
        var taskEntity = taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found task by id=" + id));
        if (taskEntity.getStatus() != TaskStatus.IN_PROGRESS){
            throw new IllegalStateException("Can complete only IN_PROGRESS task");
        }
        taskEntity.setStatus(TaskStatus.DONE);
        taskEntity.setDoneDateTime(LocalDateTime.now());
        taskRepository.save(taskEntity);
        return toTask(taskEntity);
    }
}
