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
//            [1, 日本の祝日, 2, 誕生日, 3, ファミリー カレンダー, 4, raika.momo7@gmail.com, 5, 学校,
        //     6, ryou120710@gmail.com, 7, シフト, 8, 記念日, 9, sample, 10, 【IS13-2】IoT実習４ 2021年度 前期,
        //     11, 2021(A)Mon(p)【IS13-2】システムセキュリティ１, 12, 2021(A)Tue(a)【IS13-2】Webシステム開発,
        //     13, [IS13-2]システムリサーチ 2021年度 3年次前期　木3～4　717, 14, 【IS13-2】シスコ認定技術3 水曜日 1～8時限,
        //     15, IS13-2  外国語３ 21前期(金)5～6限　担当：田中, 16, 前期：IS13-2　ビジネススキル２ 月曜日　10：40-12：10　＠717,
        //     17, 【IS13-2】経営戦略とマーケティング 火曜日 14:40～16:10／担当：妹尾, 18, 2021(B)Tue(a)【IS13-2】ネットワークセキュリティ演習１,
        //     19, 2021(B)Wed(a)【IS13-2】システムセキュリティ２, 20, IS13-2　ビジネススキル３ 2021後期　金曜日10:40-12:10＠711,
        //     21, IS13-2  外国語４ 金曜1～2限（21後期）担当：田中, 22, システム開発グループ演習２, 23, 2021(B)Wed(p)【IS13-2】ネットワークセキュリティ実習１,
        //     24, IS13 卒業制作1➡2 2022通年担当：大日方、市川、中西, 25, IS13-2 ネットワークセキュリティ実習２ 2022前期 木曜 9:00～12:10 担当：勝島,
        //     26, IS13-2 ネットワークセキュリティ演習２ 2022前期 火曜 13:00～16:10 担当：勝島, 27, 日本の祝日, 28, 誕生日,
        //     29, IS13 ファイナンシャルプランニング 2022年度後期 金曜5-6限,30605教室, 30, k019c1355@g.neec.ac.jp,
        //     31, 《CD66》サーバ構築実習2 専門科目（水曜1～4限、担当：下川、SA：手嶋さん）, 32, IS15-1 Webセキュリティ実習 2022後期 火曜9:00～10:30 担当：中西，SSK,
        //     33, NT25-1 Webセキュリティ実習 2022後期 火曜10:40～12:10 担当：中西，SSK]



        }
        //        予定をカレンダーへ登録

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
    }
}

