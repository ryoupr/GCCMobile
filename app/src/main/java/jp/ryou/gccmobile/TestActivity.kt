package jp.ryou.gccmobile

import android.content.ContentResolver
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract.Calendars
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

    //    Buck to mainactivity.
        val buck = findViewById<Button>(R.id.buck)
        buck.setOnClickListener {
            Toast.makeText(this , "Jump main activity.",Toast.LENGTH_SHORT).show()
            val intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
        }
    
        val calendar_list = findViewById<Button>(R.id.getCalendarList)
        calendar_list.setOnClickListener {
            Toast.makeText(this, "Output calendar list on console.", Toast.LENGTH_SHORT).show()

            val CALENDAR_PROJECTION = arrayOf(
                Calendars._ID,
                Calendars.NAME,
                Calendars.ACCOUNT_NAME,
                Calendars.ACCOUNT_TYPE,
                Calendars.CALENDAR_COLOR,
                Calendars.CALENDAR_COLOR_KEY,
                Calendars.CALENDAR_DISPLAY_NAME,
                Calendars.CALENDAR_ACCESS_LEVEL,
                Calendars.CALENDAR_TIME_ZONE,
                Calendars.VISIBLE,
                Calendars.SYNC_EVENTS,
                Calendars.OWNER_ACCOUNT
            )
//            インデックスを指定しておくとパフォーマンスが向上する
            val CALENDAR_PROJECTION_IDX_ID = 0
            val CALENDAR_PROJECTION_IDX_NAME = 1
            val CALENDAR_PROJECTION_IDX_ACCOUNT_NAME = 2
            val CALENDAR_PROJECTION_IDX_ACCOUNT_TYPE = 3
            val CALENDAR_PROJECTION_IDX_CALENDAR_COLOR = 4
            val CALENDAR_PROJECTION_IDX_CALENDAR_COLOR_KEY = 5
            val CALENDAR_PROJECTION_IDX_DISPLAY_NAME = 6
            val CALENDAR_PROJECTION_IDX_ACCESS_LEVEL = 7
            val CALENDAR_PROJECTION_IDX_TIME_ZONE = 8
            val CALENDAR_PROJECTION_IDX_VISIBLE = 9
            val CALENDAR_PROJECTION_IDX_SYNC_EVENTS = 10
            val CALENDAR_PROJECTION_IDX_OWNER_ACCOUNT = 11

//            クエリ条件の設定
            val uri : Uri = Calendars.CONTENT_URI
            val projection = CALENDAR_PROJECTION
            val selection : String? = null
            val selectionArgs: Array<String>? = null
            val sortOrder : String? = null

//            クエリ発行を行いカーソルを取得
            val cr : ContentResolver = applicationContext.contentResolver
            val cur : Cursor? = cr.query(
                uri,
                CALENDAR_PROJECTION,
                selection,
                selectionArgs,
                sortOrder
            )

        }    
    }
}

