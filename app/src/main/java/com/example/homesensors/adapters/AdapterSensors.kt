package com.example.homesensors.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.homesensors.R
import com.example.homesensors.`interface`.onItemClickListener
import com.example.homesensors.entities.temperatureSensor
import com.example.homesensors.entities.temperatureSensor.Companion.TYPE_HUMIDITY
import com.example.homesensors.entities.temperatureSensor.Companion.TYPE_TEMPERATURE
import java.util.*


class AdapterSensors(mContext: Context) : RecyclerView.Adapter<AdapterSensors.NoteHolder>() {

    private var allSensors: List<temperatureSensor> = ArrayList()

    private val locContext: Context

    lateinit var listner: onItemClickListener


    fun setOnClikcListener(listener: onItemClickListener) {
        this.listner = listener
    }

    init {
        this.locContext = mContext
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.home_node, parent, false)
        return NoteHolder(itemView, viewType, listner)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val leftPossition: Int = position * 2
        val rightPosstion: Int = ((2 * position) + 1)

        val leftSensor: temperatureSensor = this.getSensorAt(leftPossition)
        when (leftSensor.getTypeSensor()) {
            TYPE_TEMPERATURE -> {
                holder.leftValue.text = leftSensor.getValue().toString() + " C"
            }
            TYPE_HUMIDITY -> {
                holder.leftValue.text = leftSensor.getValue().toString() + " %"
                holder.leftIcon.background = locContext.getDrawable(R.drawable.droplet)
                holder.leftIcon.setImageResource(R.drawable.droplet)
            }
        }

        if (rightPosstion >= itemCount) {
            holder.rightCardView.visibility = View.GONE

        } else {
            val rightSensor: temperatureSensor = this.getSensorAt(rightPosstion)
            when (rightSensor.getTypeSensor()) {
                TYPE_TEMPERATURE -> {
                    holder.rightValue.text = rightSensor.getValue().toString() + " C"
                }
                TYPE_HUMIDITY -> {
                    holder.rightValue.text = rightSensor.getValue().toString() + " %"
                    holder.rightIcon.setImageResource(R.drawable.droplet)
                }
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return 1
    }

    override fun getItemCount(): Int {
        if (allSensors.size % 2 == 0) {
            return allSensors.size / 2
        } else {
            return allSensors.size / 2 + 1
        }
    }

    private fun getSensorAt(position: Int): temperatureSensor {
        return allSensors.get(position)
    }


    fun setAllSensor(listSensors: List<temperatureSensor>) {
        this.allSensors = listSensors
        notifyDataSetChanged()
    }


    inner class NoteHolder(view: View, viewType: Int?, onClickListener: onItemClickListener) :
        RecyclerView.ViewHolder(view) {
        lateinit var leftTitle: TextView
        lateinit var rightTitle: TextView
        lateinit var leftValue: TextView
        lateinit var rightValue: TextView
        lateinit var leftIcon: ImageView
        lateinit var rightIcon: ImageView
        lateinit var leftCardView: CardView;
        lateinit var rightCardView: CardView;

        var onClick: onItemClickListener

        init {
            loadLayout(view, viewType)
            this.onClick = onClickListener

            leftIcon.setOnClickListener(View.OnClickListener(function = {
                onClick.onOpenClick(adapterPosition * 2)
            }))

            leftCardView.setOnClickListener(View.OnClickListener(function = {
                onClick.onOpenClick(adapterPosition * 2)
            }))

            leftValue.setOnClickListener(View.OnClickListener(function = {
                onClick.onOpenClick(adapterPosition * 2)
            }))

            leftTitle.setOnClickListener(View.OnClickListener(function = {
                onClick.onOpenClick(adapterPosition * 2)
            }))


            rightIcon.setOnClickListener(View.OnClickListener(function = {
                onClick.onOpenClick((2 * adapterPosition) + 1)
            }))

            rightCardView.setOnClickListener(View.OnClickListener(function = {
                onClick.onOpenClick((2 * adapterPosition) + 1)
            }))

            rightValue.setOnClickListener(View.OnClickListener(function = {
                onClick.onOpenClick((2 * adapterPosition) + 1)
            }))

            rightTitle.setOnClickListener(View.OnClickListener(function = {
                onClick.onOpenClick((2 * adapterPosition) + 1)
            }))
        }

        private fun loadLayout(view: View, viewType: Int?) {
            if (viewType == 1) {
                leftTitle = view.findViewById(R.id.textTitle_first_column)
                rightTitle = view.findViewById(R.id.textTitle_second_column)

                leftValue = view.findViewById(R.id.value_first_column)
                rightValue = view.findViewById(R.id.value_second_column)

                leftIcon = view.findViewById(R.id.imageView_first_column)
                rightIcon = view.findViewById(R.id.imageView_second_column)

                leftCardView = view.findViewById(R.id.cardView_first_column)
                rightCardView = view.findViewById(R.id.cardView_second_column)
            }
        }
    }

}