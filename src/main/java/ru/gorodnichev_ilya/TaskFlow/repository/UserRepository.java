package ru.gorodnichev_ilya.TaskFlow.repository;

import org.springframework.data.repository.CrudRepository;
import ru.gorodnichev_ilya.TaskFlow.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
}
