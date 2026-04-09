package com.sosapp

import android.telephony.SmsManager

fun sendSMS(phone: String, message: String) {
    val smsManager = SmsManager.getDefault()
    smsManager.sendTextMessage(phone, null, message, null, null)
}