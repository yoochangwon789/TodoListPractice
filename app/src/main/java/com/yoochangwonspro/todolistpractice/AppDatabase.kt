package com.yoochangwonspro.todolistpractice

import androidx.room.Database
import com.yoochangwonspro.todolistpractice.dao.TodoDetailModelDao
import com.yoochangwonspro.todolistpractice.todomodel.TodoDetailModel

@Database(entities = [TodoDetailModel::class], version = 1)
abstract class AppDatabase {
    abstract fun todoDetailModelDao(): TodoDetailModelDao
}