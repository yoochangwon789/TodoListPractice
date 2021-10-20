package com.yoochangwonspro.todolistpractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
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
        initLoginButton()
        signUpButtonAndLoginButtonIsEnabled()
    }

    private fun initSignUpButton() {
        binding.LoginSignUpButton.setOnClickListener {
            signUp()
        }
    }

    private fun initLoginButton() {
        binding.LoginButton.setOnClickListener {
            login()
        }
    }

    private fun signUp() {

        auth.createUserWithEmailAndPassword(getEmailText(), getPasswordText())
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

    private fun login() {
        auth.signInWithEmailAndPassword(getEmailText(), getPasswordText())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    Toast.makeText(this,
                        "로그인에 실패했습니다 이메일과 비밀번호를 확인해 주세요.",
                    Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun signUpButtonAndLoginButtonIsEnabled() {
        binding.LoginEmailEditText.addTextChangedListener {
            val enable = binding.LoginEmailEditText.text.isNotEmpty() &&
                    binding.LoginPasswordEditText.text.isNotEmpty()

            binding.LoginSignUpButton.isEnabled = enable
            binding.LoginButton.isEnabled = enable
        }

        binding.LoginPasswordEditText.addTextChangedListener {
            val enable = binding.LoginEmailEditText.text.isNotEmpty() &&
                    binding.LoginPasswordEditText.text.isNotEmpty()

            binding.LoginSignUpButton.isEnabled = enable
            binding.LoginButton.isEnabled = enable
        }
    }

    private fun getEmailText(): String {
        return binding.LoginEmailEditText.text.toString()
    }

    private fun getPasswordText(): String {
        return binding.LoginPasswordEditText.text.toString()
    }
}