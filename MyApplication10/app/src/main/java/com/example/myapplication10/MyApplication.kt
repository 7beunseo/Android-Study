package com.example.myapplication10

import androidx.multidex.MultiDexApplication
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthSettings
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

// Dex : Dalvic Executale (64k)
class MyApplication : MultiDexApplication() {
    companion object {
        fun checkAuth(): Boolean {
            var currentUser = auth.currentUser
            if(currentUser != null) {
                email = currentUser.email
                return currentUser.isEmailVerified
            }
            return false
        }

        lateinit var auth: FirebaseAuth
        var email: String? = null
        lateinit var db: FirebaseFirestore
        lateinit var storeage: FirebaseStorage

    }
    override fun onCreate() {
        super.onCreate()

        auth = Firebase.auth

        db = FirebaseFirestore.getInstance()

    }




}