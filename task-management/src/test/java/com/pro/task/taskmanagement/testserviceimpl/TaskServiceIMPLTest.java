package com.pro.task.taskmanagement.testserviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pro.task.taskmanagement.exception.NotFoundException;
import com.pro.task.taskmanagement.model.Task;
import com.pro.task.taskmanagement.repository.TaskRepository;
import com.pro.task.taskmanagement.service.TaskServiceIMPL;

@ExtendWith(MockitoExtension.class)
public class TaskServiceIMPLTest {

	@Mock
	private TaskRepository taskRepository;

	@InjectMocks
	private TaskServiceIMPL taskServiceIMPL;

	@Test
	public void testCreateTask_Success() {
		doAnswer(invocation -> invocation.getArgument(0)).when(taskRepository).save(any(Task.class));
		Task taskToCreate = new Task();
		Task createdTask = taskServiceIMPL.createTask(taskToCreate);

		assertNotNull(createdTask);
		verify(taskRepository).save(taskToCreate);
	}

	@Test
	public void testGetAllTask_Success() {
		List<Task> tasks = new ArrayList();

		tasks.add(new Task());
		when(taskRepository.findAll()).thenReturn(tasks);

		List<Task> fetchedTasks = taskServiceIMPL.getAllTask();

		assertEquals(1, fetchedTasks.size());
		verify(taskRepository).findAll();
	}

	@Test
	public void testGetTaskById_Success() {
		Task task = new Task();
		when(taskRepository.findById(anyInt())).thenReturn(Optional.of(task));

		Task fetchedTask = taskServiceIMPL.getTaskById(1);

		assertNotNull(fetchedTask);
		verify(taskRepository).findById(1);
	}

	@Test
	public void testUpdateTask_Success() {
		Task existingTask = new Task();
		existingTask.setTaskType("Development");
		existingTask.setTaskName("Create RESTful Web Services");
		existingTask.setTaskAssignedTo("Kiran Ghogare");
		existingTask.setUpdatedDate(LocalDateTime.now());
		existingTask.setUpdatedBy("John");

		Task updatedTask = new Task();
		updatedTask.setTaskType("Testing");
		updatedTask.setTaskName("Test RestFul Web Services");
		updatedTask.setTaskAssignedTo("Rohan Pawar");
		updatedTask.setUpdatedDate(LocalDateTime.now());
		updatedTask.setUpdatedBy("Ibrahim");

		when(taskRepository.findById(anyInt())).thenReturn(Optional.of(existingTask));

		ArgumentCaptor<Task> savedTaskCaptor = ArgumentCaptor.forClass(Task.class);
		when(taskRepository.save(savedTaskCaptor.capture())).thenReturn(updatedTask);

		Task resultTask = taskServiceIMPL.updateTask(1, updatedTask);

		assertEquals("Testing", resultTask.getTaskType());
		assertEquals("Test RestFul Web Services", resultTask.getTaskName());
		assertEquals("Rohan Pawar", resultTask.getTaskAssignedTo());
		assertEquals("Ibrahim", resultTask.getUpdatedBy());

		Task capturedTask = savedTaskCaptor.getValue();
		assertEquals("Testing", capturedTask.getTaskType());
		assertEquals("Test RestFul Web Services", capturedTask.getTaskName());
		assertEquals("Rohan Pawar", capturedTask.getTaskAssignedTo());
		assertEquals("Ibrahim", capturedTask.getUpdatedBy());

		verify(taskRepository).findById(1);
		verify(taskRepository).save(any(Task.class));
	}

	@Test
	public void testUpdateTask_NotFound()
	{
		   when(taskRepository.findById(anyInt())).thenReturn(Optional.empty());

		    assertThrows(NotFoundException.class, () -> taskServiceIMPL.updateTask(1, new Task()));

		    verify(taskRepository).findById(1);
	}

	@Test
	public void testDeleteTask_Success() {
		taskServiceIMPL.deleteTask(1);

		verify(taskRepository).deleteById(1);
	}
}
