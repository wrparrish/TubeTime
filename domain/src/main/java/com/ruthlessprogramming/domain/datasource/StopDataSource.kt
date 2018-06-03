package com.ruthlessprogramming.domain.datasource

import com.ruthlessprogramming.domain.Arrival
import com.ruthlessprogramming.domain.LineDetails
import com.ruthlessprogramming.domain.StopPoint
import com.ruthlessprogramming.domain.usecase.StopParams
import io.reactivex.Observable

interface StopDataSource :
    DataSource<StopParams, StopPoint> {

    fun getStopsByParams(params: StopParams): Observable<List<StopPoint>>

    fun getArrivalsByStopId(stopId: String): Observable<List<Arrival>>

    fun getLineDetails(lineId: String): Observable<LineDetails>
}