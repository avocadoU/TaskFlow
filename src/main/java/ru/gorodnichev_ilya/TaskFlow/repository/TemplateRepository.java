package ru.gorodnichev_ilya.TaskFlow.repository;

import org.springframework.data.repository.CrudRepository;
import ru.gorodnichev_ilya.TaskFlow.entity.Template;

public interface TemplateRepository extends CrudRepository<Template, Long> {
}
