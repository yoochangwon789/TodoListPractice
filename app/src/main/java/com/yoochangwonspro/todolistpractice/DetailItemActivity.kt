package com.yoochangwonspro.todolistpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.yoochangwonspro.todolistpractice.databinding.ActivityDetailItemBinding
import com.yoochangwonspro.todolistpractice.todomodel.TodoDetailModel

class DetailItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailItemBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Room.databaseBuilder(
            this,
            AppDatabase::class.java, "todoDetailRoom"
        ).build()

        val itemName = intent.getStringExtra("itemName")
        val itemId = intent.getStringExtra("itemId")

        binding.detailItemTopTextView.text = itemName.orEmpty()

        Thread {
            val todoDetail = db.todoDetailModelDao().getOnTodoDetail(itemId ?: "")
            runOnUiThread {
                binding.detailEditText.setText(todoDetail?.itemDetail.orEmpty())
            }
        }.start()

        binding.detailSaveButton.setOnClickListener {
            Thread {
                db.todoDetailModelDao().setTodoDetail(
                    TodoDetailModel(
                        itemId ?: "",
                        itemName,
                        binding.detailEditText.text.toString()
                    )
                )
            }.start()
        }
    }
}