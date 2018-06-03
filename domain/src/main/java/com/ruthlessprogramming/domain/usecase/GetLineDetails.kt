package com.ruthlessprogramming.domain.usecase

import com.ruthlessprogramming.domain.LineDetails
import com.ruthlessprogramming.domain.datasource.StopDataSource
import io.reactivex.Observable
import javax.inject.Inject

class GetLineDetails @Inject constructor(val api: StopDataSource) :
    UseCase<GetLineDetails.Request, GetLineDetails.Response> {

    override fun execute(request: Request): Observable<Response> {
        return api.getLineDetails(request.lineId)
            .map { details -> Response(Result.Success(details)) }
            .onErrorReturn { Response(Result.Error(it)) }
    }

    class Request(val lineId: String) : UseCase.Request
    class Response(val result: Result<LineDetails>) : UseCase.Response
}