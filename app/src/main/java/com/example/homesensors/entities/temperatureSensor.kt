package com.example.homesensors.entities

import java.util.*

/*temp -> temperature, humd -> humidity
*/
class temperatureSensor(value: Double, syns: Boolean, serialNumber: String) {

    var SERIAL_TEMPERATURE: String = "temp"
    var SERIAL_HUMIDITY: String = "humd"

    companion object {
        const val TYPE_TEMPERATURE = 1
        const val TYPE_HUMIDITY = 2
    }

    private var value: Double
    private var syns: Boolean
    private var synsLastDate: Calendar
    private val serialNumber: String


    init {
        this.value = value
        this.syns = syns
        this.serialNumber = serialNumber
        synsLastDate = getCurrentTime()
    }

    fun getCurrentTime(): Calendar{
        return Calendar.getInstance()
    }

    fun setCurrentTime(){
        synsLastDate = getCurrentTime()
    }

    fun getValue(): Double{
        return value
    }

    fun setValue(temperature: Double){
        this.value = temperature
    }

    fun getSynsState(): Boolean{
        return syns
    }

    fun setSynsState(state: Boolean){
        this.syns = state
    }

    fun getLastDateSyns(): Calendar{
        return synsLastDate
    }

    fun getTypeSensor(): Int{
        when (serialNumber) {
            SERIAL_HUMIDITY -> return TYPE_HUMIDITY
            SERIAL_TEMPERATURE -> return TYPE_TEMPERATURE
            else -> return TYPE_TEMPERATURE
        }
    }

}