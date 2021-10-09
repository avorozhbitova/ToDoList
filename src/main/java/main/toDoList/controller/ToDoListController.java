package main.toDoList.controller;

import main.toDoList.model.Task;
import main.toDoList.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ToDoListController {
    private TaskService taskService;

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public List<Task> list() {
        Iterable<Task> taskIterable = taskService.findAll();

        List<Task> tasks = new ArrayList<>();
        for (Task task : taskIterable) {
            tasks.add(task);
        }
        return tasks;
    }

    @PostMapping("/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public int add(Task task) {
        Task newTask = taskService.save(task);
        return newTask.getId();
    }

    @PutMapping("/tasks")
    public void finish() {
        for (Task task : taskService.findAll()) {
            Task updatedTask = task;
            updatedTask.setFinished(true);
            taskService.save(updatedTask);
        }
    }

    @DeleteMapping("/tasks")
    public void delete() {
        taskService.deleteAll();
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity getTask(@PathVariable int id) {
        if (taskService.findById(id).isPresent()) {
            return new ResponseEntity(taskService.findById(id), HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PostMapping("/tasks/{id}")
    public ResponseEntity postTask() {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(null);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity updateTask(@PathVariable int id, Task task) {
        Optional<Task> updatedTask = taskService.findById(id);
        ResponseEntity responseEntity;
        if (updatedTask.isPresent()) {
            responseEntity = new ResponseEntity(task, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity(task, HttpStatus.CREATED);
        }
        taskService.save(task);
        return responseEntity;
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity deleteTask(@PathVariable int id) {
        if (taskService.findById(id).isPresent()) {
            taskService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return ResponseEntity.status(HttpStatus.GONE).body(null);
    }
}
