package ru.gorodnichev_ilya.TaskFlow.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @PostConstruct
    public void init() {
        System.out.println("=== Конфигурация приложения ===");
        System.out.println("Имя приложения: " + appName);
        System.out.println("Версия приложения: " + appVersion);
        System.out.println("===============================");
    }
}
