package com.jars.itodolist.controller;


import com.example.api.TodolistApi;
import com.example.model.TodoItem;
import com.example.model.UpdateTodoItem200Response;
import com.jars.itodolist.interfaces.IToDoListService;
import com.jars.itodolist.mapper.ToDoItemMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestToDoController implements TodolistApi {

    @InjectMocks
    private ToDoController toDoController; // Inject the controller itself

    @Mock
    private IToDoListService iToDoListService;

    @Mock
    private ToDoItemMapper toDoItemMapper;


    @Test
    public void testGetAllTodo_Success() throws Exception {
        // Mock data
       List<TodoItem> mockToDoList = new ArrayList<>();
        TodoItem todo1 = new TodoItem();
        todo1.setId(1);
        todo1.setItemDescription("Task 1");
        todo1.setStatus("ACTIVE");

        TodoItem todo2 = new TodoItem();
        todo2.setId(1);
        todo2.setItemDescription("Task 1");
        todo2.setStatus("ACTIVE");
        mockToDoList.add(todo1);
        mockToDoList.add(todo2);

        // Mock service behavior
        Mockito.when(toDoItemMapper.toToDoItem(toDoItemMapper.toToDoItemEntity(Mockito.any(TodoItem.class)))).thenReturn(new TodoItem());

        // Call the controller method
        ResponseEntity<List<TodoItem>> response = toDoController.getAllTodo();

        // Assert response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<TodoItem> returnedList = response.getBody();
        assertNotNull(returnedList);
        assertEquals(2, returnedList.size());

    }

    @Test
    public void testGetAllTodo_Exception() throws Exception {
        // Mock service throws exception
       // Mockito.when(iToDoListService.getAllToDoItem()).thenThrow(new RuntimeException("Test Exception"));

        // Call the controller method
        ResponseEntity<List<TodoItem>> response = toDoController.getAllTodo();

        // Assert response status
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }
    private static final Logger logger = LoggerFactory.getLogger(ToDoController.class);


    @Test
    public void testAddTodoItem_Success() throws Exception {
        // Mock data
        TodoItem newTodoItem = new TodoItem();
        newTodoItem.setId(1);
        newTodoItem.setItemDescription("Task 1");
        newTodoItem.setStatus("ACTIVE");
        TodoItem savedToDoItem = new TodoItem();
        savedToDoItem.setId(1);
        savedToDoItem.setItemDescription("Task 1");
        savedToDoItem.setStatus("ACTIVE");

        // Mock service behavior
       /* Mockito.when(iToDoListService.addToDo(Mockito.any(TodoItem.class)))
                .thenReturn(toDoItemMapper.toToDoItemEntity(savedToDoItem));*/
        // Call the controller method
        ResponseEntity<UpdateTodoItem200Response> response = toDoController.addTodoItem(newTodoItem);

        // Assert response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        UpdateTodoItem200Response updateResponse = response.getBody();
        assertNotNull(updateResponse);
        assertEquals(Optional.of(1), updateResponse.getId());
    }

    @Test
    public void testAddTodoItem_Exception() throws Exception {
        // Mock data

        TodoItem newTodoItem = new TodoItem();
        newTodoItem.setId(1);
        newTodoItem.setItemDescription("Task 1");
        newTodoItem.setStatus("ACTIVE");

        // Mock service throws exception
        Mockito.when(iToDoListService.addToDo(Mockito.any(TodoItem.class))).thenThrow(new RuntimeException("Test Exception"));

        // Call the controller method
        ResponseEntity<UpdateTodoItem200Response> response = toDoController.addTodoItem(newTodoItem);

        // Assert response status
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testDeleteTodoItem_Success() throws Exception {
        // Mock service behavior
        Mockito.doNothing().when(iToDoListService).deleteTodo(1); // Assuming id is 1

        // Call the controller method
        ResponseEntity<Void> response = toDoController.deleteTodoItem("1");

        // Assert response status
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteTodoItem_Exception() throws Exception {
        // Mock service throws exception
        Mockito.doThrow(new RuntimeException("Test Exception")).when(iToDoListService).deleteTodo(1);

        // Call the controller method
        ResponseEntity<Void> response = toDoController.deleteTodoItem("1");

        // Assert response status



    }
    }