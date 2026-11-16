package ru.gorodnichev_ilya.TaskFlow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.gorodnichev_ilya.TaskFlow.entity.Project;
import ru.gorodnichev_ilya.TaskFlow.entity.Task;
import ru.gorodnichev_ilya.TaskFlow.entity.User;
import ru.gorodnichev_ilya.TaskFlow.repository.ProjectRepository;
import ru.gorodnichev_ilya.TaskFlow.repository.TaskRepository;
import ru.gorodnichev_ilya.TaskFlow.repository.TaskCriteriaRepository;
import ru.gorodnichev_ilya.TaskFlow.repository.UserRepository;
import ru.gorodnichev_ilya.TaskFlow.service.ProjectTaskService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TaskFlowTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskCriteriaRepository taskCriteriaRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectTaskService projectTaskService;

    private User testUser;
    private Project testProject;
    private Task testTask;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setFullName("Иван Иванов");
        testUser.setEmail("ivan@example.com");
        testUser.setRole("USER");
        userRepository.save(testUser);

        testProject = new Project("Тестовый проект", "Описание", "ACTIVE");
        projectRepository.save(testProject);

        testTask = new Task(null, "Тестовая задача", "Описание", "TODO", 1);
        testTask.setProject(testProject);
        testTask.setAssignee(testUser);
        taskRepository.save(testTask);
    }

    @Test
    void testFindByStatusAndPriority() {
        List<Task> found = taskRepository.findByStatusAndPriority("TODO", 1);
        assertFalse(found.isEmpty());
        assertEquals("Тестовая задача", found.get(0).getTitle());

        List<Task> notFound = taskRepository.findByStatusAndPriority("DONE", 1);
        assertTrue(notFound.isEmpty());
    }

    @Test
    void testFindTasksByAssigneeFullName() {
        List<Task> found = taskRepository.findTasksByAssigneeFullName("Иван Иванов");
        assertFalse(found.isEmpty());
        assertEquals(1, found.size());

        List<Task> notFound = taskRepository.findTasksByAssigneeFullName("Петр Петров");
        assertTrue(notFound.isEmpty());
    }


    @Test
    void testCriteriaFindTasksByStatusAndPriority() {
        List<Task> found = taskCriteriaRepository.findTasksByStatusAndPriority("TODO", 1);
        assertFalse(found.isEmpty());
        assertEquals("Тестовая задача", found.get(0).getTitle());
    }

    @Test
    void testCriteriaFindTasksByAssigneeFullName() {
        List<Task> found = taskCriteriaRepository.findTasksByAssigneeFullName("Иван Иванов");
        assertFalse(found.isEmpty());
        assertEquals(1, found.size());
    }

    @Test
    void testCreateProjectWithTask_Success() {
        Project newProject = new Project("Новый проект", "Описание", "ACTIVE");
        Task newTask = new Task(null, "Новая задача", "Описание", "TODO", 1);

        projectTaskService.createProjectWithTask(newProject, newTask);

        assertNotNull(newProject.getId());
        assertNotNull(newTask.getId());
        assertTrue(projectRepository.findById(newProject.getId()).isPresent());
        assertTrue(taskRepository.findById(newTask.getId()).isPresent());
    }

    @Test
    void testCreateProjectWithTask_Rollback() {
        Project newProject = new Project("Проект с ошибкой", "Описание", "ACTIVE");
        Task newTask = new Task(null, "ERROR", "Описание", "TODO", 1);

        assertThrows(RuntimeException.class, () -> {
            projectTaskService.createProjectWithTask(newProject, newTask);
        });

        assertNull(newProject.getId());
        assertTrue(projectRepository.findById(newProject.getId()).isEmpty());
    }
}