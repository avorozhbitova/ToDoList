package main.toDoList.service;

import main.toDoList.model.Task;

import java.util.Optional;

public interface TaskService {
    Task save(Task task);

    Optional<Task> findById(int id);

    Iterable<Task> findAll();

    void deleteById(int id);

    void deleteAll();

    long count();
}
