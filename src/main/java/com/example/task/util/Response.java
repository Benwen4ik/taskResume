package com.example.task.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Класс для отправки ответов
 */
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Response {

    private String message;

    private long timestamp;
}