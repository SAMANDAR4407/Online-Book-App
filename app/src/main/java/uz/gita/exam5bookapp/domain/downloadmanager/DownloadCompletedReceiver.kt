package uz.gita.exam5bookapp.domain.downloadmanager

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

/**
 *    Created by Kamolov Samandar on 14.05.2023 at 7:07
 */

class DownloadCompletedReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == "android.intent.action.DOWNLOAD_COMPLETE") {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L)
            if(id != -1L) {
                Toast.makeText(context, "File Downloaded!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}