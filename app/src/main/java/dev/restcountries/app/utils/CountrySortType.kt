package dev.restcountries.app.utils

import androidx.annotation.DrawableRes
import dev.restcountries.app.R
import dev.restcountries.app.model.BasicCountryInfo


enum class CountrySortType(@DrawableRes val resId: Int, val comparator: Comparator<BasicCountryInfo>) {

    NAME(R.drawable.empty, compareBy(BasicCountryInfo::name)),
    POPULATION(R.drawable.ic_outline_people_40dp, compareByDescending(BasicCountryInfo::population)),
    AREA(R.drawable.ic_outline_zoom_out_map_40dp, compareByDescending(BasicCountryInfo::area));
}
