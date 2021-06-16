package com.ad340_projects

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth


class ResetPasswordActivity : AppCompatActivity() {
    private var inputEmail: EditText? = null
    private var btnReset: Button? = null
    private var btnBack: Button? = null
    private var auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        inputEmail = findViewById<View>(R.id.email) as EditText
        btnReset = findViewById<View>(R.id.btn_reset_password) as Button
        btnBack = findViewById<View>(R.id.btn_back) as Button
        auth = FirebaseAuth.getInstance()

        btnBack!!.setOnClickListener{
            val intent = Intent(this, ResetPasswordActivity::class.java,)
                finish()
            }

        btnReset!!.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java,)
                val email = inputEmail!!.text.toString().trim { it <= ' ' }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(
                        application,
                        "Enter your registered email id",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                auth!!.sendPasswordResetEmail(email)
                    .addOnCompleteListener(this) {
                        fun onComplete(task: Task<Void?>) {
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    this@ResetPasswordActivity,
                                    "We've sent an instructions to reset the password!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    this@ResetPasswordActivity,
                                    "Failed to send reset email!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
            }
        }
    }
