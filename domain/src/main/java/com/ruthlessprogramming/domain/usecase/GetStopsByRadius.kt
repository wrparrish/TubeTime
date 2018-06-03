package com.ruthlessprogramming.domain.usecase

import com.ruthlessprogramming.domain.StopPoint
import com.ruthlessprogramming.domain.datasource.StopDataSource
import io.reactivex.Observable
import javax.inject.Inject

class GetStopsByRadius @Inject constructor(private val dataSource: StopDataSource) :
    UseCase<GetStopsByRadius.Request, GetStopsByRadius.Response> {

    override fun execute(request: Request): Observable<Response> {
        return dataSource.getStopsByParams(request.params)
            .map { stops -> Response(stops) }

    }


    class Request(val params: StopParams) :
        UseCase.Request

    class Response(val result: List<StopPoint>) :
        UseCase.Response
}