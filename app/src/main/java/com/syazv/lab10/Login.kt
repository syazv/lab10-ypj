package com.syazv.lab10

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.syazv.lab10.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Dapatkan connection ke database
        auth = FirebaseAuth.getInstance()

        binding.loginLoginBtn.setOnClickListener {
            if (binding.loginEmailEditText.text.trim().toString().isNotEmpty() ||
                binding.loginPasswordEditText.text.trim().toString().isNotEmpty()) {

                loginUser(
                    binding.loginEmailEditText.text.toString(),
                    binding.loginPasswordEditText.text.toString()
                )

            }
            else{

                Snackbar.make(binding.root,
                    "Please check your username and password",
                    Snackbar.LENGTH_LONG)
                    .show()

            }

        }
    }

    fun loginUser(email:String, password:String){
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
            task ->
            if (task.isSuccessful) {
                val intent = Intent(this, ServiceActivity::class.java)
                startActivity(intent)
            }
            else{
                Snackbar.make(binding.root,
                    "Please check your username and password",
                    Snackbar.LENGTH_LONG)
                    .show()
            }

        }
            /* // Untuk check error log from Firebase default message
            .addOnFailureListener { err ->
                Log.d("debug", err.stackTraceToString())
                Snackbar.make(binding.root,
                    err.message!!,
                    Snackbar.LENGTH_LONG)
                    .show()
            }*/
    }


}


