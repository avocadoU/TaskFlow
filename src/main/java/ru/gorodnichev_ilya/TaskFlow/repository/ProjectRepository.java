package ru.gorodnichev_ilya.TaskFlow.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.gorodnichev_ilya.TaskFlow.entity.Project;

@RepositoryRestResource(path = "projects")
public interface ProjectRepository extends CrudRepository<Project, Long> {
}
