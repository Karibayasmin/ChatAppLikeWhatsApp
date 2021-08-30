package com.kariba.chatapplikewhatsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.editText_email
import kotlinx.android.synthetic.main.activity_login.editText_password
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Instead of default toolbar we want to show customize one, that's why set the actionbar title empty.
        val toolbar : androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_login)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Login"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            val intent = Intent(this@LoginActivity, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        mAuth = FirebaseAuth.getInstance()

        button_login.setOnClickListener {
            loginUser()
        }

    }

    private fun loginUser() {
        var email : String = editText_email.text.toString()
        var password : String = editText_password.text.toString()

        if(email.isNullOrEmpty()){
            Toast.makeText(this@LoginActivity, getString(R.string.please_enter_email), Toast.LENGTH_SHORT).show()

        }else if(password.isNullOrEmpty()){
            Toast.makeText(this@LoginActivity, getString(R.string.please_enter_password), Toast.LENGTH_SHORT).show()

        }else{
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if(task.isSuccessful){
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this@LoginActivity, getString(R.string.error_message)+ task.exception?.message.toString(), Toast.LENGTH_SHORT).show()

                }
            }
        }
    }
}