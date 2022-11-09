package com.example.simondice.view

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import com.example.simondice.R

// para observar LiveDatas
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.simondice.viewModel.MyViewModel


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var ronda: Int = 0
    private var record: Int = 0

    val miVistaModelo by viewModels<MyViewModel>()

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

        miVistaModelo.loadRecord()

        btAmarillo = findViewById(R.id.btAmarillo)
        btAmarillo.setOnClickListener(this)

        btAzul = findViewById(R.id.btAzul)
        btAzul.setOnClickListener(this)

        btRojo = findViewById(R.id.btRojo)
        btRojo.setOnClickListener(this)

        btVerde = findViewById(R.id.btVerde)
        btVerde.setOnClickListener(this)

        btStart = findViewById(R.id.btStart)
        btStart.setOnClickListener(this)

        tvRonda = findViewById(R.id.tvRonda)
        tvRecord = findViewById(R.id.tvRecord)


        tvRonda.setText("Ronda: " + ronda)

        tvRecord.setText("Record: " + record)

    }



    override fun onClick(view: View?) {
        when (view?.id) {

            btAmarillo.id -> {

                miVistaModelo.checkColor(1)

            }
            btAzul.id -> {

                miVistaModelo.checkColor(2)

            }

            btRojo.id -> {

                miVistaModelo.checkColor(3)

            }

            btVerde.id -> {


                miVistaModelo.checkColor(4)

            }

            btStart.id -> {

                btStart.isClickable = false
                miVistaModelo.startGame(btAmarillo,btAzul,btRojo,btVerde,this@MainActivity)
            }


        }


    }


}