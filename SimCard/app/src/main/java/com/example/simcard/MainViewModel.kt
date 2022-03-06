package com.example.simcard

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.PhoneStateListener
import android.telephony.SubscriptionInfo
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class MainViewModel(application: Application?) : AndroidViewModel(application!!) {
    private val telephonyManager: TelephonyManager
    private var subscriptionManager: SubscriptionManager? = null
    private var subscriptionInfo: SubscriptionInfo? = null
    private val simChangedListener: SimChangedListener
    private val liveSimState = MutableLiveData<Boolean>()


    @SuppressLint("MissingPermission")
    private fun setSubscription() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            subscriptionManager =
                getApplication<Application>().getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager
            subscriptionInfo = subscriptionManager!!.getActiveSubscriptionInfoForSimSlotIndex(0)
        } else {
            Log.d(TAG, "setSubscription() else")
        }
    }
        private fun checkSubscriptionInfo(): Boolean {
            return subscriptionInfo != null && !subscriptionInfo!!.countryIso.isEmpty()
        }


    fun registerSimState() {
        telephonyManager.listen(
            simChangedListener,
            PhoneStateListener.LISTEN_SERVICE_STATE or PhoneStateListener.LISTEN_SIGNAL_STRENGTHS
        )
    }
        fun unregisterSimState() {
            telephonyManager.listen(simChangedListener, PhoneStateListener.LISTEN_NONE)
        }


    fun checkSimState() {
        liveSimState.postValue(isSimMounted)
    }

    private val isSimMounted: Boolean
        private get() {
            Log.d(TAG, "checkSimState()")
            val isAvailable: Boolean
            val simState: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                telephonyManager.getSimState(0)
            } else {
                telephonyManager.simState
            }
            when (simState) {
                TelephonyManager.SIM_STATE_READY -> {
                    isAvailable = true
                    Log.d(TAG, "TelephonyManager.SIM_STATE_READY")
                }
                TelephonyManager.SIM_STATE_ABSENT -> {
                    isAvailable = false
                    Log.d(TAG, "TelephonyManager.SIM_STATE_ABSENT")
                }
                TelephonyManager.SIM_STATE_NETWORK_LOCKED -> {
                    isAvailable = false
                    Log.d(TAG, "TelephonyManager.SIM_STATE_NETWORK_LOCKED")
                }
                TelephonyManager.SIM_STATE_PIN_REQUIRED -> {
                    isAvailable = false
                    Log.d(TAG, "TelephonyManager.SIM_STATE_PIN_REQUIRED")
                }
                TelephonyManager.SIM_STATE_PUK_REQUIRED -> {
                    isAvailable = false
                    Log.d(TAG, "TelephonyManager.SIM_STATE_PUK_REQUIRED")
                }
                TelephonyManager.SIM_STATE_UNKNOWN -> {
                    isAvailable = false
                    Log.d(TAG, "TelephonyManager.SIM_STATE_UNKNOWN")
                }
                else -> {
                    isAvailable = false
                    Log.d(TAG, "TelephonyManager.default")
                }
            }
            return isAvailable
        }

    fun observeLiveSimState(): LiveData<Boolean> {
        return liveSimState
    }

    //endregion
    //region Manifest Permissions
    fun checkPermission(): Boolean {
        var value = false
        Log.d(TAG, "checkPermission()")
        if (isReadPhoneStatePermissionGranted) {
            Log.d(TAG, "isReadPhoneStatePermissionGranted()")
            value = true
        }
        return value
    }

    private val isReadPhoneStatePermissionGranted: Boolean
        private get() = ActivityCompat
            .checkSelfPermission(
                getApplication(),
                Manifest.permission.READ_PHONE_STATE
            ) == PackageManager.PERMISSION_GRANTED


    override fun onCleared() {
        super.onCleared()
    }

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }

    init {
        Log.d(TAG, "Constructor")
        telephonyManager =
            getApplication<Application>().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        simChangedListener = SimChangedListener(this)
        //setSubscription();
    }
}