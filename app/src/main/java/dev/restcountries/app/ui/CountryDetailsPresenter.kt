package dev.restcountries.app.ui

import androidx.annotation.StringRes
import dev.restcountries.app.R
import dev.restcountries.app.base.BasePresenter
import dev.restcountries.app.base.BaseView
import dev.restcountries.app.model.Country
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CountryDetailsPresenter(private val alpha2Code: String, view: CountriesDetailView) :
    BasePresenter<CountryDetailsPresenter.CountriesDetailView>(view) {


    private val disposable = CompositeDisposable()

    override fun onViewCreated() {
        fetchData()
    }

    fun fetchData() {

        val d = getCountryInfo(alpha2Code)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { view.showLoading() }
            .subscribe(
                { countryDataReceived(it) },
                { countryDataFailed(it) }
            )

        disposable.add(d)
    }

    override fun onViewDestroyed() {
        disposable.dispose()
    }

    private fun countryDataFailed(t: Throwable) {

        t.printStackTrace()

        view.apply {
            showLoading(false)
            showError(R.string.ouch_error)
        }
    }

    private fun countryDataReceived(country: Country) {
        view.apply {
            showLoading(false)
            updateData(country)
        }
    }


    private fun getCountryInfo(alpha2Code: String) = countriesApi.getCountryInfo("rest", "v2", alpha2Code)

    interface CountriesDetailView : BaseView {

        fun updateData(country: Country)

        fun showLoading(show: Boolean = true)

        fun showError(@StringRes resId: Int)
    }
}