package com.example.chitchat.view.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.example.chitchat.R
import com.example.chitchat.databinding.ActivitySplashBinding
import com.example.chitchat.services.utils.SharedPreference
import org.jetbrains.anko.startActivity

class Splash : AppCompatActivity() {

    private var activitySplashBinding:ActivitySplashBinding ?= null
    var sharedPreferences: SharedPreference ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = SharedPreference(this)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        activitySplashBinding = DataBindingUtil.setContentView(this,R.layout.activity_splash)

        splashFun()
    }

    private fun splashFun()
    {



            Handler().postDelayed(
                {
                    if (!sharedPreferences!!.getLoginSession())
                    {
                        startActivity<RegisterActivity>()
                        finish()
                    }
                    else {
                        startActivity<Dashboard>()
                        finish()
                    }
                }, 3000
            )
        }
    }

