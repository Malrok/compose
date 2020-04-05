package com.mrk.example.compose.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.moventes.moventest.android.models.User

class MainViewModel : ViewModel() {
    private var db = FirebaseFirestore.getInstance()

    private val users: MutableLiveData<List<User>> by lazy {
        return@lazy loadUsers()
    }

    fun getUsers(): LiveData<List<User>> {
        return users
    }

    private fun loadUsers(): MutableLiveData<List<User>> {
        val data = MutableLiveData<List<User>>()

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
                    data.value = users
                }
            }

        return data
    }
}