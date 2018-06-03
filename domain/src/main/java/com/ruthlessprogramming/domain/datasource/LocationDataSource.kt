package com.ruthlessprogramming.domain.datasource

import com.ruthlessprogramming.domain.Location
import io.reactivex.Observable

interface LocationDataSource {
    fun getLocation(): Observable<Location>
}