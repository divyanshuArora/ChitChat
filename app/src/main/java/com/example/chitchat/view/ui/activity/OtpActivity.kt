package com.example.chitchat.view.ui.activity

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

            if (otp.isEmpty()) {

                activityOtpBinding!!.editTextOTP.error = "Error"
                return@setOnClickListener
            }
            else if(otp.equals(code))
            {
                verifyCode(otp)
            }
            else
            {
                Toast.makeText(applicationContext,"Code Not Matched",Toast.LENGTH_SHORT).show()
            }
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
            Log.d("Register","token: $p1")
            mverificationId = p0

        }

        override fun onCodeAutoRetrievalTimeOut(p0: String) {
            Log.d("OtpActivity","timeout: $p0")
            Toast.makeText(applicationContext,"Time Out",Toast.LENGTH_SHORT).show()
        }

        override fun onVerificationCompleted(p0: PhoneAuthCredential)
        {
            Log.d("OtpActivity","completed: ${p0.smsCode}")
            code = p0.smsCode.toString()

            Toast.makeText(applicationContext,"Completed",Toast.LENGTH_SHORT).show()

        }

        override fun onVerificationFailed(p0: FirebaseException)
        {
        Log.d("OtpActivity","failed: $p0")
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
                Log.d("OtpActivity","Otp Not Sent")
                return

            }
            else -> {

                var credential = PhoneAuthProvider.getCredential(otp,mverificationId!!)
                signInWithCredential(credential)
            }
        }

    }

    private fun signInWithCredential(phoneAuthCredential: PhoneAuthCredential)
    {
        try {
            auth!!.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(this@OtpActivity) {

                    @Override
                    fun onComplete(@NonNull task: Task<AuthResult>) {
                        if (task.isSuccessful) {
                            //verification successful we will start the profile activity
                            Log.d("OtpActivity", "Success")

                            val user_id = FirebaseAuth.getInstance().currentUser.toString()
                            Log.d("OtpActivity","userID: $user_id")
                            sharedPreferences!!.setLoginSession(user_id)

                            //sharedPreferences!!.setLoginSession("true")
                            startActivity<Dashboard>()

                            finish()
                            Toast.makeText(this@OtpActivity, "Success", Toast.LENGTH_SHORT).show()


                        } else {

                            var message = "Somthing is wrong, we will fix it soon..."
                            Log.d("OtpActivity", message)
                            Toast.makeText(this@OtpActivity, "" + message, Toast.LENGTH_SHORT)
                                .show()

                        }
                        if (task.exception is FirebaseAuthInvalidCredentialsException)
                        {
                           var message = "Invalid code entered..."
                            Toast.makeText(this@OtpActivity, "" + message, Toast.LENGTH_SHORT).show()
                        }


                        Log.d("OtpActivity", "Failed")
                    }
                }
        }
        catch (e:Exception)
        {
            Log.d("OtpActivity", "Exception: $e")
        }

    }


}
