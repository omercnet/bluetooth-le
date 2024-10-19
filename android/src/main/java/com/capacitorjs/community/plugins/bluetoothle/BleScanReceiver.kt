package com.capacitorjs.community.plugins.bluetoothle

import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi

class BleScanReceiver : BroadcastReceiver() {
    companion object {
        var scanCallback: ScanCallback? = null  // Reference to the existing ScanCallback
        var action = "com.capacitorjs.community.plugins.bluetoothle.ACTION_FOUND"
        private val TAG = BleScanReceiver::class.java.simpleName
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {
            val results: List<ScanResult>? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                // For Android 13 and above
                intent?.getParcelableArrayListExtra(BluetoothLeScanner.EXTRA_LIST_SCAN_RESULT, ScanResult::class.java)
            } else {
                // For Android 12 and below
                @Suppress("DEPRECATION")
                intent?.getParcelableArrayListExtra(BluetoothLeScanner.EXTRA_LIST_SCAN_RESULT)
            }

            results?.forEach { result ->
                // Forward the results to the ScanCallback
                scanCallback?.onScanResult(ScanSettings.CALLBACK_TYPE_ALL_MATCHES, result)
            }
        }
}
