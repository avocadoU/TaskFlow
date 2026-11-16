package ru.gorodnichev_ilya.TaskFlow.controller;

import org.springframework.web.bind.annotation.*;
import ru.gorodnichev_ilya.TaskFlow.entity.Task;
import ru.gorodnichev_ilya.TaskFlow.repository.TaskCriteriaRepository;
import ru.gorodnichev_ilya.TaskFlow.repository.TaskRepository;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskRestController {

    private final TaskRepository taskRepository;
    private final TaskCriteriaRepository taskCriteriaRepository;

    public TaskRestController(TaskRepository taskRepository, TaskCriteriaRepository taskCriteriaRepository) {
        this.taskRepository = taskRepository;
        this.taskCriteriaRepository = taskCriteriaRepository;
    }

    @GetMapping("/search")
    public List<Task> searchTasks(@RequestParam String status, @RequestParam Integer priority) {
        return taskRepository.findByStatusAndPriority(status, priority);
    }

    @GetMapping("/by-assignee")
    public List<Task> getTasksByAssignee(@RequestParam String fullName) {
        return taskCriteriaRepository.findTasksByAssigneeFullName(fullName);
    }
}