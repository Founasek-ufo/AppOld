package com.example.homesensors.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.homesensors.R
import com.example.homesensors.entities.temperatureSensor
import com.example.homesensors.entities.temperatureSensor.Companion.SCALE_CELSIUM
import com.example.homesensors.entities.temperatureSensor.Companion.SCALE_FAHRENHEIT
import com.example.homesensors.viewModel.OneSensorViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.Math.round
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class TemperatureActivity : AppCompatActivity(), OnClickListener {

    private val TAG = "TemperatureActivity"

    companion object {
        const val EXTRA_TYPE = "com.example.homesensors.activites.EXTRA_TYPE"
    }

    lateinit var currentTemperature: TextView
    lateinit var updatingImage: ImageView
    lateinit var lastSynsText: TextView
    lateinit var mainFab: FloatingActionButton
    lateinit var mainFabExpand: Animation
    lateinit var mainFabCollspan: Animation
    lateinit var mark: TextView

    var openState: Boolean = false

    var scale: Int = SCALE_CELSIUM
    var type: Int = 1

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.homesensors.R.layout.temperature_activity)

        buildButton()
        buildAnimation()
//        buildViewModel()

        var intent: Intent = intent
        if (intent != null) run {
            type = intent.getIntExtra(EXTRA_TYPE, 0)
            Log.d(TAG, "onCreate " + type.toString())
            if (type == 2) {
                mark.text = "%"
            }

        }

        if (type == 1) {
            mark.setOnClickListener(this)
        }

//
//        val animator = ValueAnimator.ofInt(0, 50) //0 is min number, 600 is max number
//        animator.duration = 3000 //Duration is in milliseconds
//        animator.addUpdateListener { animation -> currentTemperature.setText(animation.animatedValue.toString()) }
//        animator.addListener(object : AnimatorListenerAdapter() {
//            override fun onAnimationEnd(animation: Animator) {
        // done
//                Log.d(TAG, "onAnimationEnd")
        buildViewModel()
//            }
//        })
//        animator.start()


    }

    private fun buildButton() {
        currentTemperature = findViewById(R.id.sensor_temperature)
        updatingImage = findViewById(R.id.updatingdata_image)
        mainFab = findViewById(R.id.floatingActionButton)
        lastSynsText = findViewById(R.id.update_date_text)
        mark = findViewById(R.id.temperature_mark)

        mainFab.setOnClickListener(this)


    }

    private fun buildAnimation() {
        mainFabExpand = AnimationUtils.loadAnimation(this, R.anim.rotate_collspan)
        mainFabCollspan = AnimationUtils.loadAnimation(this, R.anim.rotate_expand)
    }

    private fun buildViewModel() {
        val oneSensorViewModel = ViewModelProviders.of(this).get(OneSensorViewModel::class.java)

        oneSensorViewModel.getOneValue().observe(this,
            Observer<temperatureSensor> { t ->
                run {
                    currentTemperature.text = round(t.getValue(scale)).toString()
                    if (type == 1) {
                        when (scale) {
                            SCALE_CELSIUM -> mark.text = " °C"
                            SCALE_FAHRENHEIT -> mark.text = " °F"
                            else -> mark.text = " °C"
                        }
                    }
                    updateUI(t.getSynsState(), t.getLastDateSyns())
                }
            })
    }

    private fun updateUI(state: Boolean, lastSyns: Calendar) {
        if (state) {
            updatingImage.visibility = VISIBLE
        } else {
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
            R.id.temperature_mark -> {
                if (scale == SCALE_CELSIUM) {
                    scale = SCALE_FAHRENHEIT
                } else {
                    scale = SCALE_CELSIUM
                }
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
