package com.jars.itodolist.model;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "todo_item_entity")
public class TodoItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "userId", nullable = false)
    private Integer userId;

    @Column(name = "item_description", nullable = false)
    private String itemDescription;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @Column(name = "modified_date")
    private LocalDate modifiedDate;

    @Column(name = "status", nullable = false)
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}