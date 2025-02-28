package com.paisley.todolist.web.todo.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoCreateRequest {
    @NotBlank
    private String title;
    private String description;
    @NotBlank
    private String completed;
}
