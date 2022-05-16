package com.example.cashgametracker

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class RegisterScreenActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "RegisterScreenActivity"
    }

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var repasswordEditText: EditText
    private lateinit var registerButton: Button
    private var validator = Validators()
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_screen)

        // GETTING REFERENCE TO FIREBASE AND CHECKING IF USER IS LOGGED IN
        mAuth = FirebaseAuth.getInstance()

        initializeUI()
    }

    private fun initializeUI(){
        emailEditText = findViewById(R.id.emailRegisterEditText)
        passwordEditText = findViewById(R.id.passwordRegisterEditText)
        repasswordEditText = findViewById(R.id.repasswordRegisterEditText)
        registerButton = findViewById(R.id.registerRegisterButton)
    }

    // ONCLICK FUNCTION FOR REGISTER BUTTON
    fun onRegisterRegisterPress(view: View) {
        val email: String = emailEditText.text.toString()
        val password: String = passwordEditText.text.toString()
        val repassword: String = repasswordEditText.text.toString()

        // EMAIL VALIDATION
        if (!validator.validEmail(email)) {
            Toast.makeText(applicationContext, "Please enter valid email...", Toast.LENGTH_SHORT).show()
            return
        }

        // PASSWORD VALIDATION
        if (!validator.validPassword(password)) {
            Toast.makeText(applicationContext, "Password must contain 8 characters with one letter and one number!", Toast.LENGTH_LONG).show()
            return
        } else if(password != repassword) {
            Toast.makeText(applicationContext, "Passwords do not match!", Toast.LENGTH_SHORT).show()
            return
        }

        // CREATING USER AND ASSIGNING CALLBACK
        val authenticatorRef = mAuth.createUserWithEmailAndPassword(email, password)
        authenticatorRef.addOnCompleteListener() { task ->
            if (task.isSuccessful) { // USER SUCCESSFULLY CREATED
                Log.d(TAG, "createUserWithEmail:success")
                val user = mAuth.currentUser
                finish()
            } else { // ERROR CREATING USER
                Log.d(TAG, "createUserWithEmail:failure")
                Toast.makeText(applicationContext, "Error creating user, please try again later.", Toast.LENGTH_LONG).show()
            }
        }
    }
}