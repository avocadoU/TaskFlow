package ru.gorodnichev_ilya.TaskFlow.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gorodnichev_ilya.TaskFlow.entity.Report;
import ru.gorodnichev_ilya.TaskFlow.entity.ReportStatus;
import ru.gorodnichev_ilya.TaskFlow.service.ReportService;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createReport() {
        Long reportId = reportService.createReport();
        reportService.generateReportAsync(reportId);
        return ResponseEntity.ok(reportId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReport(@PathVariable Long id) {
        Report report = reportService.getReport(id);
        if (report == null) {
            return ResponseEntity.notFound().build();
        }
        if (report.getStatus() == ReportStatus.CREATED) {
            return ResponseEntity.ok("Отчет еще формируется...");
        }
        if (report.getStatus() == ReportStatus.ERROR) {
            return ResponseEntity.internalServerError().body("Ошибка при формировании: " + report.getContent());
        }
        return ResponseEntity.ok()
                .header("Content-Type", "text/html; charset=UTF-8")
                .body(report.getContent());
    }
}