package com.ruthlessprogramming.domain.usecase

import io.reactivex.Observable

interface UseCase<in I : UseCase.Request, O : UseCase.Response> {

    fun execute(request: I): Observable<O>

    interface Request

    interface Response
}