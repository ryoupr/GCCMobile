package jp.ryou.gccmobile

import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.CalendarContract
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ListAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import jp.ryou.gccmobile.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    //スピナー要素のテスト用
    private val spinnerItem = arrayOf(
        "a",
        "b",
        "c"
    )



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
        val CALENDAR_PROJECTION = arrayOf(
            CalendarContract.Calendars._ID,
            CalendarContract.Calendars.NAME,
            CalendarContract.Calendars.ACCOUNT_NAME,
            CalendarContract.Calendars.ACCOUNT_TYPE,
            CalendarContract.Calendars.CALENDAR_COLOR,
            CalendarContract.Calendars.CALENDAR_COLOR_KEY,
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
            CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL,
            CalendarContract.Calendars.CALENDAR_TIME_ZONE,
            CalendarContract.Calendars.VISIBLE,
            CalendarContract.Calendars.SYNC_EVENTS,
            CalendarContract.Calendars.OWNER_ACCOUNT
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
        val uri: Uri = CalendarContract.Calendars.CONTENT_URI
        val projection = CALENDAR_PROJECTION
        val selection: String? = null
        val selectionArgs: Array<String>? = null
        val sortOrder: String? = null

//            クエリ発行を行いカーソルを取得
        val cr: ContentResolver = applicationContext.contentResolver
        val cur: Cursor? = cr.query(
            uri,
            CALENDAR_PROJECTION,
            selection,
            selectionArgs,
            sortOrder
        )

//            カレンダー名一覧取得と出力
        val calendarNameList = mutableListOf<String>()
        val calendarIdList = mutableListOf<Long>()
        val calendarIdNameList = mutableListOf<String>()
        while (cur!!.moveToNext()) {
//                カーソルから各プロパティの取得
            val id: Long = cur.getLong(CALENDAR_PROJECTION_IDX_ID)
            val name: String = cur.getString(CALENDAR_PROJECTION_IDX_NAME)
            val accountName: String = cur.getString(CALENDAR_PROJECTION_IDX_ACCOUNT_NAME)
            val accountType = cur.getString(CALENDAR_PROJECTION_IDX_ACCOUNT_TYPE)
            val calendarColor: Int = cur.getInt(CALENDAR_PROJECTION_IDX_CALENDAR_COLOR)
            val calendarColorKey: Int = cur.getInt(CALENDAR_PROJECTION_IDX_CALENDAR_COLOR_KEY)
            val calendarDisplayNam: String = cur.getString(CALENDAR_PROJECTION_IDX_DISPLAY_NAME)
            val calendarAccessLevel: Int = cur.getInt(CALENDAR_PROJECTION_IDX_ACCESS_LEVEL)
            val calendarTimeZone: String = cur.getString(CALENDAR_PROJECTION_IDX_TIME_ZONE)
            val visible: Int = cur.getInt(CALENDAR_PROJECTION_IDX_VISIBLE)
            val syncEvents: Int = cur.getInt(CALENDAR_PROJECTION_IDX_SYNC_EVENTS)
            val ownerAccount: String = cur.getString(CALENDAR_PROJECTION_IDX_OWNER_ACCOUNT)

            calendarNameList.add(name)
            calendarIdList.add(id)
            val idStr  = id.toString()
            calendarIdNameList.add(idStr)
            calendarIdNameList.add(name)
        }


        //カレンダー選択スピナーの設定
        //spinnerの取得
        val spinner = findViewById<Spinner>(R.id.spinner)

        //adapter生成
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            calendarNameList
        )

        //選択肢のレイアウト
        adapter.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item)

        //adapterをspinnerにせってい

        spinner.adapter = adapter




//        ユーザー入力の受付
        val summary = findViewById<EditText>(R.id.enteredSummary)
        val location = findViewById<EditText>(R.id.enteredLocation)
        val description = findViewById<EditText>(R.id.enteredDescription)
        val sYear = findViewById<EditText>(R.id.startYearEdit)
        val eYear = findViewById<EditText>(R.id.endYearEdit)
        val sMonth = findViewById<EditText>(R.id.startMonthEdit)
        val eMonth = findViewById<EditText>(R.id.endMonthEdit)
        val sDay = findViewById<EditText>(R.id.startDayEdit)
        val eDay = findViewById<EditText>(R.id.endDayEdit)
        val sHour = findViewById<EditText>(R.id.startHourEdit)
        val eHour = findViewById<EditText>(R.id.endHourEdit)
        val sMinute = findViewById<EditText>(R.id.startMinuteEdit)
        val eMinute = findViewById<EditText>(R.id.endMinuteEdit)
        val allDay = findViewById<CheckBox>(R.id.allDayCheckBox)

        val registration = findViewById<Button>(R.id.registrationBtn)
        registration.setOnClickListener {
            Toast.makeText(this, "Registration button tapped.", Toast.LENGTH_SHORT).show()

            println("Tapped registration Btn.")
            println("-----入力された情報----------------------------------------------")
            println("summary = ${summary.text}")
            println("location = ${location.text}")
            println("description = ${description.text}")
            println("sYear = ${sYear.text}")
            println("sMonth = ${sMonth.text}")
            println("sDay = ${sDay.text}")
            println("sHour = ${sHour.text}")
            println("sMinute = ${sMinute.text}")
            println("eYear = ${eYear.text}")
            println("eMonth = ${eMonth.text}")
            println("eDay = ${eDay.text}")
            println("eHour = ${eHour.text}")
            println("eMinute = ${eMinute.text}")
            println("allDay = ${allDay.text}")

                
            println("--------------------------------------------------------------")
            println(calendarIdNameList)

        }



        val test = findViewById<Button>(R.id.test)
        test.setOnClickListener {
            Toast.makeText(this, "Test Btn Pushed.", Toast.LENGTH_SHORT).show()
            val intent = Intent(applicationContext , TestActivity::class.java)
            startActivity(intent)




        }


    }

}

