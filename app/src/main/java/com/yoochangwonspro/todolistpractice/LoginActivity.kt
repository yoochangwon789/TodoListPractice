package com.yoochangwonspro.todolistpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yoochangwonspro.todolistpractice.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initSignUpButton()
    }

    private fun initSignUpButton() {
        binding.LoginSignUpButton.setOnClickListener {
            signUp()
        }
    }

    private fun signUp() {
        val email = binding.LoginEmailEditText.text.toString()
        val password = binding.LoginPasswordEditText.text.toString()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "회원 가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this,
                        "회원 가입에 실패하셨습니다. 이미 가입한 이메일이 존재할 수 있습니다.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}