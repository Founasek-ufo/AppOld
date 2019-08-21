package com.example.homesensors.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.homesensors.MainActivity
import com.example.homesensors.R
import com.example.homesensors.handlers.AuthorisationHandler
import com.example.homesensors.handlers.IAuthorisationHandler

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    val aut:IAuthorisationHandler = AuthorisationHandler()

    override fun onClick(v: View?) {
        var toMain = Intent(this, MainActivity::class.java)
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