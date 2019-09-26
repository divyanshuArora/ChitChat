package com.example.chitchat.view.ui.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.example.chitchat.R
import com.example.chitchat.databinding.FragmentChatBinding

class Chat : Fragment() {

    var fragmentChatBinding: FragmentChatBinding ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

         fragmentChatBinding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_chat,container,false)

        var view = fragmentChatBinding!!.root

        return view
    }




}
