package dev.restcountries.app.base

import dev.restcountries.app.injection.component.DaggerPresenterInjector
import dev.restcountries.app.injection.component.PresenterInjector
import dev.restcountries.app.injection.module.ContextModule
import dev.restcountries.app.injection.module.NetworkModule
import dev.restcountries.app.network.RestCountriesApi
import dev.restcountries.app.ui.CountriesGridPresenter
import dev.restcountries.app.ui.CountryDetailsPresenter
import javax.inject.Inject

/**
 * Base presenter any presenter of the application must extend. It provides initial injections and
 * required methods.
 */
abstract class BasePresenter<out V : BaseView>(protected val view: V) {
    private val injector: PresenterInjector = DaggerPresenterInjector
        .builder()
        .baseView(view)
        .contextModule(ContextModule)
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    @Inject
    protected lateinit var countriesApi: RestCountriesApi


    open fun onViewCreated() {}

    /**
     * Called when the presenter view is destroyed
     */
    open fun onViewDestroyed() {}


    private fun inject() {
        when (this) {
            is CountriesGridPresenter -> injector.inject(this)
            is CountryDetailsPresenter -> injector.inject(this)
        }
    }
}