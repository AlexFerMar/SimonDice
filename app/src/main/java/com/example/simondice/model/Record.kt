package com.example.simondice.model


import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Estructura de la tabla record
 *
 * Emplea un ID autogenerado como clave primaria
 *
 * Guarda el record y el nombre de quien lo haya sacado
 *
 */
@Entity (tableName = "record")
data class Record (

    @PrimaryKey(autoGenerate = true)
    val recordID: Int,

    val nombre:String,

    val puntuacion:Int

)
