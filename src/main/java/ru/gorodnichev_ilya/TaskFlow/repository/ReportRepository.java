package ru.gorodnichev_ilya.TaskFlow.repository;

import org.springframework.data.repository.CrudRepository;
import ru.gorodnichev_ilya.TaskFlow.entity.Report;

public interface ReportRepository extends CrudRepository<Report, Long> {
}