package dev.restcountries.app.base

import android.content.Context

/**
 * Base view any view must implement.
 */
interface BaseView {

    /**
     * @return the view Context
     */
    fun getContext(): Context

}