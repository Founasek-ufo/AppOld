package com.example.homesensors.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.homesensors.MainActivity
import com.example.homesensors.R
import com.example.homesensors.`interface`.onAuthorizationListener
import com.example.homesensors.handlers.AuthorisationHandler
import com.example.homesensors.handlers.IAuthorisationHandler
import com.example.homesensors.handlers.SharePreferencesHandler

class LoginActivity : AppCompatActivity(), View.OnClickListener, onAuthorizationListener {
    override fun onAuthStart() {
        try {
            progresLogIn.visibility = View.VISIBLE
        }catch (e: Exception){
            Log.d(TAG, "onAuthStart " + e.message)
        }
    }

    override fun onAutEnd(state: Boolean) {
        try {
            progresLogIn.visibility = View.INVISIBLE
        } catch (e: Exception) {
            Log.d(TAG, "onAutEnd " + e.message)
        }
        if (state) {
            goToMain()
        } else {
            showError()
        }
    }

    override fun getToken(token: String) {
        sphHandler.saveToken(token)
    }

    private fun goToMain() {
        val toMain = Intent(this, MainActivity::class.java)
        startActivity(toMain)
    }

    private fun showError() {

    }

    val auth: IAuthorisationHandler = AuthorisationHandler()
    lateinit var sphHandler: SharePreferencesHandler

    override fun onClick(v: View?) {
//        goToMain()
        // TODO: for testing
        auth.callAuthorization(userName.text.trim().toString(), userPassword.text.trim().toString(), this)
    }

    private val TAG = "LoginActivity"

    lateinit var loginButton: Button
    lateinit var userName: EditText
    lateinit var userPassword: EditText
    lateinit var progresLogIn: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        sphHandler = SharePreferencesHandler(applicationContext)

        userName = findViewById(R.id.user_name)
        userPassword = findViewById(R.id.password_name)
        progresLogIn = findViewById(R.id.progress_log_in)

        loginButton = findViewById(R.id.loginButton)
        loginButton.setOnClickListener(this)
    }


}