package ru.gorodnichev_ilya.TaskFlow.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.gorodnichev_ilya.TaskFlow.entity.Report;
import ru.gorodnichev_ilya.TaskFlow.entity.ReportStatus;
import ru.gorodnichev_ilya.TaskFlow.entity.Task;
import ru.gorodnichev_ilya.TaskFlow.repository.ReportRepository;
import ru.gorodnichev_ilya.TaskFlow.repository.TaskRepository;
import ru.gorodnichev_ilya.TaskFlow.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public ReportService(ReportRepository reportRepository, UserRepository userRepository, TaskRepository taskRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    public Long createReport() {
        Report report = new Report();
        report.setStatus(ReportStatus.CREATED);
        return reportRepository.save(report).getId();
    }

    public Report getReport(Long id) {
        return reportRepository.findById(id).orElse(null);
    }

    @Async
    public CompletableFuture<Void> generateReportAsync(Long reportId) {
        return CompletableFuture.runAsync(() -> {
            try {
                long totalStart = System.currentTimeMillis();

                AtomicLong usersCount = new AtomicLong(0);
                AtomicLong usersTime = new AtomicLong(0);

                List<Task> taskList = new ArrayList<>();
                AtomicLong tasksTime = new AtomicLong(0);

                Thread threadUsers = new Thread(() -> {
                    long start = System.currentTimeMillis();
                    usersCount.set(userRepository.count());
                    usersTime.set(System.currentTimeMillis() - start);
                });

                Thread threadTasks = new Thread(() -> {
                    long start = System.currentTimeMillis();
                    taskRepository.findAll().forEach(taskList::add);
                    tasksTime.set(System.currentTimeMillis() - start);
                });

                threadUsers.start();
                threadTasks.start();

                threadUsers.join();
                threadTasks.join();

                StringBuilder html = new StringBuilder();
                html.append("<html><head><title>Отчет</title></head><body>");
                html.append("<h1>Статистика приложения</h1>");
                html.append("<table border='1'>");
                html.append("<tr><th>Показатель</th><th>Значение</th><th>Время (мс)</th></tr>");
                html.append("<tr><td>Количество пользователей</td><td>").append(usersCount.get()).append("</td><td>").append(usersTime.get()).append("</td></tr>");
                html.append("<tr><td>Список задач</td><td>").append(taskList.size()).append("</td><td>").append(tasksTime.get()).append("</td></tr>");
                html.append("</table>");

                html.append("<h2>Список задач</h2><ul>");
                for (Task task : taskList) {
                    html.append("<li>").append(task.getTitle()).append("</li>");
                }
                html.append("</ul>");

                long totalTime = System.currentTimeMillis() - totalStart;
                html.append("<p>Общее время формирования: ").append(totalTime).append(" мс</p>");
                html.append("</body></html>");

                Report report = reportRepository.findById(reportId).orElseThrow();
                report.setContent(html.toString());
                report.setStatus(ReportStatus.COMPLETED);
                reportRepository.save(report);

            } catch (Exception e) {
                Report report = reportRepository.findById(reportId).orElseThrow();
                report.setStatus(ReportStatus.ERROR);
                report.setContent("Ошибка при формировании отчета: " + e.getMessage());
                reportRepository.save(report);
            }
        });
    }
}