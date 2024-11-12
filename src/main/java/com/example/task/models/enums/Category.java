package com.example.task.models.enums;

public enum Category {

    FirstPriority("Первоочередной кандидат"),
    SecondPriority("Кандидат второй очереди"),
    LastPriority("Кандидат крайней очередности"),
    Reject("Отказано в принятии");

    Category(String name) {
    }
}
