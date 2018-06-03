package com.ruthlessprogramming.data.datasources

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationRequest
import com.patloew.rxlocation.RxLocation
import com.ruthlessprogramming.domain.Location
import com.ruthlessprogramming.domain.datasource.LocationDataSource
import io.reactivex.Observable
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject


const val dummyLat = 51.5076555
const val dummyLong = -0.1077266
const val localityToCheck = "London"

class LocationDataSourceImpl @Inject constructor(context: Context) : LocationDataSource {
    private val rxLocation: RxLocation = RxLocation(context)

    @SuppressLint("MissingPermission")
    override fun getLocation(): Observable<Location> {
        val locationRequest = LocationRequest.create()
            .setNumUpdates(1)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)

        return rxLocation.location().updates(locationRequest)
            .take(1)
            .map {
                val location = Location(it.latitude, it.longitude)
                checkIfValid(location)
            }

    }

    private fun checkIfValid(location: Location): Location {
        val address = rxLocation.geocoding().fromLocation(location.lat, location.lon).blockingGet()
        return if (address.locality == localityToCheck) location else Location(dummyLat, dummyLong)
    }
}