package com.example.chitchat.view.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.chitchat.R
import com.example.chitchat.databinding.FragmentUsersBinding
import com.example.chitchat.services.model.UserModel
import com.example.chitchat.view.adapter.UserAdapter
import com.example.chitchat.viewmodel.UserViewModel
import kotlin.Exception

class Users : Fragment() {

    var fragmentUsersBinding: FragmentUsersBinding ?= null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        fragmentUsersBinding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_users,container,false)

        return fragmentUsersBinding!!.root
    }


    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        getUserData()
    }

    private fun getUserData()
    {

        try {


        var userViewModel:UserViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)

        //var user: List<UserModel> = context?.let { userViewModel.getList(it) } as List<UserModel>

         userViewModel?.getList().observe(this, Observer {

            fragmentUsersBinding!!.setVariable(BR.userData,it)


             var recyclerView = fragmentUsersBinding!!.recyclerUser
             recyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

             var adapter = UserAdapter(context!!,it)
             recyclerView.adapter = adapter

         })




        }
        catch (e:Exception)
        {
            Log.d("UsersFragment","exception: $e")
        }

    }


}
