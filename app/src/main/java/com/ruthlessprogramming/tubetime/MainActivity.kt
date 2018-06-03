package com.ruthlessprogramming.tubetime

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.ruthlessprogramming.domain.StopPoint
import com.ruthlessprogramming.tubetime.features.linedetails.LineDetailViewImpl
import com.ruthlessprogramming.tubetime.features.stations.StationsViewImpl
import kotlinx.android.synthetic.main.activity_main.fragmentContainer


class MainActivity : AppCompatActivity(), Navigator {
    override val tagLineDetails: String = "Line_Details_Fragment"
    override val tagStations = "Stations_Fragment"

    override fun navigateToLineDetails(stopPoint: StopPoint, lineId: String) {
        val lineFragment = supportFragmentManager.findFragmentByTag(tagLineDetails)
                ?: LineDetailViewImpl.newInstance(stopPoint, lineId)

        if (!lineFragment.isAdded) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, lineFragment, tagLineDetails)
                .addToBackStack(null).commit()
        }

    }

    override fun navigateToStation() {
        val lineFragment = supportFragmentManager.findFragmentByTag(tagLineDetails)
        if (lineFragment == null) {
            val stationFragment =
                supportFragmentManager.findFragmentByTag(tagStations) ?: StationsViewImpl()

            if (!stationFragment.isAdded) {
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragmentContainer,
                    stationFragment,
                    tagStations
                ).commit()
            }
        } // we were recreated, stay on line details


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (permissionCheck()) {
            navigateToStation()
        } else {
            makePermissionRequest()
        }

    }

    private fun makePermissionRequest() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            permissionRequestLocation
        )
    }


    private fun permissionCheck(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }


    private val permissionRequestLocation = 1555
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            permissionRequestLocation -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    navigateToStation()
                } else {
                    Snackbar.make(
                        fragmentContainer,
                        R.string.error_permission_denied,
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                return
            }
        }
    }

}

interface Navigator {
    val tagLineDetails: String
    val tagStations: String
    fun navigateToLineDetails(stopPoint: StopPoint, lineId: String)
    fun navigateToStation()
}
