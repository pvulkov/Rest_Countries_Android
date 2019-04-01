package dev.restcountries.app.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import dev.restcountries.app.R
import dev.restcountries.app.base.BaseActivity
import dev.restcountries.app.databinding.ActivityCountryDetailBinding
import dev.restcountries.app.model.BasicCountryInfo
import dev.restcountries.app.model.Country
import dev.restcountries.app.utils.generateFlagImageUrl
import java.net.URLEncoder


class CountryDetailsActivity : BaseActivity<CountryDetailsPresenter>(), CountryDetailsPresenter.CountriesDetailView,
    OnMapReadyCallback {

    companion object {
        const val COUNTRY_TAG = "countryTag"
    }

    private lateinit var binding: ActivityCountryDetailBinding
    private lateinit var countryInfo: BasicCountryInfo
    private var country: Country? = null
    private lateinit var mMap: GoogleMap


    override fun onCreate(savedInstanceState: Bundle?) {
        countryInfo = intent.getParcelableExtra(COUNTRY_TAG)
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_country_detail)
        binding.apply {
            setSupportActionBar(toolbar)

            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            collapsingToolbar.title = countryInfo.name

            (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).apply {
                getMapAsync(this@CountryDetailsActivity)
                this.view?.isClickable = false
            }


            wikiLink.setOnClickListener { searchWiki() }

            Picasso.get().load(countryInfo.generateFlagImageUrl()).into(countryFlagImage)
            swipeLayout.setOnRefreshListener { presenter.fetchData() }
        }


        presenter.onViewCreated()
    }


    override fun onMapReady(googleMap: GoogleMap) {

        googleMap.apply {
            mMap = this
            mMap.uiSettings.apply {
                isRotateGesturesEnabled = false
                isTiltGesturesEnabled = false
                isScrollGesturesEnabled = false
                isZoomGesturesEnabled = false
            }
            navigateMapToCountry()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }


    override fun instantiatePresenter(): CountryDetailsPresenter {
        return CountryDetailsPresenter(countryInfo.alpha2Code, this)
    }


    override fun updateData(country: Country) {
        this.country = country
        binding.country = country
        navigateMapToCountry()
    }


    override fun showError(@StringRes resId: Int) {
        getContext().getString(resId).apply {
            Toast.makeText(this@CountryDetailsActivity, this, Toast.LENGTH_LONG).show()
        }
    }


    override fun showLoading(show: Boolean) {
        binding.swipeLayout.isRefreshing = show
    }


    private fun navigateMapToCountry() {
        country?.let {

            LatLng(it.latlng[0], it.latlng[1]).apply {
                mMap.addMarker(MarkerOptions().position(this).title(it.name))

                CameraUpdateFactory.newLatLngZoom(this, 5F).apply { mMap.animateCamera(this) }
            }

        }
    }


    private fun searchWiki() {
        country?.let {

            val escapedQuery = URLEncoder.encode("wiki + ${it.name}", "UTF-8")
            val uri = Uri.parse("http://www.google.com/#q=$escapedQuery")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }
}
