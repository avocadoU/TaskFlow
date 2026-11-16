package ru.gorodnichev_ilya.TaskFlow.console;

import org.springframework.stereotype.Component;
import ru.gorodnichev_ilya.TaskFlow.entity.Task;
import ru.gorodnichev_ilya.TaskFlow.service.TaskService;

@Component
public class CommandProcessor {

    private final TaskService taskService;

    public CommandProcessor(TaskService taskService) {
        this.taskService = taskService;
    }

    public void processCommand(String input) {
        String[] cmd = input.trim().split("\\s+");

        switch (cmd[0]) {
            case "create" -> {
                Task task = new Task(Long.valueOf(cmd[1]), cmd[2], "", "TODO", 1);
                taskService.createTask(task);
                System.out.println("Задача создана");
            }
            case "list" -> taskService.getAllTasks().forEach(System.out::println);
            case "delete" -> {
                taskService.deleteTask(Long.valueOf(cmd[1]));
                System.out.println("Удалено");
            }
            default -> System.out.println("Неизвестная команда");
        }
    }
}
