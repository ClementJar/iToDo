package com.jars.itodolist.service;

import com.example.model.TodoItem;
import com.jars.itodolist.interfaces.IToDoListService;
import com.jars.itodolist.interfaces.IUserService;
import com.jars.itodolist.mapper.ToDoItemMapper;
import com.jars.itodolist.mapper.ToDoItemMapperImpl;
import com.jars.itodolist.model.SecUserEntity;
import com.jars.itodolist.model.TodoItemEntity;
import com.jars.itodolist.repository.TodoItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ToDoService implements IToDoListService {
    private final TodoItemRepository todoItemRepository;
    private final ToDoItemMapper toDoItemMapper;
    private final IUserService iUserService;

    public ToDoService(ToDoItemMapperImpl toDoItemMapper,
                       TodoItemRepository todoItemRepository, IUserService iUserService) {
        this.toDoItemMapper = toDoItemMapper;
        this.todoItemRepository = todoItemRepository;
        this.iUserService = iUserService;
    }

    public TodoItemEntity addToDo(TodoItem todoItem) {
        SecUserEntity user = iUserService.getUserByUserNameOrEmail(todoItem.getUser());
        TodoItemEntity mappedEntity = toDoItemMapper.toToDoItemEntity(todoItem);
        mappedEntity.setUserId(user.getId());
        mappedEntity.setCreatedDate(LocalDateTime.now().toLocalDate());
        mappedEntity.setModifiedDate(LocalDateTime.now().toLocalDate());
        return todoItemRepository.save(mappedEntity);

    }

    public TodoItemEntity updateToDo(TodoItem todoItem) {
        TodoItemEntity todoItemEntity = getToDoItem(todoItem.getId());
        todoItemEntity.setItemDescription(todoItem.getItemDescription());
        todoItemEntity.setStatus(todoItem.getStatus());
        todoItemEntity.setModifiedDate(LocalDateTime.now().toLocalDate());

        return todoItemRepository.save(todoItemEntity);
    }

    public TodoItemEntity getToDoItem(int id) {
        return todoItemRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<TodoItemEntity> getAllToDoItem(String userName) {
        SecUserEntity user = iUserService.getUserByUserNameOrEmail(userName);
        return todoItemRepository.findAllByUserId(user.getId());
    }

    public void deleteTodo(int id) {
        todoItemRepository.delete(getToDoItem(id));
    }
}
