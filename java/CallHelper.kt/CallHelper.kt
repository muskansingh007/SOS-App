package com.sosapp

import android.content.Context
import android.content.Intent
import android.net.Uri

fun makeCall(context: Context, number: String) {
    val intent = Intent(Intent.ACTION_CALL)
    intent.data = Uri.parse("tel:$number")
    context.startActivity(intent)
}
