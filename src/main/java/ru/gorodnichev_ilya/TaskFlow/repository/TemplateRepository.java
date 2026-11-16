package ru.gorodnichev_ilya.TaskFlow.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.gorodnichev_ilya.TaskFlow.entity.Template;

@RepositoryRestResource(path = "templates")
public interface TemplateRepository extends CrudRepository<Template, Long> {
}
