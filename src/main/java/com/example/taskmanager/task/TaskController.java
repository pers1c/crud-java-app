package com.example.taskmanager.task;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public Task getTaskById(
            @PathVariable Long id
    ) {
        log.info("Called getTaskById with id={}", id);
        return taskService.getTaskById(id);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTask() {
        log.info("Called getAllTask");
        return ResponseEntity.ok().body(taskService.findAllTask());

    }

    @PostMapping()
    public ResponseEntity<Task> createTask(
            @RequestBody @Valid Task taskToCreate
    ) {
        log.info("Called createTask with Task={}", taskToCreate );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskService.createTask(taskToCreate));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long id,
            @RequestBody @Valid Task taskToUpdate
    ) {
        log.info("Called updateTask with id={} and Task={}", id, taskToUpdate);
        Task updated = taskService.updateTask(id, taskToUpdate);
        return ResponseEntity.ok().body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Task> deleteTask(
            @PathVariable Long id) {
        log.info("Called deleteTask with id={}", id);
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/{id}/start")
    public ResponseEntity<Task> startTask(
            @PathVariable Long id
    ){
        log.info("Called startTask with id={}", id);
        return ResponseEntity.ok().body(taskService.startTask(id));
    }
    @PostMapping("/{id}/complite")
    public ResponseEntity<Task> compliteTask(
                @PathVariable Long id
            ){
        log.info("Called copmliteTask with id={}", id);
        return ResponseEntity.ok().body(taskService.compliteTask(id));
    }
}
