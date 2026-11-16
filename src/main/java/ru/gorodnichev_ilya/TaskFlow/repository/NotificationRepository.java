package ru.gorodnichev_ilya.TaskFlow.repository;

import org.springframework.data.repository.CrudRepository;
import ru.gorodnichev_ilya.TaskFlow.entity.Notification;

public interface NotificationRepository extends CrudRepository<Notification, Long> {
}
