package com.yandex.app.model;

import com.yandex.app.service.TaskManager;
import com.yandex.app.service.InMemoryTaskManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {

    //проверьте, что объект Subtask нельзя сделать своим же эпиком;
    @Test
    void subtaskCannotBeItsOwnEpic() {
        TaskManager manager = new InMemoryTaskManager();

        // Создаем подзадачу
        Subtask subtask = new Subtask("Subtask", "Description", Task.Status.NEW, 1);
        subtask.setId(1); // Устанавливаем тот же ID, что и epicId

        assertThrows(IllegalArgumentException.class, () -> {
            manager.addSubtask(subtask);
        }, "Подзадача не может быть своим же эпиком");
    }

    //проверьте, что наследники класса Task равны друг другу, если равен их id;
    @Test
    void subtasksShouldBeEqualIfIdsAreEqual() {
        Subtask subtask1 = new Subtask("Sub 1", "Desc 1", Task.Status.NEW, 1);
        subtask1.setId(2);

        Subtask subtask2 = new Subtask("Sub 2", "Desc 2", Task.Status.DONE, 1);
        subtask2.setId(2);

        assertEquals(subtask1, subtask2, "Подзадачи с одинаковым id должны быть равны");
    }
}