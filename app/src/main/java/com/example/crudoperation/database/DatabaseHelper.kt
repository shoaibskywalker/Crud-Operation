package com.example.crudoperation.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.crudoperation.model.User

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "userInfo.db"
        private const val DATABASE_VERSION = 2

        // Table and columns names
        private const val TABLE_NAME = "user_info"
        private const val COLUMN_ID = "id"
        private const val COLUMN_MOBILE_NUMBER = "mobile_number"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_ROLE = "role"
        private const val COLUMN_SUBSCRIPTION_FEE = "subscription_fee"
        private const val COLUMN_DEPOSIT_AMOUNT = "deposit_amount"
        private const val COLUMN_LOAN_AMOUNT = "loan_amount"
        private const val COLUMN_GENDER = "gender"
        private const val COLUMN_DATE_OF_BIRTH = "date_of_birth"
        private const val COLUMN_DATE_OF_JOINING = "date_of_joining"
        private const val COLUMN_MARITAL_STATUS = "marital_status"
        private const val COLUMN_DATE_OF_MARRIAGE = "date_of_marriage"
        private const val COLUMN_CASTE = "caste"
        private const val COLUMN_RELIGION = "religion"
        private const val COLUMN_CATEGORY = "category"
        private const val COLUMN_AADHAR = "aadhar"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_MOBILE_NUMBER TEXT, " +
                "$COLUMN_NAME TEXT, " +
                "$COLUMN_ROLE TEXT, " +
                "$COLUMN_SUBSCRIPTION_FEE TEXT, " +
                "$COLUMN_DEPOSIT_AMOUNT TEXT, " +
                "$COLUMN_LOAN_AMOUNT TEXT, " +
                "$COLUMN_GENDER TEXT, " +
                "$COLUMN_DATE_OF_BIRTH TEXT, " +
                "$COLUMN_DATE_OF_JOINING TEXT, " +
                "$COLUMN_MARITAL_STATUS TEXT, " +
                "$COLUMN_DATE_OF_MARRIAGE TEXT, " +
                "$COLUMN_CASTE TEXT, " +
                "$COLUMN_RELIGION TEXT, " +
                "$COLUMN_CATEGORY TEXT, " +
                "$COLUMN_AADHAR TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
       // deleteAllData()
    }

    // Function to insert user data
    fun insertUserData(
        mobileNumber: String,
        name: String,
        role: String,
        subscriptionFee: String,
        depositAmount: String,
        loanAmount: String,
        gender: String,
        dateOfBirth: String,
        dateOfJoining: String,
        maritalStatus: String,
        dateOfMarriage: String,
        caste: String,
        religion: String,
        category: String,
        aadhar: String
    ): Long {
        val db = this.writableDatabase
        return try {
            val contentValues = ContentValues().apply {
                put(COLUMN_MOBILE_NUMBER, mobileNumber)
                put(COLUMN_NAME, name)
                put(COLUMN_ROLE, role)
                put(COLUMN_SUBSCRIPTION_FEE, subscriptionFee)
                put(COLUMN_DEPOSIT_AMOUNT, depositAmount)
                put(COLUMN_LOAN_AMOUNT, loanAmount)
                put(COLUMN_GENDER, gender)
                put(COLUMN_DATE_OF_BIRTH, dateOfBirth)
                put(COLUMN_DATE_OF_JOINING, dateOfJoining)
                put(COLUMN_MARITAL_STATUS, maritalStatus)
                put(COLUMN_DATE_OF_MARRIAGE, dateOfMarriage)
                put(COLUMN_CASTE, caste)
                put(COLUMN_RELIGION, religion)
                put(COLUMN_CATEGORY, category)
                put(COLUMN_AADHAR, aadhar)
            }
            val result = db.insert(TABLE_NAME, null, contentValues)
            if (result == -1L) {
                Log.e("DatabaseHelper", "Failed to insert data")
            } else {
                Log.d("DatabaseHelper", "Data inserted successfully")
            }
            result
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Exception during insert: ${e.message}")
            -1L // Return -1 to indicate failure
        } finally {
            db.close() // Ensure database is closed after operation
        }
    }


    @SuppressLint("Range")
    fun getAllUsers(): List<User> {
        val userList = mutableListOf<User>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        if (cursor.moveToFirst()) {
            do {
                val user = User(
                    mobileNumber = cursor.getStringOrNull(cursor, COLUMN_MOBILE_NUMBER) ?: "",
                    name = cursor.getStringOrNull(cursor, COLUMN_NAME) ?: "",
                    role = cursor.getStringOrNull(cursor, COLUMN_ROLE) ?: "",
                    subscriptionFee = cursor.getStringOrNull(cursor, COLUMN_SUBSCRIPTION_FEE) ?: "",
                    depositAmount = cursor.getStringOrNull(cursor, COLUMN_DEPOSIT_AMOUNT) ?: "",
                    loanAmount = cursor.getStringOrNull(cursor, COLUMN_LOAN_AMOUNT) ?: "",
                    gender = cursor.getStringOrNull(cursor, COLUMN_GENDER) ?: "",
                    dateOfBirth = cursor.getStringOrNull(cursor, COLUMN_DATE_OF_BIRTH) ?: "",
                    dateOfJoining = cursor.getStringOrNull(cursor, COLUMN_DATE_OF_JOINING) ?: "",
                    maritalStatus = cursor.getStringOrNull(cursor, COLUMN_MARITAL_STATUS) ?: "",
                    dateOfMarriage = cursor.getStringOrNull(cursor, COLUMN_DATE_OF_MARRIAGE) ?: "",
                    caste = cursor.getStringOrNull(cursor, COLUMN_CASTE) ?: "",
                    religion = cursor.getStringOrNull(cursor, COLUMN_RELIGION) ?: "",
                    category = cursor.getStringOrNull(cursor, COLUMN_CATEGORY) ?: "",
                    aadhar = cursor.getStringOrNull(cursor, COLUMN_AADHAR) ?: ""
                )
                userList.add(user)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return userList
    }

    fun deleteAllData() {
        val db = this.writableDatabase
        try {
            db.delete(TABLE_NAME, null, null)
            Log.d("DatabaseHelper", "All data deleted successfully.")
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error deleting all data: ${e.message}")
        } finally {
            db.close()
        }
    }


    // Extension function to handle null values
    fun Cursor.getStringOrNull(cursor: Cursor, columnName: String): String? {
        val index = cursor.getColumnIndex(columnName)
        return if (index != -1 && !cursor.isNull(index)) cursor.getString(index) else null
    }


}