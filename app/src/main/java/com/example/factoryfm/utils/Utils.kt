package com.example.factoryfm.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.squareup.picasso.Picasso

fun hideKeyboard(activity: Activity) {
    val imm: InputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    val view = activity.currentFocus ?: View(activity)
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun displayImageWithPlaceholder(imgUrl: String, target: ImageView, placeholder: Int, context: Context) {
    if (imgUrl.isNotEmpty()) {
        Picasso
            .with(context)
            .load(imgUrl)
            .placeholder(placeholder)
            .error(placeholder)
            .into(target)
    } else {
        Picasso
            .with(context)
            .load(placeholder)
            .placeholder(placeholder)
            .into(target)
    }
}
