package com.syazv.lab10

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.syazv.lab10.databinding.ActivityServiceBinding

class ServiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityServiceBinding
    private lateinit var db:FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityServiceBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Get the connection to Firebase Firestore
        db = Firebase.firestore

        readFirestoreData()
    }

    fun readFirestoreData(){
        db.collection("customers")
            .get()
            .addOnCompleteListener {
                val result:StringBuffer = StringBuffer()
                if(it.isSuccessful){
                    for (document in it.result!!){
                        val city = document.get("city") ?: "N/A"
                        val country = document.get("country") ?: "N/A"
                        val name = document.get("name") ?: "N/A"
                        val phone = document.get("phone") ?: "N/A"

                        // TO do add new TextView eg. textview widget

                        result.append("Name: ").append(name).append("\n")
                            .append("Phone: ").append(phone).append("\n")
                            .append("City: ").append(city).append("\n")
                            .append("Country: ").append(country).append("\n").append("\n").append("\n")
                    }
                    binding.serviceTextView.text = result.toString().trim()
                }
            }
    }
}