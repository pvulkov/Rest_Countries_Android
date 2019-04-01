package dev.restcountries.app.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.restcountries.app.R
import dev.restcountries.app.base.BaseActivity
import dev.restcountries.app.databinding.ActivityCountriesGridBinding
import dev.restcountries.app.databinding.CountryViewBinding
import dev.restcountries.app.model.BasicCountryInfo
import dev.restcountries.app.utils.CountrySortType
import dev.restcountries.app.utils.generateFlagImageUrl
import dev.restcountries.app.utils.getExtraInfoString
import dev.restcountries.app.utils.showToast


class CountriesGridActivity : BaseActivity<CountriesGridPresenter>(), CountriesGridPresenter.CountriesGridView,
    OnCountrySelectedListener {

    private val countriesAdapter = CountriesAdapter(this, this)
    private lateinit var binding: ActivityCountriesGridBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_countries_grid)
        binding.apply {
            setSupportActionBar(toolbar)

            adapter = countriesAdapter
            countriesView.layoutManager = GridLayoutManager(this@CountriesGridActivity, 2)
            swipeLayout.setOnRefreshListener { presenter.fetchData() }
        }
        presenter.onViewCreated()
    }


    override fun onCountrySelected(country: BasicCountryInfo) {
        presenter.onCountryClicked(country)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }

    override fun instantiatePresenter(): CountriesGridPresenter {
        return CountriesGridPresenter(this)
    }

    override fun updateData(countriesList: List<BasicCountryInfo>, sortType: CountrySortType?) {
        countriesAdapter.updateCountries(countriesList, sortType)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.country_sort_actions, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        presenter.onOptionsItemSelected(item)

        return super.onOptionsItemSelected(item)
    }

    override fun showError(@StringRes resId: Int) {
        showToast(resId)
    }

    override fun showLoading(show: Boolean) {
        binding.swipeLayout.isRefreshing = show
    }


    class CountriesAdapter(
        private val context: Context,
        private val countrySelectedListener: OnCountrySelectedListener
    ) : RecyclerView.Adapter<CountriesAdapter.CountryViewHolder>() {

        private var sortType: CountrySortType = CountrySortType.NAME
        private var countriesList: List<BasicCountryInfo> = listOf()


        fun updateCountries(countriesList: List<BasicCountryInfo>, sortType: CountrySortType?) {

            sortType?.let { this.sortType = it }
            this.countriesList = countriesList

            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
            val layoutInflater = LayoutInflater.from(context)
            val binding: CountryViewBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.country_view, parent, false)
            return CountryViewHolder(binding, countrySelectedListener)
        }

        override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
            holder.bind(countriesList[position], sortType)
        }

        override fun getItemCount(): Int {
            return countriesList.size
        }


        class CountryViewHolder(
            private val binding: CountryViewBinding,
            private val countrySelectedListener: OnCountrySelectedListener
        ) : RecyclerView.ViewHolder(binding.root) {

            fun bind(country: BasicCountryInfo, sortType: CountrySortType) {
                binding.apply {
                    this.country = country

                    countryView.setOnClickListener { countrySelectedListener.onCountrySelected(country) }
                    binding.countryExtraInfoIcon.setImageResource(sortType.resId)
                    binding.countryExtraInfoValue.text = country.getExtraInfoString(sortType)

                    Picasso.get().load(country.generateFlagImageUrl()).into(binding.countryFlag)

                    binding.executePendingBindings()
                }
            }
        }
    }

}


interface OnCountrySelectedListener {
    fun onCountrySelected(country: BasicCountryInfo)
}