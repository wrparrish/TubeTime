package com.ruthlessprogramming.data.services

import com.ruthlessprogramming.data.ArrivalDataModel
import com.ruthlessprogramming.data.LineDetailsDataModel
import com.ruthlessprogramming.data.StopsByRadiusResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val tubeStopType = "NaptanMetroStation"
private const val tubeMode = "tube"
private const val radiusMeters = 1_000
private const val shouldReturnLines = true

interface StopService {

    @GET("StopPoint")
    fun getStopsByRadius(
        @Query("stopTypes") stopType: String = tubeStopType,
        @Query("modes") modes: String = tubeMode,
        @Query("radius") radius: Int = radiusMeters,
        @Query("returnLines") returnLines: Boolean = shouldReturnLines,
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double
    ): Observable<StopsByRadiusResponse>


    @GET("StopPoint/{stopId}/Arrivals")
    fun getArrivalsForStop(
        @Path("stopId") stopId: String
    ): Observable<List<ArrivalDataModel>>

    @GET("Line/{lineId}/Route/Sequence/outbound")
    fun getLineDetails(@Path("lineId") lineId: String): Observable<LineDetailsDataModel>

}