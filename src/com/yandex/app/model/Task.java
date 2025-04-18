package com.yandex.app.model;
import java.util.Objects;

// Базовый класс для задачи
public class Task {
    private int id;
    private String name;
    private String description;
    private Status status;

    public Task(String name, String description, Status status) {
        this.id = 0;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public enum Status {
        NEW,
        IN_PROGRESS,
        DONE
    }

    // Геттеры
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Status getStatus() { return status; }

    // Сеттеры
    public void setId(int id) { this.id = id; }
    public void setStatus(Status status) { this.status = status; }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}