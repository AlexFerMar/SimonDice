package com.example.simondice.viewModel

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.simondice.model.AppDatabase


import kotlinx.coroutines.*
import java.util.ArrayList
import kotlin.random.Random

class MyViewModel(application: Application) : AndroidViewModel(application) {

    //Contexto de la aplicacion, que nos permite crear toast y guardar el record en las SharedPreferences
    private val context: Context = getApplication<Application>().applicationContext

    //Creamos una instancia de la base de datos
    val daoRecord = AppDatabase.getDatabase(context).DAORecord()

    //Ronda de la partida
    private var ronda: Int = 0

    //Nombre del jugador
    private lateinit var name: String

    //Record de rondas
    private var record: Int = loadRecord()

    //Con estas variables observamos los cambios en la ronda y el record
    var liveRonda = MutableLiveData<Int>()
    var liveRecord = MutableLiveData<Int>()

    //Inicializamos variables cuando instanciamos
    init {
        liveRonda.value = ronda
        liveRecord.value = record
    }

    //Con estas variables determinamos cuando queremos que el boton cambie de color
    var yellowBlink = MutableLiveData<Boolean>()
    var blueBlink = MutableLiveData<Boolean>()
    var redBlink = MutableLiveData<Boolean>()
    var greenBlink = MutableLiveData<Boolean>()

    //Con esta variable mandamos la orden de encender y apagar los botones mientras la secuencia se muestra
    var activateButton = MutableLiveData<Boolean>()

    //Inicializamos variables cuando instanciamos
    init {

        yellowBlink.value = false
        blueBlink.value = false
        redBlink.value = false
        greenBlink.value = false
        activateButton.value = false

    }

    //Delay que queremos entre parpadeos de boton
    private val delay: Long = 500

    //Arraylist de enteros que mas tarde se traduciran como colores
    private var cadenaColores: ArrayList<Int> = ArrayList()

    //Contador interno para comprobar si es correcta la secuencia
    private var counter = 0


    //Funcion que recoje el valor del nombre del jugador
    fun getName(name:String){

        this.name=name


    }


    /**
     * Funcion para iniciar el juego
     */
    fun startGame() {

        startRound()

    }

    /**
     * Funcion que empieza una nueva ronda
     */
    fun startRound() {

        liveRonda.setValue(ronda)

        randomColor()

        showColor()

    }

    /**
     * Funcion que genera un numero entero al azar y lo añade a la array "cadenaColores"
     */
    fun randomColor() {

        cadenaColores += Random(System.currentTimeMillis()).nextInt(1, 5)


    }

    /**
     * Funcion que comprueba que el boton pulsado por el usuario es el correcto
     *
     * colorIntroducido: Int -> Valor numerico que se genera cuando el jugador pulsa un boton
     */
    fun checkColor(colorIntroducido: Int) {

        //Esta es la ruta a seguir si se acierta el color
        if (colorIntroducido.equals(cadenaColores[counter])) {

            //Si aun no se ha completado la comprobacion de la array se aumenta el contador
            if (counter != ronda) {

                counter++

            }
            /*Si se ha introducido el ultimo numero de la secuencia correctamente, se aumenta el
            numero de ronda, se reinicia el contador y se inicia una ronda nueva
            */
            else {

                ronda++
                counter = 0

                startRound()

            }

        }


        /*Si se falla en cualquier momento de la secuencia, sale un toast avisando de la derrota
        y se reincian los valores de la ronda
         */
        else {

            Toast.makeText(context, "Has perdido!", Toast.LENGTH_SHORT).show()

            /*Si la ronda alcanzada es mayor que la del record anterior se almacena como el nuevo
            record y sale un toast de aviso
             */
            if (ronda > record) {

                saveRecord(ronda)

                Toast.makeText(context, "Nuevo Record!", Toast.LENGTH_SHORT).show()

            }

            restartGame()

        }


    }

    /**
     * Funcion que reinicia los valores para empezar una nueva partida
     */
    fun restartGame() {

        ronda = 0

        liveRecord.setValue(loadRecord())

        cadenaColores.clear()


    }


    /**
     * Funcion que inicia la secuencia de los botones del simon dice
     */
    fun showColor() {

        //Se apagan los botones para que el jugador no pueda sabotear la aplicacion mientras se muestra la secuencia
        activateButton.setValue(false)

        var jobMuestraColor: Job? = null

        //Se genera una corrutina para mostrar la secuencia de los botones
        jobMuestraColor = GlobalScope.launch(Dispatchers.Main) {

            //bucle que va "parpadenado" cada boton segun el entero que tenga asignado
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

            //Se le devuelve la funcionalidad a los botones
            activateButton.setValue(true)
            //Se genera un toast avisando al jugador que ya puede introducir la secuencia
            Toast.makeText(context, "Repite la secuencia!", Toast.LENGTH_SHORT).show()
        }
        //se llama la corrutina creada anteriormente
        jobMuestraColor

    }


    /**
     * Funcion que guarada en la base de datos local el record y el apodo de quien lo saco
     *
     * record: Int -> El número que se guarda comno record
     */
    fun saveRecord(record: Int) {

        daoRecord.insertRecord(name,record)

    }

    /**
     * Funcion que lee el record guardado
     *
     * return savedRecord: Int -> El entero almacenado como record en ese momento
     */
    fun loadRecord(): Int {

        var recor=daoRecord.getMaxRecord()

        if (recor==null) recor=0

        return recor

    }






    /*


    /**
     * Funcion que guarada un entero en SharedPreferences como el nuevo record
     *
     * record: Int -> El número que se guarda comno record
     */
    fun saveRecord(record: Int) {
        var pref: SharedPreferences = context.getSharedPreferences("record", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = pref.edit()
        editor.putInt("record", record)
        editor.commit()
        this.record = loadRecord()
    }

    /**
     * Funcion que lee el record guardado en SharedPreferences
     *
     * return savedRecord: Int -> El entero almacenado como record en ese momento
     */
    fun loadRecord(): Int {
        val pref: SharedPreferences = context.getSharedPreferences("record", Context.MODE_PRIVATE)
        val savedRecord = pref.getInt("record", 0)
        return savedRecord
    }



    */

}