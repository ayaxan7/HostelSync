package com.hustle.away.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class SupportViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    fun submitSupportRequest(
        title: String,
        description: String,
        remarks: String,
    ) {
        val askingRef = db.collection("Asking")

        // Use timestamp as the document ID
        val timestamp = System.currentTimeMillis().toString()

        val askData = mapOf(
            "title" to title,
            "description" to description,
            "remarks" to remarks
        )

        askingRef.document(timestamp).set(askData)
            .addOnSuccessListener {
                Log.d("SupportViewModel", "Support request submitted successfully")

            }
            .addOnFailureListener { e ->
                Log.e("SupportViewModel", "Error submitting support request", e)
            }
    }
}
