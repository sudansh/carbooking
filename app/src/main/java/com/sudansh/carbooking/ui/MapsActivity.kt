package com.sudansh.carbooking.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.RelativeLayout.ALIGN_PARENT_END
import android.widget.RelativeLayout.BELOW
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.ClusterManager
import com.sudansh.carbooking.R
import com.sudansh.carbooking.data.Resource
import com.sudansh.carbooking.data.Status
import com.sudansh.carbooking.repository.entity.CabLive
import com.sudansh.carbooking.repository.response.Availability
import com.sudansh.carbooking.repository.response.AvailabilityResponse
import com.sudansh.carbooking.util.action
import com.sudansh.carbooking.util.observeNonNull
import com.sudansh.carbooking.util.snack
import org.koin.android.architecture.ext.viewModel


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, TabLayout.OnTabSelectedListener {
    private var clusterManager: ClusterManager<ClusterItem>? = null
    private val viewModel by viewModel<MapsViewModel>()
    private lateinit var mMap: GoogleMap
    private val progressBar by lazy {
        ProgressBar(this).apply {
            id = R.id.progress_id
            layoutParams = RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                addRule(ALIGN_PARENT_END)
                addRule(BELOW, R.id.tab_id)
            }
        }
    }
    private val parentView by lazy { RelativeLayout(this) }
    private val tabLayout by lazy {
        TabLayout(this).apply {
            id = R.id.tab_id
            layoutParams = RelativeLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            tabGravity = TabLayout.GRAVITY_FILL
            setBackgroundColor(ContextCompat.getColor(this@MapsActivity, R.color.white))
            addOnTabSelectedListener(this@MapsActivity)
            setupWithViewPager(viewPager)
        }
    }
    private val viewPager by lazy {
        CustomViewPager(this).apply {
            id = R.id.pager_id
            adapter = CarPagerAdapter(this@MapsActivity, this@MapsActivity.supportFragmentManager)
            layoutParams = RelativeLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
                addRule(BELOW, R.id.tab_id)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentView.removeAllViews()
        parentView.id = R.id.activity_parent_view
        constructLayout()
        setContentView(parentView)

        parentView.addView(progressBar)

        viewModel.cabLive.observeNonNull(this) {
            updateLiveUI(it)
        }
        viewModel.availability.observeNonNull(this) {
            updateAvailable(it)
        }
        viewModel.firstTabActive.observeNonNull(this) { isLiveTabActive ->
            if (isLiveTabActive) {
                clusterManager?.clearItems()
                viewModel.getLive(false)
            } else {
                clusterManager?.clearItems()
            }
            clusterManager?.cluster()
        }
    }

    private fun updateAvailable(resource: Resource<AvailabilityResponse>) {
        when (resource.status) {
            Status.SUCCESS -> {
                buidAvailableMakers(resource.data?.data.orEmpty())
                progressBar.visibility = View.GONE
            }
            Status.LOADING -> {
                progressBar.visibility = View.VISIBLE
            }
            Status.ERROR -> {
                parentView.snack("Couldn't fetch!") {
                    action("Retry") { viewModel.availabilityRefresh() }
                }
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun updateLiveUI(resource: Resource<List<CabLive>>) {
        when (resource.status) {
            Status.SUCCESS -> {
                buildMarkers(resource.data.orEmpty())
                progressBar.visibility = View.GONE
            }
            Status.LOADING -> {
                progressBar.visibility = View.VISIBLE
            }
            Status.ERROR -> {
                parentView.snack("Couldn't fetch!") {
                    action("Retry") { viewModel.getLive() }
                }
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun buildMarkers(list: List<CabLive>) {
        val clusterList: List<CabLive> = if (viewModel.hideOnTrip)
            list.filter { it.is_on_trip } else list
        val bounds = LatLngBounds.Builder()
        list.forEach { bounds.include(it.position) }
        clusterManager?.let {
            it.clearItems()
            it.addItems(clusterList)
            it.cluster()
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 30))
        }
    }

    private fun buidAvailableMakers(list: List<Availability>) {
        val bounds = LatLngBounds.Builder()
        list.forEach { bounds.include(it.position) }
        clusterManager?.let {
            it.clearItems()
            it.addItems(list)
            it.cluster()
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 30))
        }
    }

    private fun constructLayout() {
        //Add map
        val frameLayout = FrameLayout(this).apply {
            id = R.id.map
        }
        val supportMapFragment = SupportMapFragment()
        supportFragmentManager.beginTransaction().add(R.id.map, supportMapFragment).commit()
        supportMapFragment.getMapAsync(this)
        parentView.addView(frameLayout)

        //Add tabs
        parentView.addView(tabLayout)
        parentView.addView(viewPager)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        clusterManager = ClusterManager(this, mMap)
        clusterManager?.renderer = MapRenderer(this, mMap, clusterManager)
        viewModel.getLive(false)
        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION)
        } else
            mMap.isMyLocationEnabled = true

    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
        }
    }

    override fun onTabReselected(tab: TabLayout.Tab) {}

    override fun onTabUnselected(tab: TabLayout.Tab) {}

    override fun onTabSelected(tab: TabLayout.Tab) {
        viewModel.setFirstTabActive(tab.text == getString(R.string.tab_title_live))
    }

    companion object {
        const val REQUEST_LOCATION = 1000
    }
}
