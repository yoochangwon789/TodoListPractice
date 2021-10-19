package com.yoochangwonspro.todolistpractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yoochangwonspro.todolistpractice.databinding.ActivityMainBinding
import com.yoochangwonspro.todolistpractice.todomodel.TodoListModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var todoListAdapter: TodoListAdapter

    private val viewModel: MainViewModel by viewModels()

    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    override fun onStart() {
        super.onStart()

        if (auth.currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

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
            viewModel.todoListModelList,
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
            val todoListModel = TodoListModel(binding.todoListNameEditText.text.toString())
            viewModel.todoListAdd(todoListModel)

            todoListAdapter.notifyDataSetChanged()
        }
    }

    private fun itemDeleteClickListener(todoListModel: TodoListModel) {
        viewModel.todoListDelete(todoListModel)
        todoListAdapter.notifyDataSetChanged()
    }

    private fun itemCompleteClickListener(todoListModel: TodoListModel) {
        viewModel.todoListCompletion(todoListModel)
        todoListAdapter.notifyDataSetChanged()
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
        todoListModel.completeTodoList = todoListModel.completeTodoList.not()
    }
}