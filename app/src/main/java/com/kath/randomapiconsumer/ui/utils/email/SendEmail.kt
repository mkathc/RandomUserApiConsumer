package com.kath.randomapiconsumer.ui.utils.email

import android.content.Context
import android.content.Intent
import android.os.Environment
import androidx.core.content.FileProvider
import com.kath.randomapiconsumer.BuildConfig
import java.io.File

class SendEmail(
    private val context: Context
) {

    fun sendEmailTo(email: String, namefile:String, isDownloaded:Boolean) {
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = "text/plain"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Envío de foto")
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Contenido")
        val root: File = Environment.getExternalStorageDirectory()
        val pathToMyAttachedFile = "${namefile}.jpg"
        val file = File(root, pathToMyAttachedFile)
        if (isDownloaded){
            val uri = FileProvider.getUriForFile(context,
                BuildConfig.APPLICATION_ID + ".provider", file)
            emailIntent.putExtra(Intent.EXTRA_STREAM, uri)
        }
        context.startActivity(Intent.createChooser(emailIntent,
            "Pick an Email provider"))
    }
}