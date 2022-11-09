package com.example.simondice.view


import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import com.example.simondice.R


import androidx.lifecycle.Observer
import com.example.simondice.viewModel.MyViewModel


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var btAmarillo: Button
    private lateinit var btAzul: Button
    private lateinit var btRojo: Button
    private lateinit var btVerde: Button
    private lateinit var btStart: Button
    private lateinit var miVistaModelo: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btAmarillo = findViewById(R.id.btAmarillo)
        btAmarillo.setOnClickListener(this)

        btAzul = findViewById(R.id.btAzul)
        btAzul.setOnClickListener(this)

        btRojo = findViewById(R.id.btRojo)
        btRojo.setOnClickListener(this)

        btVerde = findViewById(R.id.btVerde)
        btVerde.setOnClickListener(this)


        miVistaModelo = MyViewModel(btAmarillo, btAzul, btRojo, btVerde, this@MainActivity)

        btStart = findViewById(R.id.btStart)


        btStart.setOnClickListener {

            btStart.isClickable = false
            miVistaModelo.startGame()

        }

        miVistaModelo.liveRonda.observe(
            this,
            Observer(
                fun(ronda: Int) {
                    var tvRonda: TextView = findViewById(R.id.tvRonda)
                    if (ronda == 0) btStart.isClickable = true

                    tvRonda.setText("Ronda: " + ronda)

                }
            )
        )

        miVistaModelo.liveRecord.observe(
            this,
            Observer(
                fun(record: Int) {
                    var tvRecord: TextView = findViewById(R.id.tvRecord)

                    tvRecord.setText("Record: " + record)

                }
            )
        )

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


        }


    }


}