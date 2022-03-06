package com.example.simcard

import android.telephony.PhoneStateListener
import android.telephony.ServiceState
import android.telephony.SignalStrength
import android.util.Log

class SimChangedListener(mainViewModel: MainViewModel) : PhoneStateListener() {
    var mainViewModel: MainViewModel
    override fun onServiceStateChanged(serviceState: ServiceState) {
        super.onServiceStateChanged(serviceState)
        Log.d(TAG, "onServiceStateChanged($serviceState)")
        mainViewModel.checkSimState()
    }

    override fun onSignalStrengthsChanged(signalStrength: SignalStrength) {
        super.onSignalStrengthsChanged(signalStrength)
        Log.d(TAG, "onSignalStrengthsChanged($signalStrength)")
        mainViewModel.checkSimState()
    }

    companion object {
        private val TAG = SimChangedListener::class.java.simpleName
    }

    init {
        Log.d(TAG, "constructor($mainViewModel)")
        this.mainViewModel = mainViewModel
    }
}