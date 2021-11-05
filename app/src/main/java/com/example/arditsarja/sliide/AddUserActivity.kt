package com.example.arditsarja.sliide

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import com.example.arditsarja.sliide.user.UserCreate
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AddUserActivity : AppCompatActivity() {
    private val user by lazy { com.example.arditsarja.sliide.api.User.create() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)
        val addName = findViewById<EditText>(R.id.addName)
        val addEmail = findViewById<EditText>(R.id.addEmail)
        val addGender = findViewById<EditText>(R.id.addGender)
        val addStatus = findViewById<RadioButton>(R.id.active)

        val addUser = findViewById<Button>(R.id.createUser)
        addUser.setOnClickListener(View.OnClickListener {
            val name = addName.text.toString()
            val email = addEmail.text.toString()
            val gender = addGender.text.toString()
            var status = "inactive"
            if (addStatus.isChecked) {
                status = "active"
            }

            val user = UserCreate(name, email, gender, status)
            createUser(user)

        })

    }

    private fun createUser(userCreate: UserCreate) {
        val kk = user.create(userCreate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            Toast.makeText(this,"user created with id "+result.data.id,Toast.LENGTH_LONG).show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        },
                        { error ->
                            Toast.makeText(this,"Error "+ error.toString(),Toast.LENGTH_LONG).show()

                        })

    }
}
