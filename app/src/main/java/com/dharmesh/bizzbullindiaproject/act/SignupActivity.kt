package com.dharmesh.bizzbullindiaproject.act

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.chaos.view.PinView
import com.dharmesh.bizzbullindiaproject.DashboardActivity
import com.dharmesh.bizzbullindiaproject.R
import com.dharmesh.bizzbullindiaproject.databinding.LoginBinding
import com.dharmesh.bizzbullindiaproject.fact.LoginViewModelFactory
import com.dharmesh.bizzbullindiaproject.vm.LoginViewModel
import com.dharmesh.bizzbullindiaproject.vm.SignupViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class SignupActivity : AppCompatActivity() {
    private lateinit var dialog: Dialog
    private lateinit var cpwd: TextInputEditText
    private lateinit var pwd: TextInputEditText
    private lateinit var username: AppCompatEditText
    private lateinit var signupViewModel: SignupViewModel
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var binding: LoginBinding
    val TAG = "LoginActivity"
    private val firebaseAuth = Firebase.auth
    private lateinit var storedVerificationId: String

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            Log.d(TAG, "onVerificationCompleted:$credential")
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w(TAG, "onVerificationFailed", e)

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                e.printStackTrace()
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                e.printStackTrace()
            }
            e.printStackTrace()
            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.d(TAG, "onCodeSent:$verificationId")

            // Save verification ID and resending token so we can use them later
            storedVerificationId = verificationId
            resendToken = token
            showOtpDialog()
        }
    }

    // [START resend_verification]
    private fun resendVerificationCode(
        phoneNumber: String,
        token: PhoneAuthProvider.ForceResendingToken?
    ) {
        val optionsBuilder = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber("+91$phoneNumber")       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
        if (token != null) {
            optionsBuilder.setForceResendingToken(token) // callback's ForceResendingToken
        }
        PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        username = binding.edtuserid
        pwd = binding.edtpassword
        cpwd = binding.edtconfirmpassword
        val btnsignup = binding.btSignUp
        val txtsignin=binding.txtSignin
        SigninTextSpnnable()

        signupViewModel =
            ViewModelProvider(this, LoginViewModelFactory())[SignupViewModel::class.java]
        btnsignup.setOnClickListener {
            if (signupViewModel.validate(
                    username.text.toString(),
                    pwd.text.toString(),
                    cpwd.text.toString()
                )
            ) {
                sendOtp(binding.edtuserid.text.toString())
            }
        }
        txtsignin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }

        signupViewModel.showToast.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        signupViewModel.signupResult.observe(this@SignupActivity, Observer {
            if (it.msg.equals("Register successful", ignoreCase = true)) {
                dialog.dismiss()
                startActivity(Intent(this, DashboardActivity::class.java))
                finishAffinity()
            }else{
                Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun SigninTextSpnnable() {
        val spannable = SpannableString("Please Sign-in here.")
        spannable.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary)),
            7, 14,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.txtSignin.text = spannable
    }

    private fun sendOtp(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber("+91$phoneNumber")       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun showOtpDialog() {
        dialog = Dialog(
            this,
            com.google.android.material.R.style.ThemeOverlay_Material3_MaterialAlertDialog
        )
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.otp)
        /*val body = dialog.findViewById(R.id.body) as TextView
        body.text = title
        val yesBtn = dialog.findViewById(R.id.yesBtn) as Button
        val noBtn = dialog.findViewById(R.id.noBtn) as TextView
        yesBtn.setOnClickListener {
            dialog.dismiss()
        }*/
        val pinView = dialog.findViewById(R.id.pinView) as PinView
        val btnSubmit = dialog.findViewById(R.id.btSubmit) as Button
        btnSubmit.setOnClickListener {
            verifyPhoneNumberWithCode(storedVerificationId, pinView.text.toString())
        }
        dialog.show()
    }

    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        // [START verify_with_code]
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        //val credential = PhoneAuthProvider.getCredential(storedVerificationId, smsCode)
        Firebase.auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = task.result?.user
                    signupViewModel.signup(
                        username.text.toString(),
                        pwd.text.toString(),
                        cpwd.text.toString()
                    )
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        (task.exception as FirebaseAuthInvalidCredentialsException).printStackTrace()
                    }
                    // Update UI
                }
            }
    }
}