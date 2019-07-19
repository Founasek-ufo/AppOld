package com.example.homesensors

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homesensors.`interface`.onItemClickListener
import com.example.homesensors.activities.TemperatureActivity
import com.example.homesensors.activities.TemperatureActivity.Companion.EXTRA_TYPE
import com.example.homesensors.adapters.AdapterSensors
import com.example.homesensors.entities.temperatureSensor
import com.example.homesensors.viewModel.AllSensorsViewModel


class MenuActivity : AppCompatActivity(), onItemClickListener {

    override fun onOpenClick(poss: Int) {
        Log.d(TAG, "onOpenClick")
        intentToDetail(allSensors.get(poss).getTypeSensor())
    }

    private fun intentToDetail(type: Int){
        var toDetail: Intent = Intent(this, TemperatureActivity::class.java)
        toDetail.putExtra(EXTRA_TYPE,type)
        startActivity(toDetail)

    }

    private val TAG = "MenuActivity";

    lateinit var recyclerView: RecyclerView

    private var homeAdapter: AdapterSensors? = null
    private var allSensors: List<temperatureSensor> = ArrayList<temperatureSensor>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)

        buildView()
        buildRecyclerView()
        buildViewModel()
    }

    fun buildView() {
        recyclerView = findViewById(R.id.home_recyclerview)
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
        val allSensorViewModel = ViewModelProviders.of(this).get(AllSensorsViewModel::class.java)

        allSensorViewModel.getAllValues().observe(this,
            Observer<List<temperatureSensor>> { t ->
                run {
                    //                    currentTemperature.text = t.getValue().toString()
//                    updateUI(t.getSynsState(), t.getLastDateSyns())
                    allSensors = t;
                    homeAdapter!!.setAllSensor(t)
                    Log.d(TAG, "buildViewModel " + t.size)

                }
            })

    }

}


