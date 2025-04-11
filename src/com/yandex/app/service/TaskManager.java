package com.yandex.app.service;
import com.yandex.app.model.Epic;
import com.yandex.app.model.Subtask;
import com.yandex.app.model.Task;
import java.util.List;

public interface TaskManager {
    // Методы для Task
    int addTask(Task task);
    Task getTask(int id);
    List<Task> getAllTasks();
    boolean deleteTask(int id);
    void clearAllTasks();

    // Методы для Epic
    int addEpic(Epic epic);
    Epic getEpic(int id);
    List<Epic> getAllEpics();
    boolean deleteEpic(int id);
    void clearAllEpics();
    List<Subtask> getEpicSubtasks(int epicId);

    // Методы для Subtask
    int addSubtask(Subtask subtask);
    Subtask getSubtask(int id);
    List<Subtask> getAllSubtasks();
    boolean deleteSubtask(int id);
    void clearAllSubtasks();

    // Общие методы
    void clearAll();
    void updateEpicStatus(int epicId);

    List<Task> getHistory();
}
