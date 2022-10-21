package com.example.simondice

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button


import android.widget.TextView



class MainActivity : AppCompatActivity() {


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
        btAmarillo = findViewById(R.id.btAmarillo)
        btAzul = findViewById(R.id.btAzul)
        btRojo = findViewById(R.id.btRojo)
        btVerde = findViewById(R.id.btVerde)
        btStart = findViewById(R.id.btStart)
        tvRonda = findViewById(R.id.tvRonda)
        tvRecord = findViewById(R.id.tvRecord)


        btStart.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {

                btStart.isClickable = false
                startRound()


            }
        })

    }

    fun startRound() {

        Log.d("Starting Round", "Empieza la ronda")

        randomColor()

    }

    fun randomColor() {

        cadenaColores += (1..4).random()

        Log.d("Color seleccionado", cadenaColores.last().toString())

        showColor()

    }

    fun showColor() {

        for (color: Int in cadenaColores) {

            Log.d("Color mostrado", cadenaColores.last().toString())

            if (color == 0) break
            else if (color == 1) {
                btAmarillo.setBackgroundColor(Color.parseColor("FFFB00"))
                Thread.sleep(2000)
                btAmarillo.setBackgroundColor(Color.parseColor("BEBB00"))
            } else if (color == 2) {
                btAzul.setBackgroundColor(Color.parseColor("#00F3FF"))
                Thread.sleep(2000)
                btAzul.setBackgroundColor(Color.parseColor("#009FA7"))
            } else if (color == 3) {
                btRojo.setBackgroundColor(Color.parseColor("#FF0000"))
                Thread.sleep(2000)
                btRojo.setBackgroundColor(Color.parseColor("#970000"))
            } else if (color == 4) {
                btVerde.setBackgroundColor(Color.parseColor("#2EFF00"))
                Thread.sleep(2000)
                btVerde.setBackgroundColor(Color.parseColor("#1B9700"))
            }
            //TODO Variar la velocidad con las rondas

        }

        enterColor()

    }


    fun enterColor() {

        btAmarillo.setOnClickListener() {

            colorIntroducido = 1
            checkColor()

        }
        btAzul.setOnClickListener() {

            colorIntroducido = 2
            checkColor()

        }

        btRojo.setOnClickListener() {

            colorIntroducido = 3
            checkColor()

        }

        btVerde.setOnClickListener() {

            colorIntroducido = 4
            checkColor()

        }

    }

    fun checkColor() {

        if (colorIntroducido.equals(cadenaColores[counter])) {

            if (counter != ronda) {

                counter++

            } else {

                ronda++
                counter = 0
                startRound()

            }

        } else {
            //todo Almacenar el record y mostrarlo de verdad
            record = ronda

            Log.d("Stadasdasdadd", "asdasdasdasds")



            restart()

        }


    }

    fun restart() {

        ronda = 0

        cadenaColores.clear()

        btStart.isClickable = true

    }


}