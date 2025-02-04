package com.hustle.awayadminside.viewmodels

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class NoticeViewModel {
    private val db = FirebaseFirestore.getInstance()

    fun submitSupportRequest(
        title: String,
        description: String,
        remarks: String,
    ) {
        val askingRef = db.collection("Telling")

        // Use timestamp as the document ID
        val timestamp = System.currentTimeMillis().toString()

        val tellData = mapOf(
            "title" to title,
            "description" to description,
            "remarks" to remarks
        )

        askingRef.document(timestamp).set(tellData)
            .addOnSuccessListener {
                Log.d("SupportViewModel", "Notice added successfully")

            }
            .addOnFailureListener { e ->
                Log.e("SupportViewModel", "Error adding notice on bulletin", e)
            }
    }
}