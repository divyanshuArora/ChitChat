package com.example.chitchat.view.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.chitchat.R
import com.example.chitchat.databinding.ActivityRegisterBinding
import com.example.chitchat.services.model.UserModel
import com.example.chitchat.services.utils.SharedPreference
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.jetbrains.anko.startActivity


class RegisterActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener{


    var sharedPreference:SharedPreference ?= null
    var mGoogleSignInClient:GoogleSignInClient ?= null
    var activityRegisterBinding: ActivityRegisterBinding? = null
    private var reqCode = 1
    var userNumber = ""
    var googleApiClient:GoogleApiClient ?= null
    var auth:FirebaseAuth ?= null
    private var authStateListener: FirebaseAuth.AuthStateListener? = null
    var userName= ""
    var userEmail = ""
    var userId = ""
//    var storage = FirebaseStorage.getInstance()
//    var storageReference = storage.reference
    var storageReference: DatabaseReference ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_register)

        sharedPreference = SharedPreference(this)
        auth = FirebaseAuth.getInstance()


        authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->

         storageReference = FirebaseDatabase.getInstance().reference

            // Get signedIn user
            val user = firebaseAuth.currentUser
            //if user is signed in, we call a helper method to save the user details to Firebase
            if (user != null) {
                // User is signed in
                // you could place other firebase code
                //logic to save the user details to Firebase
                Log.d("RegisterActivity", "onAuthStateChanged:signed_in:" + user.uid)
            } else {
                // User is signed out
                Log.d("RegisterActivity", "onAuthStateChanged:signed_out")
            }
        }


        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

    //    GoogleSignIn.getClient(this, gso)


        var account = GoogleSignIn.getLastSignedInAccount(this)


        googleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(this, this@RegisterActivity)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()


        mGoogleSignInClient = GoogleSignIn.getClient(this,gso)


        activityRegisterBinding!!.gmailSignInButton.setOnClickListener{

            //val intent =  mGoogleSignInClient!!.signInIntent
            val intent =  mGoogleSignInClient!!.signInIntent
            startActivityForResult(intent,reqCode)
            startActivityIfNeeded(intent,reqCode)
        }

        activityRegisterBinding!!.registerButton.setOnClickListener {

            userNumber = "+91" + activityRegisterBinding!!.editTextNumber.text.toString()
            Log.d("Register", "userNumber: $userNumber")

            startActivity<OtpActivity>("userNumber" to userNumber)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == reqCode) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.

            val task: Task<GoogleSignInAccount?>? = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }


    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount?>?) {
        try {
            val account = completedTask!!.getResult(ApiException::class.java)

            Log.d("RegisterActivity","success"+account!!.displayName)
            // Signed in successfully, show authenticated UI.
            userName = account!!.displayName!!
            userEmail = account!!.email!!
            userId = account!!.id!!

            storeDataIntoFirebaseCloud(userName,userEmail,userId)
        }
        catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.

            Log.d("RegisterActivity", "signInResult:failed code=" + e.message)
          //  updateUI(null)
        }
    }

    private fun storeDataIntoFirebaseCloud(userName: String, userEmail: String, userId: String) {

        val user = UserModel(userName,userEmail,userId)
//        val firebaseUser: FirebaseUser?= auth!!.currentUser


        val databaseReference:DatabaseReference = FirebaseDatabase.getInstance()!!.getReference("/users").child(userId)

        databaseReference.setValue(user).addOnCompleteListener(object:OnCompleteListener<Void> {
            override fun onComplete(task:Task<Void>) {
                sharedPreference!!.setLoginSession("true")
                sharedPreference!!.addString("user_id",userId)
                startActivity<Dashboard>()
                finish()
            }
        }).addOnFailureListener(object: OnFailureListener {
            override fun onFailure(@NonNull e:Exception) {
                Log.e("Register", "onFailure: " + "not registered")
            }
        })

        }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Toast.makeText(applicationContext,"Connnection Failed ${p0.errorMessage}",Toast.LENGTH_SHORT).show()
    }




}