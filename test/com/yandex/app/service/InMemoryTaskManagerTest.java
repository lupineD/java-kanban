package com.yandex.app.service;

import com.yandex.app.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    //проверьте, что InMemoryTaskManager действительно добавляет задачи разного типа и может найти их по id;
    @Test
    void shouldAddAndFindTaskById() {
        TaskManager manager = new InMemoryTaskManager();
        Task task = new Task("Task", "Description", Task.Status.NEW);
        int taskId = manager.addTask(task);

        Task foundTask = manager.getTask(taskId);

        assertNotNull(foundTask, "Задача должна находиться по ID");
        assertEquals(taskId, foundTask.getId(), "ID задачи должен совпадать");
        assertEquals("Task", foundTask.getName(), "Название задачи должно сохраняться");
    }

    //проверьте, что InMemoryTaskManager действительно добавляет задачи разного типа и может найти их по id;
    @Test
    void shouldAddAndFindEpicById() {
        TaskManager manager = new InMemoryTaskManager();
        Epic epic = new Epic("Epic", "Description", Task.Status.NEW);
        int epicId = manager.addEpic(epic);

        Epic foundEpic = manager.getEpic(epicId);

        assertNotNull(foundEpic, "Эпик должен находиться по ID");
        assertEquals(epicId, foundEpic.getId(), "ID эпика должен совпадать");
        assertTrue(foundEpic.getSubtaskIds().isEmpty(), "Новый эпик должен иметь пустой список подзадач");
    }

    //проверьте, что InMemoryTaskManager действительно добавляет задачи разного типа и может найти их по id;
    @Test
    void shouldAddAndFindSubtaskById() {
        TaskManager manager = new InMemoryTaskManager();
        Epic epic = new Epic("Epic", "Description", Task.Status.NEW);
        int epicId = manager.addEpic(epic);

        Subtask subtask = new Subtask("Subtask", "Description", Task.Status.NEW, epicId);
        int subtaskId = manager.addSubtask(subtask);

        Subtask foundSubtask = manager.getSubtask(subtaskId);

        assertNotNull(foundSubtask, "Подзадача должна находиться по ID");
        assertEquals(subtaskId, foundSubtask.getId(), "ID подзадачи должен совпадать");
        assertEquals(epicId, foundSubtask.getEpicId(), "ID эпика должен сохраняться");
    }

}