package com.yoochangwonspro.todolistpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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

        val itemId = intent.getStringExtra("itemId")
        val itemName = intent.getStringExtra("itemName")

        itemTopText(itemName)
        getOnTodoDetailThread(itemId)
        initTodoDetailButton(itemId, itemName)


    }

    private fun itemTopText(itemName: String?) {
        binding.detailItemTopTextView.text = itemName.orEmpty()
    }

    private fun getOnTodoDetailThread(itemId: String?) {
        Thread {
            val todoDetail = db.todoDetailModelDao().getOnTodoDetail(itemId ?: "")
            runOnUiThread {
                binding.detailEditText.setText(todoDetail?.itemDetail.orEmpty())
            }
        }.start()
    }

    private fun initTodoDetailButton(itemId: String?, itemName: String?) {
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

            Toast.makeText(this, "메모가 저장되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}