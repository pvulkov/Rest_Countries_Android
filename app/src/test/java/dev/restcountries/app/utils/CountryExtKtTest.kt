package dev.restcountries.app.utils

import dev.restcountries.app.model.*
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*


class CountryExtKtTest {

    private lateinit var country: Country
    private lateinit var basicCountryInfo: BasicCountryInfo


    @Before
    fun setUp() {

        basicCountryInfo = genBasicCountryInfo()
        country = genDetailedCountryInfo()
    }

    @Test
    fun generateFlagImageUrl() {
        val imageUrl = basicCountryInfo.generateFlagImageUrl()
        assertEquals(imageUrl, "https://www.geonames.org/flags/x/ai.gif")
    }

    @Test
    fun getCallingCodesString() {
        val text = country.getCallingCodesText()
        assertEquals(text, "355, 356")
    }

    @Test
    fun getLanguagesString() {
        val text = country.getLanguagesString()
        assertEquals(text, "Albanian (Shqip) ")
    }

    @Test
    fun getCurrenciesString() {
        val text = country.getCurrenciesString()
        assertEquals(text, "Albanian lek (L) ")
    }

    @Test
    fun getRegionAndSubRegionString() {
        val text = country.getRegionAndSubRegionString()
        assertEquals(text, "Europe/Europe")
    }

    @Test
    fun getTimeZonesString() {
        val text = country.getTimeZonesString()
        assertEquals(text, "UTC: +01:00")
    }

    @Test
    fun getPopulationString() {
        val text = country.getPopulationString()
        assertEquals(text, "2,886,026")
    }

    @Test
    fun getExtraInfoString() {

        val empty = basicCountryInfo.getExtraInfoString(CountrySortType.NAME)
        val area = basicCountryInfo.getExtraInfoString(CountrySortType.AREA)
        val population = basicCountryInfo.getExtraInfoString(CountrySortType.POPULATION)

        assertEquals(empty, "")
        assertEquals(area, "91")
        assertEquals(population, "13,452")

    }


    private fun genBasicCountryInfo() = BasicCountryInfo(
        "Anguilla",
        "AI",
        13452,
        91.0,
        "Anguilla"
    )

    private fun genDetailedCountryInfo() = Country(
        "Albania",
        listOf(""),
        "AL",
        "AB",
        listOf("355", "356"),
        "Tirana",
        listOf(""),
        "Europe",
        "Southern Europe",
        2886026,
        listOf(41.0, 20.0),
        "Albanian",
        28748.0,
        34.5,
        listOf("UTC+01:00"),
        listOf(""),
        "",
        "",
        listOf(Currency("ALL", "Albanian lek", "L")),
        listOf(Language("sq", "sqi", "Albanian", "Shqip")),
        "https://restcountries.eu/data/alb.svg",
        listOf(),
        "ALB"
    )
}