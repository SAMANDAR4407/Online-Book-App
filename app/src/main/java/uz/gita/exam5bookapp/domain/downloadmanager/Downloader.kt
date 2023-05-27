package uz.gita.exam5bookapp.domain.downloadmanager

import android.app.DownloadManager
import android.content.Context
import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import androidx.core.net.toUri

/**
 *    Created by Kamolov Samandar on 14.05.2023 at 7:06
 */


interface Downloader {
    fun downloadFile(url: String): Long
}

class AndroidDownloader(
    private val context: Context
): Downloader {
    @RequiresApi(Build.VERSION_CODES.M)
    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun downloadFile(url: String): Long {
        val request = DownloadManager.Request(url.toUri())
//            .setMimeType("application/pdf")
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED) // process not finish
//            .addRequestHeader("Authorization", "Bearer <token>") //token maybe
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "BOOKAPP/$url.pdf")
        return downloadManager.enqueue(request)
    }
}