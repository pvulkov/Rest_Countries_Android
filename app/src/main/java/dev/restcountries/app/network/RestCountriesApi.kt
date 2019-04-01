package dev.restcountries.app.network

import dev.restcountries.app.model.BasicCountryInfo
import dev.restcountries.app.model.Country
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RestCountriesApi {


    //https://restcountries.eu/rest/v2/all?fields=name;alpha2Code;population;area;nativeName
    @GET("{rest}/{v2}/all")
    fun getCountries(
        @Path("rest") rest: String = "rest",
        @Path("v2") v2: String = "v2",
        @Query("fields") fields: String = "name;alpha2Code;population;area;nativeName"
    ): Single<List<BasicCountryInfo>>


    //https://restcountries.eu/rest/v2/alpha/co
    @GET("{rest}/{v2}/alpha/{alpha2Code}")
    fun getCountryInfo(
        @Path("rest") rest: String = "rest",
        @Path("v2") v2: String = "v2",
        @Path("alpha2Code") alpha2Code: String): Single<Country>
//

}