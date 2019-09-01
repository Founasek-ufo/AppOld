package com.example.homesensors.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
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
import com.example.homesensors.`interface`.onDataReadeListener
import com.example.homesensors.entities.temperatureSensor
import com.example.homesensors.entities.temperatureSensor.Companion.SCALE_CELSIUM
import com.example.homesensors.entities.temperatureSensor.Companion.SCALE_FAHRENHEIT
import com.example.homesensors.viewModel.OneSensorViewModel
import com.example.homesensors.viewModel.ViewModelFactory
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


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
    lateinit var lineChar: LineChart

    var openState: Boolean = false

    var scale: Int = SCALE_CELSIUM
    var type: Int = 1

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.temperature_activity)

        buildButton()
        buildAnimation()
        buildViewModel()

        val intent: Intent = intent
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
    }

    private fun buildButton() {
        currentTemperature = findViewById(R.id.sensor_temperature)
        updatingImage = findViewById(R.id.updatingdata_image)
        mainFab = findViewById(R.id.floatingActionButton)
        lastSynsText = findViewById(R.id.update_date_text)
        mark = findViewById(R.id.temperature_mark)

        mainFab.setOnClickListener(this)

        lineChar = findViewById(R.id.lineChart)



        lineChar.description.text = "Data ze senzoru"

        val x: XAxis = lineChar.xAxis
        x.position = XAxis.XAxisPosition.BOTTOM
        x.textColor = Color.RED

        val yRight: YAxis = lineChar.axisRight
        yRight.isEnabled = false

    }

    private fun buildAnimation() {
        mainFabExpand = AnimationUtils.loadAnimation(this, R.anim.rotate_collspan)
        mainFabCollspan = AnimationUtils.loadAnimation(this, R.anim.rotate_expand)
    }

    private fun buildViewModel() {
        val oneSensorViewModel =
            ViewModelProviders.of(this, ViewModelFactory(application, "ddd", object : onDataReadeListener {
                override fun dataReady() {
                    Log.d(TAG, "dataReady")
                }

                override fun dataError() {
                    Log.d(TAG, "dataError")
                }
            }))
                .get(OneSensorViewModel::class.java)

        oneSensorViewModel.getOneValue().observe(this,
            Observer<temperatureSensor> { t ->
                run {
                    currentTemperature.text = ((t.getValue(scale) * 10) / 10).toString()
                    if (type == 1) {
                        when (scale) {
                            SCALE_CELSIUM -> mark.text = " °C"
                            SCALE_FAHRENHEIT -> mark.text = " °F"
                            else -> mark.text = " °C"
                        }
                    }
                    Log.d(TAG, "buildViewModel observe: " + t.getValue().toString())
                    updateUI(t.getSynsState(), t.getLastDateSyns(), t.getValue())
                }
            })

        oneSensorViewModel.getArrayValue().observe(this,
            Observer<Array<Double>> {
                run {
                    Log.d(TAG, "buildViewModel array list")

                    val entries: MutableList<Entry> = ArrayList()

                    for (i in 10 until it.size step 1) {
                        val entry = Entry(i.toFloat(), it.get(i).toFloat())
                        entries.add(entry)
                    }


                    val dataset = LineDataSet(entries, "")
                    dataset.setDrawValues(false)

                    val lineData = LineData(dataset)

                    lineChar.data = lineData
                    lineChar.invalidate()
                }
            }
        )
    }

    private fun updateUI(state: Boolean, lastSyns: Calendar, lastValue: Double) {
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
