package ru.gorodnichev_ilya.TaskFlow.repository;

import org.springframework.stereotype.Repository;
import ru.gorodnichev_ilya.TaskFlow.entity.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepository implements CrudRepository<Task, Long> {
    private final List<Task> tasks = new ArrayList<>();

    @Override
    public void save(Task task) {
        tasks.add(task);
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(tasks);
    }

    @Override
    public Optional<Task> findById(Long id) {
        return tasks.stream()
                .filter(t -> t.getId() != null && t.getId().equals(id))
                .findFirst();
    }

    @Override
    public void deleteById(Long id) {
        tasks.removeIf(t -> t.getId() != null && t.getId().equals(id));
    }
}
