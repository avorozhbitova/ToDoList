package main.toDoList.controller;

import main.toDoList.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {
    private TaskService taskService;

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @Value("${greeting}")
    private String greeting;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("greeting", greeting);
        model.addAttribute("tasks", taskService.findAll());
        model.addAttribute("tasksCount", taskService.count());
        return "index";
    }
}
