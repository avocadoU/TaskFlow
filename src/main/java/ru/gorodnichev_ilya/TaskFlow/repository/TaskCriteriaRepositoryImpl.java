package ru.gorodnichev_ilya.TaskFlow.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import ru.gorodnichev_ilya.TaskFlow.entity.Task;

import java.util.List;

public class TaskCriteriaRepositoryImpl implements TaskCriteriaRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Task> findTasksByStatusAndPriority(String status, Integer priority) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Task> cq = cb.createQuery(Task.class);

        Root<Task> root = cq.from(Task.class);

        cq.select(root).where(
                cb.and(
                        cb.equal(root.get("status"), status),
                        cb.equal(root.get("priority"), priority)
                )
        );

        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public List<Task> findTasksByAssigneeFullName(String fullName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Task> cq = cb.createQuery(Task.class);
        Root<Task> root = cq.from(Task.class);

        cq.select(root).where(
                cb.equal(root.get("assignee").get("fullName"), fullName)
        );

        return entityManager.createQuery(cq).getResultList();
    }
}
