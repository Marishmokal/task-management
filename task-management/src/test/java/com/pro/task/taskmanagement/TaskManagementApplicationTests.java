package com.pro.task.taskmanagement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import com.pro.task.taskmanagement.model.Task;
import com.pro.task.taskmanagement.repository.TaskRepository;

@SpringBootTest(classes = TaskManagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TaskManagementApplicationTests {

	@LocalServerPort
	private int port;

	@Mock
	Task task;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Mock
	TaskRepository taskRepository;

	public ResponseEntity<String> doRestCall(String url, MultiValueMap<String, String> queryParam,
			Map<String, String> pathParam, String body, MultiValueMap<String, String> header, HttpMethod method) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
		HttpEntity<String> entity = new HttpEntity<>(body, header);
		ResponseEntity<String> response = testRestTemplate.exchange(builder.buildAndExpand(pathParam).toUri(), method,
				entity, String.class);
		return response;
	}

	@Test
	public void testGetTaskById() {
		String url = "http://localhost:" + port + "/api/tasks/{taskId}";
		Map<String, String> pathVariable = new HashMap();
		pathVariable.put("taskId", "2");
		HttpEntity<String> entity = new HttpEntity<>(null, null);
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
		ResponseEntity<String> response = testRestTemplate.exchange(builder.buildAndExpand(pathVariable).toUri(),
				HttpMethod.GET, entity, String.class);

		System.out.println(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testCreateTask() {
		String url = "http://localhost:" + port + "/api/tasks";
		String body = "{\n" + "\"taskType\": \"Development\",\n" + "\"taskName\": \"Developing REST API\",\n"
				+ "\"taskAssignedTo\": \"Pranav ketagale\",\n" + "\"createdBy\": \"Alice\"\n" + "}";
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		List<String> al = new ArrayList<>();
		al.add("application/json");
		headers.put("Content-Type", al);
		Map<String, String> pathParam = new HashMap();
		ResponseEntity<String> response = doRestCall(url, null, pathParam, body, headers, HttpMethod.POST);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void contextLoads() {
	}

}
