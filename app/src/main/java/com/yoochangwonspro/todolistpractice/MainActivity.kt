package com.yoochangwonspro.todolistpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.yoochangwonspro.todolistpractice.databinding.ActivityMainBinding
import com.yoochangwonspro.todolistpractice.todomodel.TodoListModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var todoListAdapter: TodoListAdapter

    private val todoListModelList = mutableListOf<TodoListModel>()

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initTodoListRecyclerView()
        initTodoListAddButton()
    }

    private fun initTodoListRecyclerView() {
        todoListAdapter = TodoListAdapter(
            itemDeleteClicked = {
                itemDeleteClickListener(it)
            },
            itemCompleteClicked = {
                itemCompleteClickListener(it)
            }
        )

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

    private fun itemDeleteClickListener(todoListModel: TodoListModel) {
        todoListModelList.remove(todoListModel)
        todoListAdapter.notifyDataSetChanged()
    }

    private fun itemCompleteClickListener(todoListModel: TodoListModel) {
        val isDone = todoListModel.completeTodoList.not()
        todoListModel.completeTodoList = isDone
    }
}

class MainViewModel : ViewModel() {
    val todoListModelList = mutableListOf<TodoListModel>()

    fun todoListAdd(todoListModel: TodoListModel) {
        todoListModelList.add(todoListModel)
    }

    fun todoListDelete(todoListModel: TodoListModel) {
        todoListModelList.remove(todoListModel)
    }

    fun todoListCompletion(todoListModel: TodoListModel) {
        val isDone = todoListModel.completeTodoList.not()
        todoListModel.completeTodoList = isDone
    }
}