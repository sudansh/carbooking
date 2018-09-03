package com.sudansh.carbooking.ui

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.sudansh.carbooking.R


class MapRenderer<T : ClusterItem>(mContext: Context, map: GoogleMap?, mClusterManager: ClusterManager<T>?) :
        DefaultClusterRenderer<T>(mContext, map, mClusterManager) {

    override fun onBeforeClusterItemRendered(item: T?, markerOptions: MarkerOptions?) {
        markerOptions?.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
    }

    override fun shouldRenderAsCluster(cluster: Cluster<T>?): Boolean {
        // Never render clusters.
        return false
    }
}