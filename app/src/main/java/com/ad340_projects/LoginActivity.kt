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
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {
    private var inputEmail: EditText? = null
    private var inputPassword: EditText? = null
    private var auth: FirebaseAuth? = null
    private var btnSignup: Button? = null
    private var btnLogin: Button? = null
    private var btnReset: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance()
        if (auth!!.currentUser != null) {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }

        // set the view
        setContentView(R.layout.activity_login)
        title = "Login"
        val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        inputEmail = findViewById<View>(R.id.email) as EditText
        inputPassword = findViewById<View>(R.id.password) as EditText
        btnSignup = findViewById<View>(R.id.btn_signup) as Button
        btnLogin = findViewById<View>(R.id.btn_login) as Button
        btnReset = findViewById<View>(R.id.btn_reset_password) as Button

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance()
        btnSignup!!.setOnClickListener{
            val intent = Intent(this, SignupActivity::class.java,)
            startActivity(intent)
            }

        btnReset!!.setOnClickListener{
            val intent = Intent(this, ResetPasswordActivity::class.java,)
            startActivity(intent)
        }

        btnLogin!!.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java,)
                val email = inputEmail!!.text.toString()
                val password = inputPassword!!.text.toString()
                if (isEmpty(email)) {
                    Toast.makeText(applicationContext, "Enter email address!", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                if (isEmpty(password)) {
                    Toast.makeText(applicationContext, "Enter password!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                //authenticate user
                auth!!.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) {
                        val intent =
                            Intent(this@LoginActivity, MainActivity::class.java)
                        fun onComplete(task: Task<AuthResult?>) {

                                if (!task.isSuccessful) {
                                    if (password.length < 6) {
                                        inputPassword!!.error = getString(R.string.minimum_password)
                                    } else {
                                        Toast.makeText(
                                            this@LoginActivity,
                                            getString(R.string.auth_failed),
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                } else {
                                    startActivity(intent)
                                    finish()
                                }
                            }
                        }
            }
        }
    }
