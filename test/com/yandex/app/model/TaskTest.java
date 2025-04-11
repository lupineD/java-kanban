package com.yandex.app.model;
import com.yandex.app.service.InMemoryTaskManager;
import com.yandex.app.service.TaskManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    //проверьте, что экземпляры класса Task равны друг другу, если равен их id;
    @Test
    void tasksShouldBeEqualIfIdsAreEqual() {
        Task task1 = new Task("Task 1", "Description 1", Task.Status.NEW);
        task1.setId(1);

        Task task2 = new Task("Task 2", "Description 2", Task.Status.IN_PROGRESS);
        task2.setId(1);

        assertEquals(task1, task2, "Задачи с одинаковым id должны быть равны");
    }

    //проверьте, что задачи с заданным id и сгенерированным id не конфликтуют внутри менеджера;
    @Test
    void generatedIdsShouldBeUnique() {
        TaskManager manager = new InMemoryTaskManager();

        // Добавляем несколько задач
        int id1 = manager.addTask(new Task("Task 1", "Desc", Task.Status.NEW));
        int id2 = manager.addTask(new Task("Task 2", "Desc", Task.Status.NEW));
        int id3 = manager.addTask(new Task("Task 3", "Desc", Task.Status.NEW));

        // Проверяем что все ID уникальны
        assertNotEquals(id1, id2, "Сгенерированные ID должны быть уникальными");
        assertNotEquals(id1, id3, "Сгенерированные ID должны быть уникальными");
        assertNotEquals(id2, id3, "Сгенерированные ID должны быть уникальными");
    }

    //создайте тест, в котором проверяется неизменность задачи (по всем полям) при добавлении задачи в менеджер
    @Test
    void taskShouldRemainUnchangedAfterAddingToManager() {

        TaskManager manager = new InMemoryTaskManager();
        Task originalTask = new Task("Original Task", "Original Description", Task.Status.NEW);

        // Запоминаем все исходные поля
        String originalName = originalTask.getName();
        String originalDescription = originalTask.getDescription();
        Task.Status originalStatus = originalTask.getStatus();
        int originalId = originalTask.getId(); // Должен быть 0 до добавления

        // Добавляем задачу в менеджер
        int newId = manager.addTask(originalTask);

        // Проверяем что исходный объект не изменился
        assertEquals(originalName, originalTask.getName(), "Название задачи не должно измениться");
        assertEquals(originalDescription, originalTask.getDescription(),
                "Описание задачи не должно измениться");
        assertEquals(originalStatus, originalTask.getStatus(),
                "Статус задачи не должно измениться");
        assertEquals(originalId, originalTask.getId(),
                "ID в исходном объекте не должен измениться");

        // Проверяем что в менеджере сохранена копия с правильными значениями
        Task managedTask = manager.getTask(newId);
        assertNotSame(originalTask, managedTask,
                "Менеджер должен хранить копию задачи, а не оригинальный объект");
        assertEquals(originalName, managedTask.getName(),
                "Название в менеджере должно соответствовать оригиналу");
        assertEquals(originalDescription, managedTask.getDescription(),
                "Описание в менеджере должно соответствовать оригиналу");
        assertEquals(originalStatus, managedTask.getStatus(),
                "Статус в менеджере должно соответствовать оригиналу");
    }

}