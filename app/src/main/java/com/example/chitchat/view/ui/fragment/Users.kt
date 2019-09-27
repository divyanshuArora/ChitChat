package com.example.chitchat.view.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

import com.example.chitchat.R
import com.example.chitchat.databinding.FragmentUsersBinding
import com.example.chitchat.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth

class Users : Fragment() {

    var fragmentUsersBinding: FragmentUsersBinding ?= null
    var auth: FirebaseAuth ?= null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        fragmentUsersBinding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_users,container,false)
        var view = fragmentUsersBinding!!.root

        auth = FirebaseAuth.getInstance()

        getUserData()
        return view
    }

    private fun getUserData()
    {

        var userViewModel:UserViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)





    }


}
