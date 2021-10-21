package com.yoochangwonspro.todolistpractice

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
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
            finish()
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
        initLogoutButton()

        viewModel.todoLiveData.observe(this, {
            (binding.todoListRecyclerView.adapter as TodoListAdapter).setData(it)
        })
    }

    private fun initTodoListRecyclerView() {
        todoListAdapter = TodoListAdapter(
            emptyList(),
            itemDeleteClicked = {
                itemDeleteClickListener(it)
            },
            itemCompleteClicked = {
                itemCompleteClickListener(it)
            },
            itemClickedListener = {
                itemClickedListener(it)
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

    private fun initLogoutButton() {
        binding.logoutTextView.setOnClickListener {
            if (auth.currentUser != null) {
                finish()
                startActivity(Intent(this, LoginActivity::class.java))
            } else {
                return@setOnClickListener
            }
        }
    }

    private fun itemDeleteClickListener(todoListModel: DocumentSnapshot) {
        viewModel.todoListDelete(todoListModel)
    }

    private fun itemCompleteClickListener(todoListModel: DocumentSnapshot) {
        viewModel.todoListCompletion(todoListModel)
    }

    private fun itemClickedListener(todoListModel: DocumentSnapshot) {
        val intent = Intent(this, DetailItemActivity::class.java)
        intent.putExtra("itemName", todoListModel.getString("itemName"))
        intent.putExtra("itemId", todoListModel.id)
        startActivity(intent)
    }
}

class MainViewModel : ViewModel() {
    val todoLiveData = MutableLiveData<List<DocumentSnapshot>>()

    private val db = Firebase.firestore

    init {
        fetch()
    }

    fun fetch() {
        val user = Firebase.auth.currentUser
        val docRef = db.collection(user?.uid.toString())
        docRef.addSnapshotListener { value, error ->
            if (error != null) {
                return@addSnapshotListener
            }

            if (value != null) {
                todoLiveData.value = value.documents
            }
        }
    }

    fun todoListAdd(todoListModel: TodoListModel) {
        Firebase.auth.currentUser?.let {
            db.collection(it.uid).add(todoListModel)
        }
    }

    fun todoListDelete(todoListModel: DocumentSnapshot) {
        Firebase.auth.currentUser?.let {
            db.collection(it.uid).document(todoListModel.id).delete()
        }
    }

    fun todoListCompletion(todoListModel: DocumentSnapshot) {
        Firebase.auth.currentUser?.let {
            val completeTodoList = todoListModel.getBoolean("completeTodoList") ?: false
            db.collection(it.uid).document(todoListModel.id)
                .update("completeTodoList", completeTodoList.not())
        }
    }
}