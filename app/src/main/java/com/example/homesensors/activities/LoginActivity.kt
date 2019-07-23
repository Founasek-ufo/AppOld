package com.example.homesensors.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.homesensors.MainActivity
import com.example.homesensors.R

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        var toMain: Intent = Intent(this, MainActivity::class.java)
        startActivity(toMain)
    }

    lateinit var loginButton: Button;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        loginButton = findViewById(R.id.loginButton)
        loginButton.setOnClickListener(this);
    }



}