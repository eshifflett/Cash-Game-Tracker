package com.example.cashgametracker

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class RegisterScreenActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "RegisterScreenActivity"
    }

    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var repasswordEditText: EditText
    private lateinit var registerButton: Button
    private var validator = Validators()
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDb: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_screen)

        // GETTING REFERENCE TO FIREBASE AND CHECKING IF USER IS LOGGED IN
        mAuth = FirebaseAuth.getInstance()
        mDb = Firebase.firestore // Instantiate firestore db

        // hides action bar
        if(supportActionBar != null){
            this.supportActionBar?.hide()
        }

        initializeUI()
    }

    private fun initializeUI(){
        firstNameEditText = findViewById(R.id.firstNameRegisterEditText)
        lastNameEditText = findViewById(R.id.lastNameRegisterEditText)
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
        val firstname: String = firstNameEditText.text.toString()
        val lastname: String = lastNameEditText.text.toString()

        // FIRST NAME VALIDATION
        if(!validator.validName(firstname)){
            Toast.makeText(applicationContext, "Please enter valid first name...", Toast.LENGTH_SHORT).show()
            return
        }

        // LAST NAME VALIDATION
        if(!validator.validName(lastname)){
            Toast.makeText(applicationContext, "Please enter valid last name...", Toast.LENGTH_SHORT).show()
            return
        }

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
                val user = mAuth.currentUser // get reference to created user
                val userData = hashMapOf( // create user data for db
                    "uid" to user!!.uid,
                    "first" to firstname,
                    "last" to lastname
                )
                mDb.collection("users") // add to db
                    .add(userData)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }
                finish() // finish activity
            } else { // ERROR CREATING USER
                Log.d(TAG, "createUserWithEmail:failure")
                Toast.makeText(applicationContext, "Error creating user, please try again later.", Toast.LENGTH_LONG).show()
            }
        }
    }
}