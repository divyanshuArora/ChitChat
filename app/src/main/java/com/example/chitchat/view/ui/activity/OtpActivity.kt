package com.example.chitchat.view.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import com.example.chitchat.R
import com.example.chitchat.databinding.ActivityOtpBinding
import com.example.chitchat.services.utils.SharedPreference
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import io.reactivex.internal.util.HalfSerializer.onComplete
import org.jetbrains.anko.startActivity
import java.lang.Exception
import java.util.concurrent.TimeUnit

class OtpActivity : AppCompatActivity() {

    var activityOtpBinding: ActivityOtpBinding ?= null
    var userNumber = ""
    var otp = ""
    var auth: FirebaseAuth?= null
    var mverificationId = ""
    var code = ""
    var sharedPreferences: SharedPreference ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityOtpBinding = DataBindingUtil.setContentView(this,R.layout.activity_otp)
        userNumber = intent.getStringExtra("userNumber")
        sharedPreferences = SharedPreference(this)

        auth = FirebaseAuth.getInstance()
        firebaseRegister(userNumber)


        activityOtpBinding!!.registerButton.setOnClickListener {

            otp = activityOtpBinding!!.editTextOTP.text.toString()
            verifyCode(otp)
        }
    }


    private fun firebaseRegister(userNumber: String)
    {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            userNumber, // Phone number to verify
            120,             // Timeout duration
            TimeUnit.SECONDS,   // Unit of timeout
            this,           // Activity (for callback binding)
            mCallbacks)
        Toast.makeText(applicationContext,"Otp Sent",Toast.LENGTH_SHORT).show()
    }


    val mCallbacks = object: PhoneAuthProvider.OnVerificationStateChangedCallbacks()
    {
        override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken)
        {
            super.onCodeSent(p0, p1)
            mverificationId = p0
            Log.d("Register","token: $mverificationId")
        }

        override fun onCodeAutoRetrievalTimeOut(p0: String) {
            Log.d("OtpActivity","timeout: $p0")
            Toast.makeText(applicationContext,"Time Out",Toast.LENGTH_SHORT).show()
        }

        override fun onVerificationCompleted(p0: PhoneAuthCredential)
        {

            if (p0.smsCode!= null) {
                code = p0.smsCode!!
                Log.d("OtpActivity", "completed: $code")
            }

        }

        override fun onVerificationFailed(p0: FirebaseException)
        {
            Log.d("OtpActivity","failed: ${p0.message}")
            Toast.makeText(applicationContext,"Failed",Toast.LENGTH_SHORT).show()

        }
    }


    private fun verifyCode(otp: String)
    {

        when {
            otp.isEmpty() -> {
                Toast.makeText(this@OtpActivity,"Enter Otp",Toast.LENGTH_SHORT).show()
                return

            }
            mverificationId.isEmpty() -> {
                Log.d("OtpActivity","Number Blocked From Firebase")
                return

            }
            else -> {

                var credential = PhoneAuthProvider.getCredential(otp,mverificationId!!)
                signInWithCredential(credential)
            }
        }
    }

    private fun signInWithCredential(phoneAuthCredential: PhoneAuthCredential) {

        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
            .addOnCompleteListener(this@OtpActivity,
                OnCompleteListener<AuthResult> { task ->
                    if (task.isSuccessful) {
                        //verification successful we will start the profile activity
                        Log.d("OtpActivity", "Success")

                        val user_id = FirebaseAuth.getInstance().currentUser.toString()
                        Log.d("OtpActivity", "userId: $user_id")
                        sharedPreferences!!.setLoginSession(user_id)
                        //sharedPreferences!!.setLoginSession("true")
                        // startActivity<Dashboard>()

                        val intent = Intent(this@OtpActivity, Dashboard::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)

                        Toast.makeText(this@OtpActivity, "Success", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        var message = "Invalid code entered...  ${task.exception}"
                        Toast.makeText(this@OtpActivity, "" + message, Toast.LENGTH_SHORT).show()
                    }
                })

                }
    }






