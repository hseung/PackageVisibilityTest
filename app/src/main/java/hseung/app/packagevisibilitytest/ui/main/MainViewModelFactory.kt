package hseung.app.packagevisibilitytest.ui.main

import android.content.pm.PackageManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hseung.app.packagevisibilitytest.domain.LandUsecase

class MainViewModelFactory(
    private val packageManager: PackageManager,
    private val landUsecase: LandUsecase
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = MainViewModel(packageManager, landUsecase) as T
}