package hseung.app.packagevisibilitytest.ui

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.Nullable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveEvent<T> : MutableLiveData<T>() {

    private val mPending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        // Observe the internal MutableLiveData
        super.observe(owner, Observer { t ->
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    @MainThread
    override fun setValue(@Nullable t: T?) {
        mPending.set(true)
        super.setValue(t)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        value = null
    }

//    private val mPending =
//        AtomicBoolean(false)
//
//    @MainThread
//    fun observe(owner: LifecycleOwner?, observer: Observer<T>) {
//        if (hasActiveObservers()) {
//            Log.w(
//                TAG,
//                "Multiple observers registered but only one will be notified of changes."
//            )
//        }
//
//        // Observe the internal MutableLiveData
//        super.observe(owner, object : Observer<T>() {
//            fun onChanged(@Nullable t: T) {
//                if (mPending.compareAndSet(true, false)) {
//                    observer.onChanged(t)
//                }
//            }
//        })
//    }
//
//    @MainThread
//    override fun setValue(@Nullable t: T?) {
//        mPending.set(true)
//        super.setValue(t)
//    }
//
//    /**
//     * Used for cases where T is Void, to make calls cleaner.
//     */
//    @MainThread
//    fun call() {
//        value = null
//    }
//
//    companion object {
//        private const val TAG = "SingleLiveEvent"
//    }
}
