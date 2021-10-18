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
    val itemDeleteClicked: (TodoListModel) -> Unit,
    val itemCompleteClicked: (TodoListModel) -> Unit
) : ListAdapter<TodoListModel, TodoListAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: TodoListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(todoListModel: TodoListModel) {
            binding.itemTodoTextView.text = todoListModel.itemName

            binding.deleteImageButton.setOnClickListener {
                itemDeleteClicked(todoListModel)
            }

            binding.completionButton.setOnClickListener {
                itemCompleteClicked(todoListModel)

                if (todoListModel.completeTodoList) {
                    binding.itemTodoTextView.apply {
                        paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                        setTypeface(null, Typeface.ITALIC)
                    }
                } else {
                    binding.itemTodoTextView.apply {
                        paintFlags = 0
                        setTypeface(null, Typeface.NORMAL)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TodoListItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<TodoListModel>() {
            override fun areItemsTheSame(oldItem: TodoListModel, newItem: TodoListModel): Boolean {
                return oldItem.itemName == newItem.itemName
            }

            override fun areContentsTheSame(
                oldItem: TodoListModel,
                newItem: TodoListModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}