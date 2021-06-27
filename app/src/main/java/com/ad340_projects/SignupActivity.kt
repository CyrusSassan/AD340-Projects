package com.ad340_projects

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.text.TextUtils.isEmpty
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth


class SignupActivity : AppCompatActivity() {
    private var auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = "Sign Up"
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
        }
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance()
        val btnSignIn = findViewById<View>(R.id.sign_in_button) as Button
        val btnSignUp = findViewById<View>(R.id.sign_up_button) as Button
        val inputEmail = findViewById<View>(R.id.email) as EditText
        val inputPassword = findViewById<View>(R.id.password) as EditText
        val btnResetPassword = findViewById<View>(R.id.btn_reset_password) as Button


        btnResetPassword.setOnClickListener {
            val intent = Intent(this, ResetPasswordActivity::class.java,)
            startActivity(intent)
            }


        btnSignIn.setOnClickListener {
        val intent = Intent(this, LoginActivity::class.java,)
        startActivity(intent)
    }


        btnSignUp.setOnClickListener {
        val intent = Intent(this, MainActivity::class.java,)
                val email = inputEmail.text.toString().trim { it <= ' ' }
                val password = inputPassword.text.toString().trim { it <= ' ' }

                if (isEmpty(email)) {
                    Toast.makeText(applicationContext, "Email address is required!", Toast.LENGTH_LONG)
                        .show()
                    return@setOnClickListener
                }

                if (isEmpty(password)) {
                    Toast.makeText(applicationContext, "Password is required!", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
                if (password.length < 8) {
                    Toast.makeText(
                        applicationContext,
                        "password must be at least 8 characters!",
                        Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
                //Create account in firebase
                auth!!.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this@SignupActivity,
                                "createUserWithEmail:success:" + task.isSuccessful(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                                else if (!task.isSuccessful()) {
                                    Toast.makeText(
                                        this@SignupActivity,
                                        "createUserWithEmail failure." + task.exception,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    startActivity(intent)
                                    finish()
                                }
                            }
                        }
            }
        }
