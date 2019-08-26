package com.example.homesensors.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.homesensors.`interface`.onDataReadeListener
import com.example.homesensors.activities.TemperatureActivity

class ViewModelFactory(application: Application, sensorID: String, dataReadyListener: onDataReadeListener) : ViewModelProvider.Factory {

    private var TAG = "ViewModelFactory"

    var application: Application
    var sensorID: String
    var dataReadyListener: onDataReadeListener

    init {
        this.application = application
        this.sensorID = sensorID
        this.dataReadyListener = dataReadyListener
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        Log.d(TAG, "create " + modelClass)
        if (modelClass === OneSensorViewModel::class.java) {
            Log.d(TAG, "create one temperature")
            return OneSensorViewModel(application, sensorID,dataReadyListener) as T
        } else {
            Log.d(TAG, "create all temperature")
            return AllSensorsViewModel(application, sensorID, dataReadyListener) as T

        }
    }
}