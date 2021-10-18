package com.yoochangwonspro.todolistpractice.todomodel

data class TodoListModel(
    val itemName: String,
    val completeTodoList: Boolean = false
)