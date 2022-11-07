package com.example.simondice


import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import java.util.*
import kotlin.random.Random


class MainActivity : AppCompatActivity(), View.OnClickListener {


    private var ronda: Int = 0
    private var record: Int = 0
    var cadenaColores: ArrayList<Int> = ArrayList()
    private var counter = 0
    var colorIntroducido: Int = 0

    lateinit var btAmarillo: Button
    lateinit var btAzul: Button
    lateinit var btRojo: Button
    lateinit var btVerde: Button
    lateinit var btStart: Button
    lateinit var tvRonda: TextView
    lateinit var tvRecord: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadRecord()

        btAmarillo = findViewById(R.id.btAmarillo)
        btAmarillo.setOnClickListener(this)
        btAzul = findViewById(R.id.btAzul)
        btAzul.setOnClickListener(this)
        btRojo = findViewById(R.id.btRojo)
        btRojo.setOnClickListener(this)
        btVerde = findViewById(R.id.btVerde)
        btVerde.setOnClickListener(this)
        btStart = findViewById(R.id.btStart)
        tvRonda = findViewById(R.id.tvRonda)
        tvRecord = findViewById(R.id.tvRecord)

        tvRecord.setText("Record: " + record)





        btStart.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {

                btStart.isClickable = false
                startRound()

            }
        })

    }

    fun startRound() {

        randomColor()

    }

    fun randomColor() {

        cadenaColores += Random(System.currentTimeMillis()).nextInt(1, 5)

        showColor()

    }

    fun showColor() {

        activateButton(false)

        var jobMuestraColor: Job? = null

        jobMuestraColor = GlobalScope.launch(Dispatchers.Main) {

            //TODO Variar la velocidad con las rondas

            for (color: Int in cadenaColores) {

                delay(500)

                if (color == 1) {

                    btAmarillo.setBackgroundColor(Color.parseColor("#FFFFFF"))
                    delay(1000)
                    btAmarillo.setBackgroundColor(Color.parseColor("#BEBB00"))

                } else if (color == 2) {


                    btAzul.setBackgroundColor(Color.parseColor("#FFFFFF"))
                    delay(1000)
                    btAzul.setBackgroundColor(Color.parseColor("#009FA7"))

                } else if (color == 3) {


                    btRojo.setBackgroundColor(Color.parseColor("#FFFFFF"))
                    delay(1000)
                    btRojo.setBackgroundColor(Color.parseColor("#970000"))

                } else if (color == 4) {

                    btVerde.setBackgroundColor(Color.parseColor("#FFFFFF"))
                    delay(1000)
                    btVerde.setBackgroundColor(Color.parseColor("#1B9700"))

                }

            }

            activateButton(true)
            Toast.makeText(this@MainActivity, "Repite la secuencia!", Toast.LENGTH_SHORT).show()
        }

        jobMuestraColor

    }


    fun activateButton(boolean: Boolean) {

        btVerde.isClickable = boolean
        btRojo.isClickable = boolean
        btAzul.isClickable = boolean
        btAmarillo.isClickable = boolean

    }

    fun checkColor() {

        if (colorIntroducido.equals(cadenaColores[counter])) {

            if (counter != ronda) {

                counter++

            } else {

                ronda++
                counter = 0
                tvRonda.setText("Ronda: " + ronda)

                startRound()

            }

        } else {

            Toast.makeText(this@MainActivity, "Has perdido!", Toast.LENGTH_SHORT).show()

            if (ronda > record) {

                saveRecord(ronda)

                Toast.makeText(this@MainActivity, "Nuevo Record!", Toast.LENGTH_SHORT).show()

            }



            restart()

        }


    }

    fun saveRecord(record: Int) {
        var pref: SharedPreferences = getSharedPreferences("record", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = pref.edit()
        editor.putInt("record", record)
        editor.commit()
    }

    private fun loadRecord(){
        val pref: SharedPreferences = getSharedPreferences("record", Context.MODE_PRIVATE)
        val savedRecord = pref.getInt("record",0)
        record= savedRecord
    }


    fun restart() {

        loadRecord()

        activateButton(false)

        tvRecord.setText("Record: " + record)

        ronda = 0

        tvRonda.setText("Ronda: " + ronda)

        cadenaColores.clear()

        btStart.isClickable = true

    }

    override fun onClick(view: View?) {
        when (view?.id) {

            btAmarillo.id -> {

                colorIntroducido = 1

                checkColor()

            }
            btAzul.id -> {

                colorIntroducido = 2

                checkColor()

            }

            btRojo.id -> {

                colorIntroducido = 3

                checkColor()

            }

            btVerde.id -> {

                colorIntroducido = 4

                checkColor()

            }

            btStart.id -> {

                btStart.isClickable = false
                startRound()

            }


        }


    }


}