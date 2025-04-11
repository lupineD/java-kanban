package com.yandex.app;

import com.yandex.app.service.TaskManager;
import com.yandex.app.service.InMemoryTaskManager;
import com.yandex.app.service.Managers;
import com.yandex.app.model.Task;
import com.yandex.app.model.Subtask;
import com.yandex.app.model.Epic;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();

        // Добавление тестовых данных
        int taskId = manager.addTask(new Task("Task 1", "задача 1", Task.Status.NEW));
        int epicId = manager.addEpic(new Epic("Epic 1", "Epic эпик", Task.Status.NEW));
        int subtaskId = manager.addSubtask(new Subtask("Subtask 1", "подзадача", Task.Status.NEW, epicId));

        // Просмотр задач для наполнения истории
        manager.getTask(taskId);
        manager.getEpic(epicId);
        manager.getSubtask(subtaskId);

        // Вывод всех задач
        printAllTasks(manager);
    }

    private static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        manager.getAllTasks().forEach(System.out::println);

        System.out.println("\nЭпики:");
        manager.getAllEpics().forEach(epic -> {
            System.out.println(epic);
            manager.getEpicSubtasks(epic.getId()).forEach(subtask ->
                    System.out.println("--> " + subtask));
        });

        System.out.println("\nПодзадачи:");
        manager.getAllSubtasks().forEach(System.out::println);

        System.out.println("\nИстория:");
        manager.getHistory().forEach(System.out::println);
    }
}


