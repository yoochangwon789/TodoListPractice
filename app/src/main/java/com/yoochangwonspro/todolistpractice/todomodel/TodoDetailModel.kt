package com.yoochangwonspro.todolistpractice.todomodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoDetailModel(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "item_name") val itemName: String?,
    @ColumnInfo(name = "item_detail") val itemDetail: String?
)