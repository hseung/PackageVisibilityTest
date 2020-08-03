package hseung.app.packagevisibilitytest.domain

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.util.Log

class LandUsecase(
    private val context: Context
) {
    fun landingToExternal(url: String, packageName: String) {
//        try {
            Intent(Intent.ACTION_VIEW, Uri.parse(url)).let { i ->
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                if (!TextUtils.isEmpty(packageName)) {
                    i.setPackage(packageName)
                }
                context.startActivity(i)
            }
//        } catch (e: Exception) {
//            if (!TextUtils.isEmpty(packageName)) {
//                Log.w(TAG, "[landingToExternal] failed to start with packageName: $packageName, url: $url")
//                val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                context.startActivity(i)
//            }
//            e.printStackTrace()
//        }
    }

    companion object {
        const val TAG = "LandUsecase"
    }
}