package com.yandex.app.service;

import com.yandex.app.model.Epic;
import com.yandex.app.model.Subtask;
import com.yandex.app.model.Task;

import java.util.*;

public class TaskManager {
    private int nextId = 1;  // Счетчик для генерации ID
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();

    // Добавление новой задачи
    public int addTask(Task task) {
        task.setId(nextId++);
        tasks.put(task.getId(), task);
        return task.getId();
    }

    // Добавление нового эпика
    public int addEpic(Epic epic) {
        epic.setId(nextId++);
        epics.put(epic.getId(), epic);
        return epic.getId();
    }

    // Добавление новой подзадачи (с привязкой к эпику)
    public int addSubtask(Subtask subtask) {
        Epic epic = epics.get(subtask.getEpicId());
        if (epic == null) return -1;

        subtask.setId(nextId++);
        subtasks.put(subtask.getId(), subtask);
        epic.addSubtaskId(subtask.getId()); // Добавляем только ID
        updateEpicStatus(epic.getId());
        return subtask.getId();
    }

    // Обновление статуса эпика
    public void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) return;

        boolean allNew = true;
        boolean allDone = true;
        boolean hasSubtasks = false;

        // Получаем подзадачи по их ID
        for (int subtaskId : epic.getSubtaskIds()) {
            Subtask subtask = subtasks.get(subtaskId);
            if (subtask == null) continue;

            hasSubtasks = true;
            if (subtask.getStatus() != Task.Status.NEW) allNew = false;
            if (subtask.getStatus() != Task.Status.DONE) allDone = false;
        }

        if (!hasSubtasks) {
            epic.setStatus(Task.Status.NEW);
        } else if (allDone) {
            epic.setStatus(Task.Status.DONE);
        } else if (allNew) {
            epic.setStatus(Task.Status.NEW);
        } else {
            epic.setStatus(Task.Status.IN_PROGRESS);
        }
    }

    // Удаление всех задач
    public void clearAll() {
        clearAllTasks();
        clearAllSubtasks(); // Сначала подзадачи, так как они зависят от эпиков
        clearAllEpics();
    }

    public void clearAllTasks() {
        tasks.clear();
    }
    public void clearAllSubtasks() {
        subtasks.clear();

        for (Epic epic : epics.values()) {
            epic.getSubtaskIds().clear();
            updateEpicStatus(epic.getId());
        }
    }
    public void clearAllEpics() {
        for (Epic epic : epics.values()) {
            for (int subtaskId : epic.getSubtaskIds()) {
                subtasks.remove(subtaskId);
            }
        }
        epics.clear();
    }


    // Удаление задачи по ID
    public boolean deleteTask(int id) {
        return tasks.remove(id) != null;
    }

    // Удаление эпика по ID (вместе с подзадачами)
    public boolean deleteEpic(int id) {
        Epic epic = epics.remove(id);
        if (epic == null) return false;

        // Удаляем все подзадачи этого эпика по их ID
        for (int subtaskId : epic.getSubtaskIds()) {
            subtasks.remove(subtaskId);
        }
        return true;
    }

    // Удаление подзадачи по ID
    public boolean deleteSubtask(int id) {
        Subtask subtask = subtasks.remove(id);
        if (subtask == null) return false;

        Epic epic = epics.get(subtask.getEpicId());
        if (epic != null) {
            epic.getSubtaskIds().remove(subtask);
            updateEpicStatus(epic.getId());
        }
        return true;
    }

    // Получение списка всех задач
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    // Получение списка всех эпиков
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    // Получение списка всех подзадач
    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    // Получение задачи по ID
    public Task getTask(int id) {
        Task task = tasks.get(id);
        if (task == null) {
            System.out.println("Задача с ID " + id + " не найдена");
        }
        return task;
    }

    // Получение эпика по ID
    public Epic getEpic(int id) {
        Epic epic = epics.get(id);
        if (epic == null) {
            System.out.println("Эпик с ID " + id + " не найден");
        }
        return epic;
    }

    public Subtask getSubtask(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask == null) {
            System.out.println("Подзадача с ID " + id + " не найдена");
        }
        return subtask;
    }

    // Получение подзадачи по ID
    public List<Subtask> getEpicSubtasks(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) return Collections.emptyList();

        List<Subtask> result = new ArrayList<>();
        for (int subtaskId : epic.getSubtaskIds()) {
            Subtask subtask = subtasks.get(subtaskId);
            if (subtask != null) {
                result.add(subtask);
            }
        }
        return result;
    }
}
