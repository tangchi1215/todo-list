package com.paisley.todolist.web.todo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoCreateResponse {
    private String id;
    private String title;
    private String description;
    private String completed;
    private String createDate;
    private String updateDate;
}
