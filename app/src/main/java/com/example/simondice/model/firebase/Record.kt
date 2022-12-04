package com.example.simondice.model.firebase

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Record(
    var record: Int?

)