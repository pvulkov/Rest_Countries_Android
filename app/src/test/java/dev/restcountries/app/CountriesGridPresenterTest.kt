package dev.restcountries.app

import android.app.Application
import android.content.Context
import androidx.annotation.StringRes
import dev.restcountries.app.model.BasicCountryInfo
import dev.restcountries.app.ui.CountriesGridPresenter
import dev.restcountries.app.utils.CountrySortType
import org.junit.Assert
import org.junit.Before
import org.junit.ClassRule
import org.junit.Test
import org.mockito.Mockito


//NOTE (pvalkov) Integration test
class CountriesGridPresenterTest {

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }


    private val view = StatsCountryView()
    private val presenter: CountriesGridPresenter = CountriesGridPresenter(view)


    @Before
    fun setUp() {
        view.resetStats()
    }



    @Test
    fun loadCountries() {
        presenter.fetchData()
        Assert.assertTrue("dataUpdated", view.dataUpdated)
        Assert.assertEquals("countriesSize", 250, view.countriesSize)
        Assert.assertTrue("loadingShown", view.loadingShown)
        Assert.assertFalse("isError", view.isError)
    }


    private class StatsCountryView : CountriesGridPresenter.CountriesGridView {

        var dataUpdated = false
        var countriesSize = 0
        var loadingShown = false
        var isError = false


        fun resetStats() {
            dataUpdated = false
            countriesSize = 0
            loadingShown = false
            isError = false
        }

        override fun updateData(countriesList: List<BasicCountryInfo>, sortType: CountrySortType?) {
            countriesSize = countriesList.size
            dataUpdated = true
        }

        override fun showError(@StringRes resId: Int) {
            isError = true
        }

        override fun showLoading(show: Boolean) {
            loadingShown = true
        }


        override fun getContext(): Context {
            return Mockito.mock(Application::class.java)
        }


    }
}