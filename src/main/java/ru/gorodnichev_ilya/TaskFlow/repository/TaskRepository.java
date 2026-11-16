package ru.gorodnichev_ilya.TaskFlow.repository;

import ru.gorodnichev_ilya.TaskFlow.entity.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskRepository {
    private final List<Task> tasks = new ArrayList<>();

    public void save(Task task) {
        tasks.add(task);
    }

    public Task findById(Long id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElse(null); // или вернуть Optional.empty()
    }
}
