package com.pro.task.taskmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pro.task.taskmanagement.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

}
