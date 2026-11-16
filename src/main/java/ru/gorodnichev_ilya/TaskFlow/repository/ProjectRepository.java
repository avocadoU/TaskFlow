package ru.gorodnichev_ilya.TaskFlow.repository;

import org.springframework.data.repository.CrudRepository;
import ru.gorodnichev_ilya.TaskFlow.entity.Project;

public interface ProjectRepository extends CrudRepository<Project, Long> {
}
