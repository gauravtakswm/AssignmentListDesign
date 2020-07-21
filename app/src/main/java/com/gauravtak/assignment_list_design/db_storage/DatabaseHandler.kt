package com.gauravtak.assignment_list_design.db_storage

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHandler(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    // Creating Tables
    override fun onCreate(db: SQLiteDatabase) {
        val createScreenDataTable = "CREATE TABLE $TABLE_SCREENS_DATA ($KEY_SCREEN_ID  INTEGER PRIMARY KEY, " +
                "$KEY_SCREEN  INTEGER,$KEY_COMPLETE_DATA  TEXT)"
        db.execSQL(createScreenDataTable)
    }

    // Upgrading database
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SCREENS_DATA")

        // Create tables again
        onCreate(db)
    }

    // code to add the new screen data
    fun addScreenData(completeData: String,screenKey:Int) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_SCREEN, screenKey)
        values.put(KEY_COMPLETE_DATA, completeData)

        // Inserting Row
        db.insert(TABLE_SCREENS_DATA, null, values)
        db.close() // Closing database connection
    }

    // code to get the single screen data
    fun getScreenData(screenKey: Int): String? {
        lateinit var completeData:String
        val db = this.readableDatabase
        val cursor = db.query(TABLE_SCREENS_DATA, arrayOf(KEY_SCREEN,
                KEY_COMPLETE_DATA), "$KEY_SCREEN=?", arrayOf(screenKey.toString()), null, null, null, null)
        if(cursor?.count==0) {
            db.close()
            return null
        }else {
            cursor?.moveToFirst()

            completeData = cursor!!.getString(cursor.getColumnIndex(KEY_COMPLETE_DATA))
            cursor.close()
            db.close()
        }
        return completeData
    }
    fun updateScreenData(completeData: String,screenKey: Int)
    {
        val cv by lazy{ ContentValues()}
        cv.put(KEY_COMPLETE_DATA, completeData) //These Fields should be your String values of actual column names

        cv.put(KEY_SCREEN,screenKey)
        val db = this.readableDatabase
        db.update(TABLE_SCREENS_DATA, cv, "$KEY_SCREEN=$screenKey", null)
        db.close()

    }


    companion object {
        private val DATABASE_VERSION by lazy {1}
        private val DATABASE_NAME by lazy { "storage_data"}
        private val TABLE_SCREENS_DATA by lazy {"screens_data"}
        private val KEY_SCREEN_ID by lazy{"_id"}
        private val KEY_SCREEN by lazy {"screen_key"}
        private val KEY_COMPLETE_DATA by lazy {"complete_data"}
       }
}
