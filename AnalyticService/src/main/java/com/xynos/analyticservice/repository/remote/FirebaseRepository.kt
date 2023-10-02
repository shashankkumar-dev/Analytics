package com.xynos.analyticservice.repository.remote

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.xynos.analyticservice.model.Event
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepository @Inject constructor(
    private val db: FirebaseFirestore
) {

    suspend fun uploadEventToFirebase(event: Event): Boolean {
        return try {
            Log.i(TAG, "uploadEventToFirebase: $event")
            db.collection(COLLECTION_NAME).add(event).await()
            true

        } catch (e: Exception) {
            Log.e(TAG, "uploadEventToFirebase: $e")
            false
        }
    }

    companion object {
        private const val TAG = "FirebaseRepository"
        const val COLLECTION_NAME = "analytics"
    }
}