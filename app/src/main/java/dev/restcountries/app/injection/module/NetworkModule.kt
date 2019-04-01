package dev.restcountries.app.injection.module

import dagger.Module
import dagger.Provides
import dagger.Reusable
import dev.restcountries.app.network.RestCountriesApi
import dev.restcountries.app.utils.COUNTRIES_BASE_URL
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Module providing network dependencies
 */
@Module(includes = [(ContextModule::class)])
object NetworkModule {


    /**
     * Provides the countries service implementation.
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the countries service implementation.
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideCountriesApi(retrofit: Retrofit): RestCountriesApi {
        return retrofit.create(RestCountriesApi::class.java)
    }


    /**
     * Provides the Retrofit object.
     * @return the Retrofit object
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofitInterface(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(COUNTRIES_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }

}