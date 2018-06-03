package com.ruthlessprogramming.tubetime.features.stations.state

sealed class LocationPermissionState {
    class Granted() : LocationPermissionState()
    class Denied() : LocationPermissionState()
    class Pending(): LocationPermissionState()
    class NotRequested() : LocationPermissionState()
}