package com.example.simondice.viewModel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

import kotlinx.coroutines.*
import java.util.ArrayList
import kotlin.random.Random

class MyViewModel(application: Application) : AndroidViewModel(application) {

    private val context: Context = getApplication<Application>().applicationContext

    private var ronda: Int = 0
    private var record: Int = loadRecord()


    var liveRonda = MutableLiveData<Int>()
    var liveRecord = MutableLiveData<Int>()

    init {

        liveRonda.value = ronda
        liveRecord.value = record

    }

    var yellowBlink = MutableLiveData<Boolean>()
    var blueBlink = MutableLiveData<Boolean>()
    var redBlink = MutableLiveData<Boolean>()
    var greenBlink = MutableLiveData<Boolean>()

    var activateButton = MutableLiveData<Boolean>()

    init {

        yellowBlink.value = false
        blueBlink.value = false
        redBlink.value = false
        greenBlink.value = false
        activateButton.value = false

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



            restartGame()

        }


    }

    fun restartGame() {

        ronda = 0

        liveRecord.setValue(record)

        cadenaColores.clear()


    }

    fun showColor() {

        activateButton.setValue(false)

        var jobMuestraColor: Job? = null

        jobMuestraColor = GlobalScope.launch(Dispatchers.Main) {


            for (color: Int in cadenaColores) {

                delay(delay)

                if (color == 1) {

                    yellowBlink.setValue(true)
                    delay(delay)
                    yellowBlink.setValue(false)

                } else if (color == 2) {

                    blueBlink.setValue(true)
                    delay(delay)
                    blueBlink.setValue(false)


                } else if (color == 3) {

                    redBlink.setValue(true)
                    delay(delay)
                    redBlink.setValue(false)


                } else if (color == 4) {

                    greenBlink.setValue(true)
                    delay(delay)
                    greenBlink.setValue(false)

                }

            }

            activateButton.setValue(true)
            Toast.makeText(context, "Repite la secuencia!", Toast.LENGTH_SHORT).show()
        }

        jobMuestraColor

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