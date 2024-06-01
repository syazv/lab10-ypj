package com.syazv.lab10

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.syazv.lab10.databinding.ActivitySignupBinding

class Signup : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    private lateinit var auth: FirebaseAuth;

    private lateinit var db:FirebaseFirestore;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Dapatkan connection Firebase Authentication dari app
        auth = FirebaseAuth.getInstance()

        binding.signupCreateBtn.setOnClickListener {
            createUser(binding.signupEmailEditText.text.toString(),
                binding.signupPasswordEditText.text.toString())
        }

        db = Firebase.firestore
    }

    fun createUser(email:String, password:String){
        // Create User menggunakan email dan password
        //  Bila dah sudah saya akan dapat Task
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            task -> if(task.isSuccessful){
            newCustomer()
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

    private fun newCustomer(){
        // Key value based data type
        // Name = Wan Muzaffar, Email = wanwan@gmail.com, Country : Malaysia
        val customer = hashMapOf(
            "name" to binding.signupNameEditText.text.toString().trim(),
            "city" to binding.signupCityEditText.text.toString().trim(),
            "country" to binding.signupCountryEditText.text.toString().trim(),
            "phone" to binding.signupPhoneEditText.text.toString().trim(),
        )

        db.collection("customers")
            .add(customer)
            .addOnSuccessListener {
                documentReference ->
                Log.d("debug", "Document successfully added with id ${documentReference.id}")
            }

            .addOnFailureListener { e ->
                Log.d("debug","An error happen ${e.message}")
            }

    }
}