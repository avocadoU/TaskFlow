package ru.gorodnichev_ilya.TaskFlow.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.gorodnichev_ilya.TaskFlow.entity.User;
import ru.gorodnichev_ilya.TaskFlow.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setEmail("admin@example.com");
                admin.setFullName("Администратор");
                admin.setRole("ADMIN");
                userRepository.save(admin);
                System.out.println("Создан администратор: admin / admin123");
            }
            if (userRepository.findByUsername("user").isEmpty()) {
                User user = new User();
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("user123"));
                user.setEmail("user@example.com");
                user.setFullName("Обычный пользователь");
                user.setRole("USER");
                userRepository.save(user);
                System.out.println("Создан пользователь: user / user123");
            }
        };
    }
}