package com.yandex.app.service;
import com.yandex.app.model.Task;
import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, LinkedList<Task>> versionHistory = new HashMap<>();
    private final LinkedList<Task> accessOrder = new LinkedList<>();
    private static final int MAX_HISTORY_SIZE = 10;

    @Override
    public void add(Task task) {
        if (task == null) return;

        // Создаём копию
        Task taskCopy = deepCopy(task);
        int taskId = taskCopy.getId();

        // Сохраняем версию
        versionHistory.computeIfAbsent(taskId, k -> new LinkedList<>()).add(taskCopy);
        accessOrder.add(taskCopy);

        // Удаляем самую старую версию, если превышен лимит
        if (accessOrder.size() > MAX_HISTORY_SIZE) {
            Task oldest = accessOrder.removeFirst();
            LinkedList<Task> versions = versionHistory.get(oldest.getId());
            versions.removeFirst();
            if (versions.isEmpty()) {
                versionHistory.remove(oldest.getId());
            }
        }
    }

    private Task deepCopy(Task original) {
        Task copy = new Task(original.getName(), original.getDescription(), original.getStatus());
        copy.setId(original.getId());
        return copy;
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(accessOrder);
    }

    public List<Task> getTaskVersions(int taskId) {
        return new ArrayList<>(versionHistory.getOrDefault(taskId, new LinkedList<>()));
    }
}