package jp.ryou.gccmobile

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.provider.CalendarContract.Calendars
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.provider.CalendarContract.Events
import java.util.*
import android.util.Log
import android.content.ContentValues.*
import android.widget.EditText


class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        //    Buck to mainactivity.
        val buck = findViewById<Button>(R.id.buck)
        buck.setOnClickListener {
            Toast.makeText(this, "Jump main activity.", Toast.LENGTH_SHORT).show()
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }

//        カレンダー一覧取得プロセス
        val calendarlist = findViewById<Button>(R.id.getCalendarList)
        calendarlist.setOnClickListener {
            Toast.makeText(this, "Output calendar list on console.", Toast.LENGTH_SHORT).show()
            Toast.makeText(this, "Jump main activity.", Toast.LENGTH_SHORT).show()

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
            val uri: Uri = Calendars.CONTENT_URI
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
            var calendarNameList = mutableListOf<String>()
            var calendarIdList = mutableListOf<Long>()
            var calendarIdNameList = mutableListOf<String>()
            while (cur!!.moveToNext()) {
//                カーソルから各プロパティの取得
                val id: Long = cur.getLong(CALENDAR_PROJECTION_IDX_ID)
                val name: String = cur.getString(CALENDAR_PROJECTION_IDX_NAME)
                val accountName: String = cur.getString(CALENDAR_PROJECTION_IDX_ACCOUNT_NAME)
                val accountType = cur.getString(CALENDAR_PROJECTION_IDX_ACCOUNT_TYPE)
                val calendarColor: Int = cur.getInt(CALENDAR_PROJECTION_IDX_CALENDAR_COLOR)
                val calendarColorkey: Int = cur.getInt(CALENDAR_PROJECTION_IDX_CALENDAR_COLOR_KEY)
                val calendarDisplayNam: String = cur.getString(CALENDAR_PROJECTION_IDX_DISPLAY_NAME)
                val calendarAccessLevel: Int = cur.getInt(CALENDAR_PROJECTION_IDX_ACCESS_LEVEL)
                val calendarTimeZone: String = cur.getString(CALENDAR_PROJECTION_IDX_TIME_ZONE)
                val visible: Int = cur.getInt(CALENDAR_PROJECTION_IDX_VISIBLE)
                val syncEvents: Int = cur.getInt(CALENDAR_PROJECTION_IDX_SYNC_EVENTS)
                val ownerAcount: String = cur.getString(CALENDAR_PROJECTION_IDX_OWNER_ACCOUNT)

                calendarNameList.add(name)
                calendarIdList.add(id)
                var idStr  = id.toString()
                calendarIdNameList.add(idStr)
                calendarIdNameList.add(name)
                println(calendarColorkey)
            }
            println("calendarNameList ↓↓↓")
            println(calendarNameList)
            println("calendarIdList ↓↓↓")
            println(calendarIdList)
            println("calendarIdNameList ↓↓↓")
            println(calendarIdNameList)


        }

        //        予定をカレンダーへ登録するプロセス
        val addEvent = findViewById<Button>(R.id.addEvent)
        addEvent.setOnClickListener {
            Toast.makeText(this, "予定登録ボタンが押下されました", Toast.LENGTH_SHORT).show()


            println("予定登録プロセスを開始")
//イベント開始・終了時間を設定
            var  startMillis : Long = 0
            var  endMillis : Long  = 0
            var beginTime : Calendar = Calendar.getInstance()
            beginTime.set(2023, 0, 1, 10, 0)
            startMillis = beginTime.getTimeInMillis()
            var endTime :  Calendar = Calendar.getInstance()
            endTime.set(2023, 0, 1, 12, 0)
            endMillis = endTime.getTimeInMillis();

//イベントデータを登録
            var cr : ContentResolver = getContentResolver()
            var values : ContentValues =  ContentValues()
            values.put(CalendarContract.Events.DTSTART, startMillis)
            values.put(CalendarContract.Events.DTEND, endMillis)
            values.put(CalendarContract.Events.TITLE, "テストイベント")
            values.put(CalendarContract.Events.DESCRIPTION, "これはテストイベントです。")
            values.put(CalendarContract.Events.CALENDAR_ID, 9)
            values.put(CalendarContract.Events.EVENT_TIMEZONE, "Japan/Tokyo" )
            val uri : Uri? = cr.insert(CalendarContract.Events.CONTENT_URI, values);
            println("登録完了？")
        }

        val editText = findViewById<EditText>(R.id.editTextTest)
        val goBtn = findViewById<Button>(R.id.goBtn)
        goBtn.setOnClickListener {
            Toast.makeText(applicationContext, editText.text.toString(), Toast.LENGTH_SHORT).show()
            println(editText.text.toString())
        }

        //index取得テスト
        val printIndex = findViewById<Button>(R.id.printIndex)
        printIndex.setOnClickListener {
            val values = listOf(1, "日本の祝日", 2, "誕生日", 3, "ファミリー カレンダー", 4, "raika.momo7@gmail.com", 5, "学校", 6, "ryou120710@gmail.com", 7, "シフト", 8, "記念日", 9, "sample")
            val item = "sample"

            val index = values.indexOf(item)
            println(values[index-1])
            println(index)


        }

    }
}

