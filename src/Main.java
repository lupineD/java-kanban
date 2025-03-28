public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();

        // Добавляем задачи
        int task1Id = manager.addTask(new Task("Пробежка", "Пробежать 5 км", Task.Status.NEW));
        System.out.println("Создана задача: " + manager.getTask(task1Id));
        int task2Id = manager.addTask(new Task("Купить абонемент в зал", "Выбрать зал рядом с домом", Task.Status.NEW));
        System.out.println("Создана задача: " + manager.getTask(task2Id));

        // Добавляем эпик с подзадачами
        int epic1Id = manager.addEpic(new Epic("Планирование поездки", "Организовать путешествие", Task.Status.NEW));
        int subtask1Id = manager.addSubtask(new Subtask("Купить билеты", "Найти дешевые авиабилеты", Task.Status.NEW, epic1Id));
        int subtask2Id = manager.addSubtask(new Subtask("Забронировать отель", "Отель в центре", Task.Status.NEW, epic1Id));

        // Выводим все задачи
        System.out.println("=== Все задачи ===");
        System.out.println("Обычные задачи: " + manager.getAllTasks());
        System.out.println("Эпики: " + manager.getAllEpics());
        System.out.println("Подзадачи: " + manager.getAllSubtasks());

        // Меняем статус подзадачи и проверяем эпик
        Subtask subtask1 = manager.getSubtask(subtask1Id);
        subtask1.setStatus(Task.Status.DONE);
        manager.updateEpicStatus(epic1Id);  // Обновляем статус эпика

        System.out.println("\n=== После изменения статуса подзадачи ===");
        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllEpics());

        // Удаляем задачу и эпик
        manager.deleteTask(task1Id);
        manager.deleteEpic(epic1Id);

        System.out.println("\n=== После удаления ===");
        System.out.println("Оставшиеся задачи: " + manager.getAllTasks());
        System.out.println("Оставшиеся эпики: " + manager.getAllEpics());
    }
}
