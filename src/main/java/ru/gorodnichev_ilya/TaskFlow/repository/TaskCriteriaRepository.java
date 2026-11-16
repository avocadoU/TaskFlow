package ru.gorodnichev_ilya.TaskFlow.repository;

import ru.gorodnichev_ilya.TaskFlow.entity.Task;

import java.util.List;

public interface TaskCriteriaRepository {
    List<Task> findTasksByStatusAndPriority(String status, Integer priority);

    List<Task> findTasksByAssigneeFullName(String fullName);
}
