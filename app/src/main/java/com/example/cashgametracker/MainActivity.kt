package com.example.cashgametracker

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.cashgametracker.activities.NewSessionActivity
import com.example.cashgametracker.authentication.LoginScreenActivity
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private lateinit var newSessionButton: Button
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // GETTING REFERENCE TO FIREBASE AND CHECKING IF USER IS LOGGED IN
        mAuth = FirebaseAuth.getInstance()

        // hides action bar
        if(supportActionBar != null){
            this.supportActionBar?.hide()
        }

        initializeUI()
    }

    // Function for UI initialization
    private fun initializeUI() {

    }

    override fun onStart(){
        super.onStart()

        //Reference to current user
        val currentUser = mAuth.currentUser
        if(currentUser == null){
            startActivity(Intent(this, LoginScreenActivity::class.java))
            finish()
            return
        }
    }

    // Listener function for new session button
    fun onNewSessionPress(view: View) {
        // Launching new session activity
        startActivity(Intent(this, NewSessionActivity::class.java))
    }
}