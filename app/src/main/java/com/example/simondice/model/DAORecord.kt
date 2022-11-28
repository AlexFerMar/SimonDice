package com.example.simondice.model

import androidx.room.Dao
import androidx.room.*

@Dao
interface DAORecord {

    @Query("Insert into record(nombre,puntuacion) values (:nombre,:record)")
    fun insertRecord(nombre: String, record: Int)

    @Query("Select Max(puntuacion) from record")
    fun getMaxRecord(): Int


}