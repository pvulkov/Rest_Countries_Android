package dev.restcountries.app.injection.component

import dev.restcountries.app.base.BaseView
import dev.restcountries.app.injection.module.ContextModule
import dev.restcountries.app.injection.module.NetworkModule
import dev.restcountries.app.ui.CountriesGridPresenter
import dagger.BindsInstance
import dagger.Component
import dev.restcountries.app.ui.CountryDetailsPresenter
import javax.inject.Singleton


/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [(ContextModule::class), (NetworkModule::class)])
interface PresenterInjector {

    /**
     * Injects required dependencies into the specified Presenter.
     * @param presenter Presenter into which to inject the dependencies
     */
    fun inject(presenter: CountriesGridPresenter)

    fun inject(presenter: CountryDetailsPresenter)

    @Component.Builder
    interface Builder {
        fun build(): PresenterInjector

        fun networkModule(networkModule: NetworkModule): Builder
        fun contextModule(contextModule: ContextModule): Builder

        @BindsInstance
        fun baseView(baseView: BaseView): Builder
    }
}