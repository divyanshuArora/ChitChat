package com.example.chitchat.view.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.chitchat.R
import com.example.chitchat.databinding.ActivityDashboardBinding
import com.example.chitchat.view.adapter.TabAdapter
import com.example.chitchat.view.ui.fragment.Chat
import com.example.chitchat.view.ui.fragment.Users
import kotlinx.android.synthetic.main.activity_dashboard.*

class Dashboard : AppCompatActivity() {

    var activityDashboard: ActivityDashboardBinding ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityDashboard = DataBindingUtil.setContentView(this,R.layout.activity_dashboard)
        val adapter = TabAdapter(supportFragmentManager)
        adapter.addFragment(Chat(), "Chat")
        adapter.addFragment(Users(), "Users")
        activityDashboard!!.viewPager.adapter = adapter
        activityDashboard!!.tabLayout.setupWithViewPager(viewPager)

    }
}
