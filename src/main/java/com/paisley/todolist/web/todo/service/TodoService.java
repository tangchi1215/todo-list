package com.paisley.todolist.web.todo.service;

import com.paisley.todolist.dao.model.Todo;
import com.paisley.todolist.util.DateUtil;
import com.paisley.todolist.web.todo.domain.TodoCreateRequest;
import com.paisley.todolist.web.todo.domain.TodoCreateResponse;
import com.paisley.todolist.web.todo.domain.TodoUpdateRequest;
import com.paisley.todolist.web.todo.domain.TodoUpdateResponse;
import com.paisley.todolist.web.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    // 取得所有待辦事項
    public List<TodoCreateResponse> getAllTodos() {
        return todoRepository.findAll().stream()
                .map(this::convertToCreateResponse)
                .toList();
    }

    // 取得單一待辦事項
    public Optional<TodoCreateResponse> getTodoById(Long id) {
        return todoRepository.findById(id)
                .map(this::convertToCreateResponse);
    }

    // 新增待辦事項
    public TodoCreateResponse createTodo(TodoCreateRequest request) {
        Todo todo = Todo.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .completed(request.getCompleted() != null ? request.getCompleted() : "N") // ✅ 預設 'N'
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Todo savedTodo = todoRepository.save(todo);
        return convertToCreateResponse(savedTodo);
    }

    // 更新待辦事項
    public Optional<TodoUpdateResponse> updateTodo(Long id, TodoUpdateRequest request) {
        return todoRepository.findById(id).map(todo -> {
            todo.setTitle(request.getTitle());
            todo.setDescription(request.getDescription());
            todo.setCompleted(request.getCompleted() != null ? request.getCompleted() : todo.getCompleted()); // ✅ 不傳時保留原狀態
            todo.setUpdateDate(LocalDateTime.now());

            Todo updatedTodo = todoRepository.save(todo);
            return convertToUpdateResponse(updatedTodo);
        });
    }

    // 刪除待辦事項
    public void deleteTodoById(Long id) {
        todoRepository.deleteById(id);
    }


    private TodoCreateResponse convertToCreateResponse(Todo todo) {
        return TodoCreateResponse.builder()
                .id(String.valueOf(todo.getId()))
                .title(todo.getTitle())
                .description(todo.getDescription())
                .completed(todo.getCompleted())
                .createDate(DateUtil.format(todo.getCreateDate(), "yyyy-mm-dd HH:mm:ss"))
                .updateDate(DateUtil.format(todo.getUpdateDate(), "yyyy-mm-dd HH:mm:ss"))
                .build();
    }

    private TodoUpdateResponse convertToUpdateResponse(Todo todo) {
        return TodoUpdateResponse.builder()
                .id(String.valueOf(todo.getId()))
                .title(todo.getTitle())
                .description(todo.getDescription())
                .completed(todo.getCompleted())
                .updateDate(DateUtil.format(todo.getUpdateDate(), "yyyy-mm-dd HH:mm:ss"))
                .build();
    }
}
