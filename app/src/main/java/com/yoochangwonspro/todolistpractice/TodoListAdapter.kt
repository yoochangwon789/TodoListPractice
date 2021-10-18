package com.yoochangwonspro.todolistpractice

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yoochangwonspro.todolistpractice.databinding.TodoListItemBinding
import com.yoochangwonspro.todolistpractice.todomodel.TodoListModel

class TodoListAdapter : ListAdapter<TodoListModel, TodoListAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: TodoListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<TodoListModel>() {
            override fun areItemsTheSame(oldItem: TodoListModel, newItem: TodoListModel): Boolean {
                TODO("Not yet implemented")
            }

            override fun areContentsTheSame(oldItem: TodoListModel, newItem: TodoListModel, ): Boolean {
                TODO("Not yet implemented")
            }

        }
    }
}