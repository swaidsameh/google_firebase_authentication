package com.example.firebase_practice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firebase_practice.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.goLogin.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.registerBtn.setOnClickListener{
            if(binding.emailEt.text.toString().isEmpty() || binding.passwordEt.text.toString().isEmpty() || binding.conpassEt.text.toString().isEmpty()){
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }

            else if (binding.passwordEt.text.toString() != binding.conpassEt.text.toString()){
                Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show()
            }
            else if(binding.passwordEt.text.toString().length < 8){
                Toast.makeText(this, "Password length should be greater than 8", Toast.LENGTH_SHORT).show()
            }
            else{
                Firebase.auth.createUserWithEmailAndPassword(binding.emailEt.text.toString(), binding.passwordEt.text.toString()) //firebase auth
                    .addOnCompleteListener {
                        if (!it.isSuccessful){
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        }else{
                            Toast.makeText(this,it.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
                Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onStart() {
        super.onStart()
        if (Firebase.auth.currentUser != null){
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
}