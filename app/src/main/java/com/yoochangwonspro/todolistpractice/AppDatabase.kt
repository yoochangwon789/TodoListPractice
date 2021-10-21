package com.yoochangwonspro.todolistpractice

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yoochangwonspro.todolistpractice.dao.TodoDetailModelDao
import com.yoochangwonspro.todolistpractice.todomodel.TodoDetailModel

@Database(entities = [TodoDetailModel::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun todoDetailModelDao(): TodoDetailModelDao
}