package ru.gorodnichev_ilya.TaskFlow.service;

import ru.gorodnichev_ilya.TaskFlow.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    void createTask(Task task);

    List<Task> getAllTasks();

    Optional<Task> getTaskById(Long id);

    void deleteTask(Long id);
}
