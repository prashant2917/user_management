package com.pocket.usermanagement.utils



import android.util.Log
import com.pocket.usermanagement.BuildConfig


object AppLogger {

    private const val TAG = "AppLogger"

    fun d(message: String) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, message)
        }
    }

    fun d(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message)
        }
    }

    fun i(message: String) {
        Log.i(TAG, message)
    }

    fun i(tag: String, message: String) {
        Log.i(tag, message)
    }

    fun w(message: String) {
        Log.w(TAG, message)
    }

    fun w(tag: String, message: String) {
        Log.w(tag, message)}

    fun e(message: String) {
        Log.e(TAG, message)
    }

    fun e(tag: String, message: String) {
        Log.e(tag, message)
    }

    fun e(message: String, throwable: Throwable) {
        Log.e(TAG, message, throwable)
    }

    fun e(tag: String, message: String, throwable: Throwable) {
        Log.e(tag, message, throwable)
    }
}