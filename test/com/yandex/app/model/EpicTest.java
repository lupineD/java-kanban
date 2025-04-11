package com.yandex.app.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.yandex.app.service.TaskManager;
import com.yandex.app.service.InMemoryTaskManager;

class EpicTest {

    //проверьте, что наследники класса Task равны друг другу, если равен их id;
    @Test
    void epicsShouldBeEqualIfIdsAreEqual() {
        Epic epic1 = new Epic("Epic 1", "Desc 1", Task.Status.NEW);
        epic1.setId(3);

        Epic epic2 = new Epic("Epic 2", "Desc 2", Task.Status.IN_PROGRESS);
        epic2.setId(3);

        assertEquals(epic1, epic2, "Эпики с одинаковым id должны быть равны");
    }

    //проверьте, что объект Epic нельзя добавить в самого себя в виде подзадачи;
    @Test
    void epicCannotBeAddedAsItsOwnSubtask() {
        TaskManager manager = new InMemoryTaskManager();
        Epic epic = new Epic("Epic", "Description", Task.Status.NEW);
        int epicId = manager.addEpic(epic);

        Subtask subtask = new Subtask("Invalid subtask", "Trying to add epic to itself",
                Task.Status.NEW, epicId);
        subtask.setId(epicId); // Устанавливаем тот же ID, что и у эпика

        assertThrows(IllegalArgumentException.class, () -> {
            manager.addSubtask(subtask);
        }, "Попытка добавить эпик в самого себя должна вызывать исключение");
    }
}

