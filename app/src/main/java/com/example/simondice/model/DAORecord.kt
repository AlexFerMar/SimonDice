package com.example.simondice.model

import androidx.room.Dao
import androidx.room.*

@Dao
interface DAORecord {

    //Query empleada para guardar un record (se guarda un nombre y la ronda record
    @Query("Insert into record(nombre,puntuacion) values (:nombre,:record)")
    fun insertRecord(nombre: String, record: Int)

    //Query que selecciona la puntuacion maxima de la base de datos (de momento, con el estado actual del programa, solo esta query es necesaria)
    @Query("Select Max(puntuacion) from record")
    fun getMaxRecord(): Int


}