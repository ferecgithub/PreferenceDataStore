package com.ferechamitbeyli.preferencesdatastore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.asLiveData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var userManager: UserManager
    var name = ""
    var age = 0
    var gender = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userManager = UserManager(this)

        buttonSave()

        observeData()

    }

    private fun buttonSave() {
        save_btn.setOnClickListener {
            name = name_et.text.toString()
            age = age_et.text.toString().toInt()
            val isMale = gender_switch.isChecked

            GlobalScope.launch {
                userManager.storeUser(age, name, isMale)
            }
        }
    }

    private fun observeData() {
        userManager.userNameFlow.asLiveData().observe(this, {
            name = it
            name_tv.text = it.toString()
        })

        userManager.userAgeFlow.asLiveData().observe(this, {
            age = it
            age_tv.text = it.toString()
        })

        userManager.userGenderFlow.asLiveData().observe(this, {
            gender = if(it) "Male" else "Female"
            gender_tv.text = gender
        })
    }
}