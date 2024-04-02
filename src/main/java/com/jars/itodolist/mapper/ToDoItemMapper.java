package com.jars.itodolist.mapper;

import com.example.model.TodoItem;
import com.jars.itodolist.model.TodoItemEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ToDoItemMapper {
    TodoItem toToDoItem(TodoItemEntity todoItemEntity);
    TodoItemEntity toToDoItemEntity(TodoItem todoItem);
}
