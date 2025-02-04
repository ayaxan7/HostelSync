package com.hustle.awayadminside.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.hustle.awayadminside.dataclasses.SupportRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class HomeScreenViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _supportRequests = MutableStateFlow<List<SupportRequest>>(emptyList())
    val supportRequests: StateFlow<List<SupportRequest>> = _supportRequests

    fun fetchSupportRequests() {
        viewModelScope.launch {
            db.collection("Asking")
                .get()
                .addOnSuccessListener { result ->
                    val requests = result.documents.map { doc ->
                        SupportRequest(
                            title = doc.getString("title") ?: "",
                            description = doc.getString("description") ?: "",
                            remarks = doc.getString("remarks") ?: "",
                            timestamp = formatTimestamp(doc.id)  // Convert timestamp
                        )
                    }
                    _supportRequests.value = requests
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun formatTimestamp(timestamp: String): String {
        return try {
            val millis = timestamp.toLong()
            val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a") // Example: 05 Feb 2025, 10:30 AM
            sdf.format(Date(millis))
        } catch (e: Exception) {
            Log.e("SupportViewModel", "Error formatting timestamp", e)
            "Invalid date"
        }
    }
}

