package com.example.homesensors

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.view.View.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.homesensors.entities.temperatureSensor
import com.example.homesensors.viewModel.OneSensorViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(), OnClickListener {

    lateinit var currentTemperature: TextView
    lateinit var updatingImage: ImageView
    lateinit var lastSynsText: TextView
    lateinit var mainFab: FloatingActionButton
    lateinit var mainFabExpand: Animation
    lateinit var mainFabCollspan: Animation

    var openState: Boolean = false

    val sysTem = temperatureSensor(25.0, true, "DDD")

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buildButton()
        buildAnimation()
        buildViewModel()



        currentTemperature.text = sysTem.getValue().toString()
    }


    private fun buildButton() {
        currentTemperature = findViewById(R.id.sensor_temperature)
        updatingImage = findViewById(R.id.updatingdata_image)
        mainFab = findViewById(R.id.floatingActionButton)
        lastSynsText = findViewById(R.id.update_date_text)

        mainFab.setOnClickListener(this)
    }

    private fun buildAnimation() {
        mainFabExpand = AnimationUtils.loadAnimation(this, R.anim.rotate_collspan)
        mainFabCollspan = AnimationUtils.loadAnimation(this, R.anim.rotate_expand)
    }

    private fun buildViewModel() {
        val oneSensorViewModel = ViewModelProviders.of(this).get(OneSensorViewModel::class.java)

        oneSensorViewModel.getOneValue().observe(this,
        Observer<temperatureSensor> { t -> run {    currentTemperature.text = t.getValue().toString()
                                                    updateUI(t.getSynsState(), t.getLastDateSyns())
                                                }
        })
    }

    private fun updateUI(state: Boolean, lastSyns: Calendar){
        if(state){
            updatingImage.visibility = VISIBLE
        }else{
            updatingImage.visibility = GONE
        }

        val formatData: DateFormat = SimpleDateFormat("dd.MM.yy H:mm:ss")
        lastSynsText.text = formatData.format(lastSyns.time)
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.floatingActionButton -> {
                showHideFab(openState)
                openState = !openState
            }
            else -> showHideFab(false)
        }
    }

    private fun showHideFab(state: Boolean) {
        if (state) {
            mainFab.startAnimation(mainFabExpand)
        } else {
            mainFab.startAnimation(mainFabCollspan)
        }

    }

}
