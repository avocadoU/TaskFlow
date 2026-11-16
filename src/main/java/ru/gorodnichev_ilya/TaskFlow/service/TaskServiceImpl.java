package ru.gorodnichev_ilya.TaskFlow.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.gorodnichev_ilya.TaskFlow.entity.Task;
import ru.gorodnichev_ilya.TaskFlow.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void createTask(Task task) {
        if (task.getStatus() == null || task.getStatus().isBlank()) {
            task.setStatus("TODO");
        }
        taskRepository.save(task);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
