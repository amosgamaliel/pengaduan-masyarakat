package com.rectangle.cepuonline.util

import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import android.provider.OpenableColumns
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar


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
        DialogInterface.OnClickListener { dialog, which -> alertDialog.dismiss() })
    alertDialog.show()
}