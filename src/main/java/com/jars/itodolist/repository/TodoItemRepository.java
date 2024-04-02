package com.jars.itodolist.repository;

import com.jars.itodolist.model.TodoItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoItemRepository extends JpaRepository<TodoItemEntity, Integer> {
    List<TodoItemEntity> findAllByUserId(int userId);
}