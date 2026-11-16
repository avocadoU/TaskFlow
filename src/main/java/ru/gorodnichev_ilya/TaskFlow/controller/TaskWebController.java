package ru.gorodnichev_ilya.TaskFlow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gorodnichev_ilya.TaskFlow.repository.TaskRepository;

@Controller
@RequestMapping("/view")
public class TaskWebController {

    private final TaskRepository taskRepository;

    public TaskWebController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/tasks")
    public String taskListView(Model model) {
        model.addAttribute("tasks", taskRepository.findAll());
        return "tasks";
    }
}