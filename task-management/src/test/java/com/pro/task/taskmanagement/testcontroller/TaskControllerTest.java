package com.pro.task.taskmanagement.testcontroller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.pro.task.taskmanagement.controller.TaskController;
import com.pro.task.taskmanagement.model.Task;
import com.pro.task.taskmanagement.service.TaskServiceIMPL;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

	@Mock
	TaskServiceIMPL taskService;

	@Mock
	Task task;

	@Mock
	BindingResult bindingResult;

	@InjectMocks
	TaskController taskController;

	@Mock
	List<Task> list;

	@Test
	public void test_createTask() {
		Mockito.when(taskService.createTask(Mockito.any())).thenReturn(task);
		ResponseEntity<Task> createTask = taskController.createTask(task, bindingResult);
		assertEquals(HttpStatus.CREATED, createTask.getStatusCode());
	}

	@Test
	public void testGetTaskByID() {
		Mockito.when(taskService.getTaskById(Mockito.anyInt())).thenReturn(task);
		ResponseEntity<Task> getTask = taskController.getTaskById(1);
		assertEquals(HttpStatus.OK, getTask.getStatusCode());
	}

	@Test
	public void testUpdateTask() {
		Mockito.when(taskService.updateTask(Mockito.anyInt(), Mockito.any())).thenReturn(task);
		ResponseEntity<Task> updatetask = taskController.updatetask(1, task, bindingResult);
		assertEquals(HttpStatus.OK, updatetask.getStatusCode());

	}

	@Test
	public void testGetAllTask() {
		list = new ArrayList<>();
		list.add(task);
		Mockito.when(taskService.getAllTask()).thenReturn(list);
		List<Task> allTask = taskController.getAllTask();
		assertEquals(task, allTask.get(0));

	}

	@Test
	public void testDeleteTask() {
		Mockito.doNothing().when(taskService).deleteTask(Mockito.anyInt());
		taskController.deleteTask(1);
		verify(taskService, times(1)).deleteTask(1);
	}
}
