package com.pro.task.taskmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pro.task.taskmanagement.exception.NotFoundException;
import com.pro.task.taskmanagement.model.Task;
import com.pro.task.taskmanagement.repository.TaskRepository;

@Service
public class TaskServiceIMPL implements TaskService {

	@Autowired
	private final TaskRepository taskRepository;

	private TaskServiceIMPL(TaskRepository taskRepository) {
		super();
		this.taskRepository = taskRepository;
	}

	@Override
	public Task createTask(Task task) {

		return taskRepository.save(task);
	}

	@Override
	public List<Task> getAllTask() {

		return taskRepository.findAll();
	}

	@Override
	public Task getTaskById(int taskId) {
		return taskRepository.findById(taskId).get();
	}

	@Override
	public Task updateTask(int taskId, Task updatedTask) {
		Optional<Task> existingTask = taskRepository.findById(taskId);
		if (existingTask.isPresent()) {
			Task task = existingTask.get();
			task.setTaskType(updatedTask.getTaskType());
			task.setTaskName(updatedTask.getTaskName());
			task.setTaskAssignedTo(updatedTask.getTaskAssignedTo());
			task.setUpdatedDate(updatedTask.getUpdatedDate());
			task.setUpdatedBy(updatedTask.getUpdatedBy());
			return taskRepository.save(task);
		} else {
			throw new NotFoundException("Task Not Fount With Id" + taskId);
		}
	}

	@Override
	public void deleteTask(int taskId) {
		taskRepository.deleteById(taskId);
	}

}
