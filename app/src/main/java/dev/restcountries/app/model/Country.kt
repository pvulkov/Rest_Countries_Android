package dev.restcountries.app.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import dev.restcountries.app.utils.*
import java.text.NumberFormat

data class Country(

    val name: String,
    val topLevelDomain: List<String>,
    val alpha2Code: String,
    val alpha3Code: String,
    val callingCodes: List<String>,
    val capital: String,
    val altSpellings: List<String>,
    val region: String,
    val subregion: String,
    val population: Int,
    val latlng: List<Double>,
    val demonym: String,
    val area: Double,
    val gini: Double,
    val timezones: List<String>,
    val borders: List<String>,
    val nativeName: String,
    val numericCode: String,
    val currencies: List<Currency>,
    val languages: List<Language>,
    //TODO (pvalkov) figure out how to map unknown key to value
//    "translations":{
//    "de":"Afghanistan",
//    "es":"Afganistán",
//    "fr":"Afghanistan",
//    "ja":"アフガニスタン",
//    "it":"Afghanistan",
//    "br":"Afeganistão",
//    "pt":"Afeganistão",
//    "nl":"Afghanistan",
//    "hr":"Afganistan",
//    "fa":"افغانستان"
//},
    val flag: String,
    val regionalBlocs: List<RegionalBlocs>,
    val cioc: String

) : BaseObservable() {

    @Bindable
    fun getCallingCodesText(): String = getCallingCodesString()


    @Bindable
    fun getLanguagesText(): String = getLanguagesString()

    @Bindable
    fun getCurrenciesText(): String = getCurrenciesString()

    @Bindable
    fun getRegionAndSubRegionText(): String = getRegionAndSubRegionString()

    @Bindable
    fun getTimeZonesText(): String = getTimeZonesString()

    @Bindable
    fun getAreaText(): String = NumberFormat.getInstance().format(area)


    @Bindable
    fun getGiniText(): String = this.gini.toString()

    @Bindable
    fun getPopulationText(): String = getPopulationString()

}


data class Currency(val code: String, val name: String, val symbol: String)

data class RegionalBlocs(
    val acronym: String,
    val name: String,
    val otherAcronyms: List<String>,
    val otherNames: List<String>
)


data class Language(val iso639_1: String, val iso639_2: String, val name: String, val nativeName: String)