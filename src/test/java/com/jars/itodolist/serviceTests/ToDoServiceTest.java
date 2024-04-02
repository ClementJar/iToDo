package com.jars.itodolist.serviceTests;

import com.example.model.TodoItem;
import com.jars.itodolist.interfaces.IUserService;
import com.jars.itodolist.mapper.ToDoItemMapper;
import com.jars.itodolist.model.SecUserEntity;
import com.jars.itodolist.model.TodoItemEntity;
import com.jars.itodolist.service.ToDoService;
import jakarta.persistence.EntityNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ToDoServiceTest {

    @Autowired
    private ToDoService toDoService;

    @MockBean
    private IUserService mockUserService;

    @MockBean
    private ToDoItemMapper toDoItemMapper;

    private SecUserEntity createUser(String username, String email, String password) {
        SecUserEntity secUserEntity = new SecUserEntity();
        secUserEntity.setEmail(email);
        secUserEntity.setPassword(password);
        secUserEntity.setUsername(username);
        return secUserEntity;
    }

    private TodoItem createTodoItem(String itemDescription, String userName, String status) {
       TodoItem todoItem = new TodoItem();
        todoItem.status(status);
        todoItem.setItemDescription(itemDescription);
        todoItem.setUser(userName);

        return  todoItem;
    }

    private TodoItemEntity createTodoItemEntity(int id, int userId, String description, String status) {
        TodoItemEntity entity = new TodoItemEntity();
        entity.setId(id);
        entity.setUserId(userId);
        entity.setItemDescription(description);
        entity.setStatus(status);
        return entity;
    }


@Test
public void testAddToDo_Success() {
    // Create mock data
    TodoItem todoItem = createTodoItem("Test Item", "This is a test todo item", "status");
    SecUserEntity user = createUser("Jack","test@example.com", "password");

    // Mock behavior of IUserService
    Mockito.when(mockUserService.getUserByUserNameOrEmail(todoItem.getUser()))
            .thenReturn(user);

    // Mock behavior of ToDoItemMapper
    TodoItemEntity mappedEntity = new TodoItemEntity(); // Prepare mock entity
    Mockito.when(toDoItemMapper.toToDoItemEntity(todoItem)).thenReturn(mappedEntity);

    // Call the service method
    TodoItemEntity savedEntity = toDoService.addToDo(todoItem);

    // Assertions
    assertNotNull(savedEntity, "Saved entity should not be null");
    assertEquals(user.getId(), savedEntity.getUserId());
    assertTrue(savedEntity.getCreatedDate().isEqual(LocalDate.now()));
}

@Test
public void testUpdateToDo_Success() throws Exception {
    // Create mock data
    int todoItemId = 1;
    TodoItem updateItem = createTodoItem("Updated Item", "Description updated", "PENDING");

    // Mock behavior to retrieve existing entity
    TodoItemEntity existingEntity = createTodoItemEntity(todoItemId, 1, "Original description", "PENDING");
    Mockito.when(toDoService.getToDoItem(todoItemId)).thenReturn(existingEntity);

    // Call the service method
    TodoItemEntity updatedEntity = toDoService.updateToDo(updateItem);

    // Assertions
    assertEquals(todoItemId, updatedEntity.getId());
    assertEquals(updateItem.getItemDescription(), updatedEntity.getItemDescription());
    assertEquals(updateItem.getStatus(), updatedEntity.getStatus());
    assertTrue(updatedEntity.getModifiedDate().isEqual(LocalDate.now()));
}

@Test
public void testGetToDoItem_Success() {
    // Mock behavior to retrieve existing entity
    int todoItemId = 1;
    TodoItemEntity existingEntity = createTodoItemEntity(todoItemId, 1, "Original description", "PENDING");
    Mockito.when(toDoService.getToDoItem(todoItemId)).thenReturn(existingEntity);

    // Call the service method
    TodoItemEntity retrievedEntity = toDoService.getToDoItem(todoItemId);

    // Assertions
    assertNotNull(retrievedEntity, "Retrieved entity should not be null");
    assertEquals(todoItemId, retrievedEntity.getId());
}

@Test(expected = EntityNotFoundException.class)
public void testGetToDoItem_NotFound() {
    // Mock behavior to return null for non-existent ID
    int nonExistentId = 10;
    Mockito.when(toDoService.getToDoItem(nonExistentId)).thenReturn(null);

    // Call the service method (expect exception)
    toDoService.getToDoItem(nonExistentId);
}
}
