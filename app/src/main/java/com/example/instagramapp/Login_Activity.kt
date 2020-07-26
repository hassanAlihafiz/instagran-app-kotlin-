package com.example.instagramapp

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


class Login_Activity : AppCompatActivity() {
    var auth:FirebaseAuth?=null
    var callbackManager:CallbackManager?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth=FirebaseAuth.getInstance()

        btn_email_login.setOnClickListener {
            signinAndSignUp()
        }
        //facebook login button
        btnFacebook.setOnClickListener {
          //First Step
            facebookLogin()
        }

      //  VwezNGfVHPI0v9E6SXeQNQRbBeg=
        //printHashKey()
    callbackManager = CallbackManager.Factory.create()
    }
    fun facebookLogin(){
        LoginManager.getInstance()
            .logInWithReadPermissions(this,Arrays.asList("public_profile","email"))
   LoginManager.getInstance()
       .registerCallback(callbackManager,object :FacebookCallback<LoginResult>{
           override fun onSuccess(result: LoginResult?) {
             //Second Step
              handlerFacebookAccessToken(result?.accessToken)
           }

           override fun onCancel() {

           }

           override fun onError(error: FacebookException?) {

           }

       })
    }
fun handlerFacebookAccessToken(token : AccessToken?){
    var credential= FacebookAuthProvider.getCredential(token?.token!!)
    auth?.signInWithCredential(credential)?.addOnCompleteListener {
            task ->
        if(task.isSuccessful){
         //Third Step
            //Login
            moveMainPage(task.result?.user)
        }else{
            //Show the error mesage
            Toast.makeText(this,task.exception?.message,Toast.LENGTH_LONG).show()
        }

    }


}
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    callbackManager?.onActivityResult(requestCode,resultCode,data)
    }

    fun printHashKey() = try {
        val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        for (signature in info.signatures) {
            val md = MessageDigest.getInstance("SHA")
            md.update(signature.toByteArray())
           val hashkey= String(Base64.encode(md.digest(),0))
            Log.i("TAG:","print hashkey() haskey : $hashkey")
        }
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    }







     fun  signinAndSignUp(){
    auth?.createUserWithEmailAndPassword(txtEmail.text.toString(),txtPassword.text.toString())
        ?.addOnCompleteListener {
            task ->
            if(task.isSuccessful){
                //creating a user account
         moveMainPage(task.result?.user)
            }else if(task.exception?.message.isNullOrEmpty()){
Toast.makeText(this,task.exception?.message,Toast.LENGTH_LONG).show()
            }else{
                //login if u have account
                signInEmail()
            }

    }
}
    fun  signInEmail(){
        auth?.signInWithEmailAndPassword(txtEmail.text.toString(),txtPassword.text.toString())
            ?.addOnCompleteListener {
                    task ->
                if(task.isSuccessful){
                   //Login
                    moveMainPage(task.result?.user)
                }else{
                    //Show the error mesage
                    Toast.makeText(this,task.exception?.message,Toast.LENGTH_LONG).show()
                }

            }
    }
    fun moveMainPage(user: FirebaseUser?){
        if(user!=null){
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}