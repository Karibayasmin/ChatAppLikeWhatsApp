package com.kariba.chatapplikewhatsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth

    private lateinit var refUsers : DatabaseReference

    private var firebaseUserId : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Instead of default toolbar we want to show customize one, that's why set the actionbar title empty.
        val toolbar : androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_register)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Register"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            val intent = Intent(this@RegisterActivity, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        mAuth = FirebaseAuth.getInstance()

        button_register.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        var userName : String = editText_userName.text.toString()
        var email : String = editText_email.text.toString()
        var password : String = editText_password.text.toString()

        if(userName.isNullOrEmpty()){
            Toast.makeText(this@RegisterActivity, getString(R.string.please_enter_username), Toast.LENGTH_SHORT).show()

        }else if(email.isNullOrEmpty()){
            Toast.makeText(this@RegisterActivity, getString(R.string.please_enter_email), Toast.LENGTH_SHORT).show()

        }else if(password.isNullOrEmpty()){
            Toast.makeText(this@RegisterActivity, getString(R.string.please_enter_password), Toast.LENGTH_SHORT).show()

        }else{
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if(task.isSuccessful){
                    firebaseUserId = mAuth.currentUser?.uid ?: ""
                    refUsers = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUserId)

                    val userHashMap = HashMap<String, Any>()
                    userHashMap["uid"] = firebaseUserId
                    userHashMap["username"] = userName
                    userHashMap["profile"] = "https://firebasestorage.googleapis.com/v0/b/chatapp-like-whatsapp-fac4c.appspot.com/o/profile.png?alt=media&token=762f9ffe-f786-45e9-aa1f-e0f3ae388db2"
                    userHashMap["cover"] = "https://firebasestorage.googleapis.com/v0/b/chatapp-like-whatsapp-fac4c.appspot.com/o/cover.jpg?alt=media&token=22f67c48-3f8f-42ce-b640-64c35f141061"
                    userHashMap["status"] = "offline"
                    userHashMap["search"] = userName.toLowerCase()
                    userHashMap["facebook"] = "https://m.facebook.com"
                    userHashMap["instagram"] = "https://m.instagram.com"
                    userHashMap["website"] = "https://www.google.com"

                    refUsers.updateChildren(userHashMap).addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            finish()
                        }
                    }


                }else{
                    Toast.makeText(this@RegisterActivity, getString(R.string.error_message)+ task.exception?.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}