package jp.ryou.gccmobile

import android.app.Application
import android.content.ContentResolver
import android.content.ContentValues
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
import androidx.appcompat.widget.DecorContentParent
import androidx.core.app.ActivityCompat
import jp.ryou.gccmobile.databinding.ActivityMainBinding
import java.util.*

class MyApp :Application(){
    var QRResult: String? = null
    var selectedCalendar : String? = null
    var test : String? = "Test"

    companion object {
        private var instance : MyApp? = null

        fun  getInstance(): MyApp {
            if (instance == null)
                instance = MyApp()

            return instance!!
        }
    }
}







class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding




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
        calendarNameList.add("登録先カレンダーを選択してください")
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
            calendarIdNameList.add(idStr.toString())
            calendarIdNameList.add(name.toString())
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

        //adapterをspinnerに設定

        spinner.adapter = adapter

        //選択されたアイテムを取得
        // リスナーを登録

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            //　アイテムが選択された時
            override fun onItemSelected(parent: AdapterView<*>?,
                                        view: View?, position: Int, id: Long) {
                val spinnerParent = parent as Spinner
                val selectedCalendar = spinnerParent.selectedItem as String
                println("スピナーから${selectedCalendar}が選択されました。")
                if (selectedCalendar!="登録先カレンダーを選択してください"){
                    Toast.makeText(this@MainActivity, "$selectedCalendar が選択されました。", Toast.LENGTH_SHORT).show()
                }
            }

            //　アイテムが選択されなかった
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //選択されなかった場合の処理
            }
        }

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


        val addEvent = findViewById<Button>(R.id.registrationBtn)
        addEvent.setOnClickListener {
            Toast.makeText(this, "予定登録開始", Toast.LENGTH_SHORT).show()

            //カレンダーIDの特定
            //calendarIdNameListから選択されたカレンダー名（文字列）を検索し、インデックスから１引けばIDとなるはず。
            var calendarId : Int? = null
            val selected = spinner.selectedItem.toString()
            var index = calendarIdNameList.indexOf(selected)
            index -= 1
            if (index >= 0) {
                calendarId = calendarIdNameList[index].toInt()
            }

            //イベント開始・終了時間を設定
            var  startMillis : Long = 0
            var  endMillis : Long  = 0
            var beginTime : Calendar = Calendar.getInstance()
            //月は指定した値+1月が予定追加の対象になる
            beginTime.set(2023, 2, 1, 10, 0)
            startMillis = beginTime.getTimeInMillis()
            var endTime : Calendar = Calendar.getInstance()
            endTime.set(2023, 2, 1, 14, 0)
            endMillis = endTime.getTimeInMillis();


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
            println("calendaId = $calendarId")
            println("--------------------------------------------------------------")


            println("予定登録プロセスを開始")
            //イベントデータを登録
            var cr : ContentResolver = getContentResolver()
            var values : ContentValues =  ContentValues()
            values.put(CalendarContract.Events.DTSTART, startMillis)
            values.put(CalendarContract.Events.DTEND, endMillis)
            values.put(CalendarContract.Events.TITLE, "${summary.text}")
            values.put(CalendarContract.Events.DESCRIPTION, "${description.text}")
            values.put(CalendarContract.Events.EVENT_LOCATION, "${location.text}")
            values.put(CalendarContract.Events.CALENDAR_ID, calendarId)
            values.put(CalendarContract.Events.EVENT_TIMEZONE, "Japan/Tokyo" )
            val uri : Uri? = cr.insert(CalendarContract.Events.CONTENT_URI, values);
            println("登録完了")
        }

        val test = findViewById<Button>(R.id.test)
        test.setOnClickListener {
            Toast.makeText(this, "Test Btn Pushed.", Toast.LENGTH_SHORT).show()
            val intent = Intent(applicationContext , TestActivity::class.java)
            startActivity(intent)
        }


        val anyBtn = findViewById<Button>(R.id.anyBtn)
        anyBtn.setOnClickListener {
            println("anyBtn was pushed.")
            }
        }
    }


