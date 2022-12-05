package jp.ryou.gccmobile

import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.CalendarContract
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        カレンダーへのアクセス権要求
        if (Build.VERSION.SDK_INT >= 23) {
            val PERMISSION_WRITE_EX_STR = 1
            if (ActivityCompat.checkSelfPermission(this , android.Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this , android.Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED )
            {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        android.Manifest.permission.READ_CALENDAR,
                        android.Manifest.permission.WRITE_CALENDAR
                    ),
                    PERMISSION_WRITE_EX_STR
                )
            }
        }







        val registration = findViewById<Button>(R.id.registrationBtn)
        registration.setOnClickListener {
            Toast.makeText(this, "Registration button tapped.", Toast.LENGTH_SHORT).show()

            println("Tapped registration Btn.")

        }


        val choseFromCalendar = findViewById<Button>(R.id.choseFromCalendar)
        choseFromCalendar.setOnClickListener {
            Toast.makeText(this, "choseFromCalendar button tapped.", Toast.LENGTH_SHORT).show()
        }

        val cancel = findViewById<Button>(R.id.cancelBtn)
        cancel.setOnClickListener {
            Toast.makeText(this, "Cancel button tapped.", Toast.LENGTH_SHORT).show()
        }

        val test = findViewById<Button>(R.id.test)
        test.setOnClickListener {
            Toast.makeText(this, "Test Btn Pushed.", Toast.LENGTH_SHORT).show()
            val intent = Intent(applicationContext , TestActivity::class.java)
            startActivity(intent)


////GoogleHelpの内容
//            val EVENT_PROJECTION: Array<String> = arrayOf(
//                CalendarContract.Calendars._ID,
//                CalendarContract.Calendars.ACCOUNT_NAME,
//                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
//                CalendarContract.Calendars.OWNER_ACCOUNT
//            )
//            println("↓EVENT_PROJECTIONの中身")
//            for (i in 0..EVENT_PROJECTION.size - 1) {
//                println(" " + EVENT_PROJECTION[i])
//            }
//            println()
//
//            val PROJECTION_ID_INDEX = 0
//            val PROJECTION_ACCOUNT_NAME_INDEX = 1
//            val PROJECTION_DISPLAY_NAME_INDEX = 2
//            val PROJECTION_OWNER_ACCOUNT_INDEX = 3
//
////            Run query.
//            val uri: Uri = CalendarContract.Calendars.CONTENT_URI
//            val selection: String = "((${CalendarContract.Calendars.ACCOUNT_NAME} = ?) AND (" +
//                    "${CalendarContract.Calendars.ACCOUNT_TYPE} = ?) AND (" +
//                    "${CalendarContract.Calendars.OWNER_ACCOUNT} = ?))"
//            val selectionArgs: Array<String> = arrayOf(
//                "ryou120710@gmail.com",
//                "com.google",
//                "ryou120710@gmail.com"
//            )
//            val cur: Cursor? = contentResolver.query(uri, EVENT_PROJECTION, selection, selectionArgs, null)


        }


    }

}

