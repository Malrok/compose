package com.mrk.example.compose.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.moventes.moventest.android.models.User
import com.mrk.example.compose.models.FirestoreQuery

class MainViewModel : ViewModel() {
    private var db = FirebaseFirestore.getInstance()

    private val usersQuery: MutableLiveData<FirestoreQuery<User>> by lazy {
        return@lazy loadUsers()
    }

    fun getUsers(): LiveData<FirestoreQuery<User>> {
        return usersQuery
    }

    private fun loadUsers(): MutableLiveData<FirestoreQuery<User>> {
        val data = MutableLiveData<FirestoreQuery<User>>()

        this.db
            .collection("users")
            .limit(10)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val users = ArrayList<User>()
                    for (document in task.result!!) {
                        users.add(document.toObject(User::class.java))
                    }
                    data.value = FirestoreQuery(loading = false, data = users)
                } else {
                    data.value = FirestoreQuery(loading = false, error = true)
                }
            }

        return data
    }
}