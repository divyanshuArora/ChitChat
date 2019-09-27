package com.example.chitchat.services.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chitchat.services.model.UserModel
import com.google.firebase.database.*

class UserRepository
{

    companion object users
    {
        fun getInstance():UserRepository
        {
            return UserRepository()
        }
    }




    class getUsersList: LiveData<List<UserModel>>()
    {
        override fun onActive() {
            super.onActive()

            FirebaseDatabase.getInstance().getReference("/users").addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    Log.d("UserRepository","databaseError ${p0.message}")
                }

                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.exists())
                    {
                        //value = p0.children.spliterator()
                    }
                }

            })

        }
    }











//
//    fun getUsersList(): LiveData<List<UserModel>>
//    {
//        var data= MutableLiveData<List<UserModel>>()
//        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("/users")
//
//        databaseReference.addValueEventListener(object:ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//
////                value = dataSnapshot.getValue(String::class.java)
//
//                for (dataSnapshot1:DataSnapshot in dataSnapshot.children)
//                {
//                        data.value =
//                }
//
//
//
//                Log.d(TAG, "Value is: " + dataSnapshot.children)
//            }
//            override fun onCancelled(error: DatabaseError) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException())
//            }
//        })
//
//        return data
//    }





}