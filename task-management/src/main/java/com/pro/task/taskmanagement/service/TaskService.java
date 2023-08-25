package com.pro.task.taskmanagement.service;

import java.util.List;

import com.pro.task.taskmanagement.model.Task;

public interface TaskService {

	List<Task> getAllTask();

	Task getTaskById(int taskId);

	Task createTask(Task task);

	Task updateTask(int taskId, Task updatedTask);

	void deleteTask(int taskId);

}
