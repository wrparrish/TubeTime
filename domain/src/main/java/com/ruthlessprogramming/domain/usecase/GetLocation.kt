package com.ruthlessprogramming.domain.usecase

import com.ruthlessprogramming.domain.Location
import com.ruthlessprogramming.domain.datasource.LocationDataSource
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetLocation @Inject constructor(private val locationDataSource: LocationDataSource) :
    UseCase<GetLocation.Request, GetLocation.Response> {

    override fun execute(request: Request): Observable<Response> {
        return locationDataSource.getLocation()
            .map { Response(it) }
            .subscribeOn(Schedulers.io())
    }

    object Request : UseCase.Request
    class Response(val resolvedLocation: Location) : UseCase.Response
}