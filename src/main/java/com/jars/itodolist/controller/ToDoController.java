package com.jars.itodolist.controller;


import com.example.api.TodolistApi;
import com.example.model.*;
import com.jars.itodolist.interfaces.IToDoListService;
import com.jars.itodolist.mapper.ToDoItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ToDoController implements TodolistApi {
    Logger logger = LoggerFactory.getLogger(ToDoController.class);
    private final IToDoListService iToDoListService;
    private final ToDoItemMapper toDoItemMapper;

    public ToDoController(IToDoListService iToDoListService,
                          ToDoItemMapper toDoItemMapper) {
        this.iToDoListService = iToDoListService;
        this.toDoItemMapper = toDoItemMapper;
    }
    @GetMapping("/toDoList")
    public ModelAndView toDoList(Authentication authentication) {
        ModelAndView mvn = new ModelAndView("toDoList");
        mvn.addObject("userName",authentication.getName());
        return mvn;
    }

    @Override
    @ResponseBody
    public ResponseEntity<UpdateTodoItem200Response> addTodoItem(TodoItem todoItem) {

        UpdateTodoItem200Response updateTodoItem200Response = new UpdateTodoItem200Response();
        try {
            updateTodoItem200Response.setId(iToDoListService.addToDo(todoItem).getId());
            return ResponseEntity.ok(updateTodoItem200Response);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    @ResponseBody
    public ResponseEntity<List<TodoItem>> getAllTodo() {
        try {
           String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            return ResponseEntity.ok( iToDoListService.getAllToDoItem(userName)
                    .stream().map(toDoItemMapper::toToDoItem).collect(Collectors.toList()));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @Override
    @ResponseBody
    public ResponseEntity<TodoItem> getTodoById(Integer id) {
        try {
            return ResponseEntity.ok(toDoItemMapper.toToDoItem(iToDoListService.getToDoItem(id)));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    @ResponseBody
    public ResponseEntity<UpdateTodoItem200Response> updateTodoItem(TodoItem todoItem) {
        UpdateTodoItem200Response updateTodoItem200Response = new UpdateTodoItem200Response();
        try {
            updateTodoItem200Response.setId(iToDoListService.updateToDo(todoItem).getId());
            return ResponseEntity.ok(updateTodoItem200Response);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<Void> deleteTodoItem(String id) {
        try {
            iToDoListService.deleteTodo(Integer.parseInt(id));
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
