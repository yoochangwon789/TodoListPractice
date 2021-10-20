package com.yoochangwonspro.todolistpractice.todomodel

data class TodoListModel(
    val itemName: String?,
    var completeTodoList: Boolean = false
)