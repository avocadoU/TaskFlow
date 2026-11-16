package ru.gorodnichev_ilya.TaskFlow.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gorodnichev_ilya.TaskFlow.entity.Project;
import ru.gorodnichev_ilya.TaskFlow.entity.Task;
import ru.gorodnichev_ilya.TaskFlow.repository.ProjectRepository;
import ru.gorodnichev_ilya.TaskFlow.repository.TaskRepository;

@Service
public class ProjectTaskService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    public ProjectTaskService(ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    @Transactional
    public void createProjectWithTask(Project project, Task task) {
        projectRepository.save(project);

        task.setProject(project);

        taskRepository.save(task);
    }
}