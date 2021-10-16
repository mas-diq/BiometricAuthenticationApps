package com.masdiq.biometricauthenticationapps

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_other.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getBiometric()
//        Log.d(
//            TAG,
//            "Hardware ID:" + Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
//        )
    }

    fun move() {
        Handler(mainLooper).postDelayed({
            val move = Intent(this, OtherActivity::class.java)
            startActivity(move)
            finish()
        }, 3000)
    }

    fun getBiometric() {
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt =
            BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
                @SuppressLint("SetTextI18n")
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    tv_status.text = "Login Successfully"
                    update()
                    move()
                }

                @SuppressLint("SetTextI18n")
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    tv_status.text = "Authentication Error : $errorCode"
                }

                @SuppressLint("SetTextI18n")
                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    tv_status.text = "Authentication Failed"
                }
            })
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Login using fingerprint")
            .setNegativeButtonText("Cannot recognize pattern")
            .build()

        btn_finger.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }
    }

    fun update() {
        RetrofitClient.instance.putPost(
            tv_id.text.toString(),
            tv_name.text.toString(),
            tv_address.text.toString(),
            tv_age.text.toString(),
        ).enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                val responseText = "Data has updated"
                tv_status_other.text = responseText
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                tv_status_other.text = t.message
            }
        })
    }
}