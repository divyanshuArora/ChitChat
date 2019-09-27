package com.example.chitchat.view.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.example.chitchat.R
import com.example.chitchat.databinding.ActivityDashboardBinding
import com.example.chitchat.services.utils.SharedPreference
import com.example.chitchat.view.adapter.TabAdapter
import com.example.chitchat.view.ui.fragment.Chat
import com.example.chitchat.view.ui.fragment.Users
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.android.synthetic.main.activity_dashboard.*
import org.jetbrains.anko.startActivity

class Dashboard : AppCompatActivity() {

    var activityDashboard: ActivityDashboardBinding ?= null
    var sharedPreference: SharedPreference ?= null
    var mGoogleSignIn:GoogleSignInClient ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDashboard = DataBindingUtil.setContentView(this,R.layout.activity_dashboard)

        sharedPreference = SharedPreference(this)


        val adapter = TabAdapter(supportFragmentManager)
        adapter.addFragment(Chat(), "Chat")
        adapter.addFragment(Users(), "Users")
        activityDashboard!!.viewPager.adapter = adapter
        activityDashboard!!.tabLayout.setupWithViewPager(viewPager)

    }


    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.logout)
        {
            sharedPreference!!.clearSharedPreference()
            AuthUI.getInstance().signOut(this).addOnCompleteListener {startActivity<RegisterActivity>()}


            finish()
        }

        return super.onOptionsItemSelected(item)
    }
}
