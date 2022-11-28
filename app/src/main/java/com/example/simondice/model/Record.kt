package com.example.simondice.model


import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity (tableName = "record")
data class Record (

    @PrimaryKey(autoGenerate = true)
    val recordID: Int,

    val nombre:String,

    val puntuacion:Int

)
