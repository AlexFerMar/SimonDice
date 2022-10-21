package com.example.simondice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView


class MainActivity : AppCompatActivity() {


    private var ronda: Int = 0
    private var record: Int = 0
    private var cadenaColores = IntArray(1000)
    private var counter = 0
    var colorIntroducido: Int = 0


    lateinit var btAmarillo: Button
    lateinit var btAzul: Button
    lateinit var btRojo: Button
    lateinit var btVerde: Button
    lateinit var btStart: Button
    lateinit var tvRonda: TextView
    lateinit var tvRecord: TextView
    lateinit var ivColor: ImageView

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
        ivColor = findViewById(R.id.ivColor)

        btStart.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {

                btStart.isClickable = false
                startRound()


            }
        })

    }

    fun startRound() {

        randomColor()
        enterColor()

    }

    fun randomColor() {

        cadenaColores += (1..4).random()

    }

    fun showColor(){





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

        if (colorIntroducido == cadenaColores[counter]) {

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

            restart()

        }


    }

    fun restart() {

        ronda = 0

        cadenaColores = IntArray(1000)

        btStart.isClickable = true

    }


}