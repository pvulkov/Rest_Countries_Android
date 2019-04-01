package dev.restcountries.app.ui

import android.content.Intent
import android.view.MenuItem
import androidx.annotation.StringRes
import dev.restcountries.app.R
import dev.restcountries.app.base.BasePresenter
import dev.restcountries.app.base.BaseView
import dev.restcountries.app.model.BasicCountryInfo
import dev.restcountries.app.utils.CountrySortType
import dev.restcountries.app.utils.setData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CountriesGridPresenter(view: CountriesGridView) : BasePresenter<CountriesGridPresenter.CountriesGridView>(view) {


    private var sortType = CountrySortType.NAME
    private var disposable = CompositeDisposable()
    private val countries = mutableListOf<BasicCountryInfo>()

    override fun onViewCreated() {
        fetchData()
    }

    fun fetchData() {

        val d = countriesApi.getCountries()
            .map { unsorted -> unsorted.sortedWith(sortType.comparator) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { view.showLoading() }
            .subscribe(
                { countriesDataReceived(it) },
                { countriesDataFailed(it) }
            )

        disposable.add(d)
    }

    override fun onViewDestroyed() {
        disposable.dispose()
    }

    fun onCountryClicked(country: BasicCountryInfo) {
        Intent(view.getContext(), CountryDetailsActivity::class.java).apply {
            putExtra(CountryDetailsActivity.COUNTRY_TAG, country)
            view.getContext().startActivity(this)
        }
    }

    fun onOptionsItemSelected(item: MenuItem) {

        when (item.itemId) {
            R.id.menu_order_by_name -> CountrySortType.NAME
            R.id.menu_order_by_population -> CountrySortType.POPULATION
            R.id.menu_order_by_area -> CountrySortType.AREA
            else -> return /* NOTE (pvalkov) not interested in handling any other actions atm*/

        }.apply {
            if (sortType == this)
                return

            sortType = this
            val newList = countries.sortedWith(sortType.comparator)
            countries.setData(newList)
            view.updateData(countries, sortType)
        }
    }


    private fun countriesDataFailed(throwable: Throwable) {

        throwable.printStackTrace()

        view.apply {
            showLoading(false)
            showError(R.string.ouch_error)
        }
    }


    private fun countriesDataReceived(countriesList: List<BasicCountryInfo>) {

        countries.setData(countriesList)

        view.apply {
            showLoading(false)
            updateData(countries)
        }
    }


    interface CountriesGridView : BaseView {

        fun updateData(countriesList: List<BasicCountryInfo>, sortType: CountrySortType? = CountrySortType.NAME)

        fun showLoading(show: Boolean = true)

        fun showError(@StringRes resId: Int)
    }
}