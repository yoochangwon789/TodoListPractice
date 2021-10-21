package com.yoochangwonspro.todolistpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.yoochangwonspro.todolistpractice.databinding.ActivityDetailItemBinding
import com.yoochangwonspro.todolistpractice.todomodel.TodoDetailModel

class DetailItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailItemBinding

    private val db: AppDatabase by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java, "todoDetailRoom"
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val itemName = intent.getStringExtra("itemName")
        val itemId = intent.getStringExtra("itemId")

        itemTopText(itemName)

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

    private fun itemTopText(itemName: String?) {
        binding.detailItemTopTextView.text = itemName.orEmpty()
    }
}