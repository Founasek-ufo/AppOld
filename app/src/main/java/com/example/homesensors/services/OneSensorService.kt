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

class OneSensorService(application: Application, sensorID: String, dataReadyListener: onDataReadeListener) :
    onDataReadeListener {
    override fun dataReady() {
        Log.d(TAG, "dataReady")
    }

    private val dbHandler: DatabaseHandler

    private val TAG = "OneSensorService"
    private var oneSensor: MutableLiveData<temperatureSensor> = MutableLiveData()
    private val trySensor: temperatureSensor = temperatureSensor(12.0, false, "ddd")

    private var arrayValue: MutableLiveData<Array<Double>> = MutableLiveData()
    private var tryArrayValue: Array<Double> = arrayOf(10.0, 12.0)


    var application: Application
    var sensorID: String
    var dataReadyListener: onDataReadeListener


    init {
        oneSensor.value = trySensor
        arrayValue.value = tryArrayValue

        this.application = application
        this.sensorID = sensorID
        this.dataReadyListener = dataReadyListener


        dbHandler = DatabaseHandler(application)
    }

    fun getOneSensors(): LiveData<temperatureSensor> {
        return oneSensor
    }

    fun getArrayValue(): LiveData<Array<Double>> {
        return arrayValue
    }

    fun registrationListener() {
        callOneSensor("c9ec2fbe-3af2-4d4e-80bd-830cc3d34543")
        Log.d(TAG, "registrationListener")
    }

    fun unRegistrationListener() {
        Log.d(TAG, "unRegistrationListener")
    }

    fun callOneSensor(sensorID: String) {
        val dotaz = "{ device(id: \"c9ec2fbe-3af2-4d4e-80bd-830cc3d34543\") { id data } }"
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
                        val device = data.optJSONObject("device")
                        val dataSen = device.getJSONArray("data")
                        val oneDataSet: JSONObject = dataSen.getJSONObject(0)
                        val oneTemp = oneDataSet.get("BASIC_Temp")

                        Log.d(TAG, "callOneSensor show " + oneDataSet.toString())
                        Log.d(TAG, "callOneSensor show " + oneTemp.toString())

                        val finalTemp: Double
                        if (oneTemp is Int) {
                            finalTemp = oneTemp.toDouble()
                        } else {
                            finalTemp = oneTemp as Double
                        }




                        setSensorValue(finalTemp)

                        var pole = arrayOf<Double>()
                        for (x in 0 until 100 step 1) {
                            val oneDataSetFor = dataSen.getJSONObject(x)
                            val oneTempFor = oneDataSetFor.get("BASIC_Temp")
                            var finalTemp: Double
                            if (oneTempFor is Int) {
                                finalTemp = oneTempFor.toDouble()
                            } else {
                                finalTemp = oneTempFor as Double
                            }
                            pole += finalTemp
                        }

                        setArrayValue(pole)

                        Log.d(TAG, "onResponse pole size " + pole.size)
                        for (poleS in pole) {
                            Log.d(TAG, "onResponse pole " + poleS)
                        }
                    } catch (e: JSONException) {
                        Log.d(TAG, "callOneSensor onResponse Err:" + e.message)
                    }
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "onFailure " + e.message)
            }


        })
    }

    private fun setSensorValue(lastValue: Double) {
        Log.d(TAG, "setSensorValue: " + trySensor.getValue())
        val newTrySensor = temperatureSensor(lastValue, true, "ddd")
        oneSensor.postValue(newTrySensor)
        Log.d(TAG, "setSensorValue: " + trySensor.getValue())
    }

    private fun setArrayValue(arryValue: Array<Double>) {
        arrayValue.postValue(arryValue)
    }

}