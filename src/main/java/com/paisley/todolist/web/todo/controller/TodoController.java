package com.paisley.todolist.web.todo.controller;

import com.paisley.todolist.web.todo.domain.TodoCreateRequest;
import com.paisley.todolist.web.todo.domain.TodoCreateResponse;
import com.paisley.todolist.web.todo.domain.TodoUpdateRequest;
import com.paisley.todolist.web.todo.domain.TodoUpdateResponse;
import com.paisley.todolist.web.todo.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    @GetMapping
    public List<TodoCreateResponse> getAllTodos() {
        return todoService.getAllTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoCreateResponse> getTodoById(@PathVariable Long id) {
        return todoService.getTodoById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public TodoCreateResponse createTodo(@Valid @RequestBody TodoCreateRequest request) {
        return todoService.createTodo(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoUpdateResponse> updateTodo(@PathVariable Long id, @Valid @RequestBody TodoUpdateRequest request) {
        return todoService.updateTodo(id, request)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodoById(id);
        return ResponseEntity.noContent().build();
    }

}
