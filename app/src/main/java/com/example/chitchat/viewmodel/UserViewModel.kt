package com.example.chitchat.viewmodel

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chitchat.services.model.UserModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserViewModel: ViewModel()
{



    open class getUsersList : LiveData<List<UserModel>>() {
            override fun onActive() {
                super.onActive()
                var users = MutableLiveData<UserModel>()
                FirebaseDatabase.getInstance().getReference("/users")
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {

                            Log.d("UserRepository", "databaseError ${p0.message}")
                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            if (p0.exists()) {


                                Log.d("UserRepository", "UserList ${p0.children}")
                            }
                        }

                    })

            }
        }
    }


