package com.yoochangwonspro.todolistpractice

import android.graphics.Paint
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yoochangwonspro.todolistpractice.databinding.TodoListItemBinding
import com.yoochangwonspro.todolistpractice.todomodel.TodoListModel

class TodoListAdapter(
    val todoListData: MutableList<TodoListModel>,
    val itemDeleteClicked: (TodoListModel) -> Unit,
    val itemCompleteClicked: (TodoListModel) -> Unit
) : RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {

    class ViewHolder(val binding: TodoListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TodoListItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.itemTodoTextView.text = todoListData[position].itemName

        holder.binding.deleteImageButton.setOnClickListener {
            itemDeleteClicked(todoListData[position])
        }

        holder.binding.completionButton.setOnClickListener {
            itemCompleteClicked(todoListData[position])

            if (todoListData[position].completeTodoList) {
                holder.binding.itemTodoTextView.apply {
                    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    setTypeface(null, Typeface.ITALIC)
                }
            } else {
                holder.binding.itemTodoTextView.apply {
                    paintFlags = 0
                    setTypeface(null, Typeface.NORMAL)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return todoListData.size
    }
}