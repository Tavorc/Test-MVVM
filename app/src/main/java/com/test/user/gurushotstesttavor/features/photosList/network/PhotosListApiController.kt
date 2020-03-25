package com.test.user.gurushotstesttavor.features.photosList.network

import com.test.user.gurushotstesttavor.features.photosList.network.response.PhotosListResponse
import com.test.user.tavorapplication.infrastructure.network.ApiController

import io.reactivex.Observable

class PhotosListApiController : ApiController<PhotosListApi>() {

    override fun getEndpoint(): String {
        return PhotosListConstant.END_POINT
    }

    override fun getApiClass(): Class<PhotosListApi> {
        return PhotosListApi::class.java
    }

    fun getPhotosList(memberId: String, getLikes: Boolean, limit: Int,start: Int) : Observable<PhotosListResponse>{
       return api.getPhotosList(memberId,getLikes,limit,start)
    }
}