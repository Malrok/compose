package com.mrk.example.compose.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.mrk.example.compose.models.FirestoreQuery
import com.mrk.example.compose.models.UserEntity
import com.mrk.example.compose.models.UserModel

class MainViewModel : ViewModel() {
    private var db = FirebaseFirestore.getInstance()

    private val usersQuery: MutableLiveData<FirestoreQuery<List<UserModel>>> by lazy {
        return@lazy loadUsers()
    }

    fun getUsers(): LiveData<FirestoreQuery<List<UserModel>>> {
        return usersQuery
    }

    fun getUserById(id: String): LiveData<FirestoreQuery<UserModel>> {
        val data = MutableLiveData<FirestoreQuery<UserModel>>()

        if (id != "-1") {
            this.db
                .collection("users")
                .document(id)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        if (task.result != null) {
                            val user = task.result!!.toObject(UserEntity::class.java)?.toModel(id)
                            data.value = FirestoreQuery(loading = false, data = user!!)
                        }
                    } else {
                        data.value = FirestoreQuery(loading = false, error = true, data = UserModel())
                    }
                }
        } else {
            data.value = FirestoreQuery(loading = false, error = false, data = UserModel())
        }

        return data
    }

    fun pushUser(user: UserModel): LiveData<Boolean> {
        val data = MutableLiveData<Boolean>()

        val collection = this.db.collection("users")

        val documentReference: DocumentReference = if (user.id != null) {
            collection.document(user.id!!)
        } else {
            collection.document()
        }

        documentReference.set(user.toEntity())
            .addOnCompleteListener { task ->
                data.value = task.isSuccessful
            }

        return data
    }

    private fun loadUsers(): MutableLiveData<FirestoreQuery<List<UserModel>>> {
        val data = MutableLiveData<FirestoreQuery<List<UserModel>>>()

        this.db
            .collection("users")
            .limit(10)
            .addSnapshotListener { snapshot, e ->
                if (e == null) {
                    val users = ArrayList<UserModel>()
                    for (document in snapshot?.documents!!) {
                        val user = document.toObject(UserEntity::class.java)?.toModel(document.id)
                        users.add(user!!)
                    }
                    data.value = FirestoreQuery(loading = false, data = users)
                } else {
                    data.value = FirestoreQuery(loading = false, error = true, data = emptyList())
                }
            }

        return data
    }
}