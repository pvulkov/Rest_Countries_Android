package dev.restcountries.app.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes


fun Context.showToast(@StringRes resId: Int) = Toast.makeText(this, resId, Toast.LENGTH_LONG).show()