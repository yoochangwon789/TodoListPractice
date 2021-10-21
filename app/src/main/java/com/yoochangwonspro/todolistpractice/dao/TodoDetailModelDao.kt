package com.yoochangwonspro.todolistpractice.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yoochangwonspro.todolistpractice.todomodel.TodoDetailModel

@Dao
interface TodoDetailModelDao {

    @Query("SELECT * FROM tododetailmodel WHERE id == :id")
    fun getOnTodoDetail(id: String): TodoDetailModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setTodoDetail(todoDetailModel: TodoDetailModel)
}