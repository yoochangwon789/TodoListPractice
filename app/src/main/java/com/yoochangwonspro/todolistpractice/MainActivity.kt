package com.yoochangwonspro.todolistpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.yoochangwonspro.todolistpractice.databinding.ActivityMainBinding
import com.yoochangwonspro.todolistpractice.todomodel.TodoListModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var todoListAdapter: TodoListAdapter

    private val todoListModelList = mutableListOf<TodoListModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initTodoListRecyclerView()
        initTodoListAddButton()
    }

    private fun initTodoListRecyclerView() {
        todoListAdapter = TodoListAdapter()
        binding.todoListRecyclerView.adapter = todoListAdapter
        binding.todoListRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun initTodoListAddButton() {
        binding.todoListAddButton.setOnClickListener {
            todoListModelList.add(TodoListModel(binding.todoListNameEditText.text.toString()))
            todoListAdapter.submitList(todoListModelList)
            todoListAdapter.notifyDataSetChanged()
        }
    }
}