package ru.gorodnichev_ilya.TaskFlow.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.gorodnichev_ilya.TaskFlow.entity.Notification;

@RepositoryRestResource(path = "notifications")
public interface NotificationRepository extends CrudRepository<Notification, Long> {
}
