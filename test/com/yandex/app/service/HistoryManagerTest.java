package com.yandex.app.service;
import com.yandex.app.model.Task;
import com.yandex.app.model.Task.Status;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class HistoryManagerTest {
    @Test
    void shouldPreservePreviousTaskVersionsInHistory() {
        HistoryManager manager = new InMemoryHistoryManager();

        // Создаем и добавляем первоначальную версию задачи
        Task originalTask = new Task("Original", "Desc", Status.NEW);
        originalTask.setId(1);
        manager.add(originalTask);

        // Модифицируем задачу
        Task modifiedTask = new Task("Modified", "New Desc", Status.IN_PROGRESS);
        modifiedTask.setId(1); // Тот же ID

        // Добавляем измененную версию
        manager.add(modifiedTask);

        // Получаем историю
        List<Task> history = manager.getHistory();

        // Проверяем что в истории две разные версии
        assertEquals(2, history.size(), "История должна содержать обе версии");

        // Проверяем что первая версия сохранила исходные данные
        Task firstVersion = history.get(0);
        assertEquals("Original", firstVersion.getName());
        assertEquals("Desc", firstVersion.getDescription());
        assertEquals(Status.NEW, firstVersion.getStatus());

        // Проверяем что вторая версия содержит новые данные
        Task secondVersion = history.get(1);
        assertEquals("Modified", secondVersion.getName());
        assertEquals("New Desc", secondVersion.getDescription());
        assertEquals(Status.IN_PROGRESS, secondVersion.getStatus());

        // Проверяем что это разные объекты в памяти
        assertNotSame(firstVersion, secondVersion);
    }
}