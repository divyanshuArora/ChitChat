package com.example.chitchat.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chitchat.services.model.UserModel
import com.example.chitchat.services.repository.UserRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

open class UserViewModel(application: Application): AndroidViewModel(application) {


    fun getList(): LiveData<List<UserModel>>
    {

        return UserRepository.getInstance().getUserList()
    }
}








