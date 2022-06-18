package com.example.cashgametracker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

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
}