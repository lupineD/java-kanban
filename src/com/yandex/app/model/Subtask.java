package com.yandex.app.model;

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
}
