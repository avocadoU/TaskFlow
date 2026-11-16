package ru.gorodnichev_ilya.TaskFlow.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.gorodnichev_ilya.TaskFlow.entity.Project;
import ru.gorodnichev_ilya.TaskFlow.entity.Task;
import ru.gorodnichev_ilya.TaskFlow.repository.ProjectRepository;
import ru.gorodnichev_ilya.TaskFlow.repository.TaskRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProjectTaskServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private ProjectTaskService projectTaskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProjectWithTask_Success() {
        Project project = new Project("Новый проект", "Описание", "ACTIVE");
        Task task = new Task(null, "Новая задача", "Описание", "TODO", 1);

        when(projectRepository.save(any(Project.class))).thenReturn(project);
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        projectTaskService.createProjectWithTask(project, task);

        assertEquals(project, task.getProject());

        verify(projectRepository, times(1)).save(project);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void testCreateProjectWithTask_ThrowsException() {
        Project project = new Project("Проект с ошибкой", "Описание", "ACTIVE");
        Task task = new Task(null, "ERROR", "Описание", "TODO", 1);

        when(projectRepository.save(any(Project.class))).thenReturn(project);
        when(taskRepository.save(any(Task.class))).thenThrow(new RuntimeException("Ошибка БД"));

        assertThrows(RuntimeException.class, () -> {
            projectTaskService.createProjectWithTask(project, task);
        });

        verify(taskRepository, times(1)).save(task);
    }
}