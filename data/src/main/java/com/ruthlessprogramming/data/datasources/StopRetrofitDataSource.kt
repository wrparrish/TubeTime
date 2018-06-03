package com.ruthlessprogramming.data.datasources

import com.ruthlessprogramming.data.services.StopService
import com.ruthlessprogramming.data.toDomain
import com.ruthlessprogramming.domain.Arrival
import com.ruthlessprogramming.domain.LineDetails
import com.ruthlessprogramming.domain.StopPoint
import com.ruthlessprogramming.domain.datasource.StopDataSource
import com.ruthlessprogramming.domain.usecase.StopParams
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class StopRetrofitDataSource @Inject constructor(private val api: StopService) : StopDataSource {
    override fun getLineDetails(lineId: String): Observable<LineDetails> {
        return api.getLineDetails(lineId)
            .map { it.toDomain() }
    }

    override fun getArrivalsByStopId(stopId: String): Observable<List<Arrival>> {
        return api.getArrivalsForStop(stopId)
            .map { it.filter { it.direction == "outbound" }.map { it.toDomain() } }

    }

    override fun getStopsByParams(params: StopParams):  Observable<List<StopPoint>> {
        return when (params) {
             is StopParams.LocationParams -> {
                 getStopsByLocation(params)
             }

            is StopParams.IdParams -> {
                getStopsById(params)
            }

            is StopParams.NameParams -> {
                getStopsByName(params)
            }

         }
    }

    private fun getStopsByName(params: StopParams.NameParams): Observable<List<StopPoint>> {
        return  Observable.empty()
    }

    private fun getStopsById(params: StopParams.IdParams): Observable<List<StopPoint>> {
        return Observable.empty()
    }

    private fun getStopsByLocation(params: StopParams.LocationParams): Observable<List<StopPoint>> {
        return api.getStopsByRadius(latitude = params.latitude, longitude = params.longitude)
            .map { it.stopPoints.map { it.toDomain() } }
    }

    override fun getAll(): Observable<List<StopPoint>> = Observable.empty()

    override fun get(key: StopParams): Observable<StopPoint> = Observable.empty()

    override fun put(key: StopParams, value: StopPoint): Observable<StopPoint> = Observable.empty()
    override fun remove(key: StopParams): Observable<StopPoint> = Observable.empty()

}