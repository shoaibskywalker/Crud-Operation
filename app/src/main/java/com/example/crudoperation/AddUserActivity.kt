package com.example.crudoperation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.crudoperation.database.DatabaseHelper
import com.example.crudoperation.databinding.ActivityAddUserBinding

class AddUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddUserBinding
    private lateinit var databaseHelper: DatabaseHelper
    var mainRole:String = ""
    var mainGender:String = ""
    var mainMarital:String = ""

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)

        binding.imageBack.setOnClickListener{
            finish()
        }



        binding.buttonSubmit.setOnClickListener {


            val number = binding.number.text.toString().trim()
            val name = binding.name.text.toString().trim()


            val subscriptionFee = binding.SubscribtionFee.text.toString().trim()
            val depositAmount = binding.DepositAmoun.text.toString().trim()
            val loanAmount = binding.LoanAmoun.text.toString().trim()

            val dob = binding.DOB.text.toString().trim()
            val doj = binding.DOJoin.text.toString().trim()

            val dateOfMarriage = binding.Marriage.text.toString().trim()

            val caste = binding.Caste.text.toString().trim()
            val religion = binding.Religion.text.toString().trim()
            val category = binding.Category.text.toString().trim()
            val aadhar = binding.Aadhar.text.toString().trim()

            val role = findViewById<RadioGroup>(R.id.radio_role)
            val selectedRoleId = role.checkedRadioButtonId

            if (selectedRoleId != -1) {
                val selectedRole = findViewById<RadioButton>(selectedRoleId)
                mainRole = selectedRole.text.toString()
            } else {
                // Handle case when no role is selected
                Toast.makeText(this, "Please select a role", Toast.LENGTH_SHORT).show()
            }

            val gender = findViewById<RadioGroup>(R.id.radio_group)
            val selectedGenderId = gender.checkedRadioButtonId

            if (selectedGenderId != -1) {
                val selectedGender = findViewById<RadioButton>(selectedGenderId)
                mainGender = selectedGender.text.toString()
            } else {
                // Handle case when no role is selected
                Toast.makeText(this, "Please select a role", Toast.LENGTH_SHORT).show()
            }

            val marital = findViewById<RadioGroup>(R.id.radio_group1)
            val selectedMarital = marital.checkedRadioButtonId

            if (selectedMarital != -1) {
                val selectedmarita = findViewById<RadioButton>(selectedMarital)
                mainMarital = selectedmarita.text.toString()
            } else {
                // Handle case when no role is selected
                Toast.makeText(this, "Please select a role", Toast.LENGTH_SHORT).show()
            }


            val result = databaseHelper.insertUserData(
                mobileNumber = number,
                name = name,
                role = mainRole,
                subscriptionFee = subscriptionFee,
                depositAmount = depositAmount,
                loanAmount = loanAmount,
                gender = mainGender,
                dateOfBirth = dob,
                dateOfJoining = doj,
                maritalStatus = mainMarital,
                dateOfMarriage = dateOfMarriage,
                caste = caste,
                religion = religion,
                category = category,
                aadhar = aadhar
            )

            if (result != -1L) {
                Toast.makeText(this, "Data saved successfully!", Toast.LENGTH_SHORT).show()
                val intent = Intent()
                setResult(RESULT_OK, intent)
                finish()
            } else {
                Toast.makeText(this, "Failed to save data.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}