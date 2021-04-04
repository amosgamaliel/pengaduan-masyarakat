package com.rectangle.cepuonline.util

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.abs


fun Context.toast(message : String){
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}
//fun ProgressBar.show(){
//    progress_bar.visibility = View.VISIBLE
//}
//fun ProgressBar.hide(){
//    progress_bar.visibility = View.GONE
//}
fun View.snackbar(message: String){
    Snackbar.make(this,message,Snackbar.LENGTH_SHORT).also { snackbar ->
        snackbar.setAction("OK"){
            snackbar.dismiss()
        }
    }.show()
}

@RequiresApi(Build.VERSION_CODES.O)
fun Date.parseDate(date : Date):String{
    val dates = "2021-01-01 09:09:23"
    val formatterMili: DateTimeFormatter =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val localDate: LocalDateTime = LocalDateTime.parse(dates, formatterMili)
    val timeInMilliseconds: Long =
        localDate.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli()

    //API.log("Day Ago "+dayago);
    //API.log("Day Ago "+dayago);
    val result = "now"
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val todayDate: String = formatter.format(Date())
    val calendar = Calendar.getInstance()

    val dayagolong: Long = java.lang.Long.valueOf(timeInMilliseconds) * 1000
    calendar.timeInMillis = dayagolong
    val agoformater: String = formatter.format(calendar.time)

    var currentDate: Date? = null
    var createDate: Date? = null

    try {
        currentDate = formatter.parse(todayDate)
        createDate = formatter.parse(agoformater)
        var different =
            abs(currentDate.time - createDate.time)
        val secondsInMilli: Long = 1000
        val minutesInMilli = secondsInMilli * 60
        val hoursInMilli = minutesInMilli * 60
        val daysInMilli = hoursInMilli * 24
        val elapsedDays = different / daysInMilli
        different = different % daysInMilli

        val elapsedHours = different / hoursInMilli
        different = different % hoursInMilli

        val elapsedMinutes = different / minutesInMilli
        different = different % minutesInMilli

        val elapsedSeconds = different / secondsInMilli
        if (elapsedDays == 0L) {
            if (elapsedHours == 0L) {
                if (elapsedMinutes == 0L) {
                    if (elapsedSeconds < 0) {
                        return "0" + " s"
                    } else {
                        if (elapsedDays > 0 && elapsedSeconds < 59) {
                            return "now"
                        }
                    }
                } else {
                    return elapsedMinutes.toString() + "m ago"
                }
            } else {
                return elapsedHours.toString() + "h ago"
            }
        } else {
            if (elapsedDays <= 29) {
                return elapsedDays.toString() + "d ago"
            }
            if (elapsedDays in 30..58) {
                return "1Mth ago"
            }
            if (elapsedDays in 59..87) {
                return "2Mth ago"
            }
            if (elapsedDays in 88..116) {
                return "3Mth ago"
            }
            if (elapsedDays in 117..145) {
                return "4Mth ago"
            }
            if (elapsedDays in 146..174) {
                return "5Mth ago"
            }
            if (elapsedDays in 175..203) {
                return "6Mth ago"
            }
            if (elapsedDays in 204..232) {
                return "7Mth ago"
            }
            if (elapsedDays in 233..261) {
                return "8Mth ago"
            }
            if (elapsedDays > 261 && elapsedDays <= 290) {
                return "9Mth ago"
            }
            if (elapsedDays > 290 && elapsedDays <= 319) {
                return "10Mth ago"
            }
            if (elapsedDays > 319 && elapsedDays <= 348) {
                return "11Mth ago"
            }
            if (elapsedDays > 348 && elapsedDays <= 360) {
                return "12Mth ago"
            }
            if (elapsedDays > 360 && elapsedDays <= 720) {
                return "1 year ago"
            }
            if (elapsedDays > 720) {
                val formatterYear =
                    SimpleDateFormat("MM/dd/yyyy")
                val calendarYear = Calendar.getInstance()
                calendarYear.timeInMillis = dayagolong
                return formatterYear.format(calendarYear.time).toString() + "sue"
            }
        }
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return result;
}

fun ContentResolver.getFileName(fileUri: Uri): String {
    var name = ""
    val returnCursor = this.query(fileUri, null, null, null, null)
    if (returnCursor != null) {
        val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        name = returnCursor.getString(nameIndex)
        returnCursor.close()
    }
    return name
}

fun alertDialogShow(context: Context?, message: String?) {
    val alertDialog: AlertDialog = AlertDialog.Builder(context).create()
    alertDialog.setMessage(message)
    alertDialog.setButton("OK",
        DialogInterface.OnClickListener { dialog, which ->
            if( context is Activity )
                context.finish()
            alertDialog.dismiss()
        })

    alertDialog.show()
}