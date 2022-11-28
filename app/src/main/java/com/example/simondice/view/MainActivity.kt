package com.example.simondice.view


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels


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
    val miVistaModelo by viewModels<MyViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Declaracion de los botones junto con el "set" de sus OnClickListener
        btAmarillo = findViewById(R.id.btAmarillo)
        btAmarillo.setOnClickListener(this)

        btAzul = findViewById(R.id.btAzul)
        btAzul.setOnClickListener(this)

        btRojo = findViewById(R.id.btRojo)
        btRojo.setOnClickListener(this)

        btVerde = findViewById(R.id.btVerde)
        btVerde.setOnClickListener(this)

        btStart = findViewById(R.id.btStart)

        introduceNombre()

        btStart.setOnClickListener {

            miVistaModelo.restartGame()

            miVistaModelo.startGame()

        }

        //Observacion del cambio de Ronda
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
        //Observacion del cambio de record
        miVistaModelo.liveRecord.observe(
            this,
            Observer(
                fun(record: Int) {
                    var tvRecord: TextView = findViewById(R.id.tvRecord)

                    tvRecord.setText("Record: " + record)

                }
            )
        )
        /*Observacion del parpadeo de llos botones de colores. Segun el "blink" que cambie, se cambiara
        el color del boton a uno mas claro u oscuro, consiguiendo el efecto de parpadeo
         */
        miVistaModelo.yellowBlink.observe(
            this,
            Observer(
                fun(blink: Boolean) {

                    if (blink) btAmarillo.setBackgroundColor(Color.parseColor("#FFFFFF"))
                    else btAmarillo.setBackgroundColor(Color.parseColor("#BEBB00"))

                }
            )
        )

        miVistaModelo.blueBlink.observe(
            this,
            Observer(
                fun(blink: Boolean) {

                    if (blink) btAzul.setBackgroundColor(Color.parseColor("#FFFFFF"))
                    else btAzul.setBackgroundColor(Color.parseColor("#009FA7"))

                }
            )
        )

        miVistaModelo.redBlink.observe(
            this,
            Observer(
                fun(blink: Boolean) {

                    if (blink) btRojo.setBackgroundColor(Color.parseColor("#FFFFFF"))
                    else btRojo.setBackgroundColor(Color.parseColor("#970000"))

                }
            )
        )

        miVistaModelo.greenBlink.observe(
            this,
            Observer(
                fun(blink: Boolean) {

                    if (blink) btVerde.setBackgroundColor(Color.parseColor("#FFFFFF"))
                    else btVerde.setBackgroundColor(Color.parseColor("#1B9700"))


                }
            )
        )

        //Observacion "activateButton" que dictamina cuando los botones de colores estan activos
        miVistaModelo.activateButton.observe(
            this,
            Observer(
                fun (activated: Boolean) {

                    btVerde.isClickable = activated
                    btRojo.isClickable = activated
                    btAzul.isClickable = activated
                    btAmarillo.isClickable = activated

                }
            )
        )



    }



    @SuppressLint("MissingInflatedId")
    fun introduceNombre() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        builder.setTitle("Introduce tu apodo")
        val dialogLayout = inflater.inflate(R.layout.introduce_nombre, null)
        val editText  = dialogLayout.findViewById<EditText>(R.id.editText)
        builder.setView(dialogLayout)
        builder.setPositiveButton("OK") { dialogInterface, i -> miVistaModelo.getName(editText.toString()) }
        builder.show()
    }



    //OnClickListener de los botones de colores del simon dice
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