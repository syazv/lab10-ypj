package com.syazv.lab10

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.syazv.lab10.databinding.ActivitySignupBinding

class Signup : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    private lateinit var auth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Dapatkan connection Firebase Authentication dari app
        auth = FirebaseAuth.getInstance()

        binding.signupCreateBtn.setOnClickListener {
            createUser(binding.signupEmailEditText.text.toString(), binding.signupPasswordEditText.text.toString())
        }

    }

    fun createUser(email:String, password:String){
        // Create User menggunakan email dan password
        //  Bila dah sudah saya dapat Task
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                task -> if(task.isSuccessful){
            val intent = Intent(this, ThankyouActivity::class.java)
            startActivity(intent)
        }
        else{
            Snackbar.make(
                binding.root,
                "Enter a valid username and password",
                Snackbar.LENGTH_LONG
            ).show()
        }
        }
    }
}