package uz.gita.exam5bookapp.utils

import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

fun ComponentActivity.checkPermissions(array : List<String>, block : ()->Unit){
    Dexter.withContext(this).withPermissions(array).withListener(object : MultiplePermissionsListener{
        override fun onPermissionsChecked(report: MultiplePermissionsReport) {
            report.let{
                if (report.areAllPermissionsGranted())
                    block.invoke()
            }
        }

        override fun onPermissionRationaleShouldBeShown(p0: MutableList<PermissionRequest>?, p1: PermissionToken?) {
            val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse("package:$packageName")
            startActivity(intent)
        }
    }).check()
}