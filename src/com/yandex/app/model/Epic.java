package com.yandex.app.model;

import java.util.ArrayList;

//Эпик (наследуется от Task, но содержит подзадачи)
public class Epic extends Task {
    private ArrayList<Integer> subtaskIds = new ArrayList<>(); // ID подзадач

    public Epic(String name, String description, Status status) {
        super(name, description, status);
    }

    public ArrayList<Integer> getSubtaskIds() {
        return new ArrayList<>(subtaskIds); // Возвращаем копию для защиты данных
    }

    public void addSubtaskId(int subtaskId) {
        subtaskIds.add(subtaskId);
    }

    public void removeSubtaskId(int subtaskId) {
        subtaskIds.remove(Integer.valueOf(subtaskId));
    }
}
