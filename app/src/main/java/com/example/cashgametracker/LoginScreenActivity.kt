package com.example.cashgametracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginScreenActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "LoginScreenActivity"
    }

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDb: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)

        mAuth = FirebaseAuth.getInstance() // Instantiate FirebaseAuth instance
        mDb = Firebase.firestore // Instantiate firestore db

        // hides action bar
        if(supportActionBar != null){
            this.supportActionBar?.hide()
        }

        //UI initialization
        initializeUI()
    }

    // FUNCTION TO INITIALIZE UI
    private fun initializeUI() {
        // INITIALIZING UI VARIABLES
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.registerButton)

    }

    override fun onStart(){
        super.onStart()

        //Reference to current user
        val currentUser = mAuth.currentUser
        //Starting main activity if logged in
        if(currentUser != null){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    // ONCLICK FUNCTION FOR LOGIN BUTTION
    fun onLoginPress(view: View) {
        val email: String = emailEditText.text.toString()
        val password: String = passwordEditText.text.toString()

        // LOGGING IN
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if(task.isSuccessful){ // LOGIN SUCCESS
                startActivity(Intent(this, MainActivity::class.java))
            } else { // LOGIN FAILURE
                Log.d(TAG, "signInWithEmailAndPassword:failure")
                Toast.makeText(applicationContext, "Error logging in, please try again.", Toast.LENGTH_LONG).show()
            }
        }
    }

    // ONCLICK FUNCTION FOR REGISTER BUTTION
    fun onRegisterPress(view: View) {
        startActivity(Intent(this, RegisterScreenActivity::class.java))
    }
}