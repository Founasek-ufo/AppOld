package com.example.homesensors

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homesensors.`interface`.onDataReadeListener
import com.example.homesensors.`interface`.onItemClickListener
import com.example.homesensors.activities.TemperatureActivity
import com.example.homesensors.activities.TemperatureActivity.Companion.EXTRA_TYPE
import com.example.homesensors.adapters.AdapterSensors
import com.example.homesensors.entities.temperatureSensor
import com.example.homesensors.viewModel.AllSensorsViewModel
import com.example.homesensors.viewModel.ViewModelFactory


class MainActivity : AppCompatActivity(), onItemClickListener {

    override fun onOpenClick(poss: Int) {
        Log.d(TAG, "onOpenClick")
        intentToDetail(allSensors.get(poss).getTypeSensor())
    }

    private fun intentToDetail(type: Int) {
        val toDetail = Intent(this, TemperatureActivity::class.java)
        toDetail.putExtra(EXTRA_TYPE, type)
        startActivity(toDetail)
//test s
    }

    private val TAG = "MainActivity"

    lateinit var recyclerView: RecyclerView
    lateinit var sensorText: TextView
    lateinit var indictorSync: ImageView
    lateinit var textBeforeSync: TextView
    lateinit var progresBarBeforeSync: ProgressBar

    private var homeAdapter: AdapterSensors? = null
    private var allSensors: List<temperatureSensor> = ArrayList<temperatureSensor>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        buildView()
        buildRecyclerView()
        buildViewModel()

        sensorText.text = "0 " + getString(R.string.sensor_text_one)
    }

    fun buildView() {
        recyclerView = findViewById(R.id.home_recyclerview)
        sensorText = findViewById(R.id.sensors_active)
        indictorSync = findViewById(R.id.senosrs_update)
        textBeforeSync = findViewById(R.id.sensors_synchronization)
        progresBarBeforeSync = findViewById(R.id.progress_synchronization)
    }

    fun buildRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)

        homeAdapter = AdapterSensors(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(DividerItemDecoration(this, 0))
        recyclerView.adapter = homeAdapter

        homeAdapter!!.setOnClikcListener(this)
    }

    private fun buildViewModel() {

        val allSensorViewModel =
            ViewModelProviders.of(this, ViewModelFactory(application, "ddd", object : onDataReadeListener {
                override fun dataReady() {
                    Log.d(TAG, "dataReady")
                }

                override fun dataError() {
                    textBeforeSync.text = "Error"
                    indictorSync.visibility = View.INVISIBLE
                }
            }))
                .get(AllSensorsViewModel::class.java)

        allSensorViewModel.getAllValues().observe(this,
            Observer<List<temperatureSensor>> { t ->
                run {
                    allSensors = t
                    if (allSensors.size < 2) {
                        sensorText.text = t.size.toString() + " " + getString(R.string.sensor_text_one)
                    } else {
                        sensorText.text = t.size.toString() + " " + getString(R.string.sensor_text_more)
                    }
                    textBeforeSync.visibility = View.INVISIBLE
                    progresBarBeforeSync.visibility = View.INVISIBLE
                    indictorSync.visibility = View.VISIBLE
                    sensorText.visibility = View.VISIBLE
                    homeAdapter!!.setAllSensor(t)
                    Log.d(TAG, "buildViewModel " + t.size)
                }
            })
    }

}


