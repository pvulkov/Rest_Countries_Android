package dev.restcountries.app

import android.app.Application
import android.content.Context
import androidx.annotation.StringRes
import dev.restcountries.app.model.Country
import dev.restcountries.app.ui.CountryDetailsPresenter
import org.junit.Assert
import org.junit.Before
import org.junit.ClassRule
import org.junit.Test
import org.mockito.Mockito

//NOTE (pvalkov) Integration test
class CountryDetailsPresenterTest {

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }


    private val alpha2Code = "af"
    private val view = StatsDetailsCountryView()
    private val presenter: CountryDetailsPresenter = CountryDetailsPresenter(alpha2Code, view)


    @Before
    fun setUp() {
        view.resetStats()
    }

    @Test
    fun loadCountryDetails_success() {
        presenter.fetchData()

        Assert.assertTrue("dataUpdated", view.dataUpdated)
        Assert.assertNotNull("country not null", view.country)
        Assert.assertEquals("check country name", view.country?.name, "Afghanistan")
        Assert.assertTrue("showLoadingCounter", view.loadingShown)
        Assert.assertFalse("hideLoadingCounter", view.isError)
    }

    @Test
    fun loadCountryDetails_fail() {
        view.resetStats()
        CountryDetailsPresenter("ff", view).fetchData()

        Assert.assertFalse("dataUpdated", view.dataUpdated)
        Assert.assertNull("country not null", view.country)
        Assert.assertNotEquals ("check country name", view.country?.name, "Afghanistan")
        Assert.assertTrue("showLoadingCounter", view.loadingShown)
        Assert.assertTrue("hideLoadingCounter", view.isError)
    }


    private class StatsDetailsCountryView : CountryDetailsPresenter.CountriesDetailView {

        var dataUpdated = false
        var country: Country? = null
        var loadingShown = false
        var isError = false


        fun resetStats() {
            dataUpdated = false
            country = null
            loadingShown = false
            isError = false
        }

        override fun updateData(country: Country) {
            this.country = country
            dataUpdated = true
        }


        override fun showLoading(show: Boolean) {
            loadingShown = true
        }

        override fun showError(@StringRes resId: Int) {
            isError = true
        }


        override fun getContext(): Context {
            return Mockito.mock(Application::class.java)
        }
    }
}