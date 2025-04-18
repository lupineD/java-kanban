package com.yandex.app.model;
import java.util.Objects;

// Подзадача (наследуется от Task, но относится к эпику)
public class Subtask extends Task {
    private int epicId;

    public Subtask(String name, String description, Task.Status status, int epicId) {
        super(name, description, status);
        this.epicId = epicId;
    }

    public int getEpicId() { return epicId; }

    @Override
    public String toString() {
        return "Subtask{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", epicId=" + epicId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o); // Сравниваем по id как в Task
    }

    @Override
    public int hashCode() {
        return super.hashCode(); // Используем реализацию из Task
    }
}
