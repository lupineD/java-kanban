package com.yandex.app.service;
import com.yandex.app.model.Epic;
import com.yandex.app.model.Subtask;
import com.yandex.app.model.Task;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    private int nextId = 1;
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();
    private final HistoryManager historyManager;

    public InMemoryTaskManager() {
        this.historyManager = new InMemoryHistoryManager();
    }

    @Override
    public int addTask(Task task) {
        // Создаём копию задачи, не изменяя оригинал
        Task taskCopy = new Task(task.getName(), task.getDescription(), task.getStatus());
        int newId = nextId++;
        taskCopy.setId(newId);
        tasks.put(newId, taskCopy);
        return newId;
    }

    @Override
    public int addEpic(Epic epic) {
        epic.setId(nextId++);
        epics.put(epic.getId(), epic);
        return epic.getId();
    }

    @Override
    public int addSubtask(Subtask subtask) {
        // Проверка, что подзадача не ссылается на саму себя (если это Epic)
        if (subtask.getId() == subtask.getEpicId()) {
            throw new IllegalArgumentException("Epic cannot be a subtask of itself");
        }

        Epic epic = epics.get(subtask.getEpicId());
        if (epic == null) {
            return -1;
        }

        subtask.setId(nextId++);
        subtasks.put(subtask.getId(), subtask);
        epic.addSubtaskId(subtask.getId());
        updateEpicStatus(epic.getId());
        return subtask.getId();
    }

    @Override
    public void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) return;

        boolean allNew = true;
        boolean allDone = true;
        boolean hasSubtasks = false;

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

    // Остальные методы остаются без изменений, но добавляется аннотация @Override
    @Override
    public void clearAll() {
        clearAllTasks();
        clearAllSubtasks();
        clearAllEpics();
    }

    @Override
    public void clearAllTasks() {
        tasks.clear();
    }

    @Override
    public void clearAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.getSubtaskIds().clear();
            updateEpicStatus(epic.getId());
        }
    }

    @Override
    public void clearAllEpics() {
        subtasks.clear();
        epics.clear();
    }

    @Override
    public boolean deleteTask(int id) {
        return tasks.remove(id) != null;
    }

    @Override
    public boolean deleteEpic(int id) {
        Epic epic = epics.remove(id);
        if (epic == null) return false;

        for (int subtaskId : epic.getSubtaskIds()) {
            subtasks.remove(subtaskId);
        }
        return true;
    }

    @Override
    public boolean deleteSubtask(int id) {
        Subtask subtask = subtasks.remove(id);
        if (subtask == null) return false;

        Epic epic = epics.get(subtask.getEpicId());
        if (epic != null) {
            epic.getSubtaskIds().remove(Integer.valueOf(id));
            updateEpicStatus(epic.getId());
        }
        return true;
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public Task getTask(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic;
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            historyManager.add(subtask);
        }
        return subtask;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
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
