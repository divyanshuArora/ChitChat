package com.example.chitchat.view.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.example.chitchat.R
import com.example.chitchat.databinding.ActivityRegisterBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import org.jetbrains.anko.startActivity
import java.util.concurrent.TimeUnit


class RegisterActivity : AppCompatActivity() {

    var activityRegisterBinding: ActivityRegisterBinding? = null

    var userNumber = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_register)


        activityRegisterBinding!!.registerButton.setOnClickListener {


            userNumber = "+91" + activityRegisterBinding!!.editTextNumber.text.toString()
            Log.d("Register", "userNumber: $userNumber")

            startActivity<OtpActivity>("userNumber" to userNumber)


        }
    }


}