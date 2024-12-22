package com.example.crudoperation

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crudoperation.adapter.UserAdapter
import com.example.crudoperation.database.DatabaseHelper
import com.example.crudoperation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var userAdapter: UserAdapter

    companion object {
        const val REQUEST_CODE_ADD_USER = 1  // Define request code
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fab.setOnClickListener {
            val intent = Intent(this, AddUserActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_ADD_USER)
        }
        databaseHelper = DatabaseHelper(this)

        val userList = databaseHelper.getAllUsers()

        userAdapter = UserAdapter(userList)
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.adapter = userAdapter
        userAdapter.notifyDataSetChanged()

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_ADD_USER && resultCode == RESULT_OK) {
            // Fetch updated user data from the database
            val updatedUserList = databaseHelper.getAllUsers()

            // Update the RecyclerView with the new data
            userAdapter = UserAdapter(updatedUserList)  // Create new adapter with updated data
            binding.recyclerview.layoutManager = LinearLayoutManager(this)
            binding.recyclerview.adapter = userAdapter
            userAdapter.notifyDataSetChanged()  // Notify adapter about the data change
        }
    }
}