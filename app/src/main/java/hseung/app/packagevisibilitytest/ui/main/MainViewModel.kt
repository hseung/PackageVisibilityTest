package hseung.app.packagevisibilitytest.ui.main

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import hseung.app.packagevisibilitytest.domain.LandUsecase
import hseung.app.packagevisibilitytest.ui.SingleLiveEvent
import java.util.*

class MainViewModel(
    val packageManager: PackageManager,
    val landUsecase: LandUsecase
) : ViewModel() {
    val title = "Package Visibility Test"
    val showSnackbar = SingleLiveEvent<String>()

    fun onGetPackageInfo() {
        if (isInstalled("com.google.android.deskclock")) {
            showSnackbar.value = "Clock installed"
        } else {
            showSnackbar.value = "Clock not installed"

        }
    }

    fun onQueryIntentActivities() {
        queryIntentActivitiesForActionView().let {
            Log.d(TAG, "[onQueryIntentActivities] $it")
            showSnackbar.value = "QueryIntentActivities for view url: ${it}"
        }
    }

    fun onResolveActivity() {
        showSnackbar.value = "[onResolveActivity] getDefaultBrowserPackageName: ${getDefaultBrowserPackageName()}"
    }

    fun onGetInstalledPackages() {
        getInstalledPackages().let {
            Log.d(TAG, "[onGetInstalledPackages] $it")
            showSnackbar.value = "GetInstalledPackages: $it"
        }
    }

    fun onGetInstalledApplications() {
        getInstalledApplications().let {
            Log.d(TAG, "[onGetInstalledApplications] $it")
            showSnackbar.value = "GetInstalledApplications: $it"
        }
    }

    fun onIntentSetPackage() {
        val packageName = "com.android.chrome"
        try {
            landUsecase.landingToExternal("https://www.amazon.com", packageName)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to start with packageName: $packageName", e)
        }
    }

    fun isInstalled(packageName: String): Boolean {
        return try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(TAG, "", e)
            false
        }
    }

    private fun queryIntentActivitiesForActionView(): List<String> {
        Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com")).let { intent ->
            val list = packageManager.queryIntentActivities(intent, 0)
            val packages: MutableList<String> = ArrayList()
            for (info in list) {
                packages.add(info.activityInfo.packageName)
            }
            return packages
        }
    }

    fun getDefaultBrowserPackageName(): String {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
        val componentName = intent.resolveActivity(packageManager) ?: return ""
        return componentName.packageName
    }

    private fun getInstalledPackages(): List<String> {
        val list = packageManager.getInstalledPackages(0)
        val packages: MutableList<String> = ArrayList()
        for (info in list) {
            packages.add(info.packageName)
        }
        return packages
    }

    private fun getInstalledApplications(): List<String> {
        val list = packageManager.getInstalledApplications(0)
        val packages: MutableList<String> = ArrayList()
        for (info in list) {
//            if ((info.flags and ApplicationInfo.FLAG_SYSTEM) == 0) {
                packages.add(info.packageName)
//            }
        }
        return packages
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}