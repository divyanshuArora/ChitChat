package com.example.chitchat.services.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chitchat.services.model.UserModel
import com.firebase.ui.auth.data.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


open class UserRepository
{
    companion object Factory
        {
            fun getInstance(): UserRepository
            {
                return UserRepository()
            }
        }

    fun getUserList(): LiveData<List<UserModel>>
    {

        var user1: MutableLiveData<List<UserModel>> ?= null

        FirebaseDatabase.getInstance().getReference("/users")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Log.d("UserRepository", "databaseError ${p0.message}")
                }

                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.exists())
                    {
                        for (ds: DataSnapshot in p0.children) {
                                try {
                                    val userModelHashMap = ds.value as HashMap<String, String>
//                                    var userList: List<UserModel> = MutableList()<UserModel>(userModelHashMap.values)
//                                    user = MutableLiveData<List<UserModel>> (userModelHashMap.values)
                                    //user = userList

//                                    var list: List<UserModel> = userModelHashMap.values as List<UserModel>
//                                    Log.d("UserRepo",list.toString())

                                    val valueList: List<String?> = ArrayList<String?>(userModelHashMap.values)
                                    Log.d("UserRepo","ValueList  $valueList")



                                    //user!!.value = valueList as List<UserModel>
                                    Log.d("UserRepo","userValue $valueList!!")

                                    val user  = UserModel(valueList[0]!!,valueList[2]!!,valueList[1]!!)

                                    user1!!.value = user as List<UserModel>



                                    //user?.value = userModelHashMap.values as MutableList<UserModel>?
//                                    user!!.value



//                                          //var user:UserModel = p0.getValue(UserModel::class.java)!!
//                                           user = ds.value as List<UserModel>
//                                           Log.d("UserViewModel", "userValue $user")
                        }
                                catch (e:Exception)
                                {
                                    Log.d("UserRepository", "Exception $e")
                                }
                        }


                    }
                }
            })
        return user1!!
    }



}