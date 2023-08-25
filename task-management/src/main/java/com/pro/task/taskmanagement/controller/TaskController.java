package com.pro.task.taskmanagement.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pro.task.taskmanagement.model.Task;
import com.pro.task.taskmanagement.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

	@Autowired
	private final TaskService taskService;

	private TaskController(TaskService taskService) {
		super();
		this.taskService = taskService;
	}

	@PostMapping
	public ResponseEntity<Task> createTask(@RequestBody @Valid Task task, BindingResult bindingResult) {
		Task generatedTask = taskService.createTask(task);
		return new ResponseEntity<>(generatedTask, HttpStatus.CREATED);
	}

	@PutMapping("/{taskId}")
	public ResponseEntity<Task> updatetask(@PathVariable int taskId, @RequestBody @Valid Task updatedTask,
			BindingResult bindingResult) {
		Task task = taskService.updateTask(taskId, updatedTask);
		return ResponseEntity.ok(task);
	}

	@GetMapping
	public List<Task> getAllTask() {
		return taskService.getAllTask();
	}

	@GetMapping("/{taskId}")
	public ResponseEntity<Task> getTaskById(@PathVariable("taskId") int taskId) {

		Task getTask = taskService.getTaskById(taskId);
		return new ResponseEntity<>(getTask, HttpStatus.OK);
	}

	@DeleteMapping("/{taskId}")
	public void deleteTask(@PathVariable("taskId") int taskId) {
		taskService.deleteTask(taskId);
	}
}
