package com.paisley.todolist.web.todo.repository;

import com.paisley.todolist.dao.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TodoRepository extends JpaRepository<Todo, Long> {
}
