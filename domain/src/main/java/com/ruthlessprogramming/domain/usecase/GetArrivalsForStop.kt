package com.ruthlessprogramming.domain.usecase

import com.ruthlessprogramming.domain.Arrival
import com.ruthlessprogramming.domain.datasource.StopDataSource
import io.reactivex.Observable
import javax.inject.Inject

class GetArrivalsForStop @Inject constructor(private val api: StopDataSource) :
    UseCase<GetArrivalsForStop.Request, GetArrivalsForStop.Response> {

    override fun execute(request: Request): Observable<Response> {
        return api.getArrivalsByStopId(request.stopId)
            .map { Response((it)) }
    }

    class Request(val stopId: String) : UseCase.Request
    class Response(val result: List<Arrival>) : UseCase.Response
}