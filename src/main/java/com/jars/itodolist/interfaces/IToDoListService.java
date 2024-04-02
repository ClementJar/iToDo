package com.jars.itodolist.interfaces;

import com.example.model.TodoItem;
import com.jars.itodolist.model.TodoItemEntity;

import java.util.List;

public interface IToDoListService {
    public List<TodoItemEntity> getAllToDoItem(String userName);
    public void deleteTodo(int id);
    public TodoItemEntity getToDoItem(int id);
    public TodoItemEntity addToDo(TodoItem todoItem);
    public TodoItemEntity updateToDo(TodoItem todoItem);

}
