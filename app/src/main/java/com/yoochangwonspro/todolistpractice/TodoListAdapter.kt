package com.yoochangwonspro.todolistpractice

import android.graphics.Paint
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentSnapshot
import com.yoochangwonspro.todolistpractice.databinding.TodoListItemBinding

class TodoListAdapter(
    private var todoListData: List<DocumentSnapshot>,
    val itemDeleteClicked: (DocumentSnapshot) -> Unit,
    val itemCompleteClicked: (DocumentSnapshot) -> Unit,
    val itemClickedListener: (DocumentSnapshot) -> Unit
) : RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {

    class ViewHolder(val binding: TodoListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TodoListItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todoData = todoListData[position]
        holder.binding.itemTodoTextView.text = todoData.getString("itemName") ?: ""

        holder.binding.deleteImageButton.setOnClickListener {
            itemDeleteClicked(todoData)
        }

        holder.binding.completionButton.setOnClickListener {
            itemCompleteClicked(todoData)
        }

        holder.binding.root.setOnClickListener {
            itemClickedListener(todoData)
        }

        if (todoData.getBoolean("completeTodoList") == true) {
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

    override fun getItemCount(): Int {
        return todoListData.size
    }

    fun setData(newData: List<DocumentSnapshot>) {
        todoListData = newData
        notifyDataSetChanged()
    }
}