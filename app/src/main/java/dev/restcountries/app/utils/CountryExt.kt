package dev.restcountries.app.utils

import dev.restcountries.app.model.BasicCountryInfo
import dev.restcountries.app.model.Country
import java.text.NumberFormat


private const val EMPTY = ""


fun BasicCountryInfo?.generateFlagImageUrl(): String? =
    this?.let { FLAG_URL + this.alpha2Code.toLowerCase() + FLAG_IMAGE_EXTENSION }


fun Country?.getCallingCodesString(): String = this?.let { it.callingCodes.joinToString(", ") } ?: EMPTY


fun Country?.getLanguagesString(): String =
    this?.let { it.languages.asSequence().map { "${it.name} (${it.nativeName}) " }.joinToString(", ") } ?: EMPTY


fun Country?.getCurrenciesString(): String =
    this?.let { it.currencies.asSequence().joinToString(", ") { "${it.name} (${it.symbol}) " } } ?: EMPTY


fun Country?.getRegionAndSubRegionString(): String = this?.let { "${it.region}/${it.region}" } ?: EMPTY


fun Country?.getTimeZonesString(): String =
    this?.let { it.timezones.asSequence().map { it.replace("UTC", "") }.joinToString(",", "UTC: ", "") } ?: EMPTY


fun Country?.getPopulationString(): String = this?.let { NumberFormat.getInstance().format(it.population) } ?: EMPTY


fun BasicCountryInfo?.getExtraInfoString(sortType: CountrySortType): String = this?.let {
    when (sortType) {
        CountrySortType.NAME -> EMPTY
        CountrySortType.POPULATION -> NumberFormat.getInstance().format(it.population)
        CountrySortType.AREA -> NumberFormat.getInstance().format(it.area)
    }
} ?: EMPTY
