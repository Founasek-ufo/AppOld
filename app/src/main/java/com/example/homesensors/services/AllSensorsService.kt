package com.example.homesensors.services

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.homesensors.`interface`.onDataReadeListener
import com.example.homesensors.entities.temperatureSensor
import com.example.homesensors.handlers.DatabaseHandler
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class AllSensorsService(application: Application, sensorID: String, dataReadyListener: onDataReadeListener) :
    onDataReadeListener {
    override fun dataReady() {
        Log.d(TAG, "dataReady")
    }

    override fun dataError() {
        Log.d(TAG, "dataError")
    }

    private val dbHandler: DatabaseHandler

    private val TAG = "AllSensorsService"
    private val allSensors: MutableLiveData<List<temperatureSensor>> = MutableLiveData()
    val trySensor1: temperatureSensor = temperatureSensor(2.0, false, "temp")

    var application: Application
    var sensorID: String
    var dataReadyListener: onDataReadeListener

    init {

        Log.d(TAG, "init all sensors service")
//        val sensoreValue: MutableList<temperatureSensor> = ArrayList()
//        sensoreValue.add(trySensor1)


//        this@AllSensorsService.allSensors.value = sensoreValue

        this.application = application
        this.sensorID = sensorID
        this.dataReadyListener = dataReadyListener

        dbHandler = DatabaseHandler(application)
    }

    fun getAllSensors(): LiveData<List<temperatureSensor>> {
        return allSensors
    }

    fun registrationListener() {
        Log.d(TAG, "registrationListener")
        callAllSensors()
    }

    fun unRegistrationListener() {
        Log.d(TAG, "unRegistrationListener")
    }

    fun callAllSensors() {
        val dotaz =
            "{ devices(modelId: 85) {id description descriptionLong lastDataUpdate model {id name vendorName } data } }"
        val map: HashMap<String, String> = hashMapOf("query" to dotaz)
        Log.d(TAG, "callOneSensor " + map.toString())
        dataReadyListener.dataReady()

        dbHandler.request.postWithToken(dbHandler.url, map, dbHandler.token, object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val resposeData = response.body()?.string()
                Log.d(TAG, "onResponse callOneSensor: " + response.toString())

                run {
                    try {
                        val jsonSen = JSONObject(resposeData)
                        val data = jsonSen.optJSONObject("data")
                        val devices = data.getJSONArray("devices")
                        val oneDataSet: JSONObject = devices.getJSONObject(0)
                        val oneTemp = oneDataSet.get("description")

                        val lastData = oneDataSet.getJSONArray("data")
                        val lastTemperatureSet = lastData.getJSONObject(0)
                        val lastTemperature = lastTemperatureSet.get("BASIC_Temp")
                        Log.d(TAG, "onResponse last temperature " + lastTemperature)

                        var finalTemp: Double = 0.0
                        if (lastTemperature is Int) {
                            finalTemp = lastTemperature.toDouble()
                        } else {
                            finalTemp = lastTemperature as Double
                        }

                        setSensorValue(oneTemp as String, finalTemp as Double)

                        Log.d(TAG, "callOneSensor show " + oneTemp.toString())
                    } catch (e: JSONException) {
                        dataReadyListener.dataError()
                        Log.d(TAG, "callOneSensor onResponse Err:" + e.message)
                    }
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "onFailure " + e.message)
            }


        })
    }

    private fun setSensorValue(descrition: String, lastTemperature: Double) {
        Log.d(TAG, "setSensorValue: " + trySensor1.getValue())
        val sensoreValue: MutableList<temperatureSensor> = ArrayList<temperatureSensor>();
        sensoreValue.add(temperatureSensor(lastTemperature, false, descrition))


        this@AllSensorsService.allSensors.postValue(sensoreValue)
        Log.d(TAG, "setSensorValue: " + trySensor1.getValue())
    }

}


