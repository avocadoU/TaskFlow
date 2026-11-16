package ru.gorodnichev_ilya.TaskFlow.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.gorodnichev_ilya.TaskFlow.entity.Task;

import java.util.List;

@RepositoryRestResource(path = "tasks")
public interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> findByStatusAndPriority(String status, Integer priority);

    List<Task> findByPriorityBetween(Integer min, Integer max);

    @Query("SELECT t FROM Task t WHERE t.assignee.fullName = :fullName")
    List<Task> findTasksByAssigneeFullName(@Param("fullName") String fullName);

}
