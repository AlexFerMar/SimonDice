package com.example.simondice.viewModel

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import java.util.ArrayList
import kotlin.random.Random

class MyViewModel constructor(
    btAmarillo: Button,
    btAzul: Button,
    btRojo: Button,
    btVerde: Button,
    context: Context
) : ViewModel() {

    private val btAmarillo: Button = btAmarillo
    private val btAzul: Button = btAzul
    private val btRojo: Button = btRojo
    private val btVerde: Button = btVerde
    private val context: Context = context

    private var ronda: Int = 0
    private var record: Int = loadRecord()


    var liveRonda = MutableLiveData<Int>()
    var liveRecord = MutableLiveData<Int>()

    init {

        liveRonda.value = ronda
        liveRecord.value = record

    }

    private val delay: Long = 500
    private var cadenaColores: ArrayList<Int> = ArrayList()
    private var counter = 0


    fun startGame() {

        startRound()
    }


    fun startRound() {

        liveRonda.setValue(ronda)


        randomColor()

        showColor()

    }


    fun randomColor() {

        cadenaColores += Random(System.currentTimeMillis()).nextInt(1, 5)


    }

    fun checkColor(colorIntroducido: Int) {

        if (colorIntroducido.equals(cadenaColores[counter])) {

            if (counter != ronda) {

                counter++

            } else {

                ronda++
                counter = 0

                startRound()

            }

        } else {

            Toast.makeText(context, "Has perdido!", Toast.LENGTH_SHORT).show()

            if (ronda > record) {

                saveRecord(ronda)

                Toast.makeText(context, "Nuevo Record!", Toast.LENGTH_SHORT).show()

            }



            restartData()

        }


    }

    fun restartData() {

        ronda = 0

        liveRecord.setValue(record)

        cadenaColores.clear()

    }

    fun showColor() {

        activateButton(false)

        var jobMuestraColor: Job? = null

        jobMuestraColor = GlobalScope.launch(Dispatchers.Main) {


            for (color: Int in cadenaColores) {

                delay(delay)

                if (color == 1) {

                    btAmarillo.setBackgroundColor(Color.parseColor("#FFFFFF"))
                    delay(delay)
                    btAmarillo.setBackgroundColor(Color.parseColor("#BEBB00"))

                } else if (color == 2) {


                    btAzul.setBackgroundColor(Color.parseColor("#FFFFFF"))
                    delay(delay)
                    btAzul.setBackgroundColor(Color.parseColor("#009FA7"))

                } else if (color == 3) {


                    btRojo.setBackgroundColor(Color.parseColor("#FFFFFF"))
                    delay(delay)
                    btRojo.setBackgroundColor(Color.parseColor("#970000"))

                } else if (color == 4) {

                    btVerde.setBackgroundColor(Color.parseColor("#FFFFFF"))
                    delay(delay)
                    btVerde.setBackgroundColor(Color.parseColor("#1B9700"))

                }

            }

            activateButton(true)
            Toast.makeText(context, "Repite la secuencia!", Toast.LENGTH_SHORT).show()
        }

        jobMuestraColor

    }

    fun activateButton(boolean: Boolean) {

        btVerde.isClickable = boolean
        btRojo.isClickable = boolean
        btAzul.isClickable = boolean
        btAmarillo.isClickable = boolean

    }


    fun saveRecord(record: Int) {
        var pref: SharedPreferences = context.getSharedPreferences("record", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = pref.edit()
        editor.putInt("record", record)
        editor.commit()
        this.record = loadRecord()
    }

    fun loadRecord(): Int {
        val pref: SharedPreferences = context.getSharedPreferences("record", Context.MODE_PRIVATE)
        val savedRecord = pref.getInt("record", 0)
        return savedRecord
    }


}