package com.example.portfolio_dio.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.hideKeyboard() {
    val im = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    im.hideSoftInputFromWindow(windowToken, 0)
}